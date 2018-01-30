package spring.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import spring.aop.BeanFactoryAware;
import spring.aop.TargetSource;
import spring.aop.proxy.cglib.ProxyFactory;
import spring.beans.BeanPostProcessor;
import spring.beans.factory.AbstractBeanFactory;
import spring.beans.factory.BeanFactory;

import java.util.List;

/**
 * 实现了BeanFactoryAware接口：这个接口提供了对 BeanFactory 的感知，这样，尽管它是容器中的一个 Bean，却
 * 可以获取容器 的引用，进而获取容器中所有的切点对象，决定对哪些对象的哪些方法进行代理。
 * 解决了 为哪些对象提供 AOP 的植入 的问题。
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor,BeanFactoryAware{

    private AbstractBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (AbstractBeanFactory)beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws Exception {
        return bean;
    }

    /**
     * 可以去看看AbstractBeanFactory的getBean()的实现
     * bean实例化后要进行初始化操作，会经过这个方法满足条件则生成相关的代理类并返回
     */
    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws Exception {
        if(bean instanceof AspectJExpressionPointcutAdvisor){
            return bean;
        }
        if(bean instanceof MethodInterceptor){
            return bean;
        }
        // 通过getBeansForType方法加载BeanFactory 中所有的 PointcutAdvisor（保证了 PointcutAdvisor 的实例化顺序优于普通 Bean。)
        // AspectJ方式实现织入,这里它会扫描所有Pointcut，并对bean做织入。
        List<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            // 匹配要拦截的类
            // 使用AspectJExpressionPointcut的matches匹配器，判断当前对象是不是要拦截的类的对象。
            if(advisor.getPointcut().getClassFilter().matches(bean.getClass())){
                // ProxyFactory继承了AdvisedSupport，所以内部封装了TargetSource和MethodInterceptor的元数据对象
                ProxyFactory proxyFactory = new ProxyFactory();
                // 设置切点的方法拦截器
                proxyFactory.setMethodInterceptor((MethodInterceptor)advisor.getAdvice());
                // 设置切点的方法匹配器
                // 利用AspectJ表达式进行方法匹配
                // AspectJExpressionPointcutAdvisor里的AspectJExpressionPointcut的getMethodMatcher()方法
                proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                // 是要拦截的类, 生成一个 TargetSource（要拦截的对象和其类型）(被代理对象)
                TargetSource targetSource = new TargetSource();
                targetSource.setTarget(bean);
                targetSource.setTargetClass(bean.getClass());
                targetSource.setInterfaces(bean.getClass().getInterfaces());
                proxyFactory.setTargetSource(targetSource);
                // 交给实现了 AopProxy接口的getProxy方法的ProxyFactory去生成代理对象
                return proxyFactory.getProxy();
            }
        }
        return bean;
    }
}
