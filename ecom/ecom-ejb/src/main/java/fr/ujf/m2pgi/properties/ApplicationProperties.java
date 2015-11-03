package fr.ujf.m2pgi.properties;

import javax.ejb.Stateless;
import java.util.Properties;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Stateless
public class ApplicationProperties implements IProperties {

    private Properties properties;

    public ApplicationProperties() {
        properties = null;
    }

    @Override
    public Properties getApplicationProperties() {
        if(properties == null)
            properties = System.getProperties();
        return properties;
    }
}
