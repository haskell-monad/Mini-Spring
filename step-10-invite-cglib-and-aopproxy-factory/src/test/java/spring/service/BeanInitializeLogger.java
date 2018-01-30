package spring.service;


import spring.beans.BeanPostProcessor;

/**
 * 实例化bean后，初始化时会调用该方法
 *
 * @author yihua.huang@dianping.com
 */
public class BeanInitializeLogger implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws Exception {
        System.out.println("Initialize bean " + beanName + " start!");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws Exception {
        System.out.println("Initialize bean " + beanName + " end!");
        return bean;
    }
}
