package fr.ujf.m2pgi.database.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by FAURE Adrien on 25/11/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class OrderSellerDTO implements Serializable {

        private static final long serialVersionUID = 1L;

        @XmlElement
        private Long orderID;

        @XmlElement
        private Collection<PhotoDTO> photos;

        @XmlElement
        private Date dateCreated;

        public Long getOrderID() {
            return orderID;
        }

        public void setOrderID(Long orderID) {
            this.orderID = orderID;
        }

        public Collection<PhotoDTO> getPhotos() {
            return photos;
        }

        public void setPhotos(Collection<PhotoDTO> photos) {
            this.photos = photos;
        }

        public Date getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
        }
}
