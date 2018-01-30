package spring.beans.factory;

import spring.aop.BeanFactoryAware;
import spring.beans.BeanDefinition;
import spring.beans.BeanReference;
import spring.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 可实现自动装配的BeanFactory
 * Created by hadoop on 2017-12-30.
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 通过反射自动装配bean的属性
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        if(bean instanceof BeanFactoryAware){
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValues().getPropertyValueList();
        for (PropertyValue propertyValue : propertyValueList) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                //如果属性是ref类型,就先实例化那个ref的bean，然后装载到这个value里
                BeanReference beanReference = (BeanReference) value;
                String ref = beanReference.getName();
                value = getBean(ref);
            }
            try {
                //getDeclaredMethod()#set属性方法名称/参数类型
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1),
                        value.getClass()
                );
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                //如果该bean没有setXXX的类似方法，就直接将value设置到相应的属性域内
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }
}
