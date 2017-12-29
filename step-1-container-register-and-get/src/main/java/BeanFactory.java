import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器
 * @author limengyu
 * @create 2017/12/29
 */
public class BeanFactory {

    private Map<String,BeanDefinition> container = new ConcurrentHashMap<>();

    /**
     * 从容器中获取Bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        return container.get(beanName).getBean();
    }

    /**
     * 注册Bean
     * @param beanName
     * @param beanDefinition
     */
    public void registerBean(String beanName,BeanDefinition beanDefinition){
        container.put(beanName, beanDefinition);
    }
}
