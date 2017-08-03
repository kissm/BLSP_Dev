package com.framework.mybatis.xmlmapper;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import com.framework.mybatis.javamapper.SelectOneByEntitySelectiveMethodGenerator;

/**
 * 
 * @author guoyuhua
 * 
 */
public class SelectOneByEntitySelectiveElementGenerator extends
		AbstractXmlElementGenerator {

	public SelectOneByEntitySelectiveElementGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

		answer.addAttribute(new Attribute(
				"id", SelectOneByEntitySelectiveMethodGenerator.METHOD_NAME)); //$NON-NLS-1$

		String parameterType = introspectedTable.getBaseRecordType();

		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				parameterType));

		answer.addAttribute(new Attribute(
				"resultMap", introspectedTable.getBaseResultMapId())); //$NON-NLS-1$

		context.getCommentGenerator().addComment(answer);

		answer.addElement(new TextElement("select")); //$NON-NLS-1$
//		XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
//		ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
//		ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
//		answer.addElement(ifElement);

		answer.addElement(getBaseColumnListElement()
				);

		StringBuilder sb = new StringBuilder();

		sb.append("from "); //$NON-NLS-1$
		sb.append(introspectedTable
				.getAliasedFullyQualifiedTableNameAtRuntime());
		sb.append(" where ");
		answer.addElement((new TextElement(sb.toString())));

		XmlElement whereTrimElement = new XmlElement("trim"); //$NON-NLS-1$
		//        whereTrimElement.addAttribute(new Attribute("prefix", "(")); //$NON-NLS-1$ //$NON-NLS-2$
		//        whereTrimElement.addAttribute(new Attribute("suffix", ")")); //$NON-NLS-1$ //$NON-NLS-2$
		whereTrimElement.addAttribute(new Attribute("prefixOverrides", "and")); //$NON-NLS-1$ //$NON-NLS-2$
		answer.addElement(whereTrimElement);

		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()
//				.getBaseColumns()
				) {
			

			XmlElement insertNotNullElement = new XmlElement("if"); //$NON-NLS-1$
			sb.setLength(0);
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != null"); //$NON-NLS-1$
			insertNotNullElement.addAttribute(new Attribute(
					"test", sb.toString())); //$NON-NLS-1$

			sb.setLength(0);
			sb.append("and ");
			sb.append(MyBatis3FormattingUtilities
					.getEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities
					.getParameterClause(introspectedColumn));
			insertNotNullElement.addElement(new TextElement(sb.toString()));
			whereTrimElement.addElement(insertNotNullElement);

		}
		sb.setLength(0);
		sb.append(" limit 1");
		whereTrimElement.addElement((new TextElement(sb.toString())));

//		ifElement = new XmlElement("if"); //$NON-NLS-1$
//		ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
//		ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
//		answer.addElement(ifElement);

		parentElement.addElement(answer);

	}
}
