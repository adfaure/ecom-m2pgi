package fr.ujf.m2pgi.database.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.DTO.UpdatePhotoDTO;
import fr.ujf.m2pgi.database.Mappers.IPhotoMapper;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Seller;
import fr.ujf.m2pgi.elasticsearch.ElasticsearchDao;
import fr.ujf.m2pgi.elasticsearch.PhotoDocument;
import fr.ujf.m2pgi.elasticsearch.PhotoServiceES;

/**
 *
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class PhotoService implements IPhotoService{

	/**
	 *
	 */
	@Inject
	private IPhotoMapper photoMapper;

	/**
	 *
	 */
	@Inject
	private IPhotoDAO photoDao;

	/**
	 *
	 */
	@Inject
	private ISellerDAO sellerDao;

	/**
	 *
	 */
	@Inject
 	private ElasticsearchDao photoDaoES;

	/**
	 *
	 * @param id
	 * @return
	 */
	public PhotoDTO deletePhoto(Long id) {
		Photo photo = photoDao.find(id);
	    if (photo != null) {
	    	photoDao.delete(id);
				if (!photoDaoES.delete(String.valueOf(id))) {
					return null;// The photo couldn't be deleted from ES.
				}
	    	return photoMapper.getDTO(photo);
	    }
	    return null;// The photo doesn't exist in our DB.
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
		  PhotoDTO created = photoMapper.getDTO(photoDao.create(photoEntity));
		  PhotoDocument doc = new PhotoDocument();
		  doc.setId(created.getPhotoId());
		  doc.setName(created.getName());
		  doc.setDescription(created.getDescription());
		  doc.setLocation(created.getWebLocation());
		  try {
			    System.out.println(photoDaoES.index(doc));
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	    return created;
	}


	public PhotoDTO updatePhoto(UpdatePhotoDTO photo) {

    Photo photoEntity = photoDao.find(photo.getPhotoId());

		if(photoEntity == null) return null;

		photoEntity.setPrice(photo.getPrice());
		photoEntity.setDescription(photo.getDescription());
		photoEntity.setName(photo.getName());

		PhotoDTO updated = photoMapper.getDTO(photoDao.update(photoEntity));

		PhotoDocument doc = new PhotoDocument();
		doc.setId(photo.getPhotoId());
		doc.setName(photo.getName());
		doc.setDescription(photo.getDescription());

		try {// Needs better handling!
			System.out.println("Updated in ES? "+photoDaoES.update(doc));
		} catch (IOException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return updated;
	}
	/**
	 *
	 * @return
     */
	public List<PhotoDTO> getAllPhotos() {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getAllPhotos()) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}

	/**
	 *
	 * @param id
	 * @return
     */
	public List<PhotoDTO> getUserPhotos(Long id) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getUserPhotos(id)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}

	/**
	 *
	 * @param login
	 * @return
     */
	public List<PhotoDTO> getUserPhotos(String login) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getUserPhotos(login)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}

	/**
	 * 
	 * @return
     */
	public Long getPhotoCount() {
		Long pCount = photoDao.getPhotoCount();
		return pCount;
	}

	/**
	 *
	 * @param uploadedInputStream
	 * @param serverLocation
     */
	// Save uploaded file to a defined location on the server
	public void saveFile(InputStream uploadedInputStream, String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
