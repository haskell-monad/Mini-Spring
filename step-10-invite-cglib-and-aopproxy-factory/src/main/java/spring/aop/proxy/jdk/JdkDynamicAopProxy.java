package spring.aop.proxy.jdk;

import org.aopalliance.intercept.MethodInvocation;
import spring.aop.proxy.AdvisedSupport;
import spring.aop.MethodMatcher;
import spring.aop.proxy.AbstractAopProxy;
import spring.aop.proxy.ReflectiveMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 一个基于JDK的动态代理
 * 只能针对实现了接口的类生成代理。于是我们就有了基本的织入功能。
 * 注意：实现了InvocationHandler接口,可以通过重写invoke方法进行控制访问
 */
public class JdkDynamicAopProxy extends AbstractAopProxy implements InvocationHandler {

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        super(advisedSupport);
    }

    @Override
    public Object getProxy() {
       return Proxy.newProxyInstance(getClass().getClassLoader(),
                advisedSupport.getTargetSource().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodMatcher methodMatcher = advisedSupport.getMethodMatcher();
        Object result;
        if(methodMatcher != null
                && methodMatcher.matches(method,advisedSupport.getTargetSource().getTargetClass())){
            // 如果方法匹配器存在，且匹配该对象的该方法匹配成功,
            // 则调用用户提供的方法拦截器的invoke方法
            MethodInvocation methodInvocation = new ReflectiveMethodInvocation(
                    advisedSupport.getTargetSource().getTarget(),
                    method,
                    args);
            result = advisedSupport.getMethodInterceptor().invoke(methodInvocation);
        }else{
            // 否则的话还是调用原对象的相关方法
            result = method.invoke(advisedSupport.getTargetSource().getTarget(),args);
        }
        return result;
    }
}
