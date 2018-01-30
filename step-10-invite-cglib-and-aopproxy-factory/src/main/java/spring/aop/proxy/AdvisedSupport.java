package spring.aop.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import spring.aop.MethodMatcher;
import spring.aop.TargetSource;

/**
 * Created by hadoop on 2017-12-31.
 */
public class AdvisedSupport {

    //要拦截的对象
    private TargetSource targetSource;
    // 方法匹配器，判断是否是需要拦截的方法
    private MethodMatcher methodMatcher;
    /**
     * 方法拦截器advice
     * Spring的AOP只支持方法级别的调用(JoinPoint)，
     * 所以其实在AopProxy里，我们只需要将MethodInterceptor放入对象的方法调用
     */
    private MethodInterceptor methodInterceptor;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
