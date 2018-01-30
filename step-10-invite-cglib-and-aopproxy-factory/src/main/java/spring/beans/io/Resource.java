package spring.beans.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 标识一个外部资源。
 * 以 Resource 接口为核心发散出的几个类，
 * 都是用于解决 IoC 容器中的内容从哪里来的问题，
 * 也就是 配置文件从哪里读取、
 * 配置文件如何读取 的问题。
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
