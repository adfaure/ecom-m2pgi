package fr.ujf.m2pgi.REST.Security;

import fr.ujf.m2pgi.database.DTO.MemberDTO;

/**
 * Created by FAURE Adrien on 25/10/15.
 */
public class PrincipalUser {

    /**
     *
     */
    private String token;

    /**
     *
     */
    private String group;

    /**
     *
     */
    private MemberDTO user;

    /**
     *
     * @return
     */
    public MemberDTO getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(MemberDTO user) {
        user.setPassword(""); //FIXME I dont want to transfer password to the client (in this way)
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     */
    public String getGroup() {
        return group;
    }

    /**
     *
     * @param group
     */
    public void setGroup(String group) {
        this.group = group;
    }
}
