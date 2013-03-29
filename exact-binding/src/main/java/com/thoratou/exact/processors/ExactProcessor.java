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

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

@SupportedAnnotationTypes("com.thoratou.exact.annotations.ExactNode")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ExactProcessor extends AbstractProcessor{
	
	private static Logger logger = CustomLogger.getLogger();

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if(!roundEnv.processingOver()){

            HashMap<String, Item> itemMap = new HashMap<String, Item>();
			
			//retrieve all classes with @ExactNode annotation
			for(TypeElement e : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ExactNode.class))){
                try {
                    TypeElement typeElement = (TypeElement) e;

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
                            try {
                                XPathPathExpr xPathPathExpr = parser.parse();

                                Item item = new Item();
                                item.setxPathPathExpr(xPathPathExpr);
                                item.setMethodName(methodName);
                                item.setReturnType(returnType);

                                itemMap.put(className, item);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
			}

            //use all annotation data to generate parsing files
		}
		return false;
	}

}
