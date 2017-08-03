package com.framework.mybatis.javamapper;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

/**
 * 
 * @author guoyuhua
 * 
 */
public class PagedSelectByEntityWithoutBLOBsMethodGenerator extends
		AbstractJavaMapperMethodGenerator {

	public static String METHOD_NAME = "pagedSelectByEntity";

	public PagedSelectByEntityWithoutBLOBsMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(
				introspectedTable.getBaseRecordType());
		importedTypes.add(type);
//		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		importedTypes.add(new FullyQualifiedJavaType(
				"com.framework.core.base.page.Page"));

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(
				"com.framework.core.base.page.Page");
		FullyQualifiedJavaType listType;
		if (introspectedTable.getRules().generateBaseRecordClass()) {
			listType = new FullyQualifiedJavaType(
					introspectedTable.getBaseRecordType());
		} else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			listType = new FullyQualifiedJavaType(
					introspectedTable.getPrimaryKeyType());
		} else {
			throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
		}

		importedTypes.add(listType);
		returnType.addTypeArgument(listType);
		method.setReturnType(returnType);

		method.setName(METHOD_NAME);
		method.addParameter(new Parameter(type, "record","@Param(\"record\")")); //$NON-NLS-1$
		method.addParameter(new Parameter(returnType, "page","@Param(\"page\")"));

		context.getCommentGenerator().addGeneralMethodComment(method,
				introspectedTable);

		addMapperAnnotations(interfaze, method);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);

	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
		return;
	}
}
