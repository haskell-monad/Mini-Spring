package spring.test.aop;

import org.junit.Test;
import spring.aop.TargetSource;
import spring.aop.proxy.AdvisedSupport;
import spring.aop.proxy.jdk.JdkDynamicAopProxy;
import spring.context.ApplicationContext;
import spring.context.ClassPathXmlApplicationContext;
import spring.service.HelloWorldService;
import spring.service.HelloWorldServiceImpl;

/**
 * Created by hadoop on 2017-12-31.
 */
public class JdkDynamicAopProxyTest {

    @Test
    public void testInterceptor() throws Exception {
        // --------- helloWorldService without AOP
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("mini-ioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();

        // --------- helloWorldService with AOP
        // 1. 设置被代理对象(Joinpoint)
        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(helloWorldService,
                HelloWorldServiceImpl.class,HelloWorldService.class);
        advisedSupport.setTargetSource(targetSource);
        //2.设置拦截器
        TimerInterceptor timerInterceptor = new TimerInterceptor();
        advisedSupport.setMethodInterceptor(timerInterceptor);

        // 补：由于用户未设置MethodMatcher，所以通过代理还是调用的原方法(JdkDynamicAopProxy中的invoke方法最后
        // 返回method.invoke(...)而不是methodInterceptor.invoke(...) )
        // 3. 创建代理(Proxy)
        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);
        HelloWorldService proxy = (HelloWorldService)jdkDynamicAopProxy.getProxy();

        // 4. 基于AOP的调用
        proxy.helloWorld();
    }
}
