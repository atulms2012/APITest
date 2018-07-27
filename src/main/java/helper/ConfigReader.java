package helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by aatul on 7/26/18.
 */
public class ConfigReader {

    public static final String configFileName = "test.properties";
    public static String getProperty(String key) {
        String value = "";

        String resourceName = configFileName;
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream resourceStream = loader.getResourceAsStream(resourceName);
            props.load(resourceStream);
            value = props.getProperty(key);

        } catch (IOException io) {
            //do nothing for now
        }
        return value;
    }
}

