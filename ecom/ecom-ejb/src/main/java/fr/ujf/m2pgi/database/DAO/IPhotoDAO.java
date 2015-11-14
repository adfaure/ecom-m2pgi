package fr.ujf.m2pgi.database.DAO;

import java.util.List;

import javax.ejb.Local;

import fr.ujf.m2pgi.database.entities.Photo;

/**
 *
 * @author AZOUZI Marwen ()
 *
 */
@Local
public interface IPhotoDAO extends IGeneriqueDAO<Photo> {

	/**
	 *
	 * @return
     */
	List<Photo> getAllPhotos();

	/**
	 *
	 * @param id
	 * @return
	 */
	List<Photo> getUserPhotos(Long id);

	/**
	 *
	 * @param login
	 * @return
	 */
	public List<Photo> getUserPhotos(String login);
	
	
	/**
	 * 
	 * @return number of photos in the DB
	 */
	public Long getPhotoCount();
	List<Photo> getAllAvailablePhotos();

}
