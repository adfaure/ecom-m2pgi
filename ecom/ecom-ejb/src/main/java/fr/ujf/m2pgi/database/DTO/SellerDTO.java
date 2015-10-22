package fr.ujf.m2pgi.database.DTO;

import fr.ujf.m2pgi.database.entities.Photo;

import java.util.Collection;

/**
 * Created by dadou on 22/10/15.
 */
public class SellerDTO extends MemberDTO {

    private Collection<Photo> photos;

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
