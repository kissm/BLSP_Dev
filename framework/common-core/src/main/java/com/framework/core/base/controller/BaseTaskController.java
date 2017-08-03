package com.framework.core.base.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BaseTaskController extends BaseController implements BeanFactoryAware {
	
	protected BeanFactory factory;

	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.factory = factory;
		
	}
	
	

}
