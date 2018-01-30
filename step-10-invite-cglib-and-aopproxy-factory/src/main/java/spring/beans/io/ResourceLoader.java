package spring.beans.io;

/**
 * Created by hadoop on 2017-12-30.
 */
public interface ResourceLoader {
    /**
     * 获取资源
     * @param location
     * @return
     */
    Resource getResource(String location);
}
