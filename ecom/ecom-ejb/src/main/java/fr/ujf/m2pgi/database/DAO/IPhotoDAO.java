package fr.ujf.m2pgi.database.DAO;

import javax.ejb.Local;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Local
public interface IPhotoDAO extends IGeneriqueDAO<Photo> {
	
	/**
	 * 
	 * @param photo
	 * @return
	 */
	PhotoDTO getPhotoDTO(Photo photo);
	
	/**
	 * 
	 * @param photo
	 * @return
	 */
	Photo getPhoto(PhotoDTO photo);
}
