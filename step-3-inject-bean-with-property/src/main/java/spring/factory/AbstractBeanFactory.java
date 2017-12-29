package spring.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import spring.BeanDefinition;

/**
 * @author limengyu
 * @create 2017/12/29
 */
public abstract class AbstractBeanFactory implements BeanFactory{

    private Map<String, BeanDefinition> container = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanName) {
        return container.get(beanName).getBean();
    }

    @Override
    public void registerBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = doCreateBean(beanDefinition);
        beanDefinition.setBean(bean);
        container.put(beanName,beanDefinition);
    }

    /**
     * 初始化Bean
     * @param beanDefinition
     * @return
     */
    public abstract Object doCreateBean(BeanDefinition beanDefinition);
}
