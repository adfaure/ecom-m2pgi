package fr.ujf.m2pgi.properties;

import javax.ejb.Local;
import java.util.Properties;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Local
public interface IProperties {

    Properties getApplicationProperties();
}
