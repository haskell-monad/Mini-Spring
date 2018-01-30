package spring.test.aop;

import org.junit.Assert;
import org.junit.Test;
import spring.aop.aspectj.AspectJExpressionPointcut;
import spring.service.HelloWorldService;
import spring.service.HelloWorldServiceImpl;

/**
 * Created by hadoop on 2017-12-31.
 */
public class AspectJExpressionPointcutTest {

    @Test
    public void testClassFilter() throws Exception {
        String expression = "execution(* spring.service.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getClassFilter().matches(HelloWorldService.class);
        Assert.assertTrue(matches);
    }



    @Test
    public void testMethodInterceptor() throws Exception {
        String expression = "execution(* spring.service.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getMethodMatcher()
                .matches(HelloWorldServiceImpl.class.getDeclaredMethod("helloWorld"), HelloWorldServiceImpl.class);
        Assert.assertTrue(matches);
    }

}
