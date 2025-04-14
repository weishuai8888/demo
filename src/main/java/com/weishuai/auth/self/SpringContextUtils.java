package com.weishuai.auth.others;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SpringContextUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
	private static ApplicationContext applicationContext;
	private static ConfigurableListableBeanFactory beanFactory;

	@Override
	@SuppressWarnings("NullableProblems")
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringContextUtils.beanFactory = beanFactory;
	}

	@Override
	@SuppressWarnings("NullableProblems")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * 获取{@link ListableBeanFactory}，可能为{@link ConfigurableListableBeanFactory} 或 {@link ApplicationContextAware}
	 *
	 * @return {@link ListableBeanFactory}
	 */
	public static ListableBeanFactory getBeanFactory() {
		final ListableBeanFactory factory = null == beanFactory ? applicationContext : beanFactory;
		if (null == factory) {
			throw new RuntimeException("ConfigurableListableBeanFactory 和 ApplicationContext 均为注入");
		}
		return factory;
	}

	public static ApplicationContext getApplicationContext(){
		check();
		return SpringContextUtils.applicationContext;
	}

	public static Object getBean(String name) {
		return getBeanFactory().getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return getBeanFactory().getBean(name, requiredType);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return getBeanFactory().getBean(requiredType);
	}
	public static <T> T getBeanCatch(Class<T> requiredType) {
		try {
			return SpringContextUtils.getBean(requiredType);
		} catch (BeansException e) {
			return null;
		}
	}
	public static boolean containsBean(String name) {
		return getBeanFactory().containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return getBeanFactory().isSingleton(name);
	}

	public static Class<?> getType(String name) {
		return getBeanFactory().getType(name);
	}

	public static <T> T getBeanNoException(String name, Class<T> beanName) {
		try {
			check();
			return getBeanFactory().getBean(name, beanName);
		} catch (Exception e) {
		}
		return null;
	}
	public static <T> List<T> getBeansByType(Class<T> clazz) {
		Map<String, T> beansOfType = applicationContext.getBeansOfType(clazz);
		return beansOfType.values()
				.stream()
				.collect(Collectors.toList());
	}

	/**
	 * 获取aop代理对象
	 *
	 * @param invoker
	 * @return
	 */
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}

	private static void check() {
		if (SpringContextUtils.applicationContext == null) {
			throw new RuntimeException("ApplicationContextAware初始化未完成，请等待服务启动完成后使用");
		}
	}

}
