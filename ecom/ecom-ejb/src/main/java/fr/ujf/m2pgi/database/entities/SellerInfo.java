package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;

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

    @OneToOne(cascade = {CascadeType.ALL})
    protected SellerPage page;

    @PrePersist
    public void setDefaultSellerPage() {
        if(this.page == null) {
            page = new SellerPage();
            page.setId(getId());
            page.setContent("Page vendeur autogénérée par défaut");
            page.setTitle("Titre de page vendeur");
        }
    }

    public SellerPage getPage() {
        return page;
    }

    public void setPage(SellerPage page) {
        this.page = page;
    }

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
