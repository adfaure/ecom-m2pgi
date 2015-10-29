package fr.ujf.m2pgi.database.Service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Seller;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class PhotoService {

	@EJB
	IPhotoDAO photoDao;
	
	@EJB
	ISellerDAO sellerDao;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PhotoDTO deletePhoto(Long id) {
		Photo photo = photoDao.find(id);
	    if (photo != null) {
	    	photoDao.delete(id);
	    	return photoDao.getPhotoDTO(photo);
	    }
	    return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PhotoDTO getPhotoById(Long id) {
		Photo photoEntity = photoDao.find(id);
		if(photoEntity != null) {
			return photoDao.getPhotoDTO(photoEntity);
		};
		return null;
	}
	
	/**
	 * 
	 * @param photo
	 * @return
	 */
	public PhotoDTO createPhoto(PhotoDTO photo) {
		Seller seller = sellerDao.find(photo.getSellerID());
		if (seller == null) return null;
		Photo photoEntity = photoDao.getPhoto(photo);
		photoEntity.setAuthor(seller);
		return photoDao.getPhotoDTO(photoDao.create(photoEntity));
	}

	public List<PhotoDTO> getAllPhotos() {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getAllPhotos()) {
			result.add(photoDao.getPhotoDTO(photo));
		}
		return result;
	}
	
	public List<PhotoDTO> getUserPhotos(Long id) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getUserPhotos(id)) {
			result.add(photoDao.getPhotoDTO(photo));
		}
		return result;
	}
	
	public List<PhotoDTO> getUserPhotos(String login) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getUserPhotos(login)) {
			result.add(photoDao.getPhotoDTO(photo));
		}
		return result;
	}
}
