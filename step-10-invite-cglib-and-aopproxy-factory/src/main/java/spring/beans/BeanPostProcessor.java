package spring.beans;

/**
 * 用于在bean定义初始化时嵌入相关操作
 * 例如：在 postProcessorAfterInitialization 方法中，
 * 使用动态代理的方式，返回一个对象的代理对象。
 * 解决了在 IoC 容器的何处植入 AOP 的问题。
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(String beanName, Object bean) throws Exception;

    Object postProcessAfterInitialization(String beanName, Object bean) throws Exception;
}
