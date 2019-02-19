package com.cn.clare.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtil implements ApplicationContextAware{
	
	private static ApplicationContext applicationContextObj;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		applicationContextObj = applicationContext;
	}
	
	public static <T> T getBeans(Class<T> clazz) {
		return applicationContextObj.getBeansOfType(clazz).values().iterator().next();
		
	}

}
