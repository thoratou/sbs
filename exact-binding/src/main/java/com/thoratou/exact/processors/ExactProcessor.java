package com.thoratou.exact.processors;

import java.lang.reflect.Method;
import java.util.Set;

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
						System.out.println("Exact class : "+clazz.getName());
						
						for(Element innerElement : typeElement.getEnclosedElements()){
							if(innerElement.getKind() == ElementKind.METHOD){
								ExactPath annotation = innerElement.getAnnotation(ExactPath.class);
								if(annotation != null){
									System.out.println("Exact method : "+innerElement.getSimpleName()+" , "+annotation.value());
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
