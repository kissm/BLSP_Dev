package com.framework.mybatis.plugin;

import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import com.framework.mybatis.xmlmapper.InsertSelectiveBatchElementGenerator;
import com.framework.mybatis.xmlmapper.PagedSelectByEntityWithoutBLOBsElementGenerator;
import com.framework.mybatis.xmlmapper.PagedSelectByExampleWithoutBLOBsElementGenerator;
import com.framework.mybatis.xmlmapper.SelectByEntitySelectiveElementGenerator;
import com.framework.mybatis.xmlmapper.SelectByPrimaryKeyListElementGenerator;
import com.framework.mybatis.xmlmapper.SelectOneByEntitySelectiveElementGenerator;

public class MyXMLMapperGenerator extends XMLMapperGenerator {

	@Override
	protected XmlElement getSqlMapElement() {
		XmlElement answer = super.getSqlMapElement();
		addSelectByEntitySelectiveElement(answer);
		addSelectOneByEntitySelectiveElement(answer);
		addInsertSelectiveBatchElement(answer);
		addPagedSelectByEntityWithoutBLOBsElement(answer);
		addPagedSelectByExampleWithoutBLOBsElement(answer);
		addSelectByPrimaryKeyListElement(answer);
		return answer;
	}

	protected void addSelectByEntitySelectiveElement(XmlElement parentElement) {
		AbstractXmlElementGenerator elementGenerator = new SelectByEntitySelectiveElementGenerator();
		initializeAndExecuteGenerator(elementGenerator, parentElement);
	}
	
	protected void addSelectOneByEntitySelectiveElement(XmlElement parentElement) {
		AbstractXmlElementGenerator elementGenerator = new SelectOneByEntitySelectiveElementGenerator();
		initializeAndExecuteGenerator(elementGenerator, parentElement);
	}
	
	
	
	protected void addInsertSelectiveBatchElement(XmlElement parentElement) {
		AbstractXmlElementGenerator elementGenerator = new InsertSelectiveBatchElementGenerator();
		initializeAndExecuteGenerator(elementGenerator, parentElement);
	}
	
	protected void addPagedSelectByEntityWithoutBLOBsElement(XmlElement parentElement) {
		AbstractXmlElementGenerator elementGenerator = new PagedSelectByEntityWithoutBLOBsElementGenerator();
		initializeAndExecuteGenerator(elementGenerator, parentElement);
	}
	
	protected void addPagedSelectByExampleWithoutBLOBsElement(XmlElement parentElement) {
		AbstractXmlElementGenerator elementGenerator = new PagedSelectByExampleWithoutBLOBsElementGenerator();
		initializeAndExecuteGenerator(elementGenerator, parentElement);
	}
	protected void addSelectByPrimaryKeyListElement(XmlElement parentElement) {
		AbstractXmlElementGenerator elementGenerator = new SelectByPrimaryKeyListElementGenerator();
		initializeAndExecuteGenerator(elementGenerator, parentElement);
	}
	

}
