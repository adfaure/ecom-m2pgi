package fr.ujf.m2pgi.Security;


import javax.ejb.Local;

/**
 * Created by AZOUZI Marwen on 25/11/15.
 */
public interface IStringDigest {
    /**
     *
     * @return
     */
    String digest(String string);
}
