package spring.beans.io;

import java.net.URL;

/**
 * Created by hadoop on 2017-12-30.
 */
public class UrlResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }
}
