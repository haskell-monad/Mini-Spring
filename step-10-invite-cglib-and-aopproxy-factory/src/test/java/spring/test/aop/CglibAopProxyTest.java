package spring.test.aop;

import org.junit.Test;
import spring.aop.TargetSource;
import spring.aop.proxy.AdvisedSupport;
import spring.aop.proxy.cglib.CglibAopProxy;
import spring.context.ApplicationContext;
import spring.context.ClassPathXmlApplicationContext;
import spring.service.HelloWorldService;
import spring.service.HelloWorldServiceImpl;

/**
 * Created by hadoop on 2017-12-31.
 */
public class CglibAopProxyTest {

    @Test
    public void testInterceptor() throws Exception {
        // --------- helloWorldService without AOP
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("mini-ioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();

        // --------- helloWorldService with AOP
        // 1. 设置被代理对象(Joinpoint)
        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldServiceImpl.class,
                HelloWorldService.class);
        advisedSupport.setTargetSource(targetSource);

        // 2. 设置拦截器(Advice)
        TimerInterceptor timerInterceptor = new TimerInterceptor();
        advisedSupport.setMethodInterceptor(timerInterceptor);

        // 补：没有设置MethodMatcher，所以拦截该类的所有方法
        // 3. 创建代理(Proxy)
        CglibAopProxy cglib2AopProxy = new CglibAopProxy(advisedSupport);
        HelloWorldService helloWorldServiceProxy = (HelloWorldService) cglib2AopProxy.getProxy();

        // 4. 基于AOP的调用
        helloWorldServiceProxy.helloWorld();


    }
}
