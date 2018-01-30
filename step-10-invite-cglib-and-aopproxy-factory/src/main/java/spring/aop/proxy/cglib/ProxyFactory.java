package spring.aop.proxy.cglib;

import spring.aop.proxy.AdvisedSupport;
import spring.aop.proxy.AopProxy;

/**
 * Created by hadoop on 2017-12-31.
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy {

    @Override
    public Object getProxy() {
        return this.createAopProxy().getProxy();
    }

    // 动态代理：通过Cglib代理提供代理对象
    public final AopProxy createAopProxy(){
        return new CglibAopProxy(this);
    }
}
