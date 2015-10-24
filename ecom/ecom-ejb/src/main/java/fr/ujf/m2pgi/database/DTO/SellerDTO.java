package fr.ujf.m2pgi.database.DTO;

import fr.ujf.m2pgi.database.entities.Photo;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by FAURE Adrien on 22/10/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SellerDTO extends MemberDTO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2501469459177868852L;

	/**
	 * 
	 */
	private Collection<Photo> photos;
    
	/**
	 * 
	 */
    @XmlElement
    private String RIB;

    
    public String getRIB() {
        return RIB;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    public Collection<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<Photo> photos) {
        this.photos = photos;
    }
}
