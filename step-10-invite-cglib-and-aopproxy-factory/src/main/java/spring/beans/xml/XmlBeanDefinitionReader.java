package spring.beans.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import spring.beans.AbstractBeanDefinitionReader;
import spring.beans.BeanDefinition;
import spring.beans.BeanReference;
import spring.beans.PropertyValue;
import spring.beans.io.ResourceLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * 从 XML 文件中读取bean的定义。具体实现了 loadBeanDefinitions() 方法
 * 注意: 在loadBeanDefinitions中并没有实例化bean，而只是注册了bean的定义
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }
    /**
     * 加载bean定义的同时注册bean
     */
    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        //获取资源输入流
        InputStream inputStream = this.getResourceLoader().getResource(location).getInputStream();
        /**
         * 从xml文件中读取所有bean的定义，并注册到registry中
         * 注意: 此时bean定义里并没有实例化该bean
         */
        doLoadBeanDefinition(inputStream);
    }
    private void doLoadBeanDefinition(InputStream inputStream) throws Exception {
        //获取工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //获取生成器
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        //从输入流中解析出document
        Document document = documentBuilder.parse(inputStream);
        //解析bean定义并注册到其中的bean到register中
        registerBeanDefinitions(document);
        //关闭输入流
        inputStream.close();
    }
    private void registerBeanDefinitions(Document document) {
        //获取根标签<beans>
        Element root = document.getDocumentElement();
        //解析bean定义
        parseBeanDefinitions(root);
    }
    private void parseBeanDefinitions(Element root) {
        //获取<beans>下面所有的<bean>
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                //解析bean标签
                processBeanDefinition((Element) node);
            }
        }
    }
    /**
     * 接续(处理)bean标签
     * @param node
     */
    private void processBeanDefinition(Element node) {
        //获取bean标签的id属性作为bean的名称
        String beanName = node.getAttribute("id");
        //获取bean标签的class属性作为bean的className
        String beanClassName = node.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        //解析bean标签下的property子标签
        processProperty(node, beanDefinition);
        //设置beanClassName的同时，也在内部设置了beanClass
        beanDefinition.setBeanClassName(beanClassName);
        //注册bean定义
        getRegister().put(beanName, beanDefinition);
    }
    /**
     * 解析bean标签的property子标签
     *
     * @param node
     * @param beanDefinition
     */
    private void processProperty(Element node, BeanDefinition beanDefinition) {
        //获取所有的property标签
        NodeList propertyNodes = node.getElementsByTagName("property");
        for (int i = 0; i < propertyNodes.getLength(); i++) {
            Node item = propertyNodes.item(i);
            if (item instanceof Element) {
                Element property = (Element) item;
                String name = property.getAttribute("name");
                String value = property.getAttribute("value");
                if (value != null && value.length() > 0) {
                    //value类型属性值
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else {
                    //否则是ref类型属性值
                    String ref = property.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("<property> element must specify a ref or value");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    //保存一个ref型属性值的属性
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }
}
