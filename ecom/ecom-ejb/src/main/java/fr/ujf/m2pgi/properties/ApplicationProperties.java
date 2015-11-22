package fr.ujf.m2pgi.properties;

import javax.ejb.Stateless;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Stateless
public class ApplicationProperties implements IProperties {

    private Properties properties;

    private boolean loaded;

    public ApplicationProperties() {
        properties = new Properties();
        loaded = false;
    }

    @Override
    public Properties getApplicationProperties() {
        if(!loaded) {
            loadAppLicationProperties();
            loaded = true;
        }
        return properties;
    }

    public void loadAppLicationProperties() {
        String fileName = System.getProperty("jboss.server.config.dir") + "/application.properties";
        File f = new File(fileName);
        try {
            properties.load(new FileInputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
