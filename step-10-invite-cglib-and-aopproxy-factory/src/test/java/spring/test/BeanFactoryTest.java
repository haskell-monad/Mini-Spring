package spring.test;

import org.junit.Test;
import spring.beans.AbstractBeanDefinitionReader;
import spring.beans.factory.AbstractBeanFactory;
import spring.beans.factory.AutowireCapableBeanFactory;
import spring.beans.io.UrlResourceLoader;
import spring.beans.xml.XmlBeanDefinitionReader;
import spring.context.ApplicationContext;
import spring.context.ClassPathXmlApplicationContext;
import spring.service.HelloWorldService;

/**
 * @author limengyu
 * @create 2017/12/29
 */
public class BeanFactoryTest {

    @Test
    public void testLazy() throws Exception {
        //1、读取bean配置
        AbstractBeanDefinitionReader reader =
                new XmlBeanDefinitionReader(new UrlResourceLoader());
        reader.loadBeanDefinitions("mini-ioc.xml");
        //2、初始化BeanFactory并注册bean
        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
        reader.getRegister().forEach((beanName, beanDefinition) -> {
            beanFactory.registerBeanDefinition(beanName, beanDefinition);
        });
        //3、获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }

    @Test
    public void testPreInstance() throws Exception {
        //1、读取bean配置
        AbstractBeanDefinitionReader reader =
                new XmlBeanDefinitionReader(new UrlResourceLoader());
        reader.loadBeanDefinitions("mini-ioc.xml");
        //2、初始化BeanFactory并注册bean
        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
        reader.getRegister().forEach((beanName, beanDefinition) -> {
            beanFactory.registerBeanDefinition(beanName, beanDefinition);
        });
        // 3.初始化bean
        beanFactory.preInstantiateSingletons();
        // 4.获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
    @Test
    public void testApplicationContext() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("mini-ioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) context.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }

    @Test
    public void testBeanPostProcessor() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("mini-ioc-postbeanprocessor.xml");
        HelloWorldService helloWorldService = (HelloWorldService) context.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
