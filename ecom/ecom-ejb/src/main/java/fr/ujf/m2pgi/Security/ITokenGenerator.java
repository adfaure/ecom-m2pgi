package fr.ujf.m2pgi.Security;


import javax.ejb.Local;

/**
 * Created by FAURE Adrien on 25/10/15.
 */
@Local
public interface ITokenGenerator {
    /**
     *
     * @return
     */
    String nextSessionId();
}
