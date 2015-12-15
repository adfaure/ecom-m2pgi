package fr.ujf.m2pgi.database.DTO;

import javax.xml.bind.annotation.XmlElement;


/**
 * Created by FAURE Adrien on 14/12/15.
 */
public class PublicSeller {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @XmlElement
    private long memberID;

    /**
     *
     */
    @XmlElement
    private String firstName;

    /**
     *
     */
    @XmlElement
    private String lastName;

    /**
     *
     */
    @XmlElement
    private String login;

    public long getMemberID() {
        return memberID;
    }

    public MemberDTO setMemberID(long memberID) {
        this.memberID = memberID;
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
