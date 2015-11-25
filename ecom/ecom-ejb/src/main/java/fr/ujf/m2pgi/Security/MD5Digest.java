package fr.ujf.m2pgi.Security;

import javax.ejb.Stateless;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by AZOUZI Marwen on 25/11/15.
 */
@Stateless
public class MD5Digest implements IStringDigest {

    public String digest(String string) {
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
          sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
      }
      catch (NoSuchAlgorithmException e) {
        System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
        return string;
      }
    }

}
