/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
 * @author Jeff Butler
 * 
 */
public class InsertSelectiveBatchMethodGenerator extends AbstractJavaMapperMethodGenerator {
	public static String METHOD_NAME = "insertSelectiveBatch";

	public InsertSelectiveBatchMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// base type
		FullyQualifiedJavaType importedType = new FullyQualifiedJavaType(
				introspectedTable.getBaseRecordType());
		importedTypes.add(importedType);
		importedTypes.add(new FullyQualifiedJavaType("com.framework.mybatis.annotation.Batch"));
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getIntInstance();
		method.setReturnType(returnType);

		method.setName(METHOD_NAME);
		
		FullyQualifiedJavaType parameterType = FullyQualifiedJavaType
				.getNewListInstance();
		FullyQualifiedJavaType listType;
		if (introspectedTable.getRules().generateBaseRecordClass()) {
			listType = new FullyQualifiedJavaType(
					introspectedTable.getBaseRecordType());
		} else {
			throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
		}

		parameterType.addTypeArgument(listType);
		method.addParameter(new Parameter(parameterType, "records")); //$NON-NLS-1$
		method.addAnnotation("@Batch");

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
