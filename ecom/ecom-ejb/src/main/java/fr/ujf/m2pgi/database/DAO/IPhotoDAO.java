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
	 * @param login
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

	public void incrementViews(Long id);

	public void incrementLikes(Long id);

	public void decrementLikes(Long id);
}
