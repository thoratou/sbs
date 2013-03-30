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
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@SupportedAnnotationTypes("com.thoratou.exact.annotations.ExactNode")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ExactProcessor extends AbstractProcessor{
	
	private static Logger logger = CustomLogger.getLogger();

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if(!roundEnv.processingOver()){

            try {
                HashMap<String, ArrayList<Item> > itemMap = new HashMap<String, ArrayList<Item>>();
                readAnnotations(roundEnv, itemMap);

                HashMap<String, HashMap<PathStep, ? extends StepImplementation> > mergedMap
                        = new HashMap<String, HashMap<PathStep, ? extends StepImplementation>>();
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
                                 HashMap<String,HashMap<PathStep, ? extends StepImplementation>> mergedMap) {
        //TODO
    }

    private void writeSources(HashMap<String,HashMap<PathStep, ? extends StepImplementation>> mergedMap)
            throws UnsupportedEncodingException {
        VelocityEngine engine = new VelocityEngine();
        engine.init();

        //read file into JAR
        InputStream configStream = getClass().getResourceAsStream("/display.vm");
        BufferedReader configReader = new BufferedReader(new InputStreamReader(configStream, "UTF-8"));

        //Template template = engine.getTemplate("display.vm");

        //use all annotation data to generate parsing files
        for(Map.Entry<String, HashMap<PathStep, ? extends StepImplementation>> entryList : mergedMap.entrySet()) {
            String className = entryList.getKey();
            HashMap<PathStep, ? extends StepImplementation> steps = entryList.getValue();

            VelocityContext context = new VelocityContext();
            context.put("class", className);

            StringWriter writer = new StringWriter();
            engine.evaluate(context, writer, "", configReader);
            //TODO
        }
    }
}
