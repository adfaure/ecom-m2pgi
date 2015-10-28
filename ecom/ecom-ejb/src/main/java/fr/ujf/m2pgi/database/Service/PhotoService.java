package fr.ujf.m2pgi.database.Service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Seller;

/**
 * 
 * @author AZOUZI Marwen ()
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
	public PhotoDTO getPhotoById(long id) {
		Photo photoEntity = photoDao.find(id);
		if(photoEntity != null) {
			System.err.println("trouvé!");
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

		Seller seller = sellerDao.findSellerByLogin("bob");
		Photo photoEntity = photoDao.getPhoto(photo);
		if(seller == null) {
			System.err.println(photo.getSellerID());
			System.err.println("selelr is null ca va bugger");
		}
		photoEntity.setAuthor(seller);
		return photoDao.getPhotoDTO(photoDao.create(photoEntity));
	}
}
