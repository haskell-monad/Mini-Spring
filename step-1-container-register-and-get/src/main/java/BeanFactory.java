import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author limengyu
 * @create 2017/12/29
 */
public class BeanFactory {

    private Map<String,BeanDefinition> CONTAINER = new ConcurrentHashMap<>();

    /**
     * 从容器中获取Bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        return CONTAINER.get(beanName).getBean();
    }

    /**
     * 注册Bean
     * @param beanName
     * @param beanDefinition
     */
    public void registerBean(String beanName,BeanDefinition beanDefinition){
        CONTAINER.put(beanName,beanDefinition);
    }
}
