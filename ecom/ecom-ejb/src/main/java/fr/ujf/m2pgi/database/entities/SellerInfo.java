package fr.ujf.m2pgi.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by FAURE Adrien on 14/11/15.
 */
@Entity
@Table(name = "sellerinfo")
public class SellerInfo {

    @Id
    private long id;

    @Column(name = "RIB", nullable = false)
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
