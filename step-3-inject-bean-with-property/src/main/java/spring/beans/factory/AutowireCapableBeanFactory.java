package spring.beans.factory;

import spring.BeanDefinition;

/**
 * @author limengyu
 * @create 2017/12/29
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory{

    @Override
    public Object doCreateBean(BeanDefinition beanDefinition) {
        try {
            Object bean = beanDefinition.getBeanClass().newInstance();
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
