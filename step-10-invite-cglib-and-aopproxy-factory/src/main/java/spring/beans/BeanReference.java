package spring.beans;


/**
 * 用于代表property标签的ref属性里的对象
 */
public class BeanReference {

    private final String name;
    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Object getBean() {
        return bean;
    }
}
