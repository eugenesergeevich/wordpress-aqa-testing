package aqajava.properties;

import lombok.extern.log4j.Log4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Log4j
public class ConfigProperties {

    public static Properties prop = useProperties();

    private static Properties useProperties() {
        String filePath = "src/config.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            log.error("Can't load properties data from file path " + filePath);
            e.printStackTrace();
        }
        return properties;
    }
}
