package fr.ujf.m2pgi.Security;

import javax.ejb.Stateless;

import java.util.UUID;

/**
 * Created by FAURE Adrien on 25/10/15.
 */
@Stateless
public class TokenGenerator implements ITokenGenerator {

    public String nextSessionId() {
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        return  uuid;
    }

}
