package spring.aop.proxy;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * Created by hadoop on 2017-12-31.
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    //目标对象
    protected Object target;
    protected Method method;
    protected Object[] args;

    public ReflectiveMethodInvocation(Object target,Method method,Object[] args){
        this.target = target;
        this.method = method;
        this.args = args;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    /**
     * 调用被拦截对象的方法
     * 如果需要支持拦截器链，可以做出以下修改：
     * 将 proceed() 方法修改为调用代理对象的方法： method.invoke(proxy,args)。
     * 在代理对象的 InvocationHandler 的 invoke 函数中，
     * 查看拦截器列表，如果有拦截器，则调用第一个拦截器并返回，
     * 否则调用原始对象的方法。
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target,args);
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
