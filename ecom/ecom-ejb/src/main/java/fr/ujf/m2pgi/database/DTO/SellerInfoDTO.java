package fr.ujf.m2pgi.database.DTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by FAURE Adrien on 14/11/15.
 */
@XmlRootElement
public class SellerInfoDTO {

    @XmlElement
    private long id;

    @XmlElement
    private String RIB;

    public String getRIB() {
        return RIB;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
