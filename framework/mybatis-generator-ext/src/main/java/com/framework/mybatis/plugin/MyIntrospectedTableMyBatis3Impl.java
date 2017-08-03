package com.framework.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;

public class MyIntrospectedTableMyBatis3Impl extends
		IntrospectedTableMyBatis3Impl {


	@Override
	protected void calculateXmlMapperGenerator(
			AbstractJavaClientGenerator javaClientGenerator,
			List<String> warnings, ProgressCallback progressCallback) {
		if(javaClientGenerator instanceof JavaMapperGenerator){
			xmlMapperGenerator = new MyXMLMapperGenerator();
            initializeAbstractGenerator(xmlMapperGenerator, warnings,
                    progressCallback);
		}
		else{
        	super.calculateXmlMapperGenerator(javaClientGenerator, warnings,
    				progressCallback);
        }
	}
	
	
	
	
	
	
	
	

}
