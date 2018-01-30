package spring.aop.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import spring.aop.proxy.AdvisedSupport;
import spring.aop.MethodMatcher;
import spring.aop.proxy.AbstractAopProxy;
import spring.aop.proxy.ReflectiveMethodInvocation;

import java.lang.reflect.Method;

/**
 *  cglib是针对类来实现代理的，他的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但
 *  因为采用的是继承， 所以不能对final修饰的类进行代理。
 */
public class CglibAopProxy extends AbstractAopProxy {

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        super(advisedSupport);
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTargetClass());
        enhancer.setInterfaces(advisedSupport.getTargetSource().getInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
        Object proxy = enhancer.create();
        return proxy;
    }

    /**
     * Cglib拦截器
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor{
        //被代理对象信息
        private AdvisedSupport advisedSupport;
        //用户编写的方法拦截器
        private org.aopalliance.intercept.MethodInterceptor delegateMethodInterceptor;

        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport){
            this.advisedSupport = advisedSupport;
            this.delegateMethodInterceptor = advisedSupport.getMethodInterceptor();
        }

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            //如果advised.getMethodMatcher()为空(编程式的使用aop，例如：Cglib2AopProxyTest.java)，拦截该类的所有方法
            // 如果有方法匹配器(声明式的在xml文件里配置了AOP)并且匹配方法成功就拦截指定的方法
            Object result;
            MethodMatcher methodMatcher = advisedSupport.getMethodMatcher();
            if(methodMatcher != null && methodMatcher.matches(method,advisedSupport.getTargetSource().getTargetClass())){
                //返回用户写的拦截器的invoke方法(用户根据需要在调用proceed方法前后添加相应行为,例如：TimerInterceptor.java)
                result = delegateMethodInterceptor.invoke(new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(),method,args,methodProxy));
            }else{
                // 有AspectJ表达式，但没有匹配该方法，返回通过methodProxy调用原始对象的该方法
                result = new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(),method,args,methodProxy).proceed();
            }
            return result;
        }
    }

    public static class CglibMethodInvocation extends ReflectiveMethodInvocation{

        //方法代理
        private MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] args,MethodProxy methodProxy) {
            super(target, method, args);
            this.methodProxy = methodProxy;
        }

        // 通过methodProxy调用原始对象的方法
        @Override
        public Object proceed() throws Throwable {
            return methodProxy.invoke(this.target,this.args);
        }
    }



}
