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
	public Photo getPhotoById(long id) {
		Query query = entityManager.createQuery("select p FROM Photo p WHERE p.photoID=:id");
		query.setParameter("id", id);
		System.out.println(query.getResultList().size());
		
		@SuppressWarnings("unchecked")
		List<Photo> photos = query.getResultList();
		if (photos != null && photos.size() == 1) {
			return photos.get(0);
		}
		return null;
	}
	
	@Override
	public
	Photo find(Object id)
	{
		return entityManager.find(Photo.class, id);
	}

	@Override
	public PhotoDTO getPhotoDTO(Photo photo) {
		PhotoDTO dto = new PhotoDTO();
		dto.setPhotoId(photo.getPhotoID());
		dto.setName(photo.getName());
		dto.setDescription(photo.getDescription());
		dto.setLocation(photo.getLocation());
		dto.setPrice(photo.getPrice());
		dto.setSellerID(photo.getAuthor().getMemberID());
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
		Seller author = new Seller(); author.setMemberID(photo.getSellerID());
		photoEntity.setAuthor(author);
		return photoEntity;
	}
	
}
