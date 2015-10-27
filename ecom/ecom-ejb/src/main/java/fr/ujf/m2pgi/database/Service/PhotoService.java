package fr.ujf.m2pgi.database.Service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class PhotoService {

	@EJB
	IPhotoDAO photoDao;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PhotoDTO getPhotoById(long id) {
		Photo photoEntity = photoDao.find(id);
		if(photoEntity != null)
			return photoDao.getPhotoDTO(photoEntity);
		return null;
	}
	
	/**
	 * 
	 * @param photo
	 * @return
	 */
	public PhotoDTO createPhoto(PhotoDTO photo) {
		return photoDao.getPhotoDTO(photoDao.create(photoDao.getPhoto(photo)));
	}
}
