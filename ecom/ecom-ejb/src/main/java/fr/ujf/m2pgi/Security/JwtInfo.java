package fr.ujf.m2pgi.Security;

/**
 * Created by AZOUZI Marwen on 10/12/15.
 */
public class JwtInfo {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String group;

    /**
     *
     * @param user
     */
    public void setUserId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Long getUserId() {
        return id;
    }

    /**
     *
     * @param group
     */
    public void setUserGroup(String group) {
        this.group = group;
    }

    /**
     *
     * @return
     */
    public String getUserGroup() {
        return group;
    }
}
