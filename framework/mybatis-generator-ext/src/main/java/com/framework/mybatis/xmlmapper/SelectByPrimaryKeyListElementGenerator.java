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
package com.framework.mybatis.xmlmapper;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import com.framework.mybatis.javamapper.SelectByPrimaryKeyListMethodGenerator;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class SelectByPrimaryKeyListElementGenerator extends
        AbstractXmlElementGenerator {

    public SelectByPrimaryKeyListElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
		List<IntrospectedColumn> introspectedColumns = introspectedTable
                .getPrimaryKeyColumns();
		if (!introspectedTable.getRules().generatePrimaryKeyClass() &&introspectedColumns.size()>1) {
			return;
		}
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SelectByPrimaryKeyListMethodGenerator.METHOD_NAME)); //$NON-NLS-1$
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }

        String parameterType = "java.util.List";
//      if (introspectedTable.getRules().generatePrimaryKeyClass()) {
//          parameterType = introspectedTable.getPrimaryKeyType();
//      } else {
//              parameterType = introspectedTable.getPrimaryKeyColumns().get(0)
//                      .getFullyQualifiedJavaType().toString();
//          
//      }

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$

        if (stringHasValue(introspectedTable
                .getSelectByPrimaryKeyQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
        }
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());
        if (introspectedTable.hasBLOBColumns()) {
            answer.addElement(new TextElement(",")); //$NON-NLS-1$
            answer.addElement(getBlobColumnListElement());
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        
        
        XmlElement forEachElement = new XmlElement("foreach"); //$NON-NLS-1$
        forEachElement.addAttribute(new Attribute(
                "collection", "list"));
        forEachElement.addAttribute(new Attribute("item", "id")); //$NON-NLS-1$ //$NON-NLS-2$
        forEachElement.addAttribute(new Attribute("separator", "or")); //$NON-NLS-1$ //$NON-NLS-2$
        forEachElement.addAttribute(new Attribute("open", "where ")); 
        forEachElement.addAttribute(new Attribute("close", " ")); 
        
        answer.addElement(forEachElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);

            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); 
            if(introspectedTable.getRules().generatePrimaryKeyClass()){
                sb.append(MyBatis3FormattingUtilities
                        .getParameterClause(introspectedColumn,"id."));
            }else{
            	sb.append(getParameterClause(introspectedColumn,"id"));
            }
            forEachElement.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins()
                .sqlMapSelectByPrimaryKeyElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
    
    public static String getParameterClause(
            IntrospectedColumn introspectedColumn, String paramName) {
        StringBuilder sb = new StringBuilder();

        sb.append("#{"); //$NON-NLS-1$
        sb.append(paramName);
        sb.append(",jdbcType="); //$NON-NLS-1$
        sb.append(introspectedColumn.getJdbcTypeName());

        if (stringHasValue(introspectedColumn.getTypeHandler())) {
            sb.append(",typeHandler="); //$NON-NLS-1$
            sb.append(introspectedColumn.getTypeHandler());
        }

        sb.append('}');

        return sb.toString();
    }
}
