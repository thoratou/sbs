/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2013 Ratouit Thomas                                    *
 *                                                                           *
 * This program is free software; you can redistribute it and/or modify it   *
 * under the terms of the GNU Lesser General Public License as published by  *
 * the Free Software Foundation; either version 3 of the License, or (at     *
 * your option) any later version.                                           *
 *                                                                           *
 * This program is distributed in the hope that it will be useful, but       *
 * WITHOUT ANY WARRANTY; without even the implied warranty of                *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser   *
 * General Public License for more details.                                  *
 *                                                                           *
 * You should have received a copy of the GNU Lesser General Public License  *
 * along with this program; if not, write to the Free Software Foundation,   *
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA, or go to   *
 * http://www.gnu.org/copyleft/lesser.txt.                                   *
 *****************************************************************************/

package com.thoratou.exact.processors;

import com.thoratou.exact.annotations.ExactNode;
import com.thoratou.exact.annotations.ExactPath;
import com.thoratou.exact.xpath.XPathParser;
import com.thoratou.exact.xpath.ast.XPathPathExpr;
import com.thoratou.exact.xpath.ast.XPathStep;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

@SupportedAnnotationTypes("com.thoratou.exact.annotations.ExactNode")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ExactProcessor extends AbstractProcessor{
	
	private static Logger logger = CustomLogger.getLogger();
    private ProcessingEnvironment processingEnv;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        super.init(processingEnv);
    }

    @Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if(!roundEnv.processingOver()){

            try {
                HashMap<String, ArrayList<Item> > itemMap = new HashMap<String, ArrayList<Item>>();
                readAnnotations(roundEnv, itemMap);

                HashMap<String, List<PathStep> > mergedMap
                        = new HashMap<String, List<PathStep>>();
                mergeClassPaths(itemMap,mergedMap);

                writeSources(mergedMap);
            } catch (Exception e) {
                logger.severe(e.getMessage());
                e.printStackTrace();
            }
		}
		return false;
	}

    private void readAnnotations(RoundEnvironment roundEnv, HashMap<String, ArrayList<Item> > itemMap)
            throws Exception {
        //retrieve all classes with @ExactNode annotation
        for(TypeElement typeElement : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ExactNode.class))){
            Class<?> clazz = Class.forName(typeElement.getQualifiedName().toString());
            String className = clazz.getName();
            logger.info("Exact class : "+clazz.getName());

            for(ExecutableElement methodElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())){
                ExactPath annotation = methodElement.getAnnotation(ExactPath.class);
                if(annotation != null){
                    String methodName = methodElement.getSimpleName().toString();
                    String returnType = methodElement.getReturnType().toString();
                    String xPathString = annotation.value();

                    logger.info("Exact method : "+methodName+" , "+annotation.value()+" , "+returnType);

                    XPathParser parser = new XPathParser(xPathString);
                    XPathPathExpr xPathPathExpr = parser.parse();

                    logger.info("XPath value = "+xPathPathExpr.toString());

                    Item item = new Item();
                    item.setxPathPathExpr(xPathPathExpr);
                    item.setMethodName(methodName);
                    item.setReturnType(returnType);

                    if(itemMap.containsKey(className)){
                        ArrayList<Item> items = itemMap.get(className);
                        items.add(item);
                    }
                    else{
                        ArrayList<Item> items = new ArrayList<Item>();
                        items.add(item);
                        itemMap.put(className,items);
                    }
                }
            }
        }
    }

    private void mergeClassPaths(HashMap<String,ArrayList<Item>> itemMap,
                                 HashMap<String,List<PathStep>> mergedMap) {
        /*
        convert Xpath path map into step-by-step merged representation
        example :

        input :
            { 'com.thoratou.example.SimpleBom' ->
                 [
                    {'toto/titi/text()', 'getTiti()', 'FieldString'},
                    {'toto/tata/@value', 'getTata()', 'FieldString'}
                    {'toto/tata/child', 'getChild()', 'com.thoratou.example.ChildBom'}
                 ]
            }

        output :
            { 'com.thoratou.example.SimpleBom', ->
                 [
                    {CHILD_ELEMENT, 'toto'} ->
                    [
                        {CHILD_ELEMENT, 'titi'} ->
                        [
                            {TEXT, 'getTiti()', 'FieldString'}
                        ],
                        {CHILD_ELEMENT, 'tata'} ->
                        [
                            {ATTRIBUTE, 'value',  'getTata()', 'FieldString'}
                            {CHILD_ELEMENT, 'child'} ->
                            [
                                {BOM, 'getChild()', 'com.thoratou.example.ChildBom'}
                            ]
                        ]
                    ]
                 ]
            }
         */
        
        for(Map.Entry<String, ArrayList<Item>> itemEntry : itemMap.entrySet()){
            //retrieve bom basic data
            String baseClassName = itemEntry.getKey();
            ArrayList<Item> itemList = itemEntry.getValue();

            //initialize new representation for this bom
            List<PathStep> newPathStepList = new ArrayList<PathStep>();
            mergedMap.put(baseClassName, newPathStepList);
            for(Item item : itemList){
                XPathPathExpr xPathPathExpr = item.getxPathPathExpr();
                String methodName = item.getMethodName();
                String returnType = item.getReturnType();
                convertXPathExpr(xPathPathExpr, methodName, returnType, newPathStepList);
            }
        }
    }

    private void convertXPathExpr(XPathPathExpr xPathPathExpr,
                                  String methodName,
                                  String returnType,
                                  List<PathStep> newPathStepList) {
        PathStep rootStep = new PathStep();
        rootStep.setStepKind(PathStep.Kind.START);
        switch (xPathPathExpr.context) {
            case ABS:
                rootStep.setStartKind(PathStep.StartKind.ABSOLUTE);
                break;
            case REL:
                rootStep.setStartKind(PathStep.StartKind.RELATIVE);
                break;
        }
        newPathStepList.add(rootStep);
        convertXPathSteps(xPathPathExpr.steps, methodName, returnType, rootStep.getChildSteps());
    }

    private void convertXPathSteps(XPathStep[] steps,
                                   String methodName,
                                   String returnType,
                                   List<PathStep> newPathStepList) {
        List<PathStep> currentList = newPathStepList;
        for(XPathStep step : steps){
            PathStep pathStep = new PathStep();
            switch (step.axis) {
                case CHILD:
                    switch (step.test) {
                        case NAME:
                            pathStep.setStepKind(PathStep.Kind.CHILD_ELEMENT);
                            pathStep.setStepValue(step.testStr());
                            //logger.info("step : child, "+pathStep.getStepValue());
                            break;
                        case NAME_WILDCARD:
                            break;
                        case NAMESPACE_WILDCARD:
                            break;
                        case TYPE_NODE:
                            break;
                        case TYPE_TEXT:
                            pathStep.setStepKind(PathStep.Kind.TEXT);
                            pathStep.setMethodName(methodName);
                            pathStep.setReturnType(returnType);
                            //logger.info("step : text, "+pathStep.getStepValue());
                            break;
                        case TYPE_COMMENT:
                            break;
                        case TYPE_PROCESSING_INSTRUCTION:
                            break;
                    }
                    break;
                case DESCENDANT:
                    break;
                case PARENT:
                    break;
                case ANCESTOR:
                    break;
                case FOLLOWING_SIBLING:
                    break;
                case PRECEDING_SIBLING:
                    break;
                case FOLLOWING:
                    break;
                case PRECEDING:
                    break;
                case ATTRIBUTE:
                    pathStep.setStepKind(PathStep.Kind.ATTRIBUTE);
                    pathStep.setStepValue(step.testStr());
                    pathStep.setMethodName(methodName);
                    pathStep.setReturnType(returnType);
                    //logger.info("step : attribute, "+pathStep.getStepValue());
                    break;
                case NAMESPACE:
                    break;
                case SELF:
                    break;
                case DESCENDANT_OR_SELF:
                    break;
                case ANCESTOR_OR_SELF:
                    break;
            }
            //TODO : optimisation, find existing step to merge them
            currentList.add(pathStep);
            //move to sub list
            currentList = pathStep.getChildSteps();
        }
    }

    private void writeSources(HashMap<String, List<PathStep>> mergedMap)
            throws IOException {
        VelocityEngine engine = new VelocityEngine();
        engine.init();

        //read file into JAR
        InputStream configStream = getClass().getResourceAsStream("/xmlreader.vm");
        BufferedReader configReader = new BufferedReader(new InputStreamReader(configStream, "UTF-8"));

        //Template template = engine.getTemplate("display.vm");

        //use all annotation data to generate parsing files
        for(Map.Entry<String, List<PathStep>> entryList : mergedMap.entrySet()) {
            String className = entryList.getKey();
            List<PathStep> steps = entryList.getValue();

            VelocityContext context = new VelocityContext();
            context.put("class", className);

            String packageName = null;
            String simpleName = null;
            try {
                Class<?> clazz = Class.forName(className);
                packageName = clazz.getPackage().getName();
                context.put("package", packageName);
                simpleName = clazz.getSimpleName();
                context.put("simpleclass", simpleName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            context.put("steps",steps);
            context.put("kindmap", PathStep.ReverseKindMap);
            context.put("startmap", PathStep.ReverseStartMap);
            context.put("indentutil", new IndentUtil());
            context.put("processingutil", new ProcessingUtil());

            logger.info("input velocity data : "+className+ " , "+steps.toString());

            //StringWriter writer = new StringWriter();
            //String packagePath = packageName.replace(".","/");
            //String fullFile = packagePath+"/"+simpleName+"XmlReader.java";
            //logger.info(fullFile);

            Filer filer = processingEnv.getFiler();
            JavaFileObject sourceFile = filer.createSourceFile(className+"XmlReader");
            Writer sourceWriter = sourceFile.openWriter();

            engine.evaluate(context, sourceWriter, "", configReader);

            sourceWriter.close();

            //logger.info("final velocity data : "+writer.getBuffer().toString());
        }
    }
}
