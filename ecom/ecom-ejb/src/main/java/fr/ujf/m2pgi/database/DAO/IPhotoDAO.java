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

	List<Photo> getPhotosSortByPrice(boolean ascending);

	List<Photo> getPhotosSortByViews(boolean ascending);

	List<Photo> getPhotosSortByLikes(boolean ascending);

	List<Photo> getPhotosSortByDate(boolean ascending);

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
	List<Photo> getUserPhotos(String login);

	/**
	 *
	 * @return number of photos in the DB
	 */
	Long getPhotoCount();

	List<Photo> getAllAvailablePhotos();

	void incrementViews(Long id);

	void incrementLikes(Long id);

	void decrementLikes(Long id);
}
