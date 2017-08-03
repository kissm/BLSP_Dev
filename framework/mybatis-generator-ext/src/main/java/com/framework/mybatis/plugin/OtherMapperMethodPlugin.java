package com.framework.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import com.framework.mybatis.javamapper.InsertSelectiveBatchMethodGenerator;
import com.framework.mybatis.javamapper.PagedSelectByEntityWithoutBLOBsMethodGenerator;
import com.framework.mybatis.javamapper.PagedSelectByExampleWithoutBLOBsMethodGenerator;
import com.framework.mybatis.javamapper.SelectByEntitySelectiveMethodGenerator;
import com.framework.mybatis.javamapper.SelectByPrimaryKeyListMethodGenerator;
import com.framework.mybatis.javamapper.SelectOneByEntitySelectiveMethodGenerator;

public class OtherMapperMethodPlugin extends PluginAdapter {
	private List<String> warnings;

	public boolean validate(List<String> warnings) {
		this.warnings = warnings;
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		addSelectByEntitySelectiveMethod(introspectedTable,interfaze);
		addSelectOneByEntitySelectiveMethod(introspectedTable, interfaze);
		addInsertSelectiveBatchMethod(introspectedTable, interfaze);
		addPagedSelectByExampleMethod(introspectedTable, interfaze);
		addPagedSelectByEntityMethod(introspectedTable, interfaze);
		addSelectByPrimaryKeyListMethod(introspectedTable, interfaze);
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}
	

	protected void addSelectByEntitySelectiveMethod(IntrospectedTable introspectedTable,Interface interfaze) {
            AbstractJavaMapperMethodGenerator methodGenerator = new SelectByEntitySelectiveMethodGenerator();
            initializeAndExecuteGenerator(introspectedTable, methodGenerator, interfaze);
        
    }
	
	protected void addSelectOneByEntitySelectiveMethod(IntrospectedTable introspectedTable,Interface interfaze) {
            AbstractJavaMapperMethodGenerator methodGenerator = new SelectOneByEntitySelectiveMethodGenerator();
            initializeAndExecuteGenerator(introspectedTable, methodGenerator, interfaze);
    }
	
	protected void addInsertSelectiveBatchMethod(IntrospectedTable introspectedTable,Interface interfaze) {
            AbstractJavaMapperMethodGenerator methodGenerator = new InsertSelectiveBatchMethodGenerator();
            initializeAndExecuteGenerator(introspectedTable, methodGenerator, interfaze);
    }
	
	
	

	protected void addPagedSelectByExampleMethod(IntrospectedTable introspectedTable,Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new PagedSelectByExampleWithoutBLOBsMethodGenerator();
        initializeAndExecuteGenerator(introspectedTable, methodGenerator, interfaze);
    }
	
	protected void addPagedSelectByEntityMethod(IntrospectedTable introspectedTable,Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new PagedSelectByEntityWithoutBLOBsMethodGenerator();
        initializeAndExecuteGenerator(introspectedTable, methodGenerator, interfaze);
    }
	
	protected void addSelectByPrimaryKeyListMethod(IntrospectedTable introspectedTable,Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new SelectByPrimaryKeyListMethodGenerator();
        initializeAndExecuteGenerator(introspectedTable, methodGenerator, interfaze);
    }
	
	
	
	
	protected void initializeAndExecuteGenerator(IntrospectedTable introspectedTable,
            AbstractJavaMapperMethodGenerator methodGenerator,
            Interface interfaze) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        //methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }
	

}
