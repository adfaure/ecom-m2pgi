package fr.ujf.m2pgi.database.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Seller;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class PhotoDAOImpl extends GeneriqueDAOImpl<Photo> implements IPhotoDAO {

	@Override
	public PhotoDTO getPhotoDTO(Photo photo) {
		PhotoDTO dto = new PhotoDTO();
		dto.setPhotoId(photo.getPhotoID());
		dto.setName(photo.getName());
		dto.setDescription(photo.getDescription());
		dto.setLocation(photo.getLocation());
		dto.setPrice(photo.getPrice());
		if (photo.getAuthor() != null) dto.setSellerID(photo.getAuthor().getMemberID());
		return dto;
	}

	@Override
	public Photo getPhoto(PhotoDTO photo) {
		Photo photoEntity =  new Photo();
		photoEntity.setPhotoID(photo.getPhotoId());
		photoEntity.setName(photo.getName());
		photoEntity.setDescription(photo.getDescription());
		photoEntity.setLocation(photo.getLocation());
		photoEntity.setPrice(photo.getPrice());
		return photoEntity;
	}
	
}
