package fr.ujf.m2pgi.database.Service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.Mappers.IPhotoMapper;
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
	private IPhotoMapper photoMapper;

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
	    	return photoMapper.getDTO(photo);
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
            PhotoDTO dto = photoMapper.getDTO(photoEntity);
			return dto;
		}
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
		Photo photoEntity = photoMapper.getentity(photo);
		photoEntity.setAuthor(seller);
		return photoMapper.getDTO(photoDao.create(photoEntity));
	}

	public List<PhotoDTO> getAllPhotos() {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getAllPhotos()) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}
	
	public List<PhotoDTO> getUserPhotos(Long id) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getUserPhotos(id)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}
	
	public List<PhotoDTO> getUserPhotos(String login) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getUserPhotos(login)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}
	
	public Long getPhotoCount() {
		Long pCount = photoDao.getPhotoCount();
		return pCount;
	}
}
