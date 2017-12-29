package spring.factory;

import spring.BeanDefinition;

/**
 * @author Administrator
 */
public interface BeanFactory {

    /**
     * 获取bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName);

    /**
     * 注册bean
     * @param beanName  org.apache.kafka.xxx
     * @param beanDefinition
     */
    public void registerBean(String beanName, BeanDefinition beanDefinition);
}
