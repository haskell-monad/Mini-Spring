package spring.beans;

import spring.beans.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现 BeanDefinitionReader 接口的抽象类
 * （未具体实现 loadBeanDefinitions，
 * 而是规范了BeanDefinitionReader 的基本结构）
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    /**
     * 通过String - beanDefinition 键值对保存IoC容器里的类定义。
     */
    private Map<String, BeanDefinition> register;
    /**
     * 保存了类资源加载器,
     * 使用时，只需要向其 loadBeanDefinitions() 传入一个资源地址，
     * 就可以自动调用其类资源加载器，
     * 并把解析到的 BeanDefinition 保存到 registry 中去。
     */
    private ResourceLoader resourceLoader;
    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.register = new HashMap<>(16);
        this.resourceLoader = resourceLoader;
    }
    public Map<String, BeanDefinition> getRegister() {
        return register;
    }
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
