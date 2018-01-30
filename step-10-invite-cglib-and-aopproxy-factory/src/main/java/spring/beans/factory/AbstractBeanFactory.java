package spring.beans.factory;

import spring.beans.BeanDefinition;
import spring.beans.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hadoop on 2017-12-30.
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    /**
     * bean定义的信息和bean的name保存在线程安全的HashMap中
     */
    private Map<String,BeanDefinition> container = new ConcurrentHashMap<>();
    /**
     * 保存完成注册的bean的name
     */
    private final List<String> beanDefinitionNames = new ArrayList<>();
    /**
     * 增加bean处理程序：
     * 例如通过AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()实现AOP的织入
     */
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    @Override
    public Object getBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = container.get(beanName);
        if(beanDefinition == null){
            throw new IllegalArgumentException("no bean named "+beanName+" is defined");
        }
        Object bean = beanDefinition.getBean();
        if(bean == null){
            //装配bean(实例化，并注入属性)
            bean = doCreateBean(beanDefinition);
            //初始化bean,例如：生成相关代理类,用于实现Aop织入
            bean = initializeBean(beanName,bean);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    /**
     * 初始化bean
     * 可在此进行AOP的相关操作：例如生成相关代理类，并返回
     * @param beanName
     * @param bean
     * @return
     * @throws Exception
     */
    private Object initializeBean(String beanName, Object bean) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(beanName, bean);
        }
        // 返回的可能是代理对象
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(beanName,bean);
        }
        return bean;
    }

    /**
     * 装配bean
     * @param beanDefinition
     * @return
     */
    private Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        //实例化bean
        Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        //注入bean属性
        applyPropertyValues(bean,beanDefinition);
        return bean;
    }

    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

    }

    /**
     * 实例化bean
     * @param beanDefinition
     * @return
     */
    private Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 注册bean定义(没有实例化)
     * @param beanName
     */
    public void registerBeanDefinition(String beanName,BeanDefinition beanDefinition){
        container.put(beanName,beanDefinition);
        beanDefinitionNames.add(beanName);
    }

    /**
     * 增加bean处理程序，例如AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()
     */
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 根据类型获取所有bean实例
     * @param type
     * @return
     */
    public List getBeansForType(Class<?> type) throws Exception {
        List<Object> beans = new ArrayList<>();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = container.get(beanName);
            if(type.isAssignableFrom(beanDefinition.getBeanClass())){
                beans.add(this.getBean(beanName));
            }
        }
        return beans;
    }

    /**
     * 预处理bean的定义，将bean的名字提前存好,实现Ioc容器中存储单例bean
     * @throws Exception
     */
    public void preInstantiateSingletons() throws Exception {
        for (String beanDefinitionName : beanDefinitionNames) {
            this.getBean(beanDefinitionName);
        }
    }
}

