package fr.ujf.m2pgi.database.DTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * Created by FAURE Adrien on 18/11/15.
 */
@XmlRootElement
public class SellerPageDTO {

    @XmlElement
    private long id;

    @XmlElement
    private String content;

    @XmlElement
    protected Collection<FullPhotoDTO> presentedPhotos;

    @XmlElement
    protected String title;

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

    public Collection<FullPhotoDTO> getPresentedPhotos() {
        return presentedPhotos;
    }

    public void setPresentedPhotos(Collection<FullPhotoDTO> presentedPhotos) {
        this.presentedPhotos = presentedPhotos;
    }
}
