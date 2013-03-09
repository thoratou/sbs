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

import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import com.thoratou.exact.annotations.ExactNode;
import com.thoratou.exact.annotations.ExactPath;

@SupportedAnnotationTypes("com.thoratou.exact.annotations.ExactNode")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ExactProcessor extends AbstractProcessor{
	
	private static Logger logger = CustomLogger.getLogger();

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if(!roundEnv.processingOver()){
			
			//retrieve all classes with @ExactNode annotation
			for(Element e : roundEnv.getElementsAnnotatedWith(ExactNode.class)){
				//verify this is a class
				if(e.getKind() == ElementKind.CLASS){
					try {
						TypeElement typeElement = (TypeElement) e;
						
						Class<?> clazz = Class.forName(typeElement.getQualifiedName().toString());
						logger.info("Exact class : "+clazz.getName());
						
						for(Element innerElement : typeElement.getEnclosedElements()){
							if(innerElement.getKind() == ElementKind.METHOD){
								ExactPath annotation = innerElement.getAnnotation(ExactPath.class);
								if(annotation != null){
									logger.info("Exact method : "+innerElement.getSimpleName()+" , "+annotation.value());
								}
							}
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		return false;
	}

}
