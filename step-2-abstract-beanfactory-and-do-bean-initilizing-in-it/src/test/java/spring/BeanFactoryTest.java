package spring;

import org.junit.Test;
import spring.factory.AutowireCapableBeanFactory;
import spring.factory.BeanFactory;
import spring.model.UserServiceImpl;

/**
 * @author limengyu
 * @create 2017/12/29
 */
public class BeanFactoryTest {

    @Test
    public void getBeanTest(){
        //构建bean容器
        BeanFactory beanFactory = new AutowireCapableBeanFactory();
        //注册bean
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName("spring.model.UserServiceImpl");
        beanFactory.registerBean("user",beanDefinition);
        //获取bean
        UserServiceImpl user = (UserServiceImpl)beanFactory.getBean("user");
        user.say();
    }
}
