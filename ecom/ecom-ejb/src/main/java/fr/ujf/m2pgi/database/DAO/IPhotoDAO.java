package fr.ujf.m2pgi.database.DAO;

import java.util.List;

import javax.ejb.Local;

import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.DTO.PhotoContextBigDTO;
import fr.ujf.m2pgi.database.DTO.PhotoContextSmallDTO;
import fr.ujf.m2pgi.database.DTO.WishListPhotoDTO;
import fr.ujf.m2pgi.database.DTO.ManagePhotoDTO;

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

	List<Photo> getReportedPhotos();

	List<Photo> getTop10Photos();

	List<PhotoContextSmallDTO> getAllPhotosContext(Long memberID);

	List<PhotoContextSmallDTO> getPhotosContext(Long memberID, List<Long> photos);

	PhotoContextBigDTO getPhotoContext(Long photoID, Long memberID);

	List<Photo> getPhotosSortByPrice(boolean ascending);

	List<Photo> getPhotosSortByViews(boolean ascending);

	List<Photo> getPhotosSortByLikes(boolean ascending);

	List<Photo> getPhotosSortByDate(boolean ascending);

	/**
	 *
	 * @param id
	 * @return
	 */
	List<ManagePhotoDTO> getUserPhotos(Long id);

	List<WishListPhotoDTO> getUserWishedPhotos(Long id);

	/**
	 *
	 * @param login
	 * @return
	 */

	List<PhotoContextSmallDTO> getLastPhotosContext(Long memberID, Long sellerID, int numberOfPics);

	List<ManagePhotoDTO> getUserPhotos(String login);

	List<Photo> getUserPhotosEntity(String login);

	/**
	 *
	 * @return number of photos in the DB
	 */
	Long getPhotoCount();

	List<Photo> getAllAvailablePhotos();
}
