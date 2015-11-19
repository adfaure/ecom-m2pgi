package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by FAURE Adrien on 18/11/15.
 */
@Entity
public class SellerPage {

    @Id
    private long id;

    @Column(name="title",length = 100,nullable = true)
    private String  title;

    @Column(name="content",length = 1000,nullable = true)
    private String content;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Collection<Photo> presentedPhotos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Collection<Photo> getPresentedPhotos() {
        return presentedPhotos;
    }

    public void setPresentedPhotos(Collection<Photo> presentedPhotos) {
        this.presentedPhotos = presentedPhotos;
    }
}