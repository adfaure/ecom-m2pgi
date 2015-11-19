package fr.ujf.m2pgi.database.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.DTO.UpdatePhotoDTO;
import fr.ujf.m2pgi.database.Mappers.IMemberMapper;
import fr.ujf.m2pgi.database.Mappers.IPhotoMapper;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;

import fr.ujf.m2pgi.elasticsearch.ElasticsearchDao;
import fr.ujf.m2pgi.elasticsearch.PhotoDocument;

/**
 *
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class PhotoService implements IPhotoService {

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
	private IMemberDAO memberDAO;

	/**
	 *
	 */
	@Inject
	private IMemberDAO memberDao;

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
			photo.setAvailable(false);
	    	photoDao.update(photo);
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
		  Member seller = memberDAO.find(photo.getSellerID());
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

	public List<PhotoDTO> getPhotosSortByPrice(boolean ascending) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByPrice(ascending)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}

	public List<PhotoDTO> getPhotosSortByViews(boolean ascending) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByViews(ascending)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}

	public List<PhotoDTO> getPhotosSortByLikes(boolean ascending) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByLikes(ascending)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}

	public List<PhotoDTO> getPhotosSortByDate(boolean ascending) {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByDate(ascending)) {
			result.add(photoMapper.getDTO(photo));
		}
		return result;
	}

	/**
	 *
	 * @return
	 */
	public List<PhotoDTO> getAllAvailablePhotos() {
		List<PhotoDTO> result = new ArrayList<PhotoDTO>();
		for(Photo photo : photoDao.getAllAvailablePhotos()) {
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
		Long pCount = photoDao.getEntityCount();
		return pCount;
	}


	public void viewPhoto(Long photoID, Long memberID)
	{
		Photo photo = photoDao.find(photoID);

    boolean exists = false;
		for(Member member: photo.getViewers())
		{
			if (member.getMemberID() == memberID)
			{
				exists = true;
				break;
			}
		}

		if (!exists)
		{
			Member member = memberDao.find(memberID);
			if (member != null)
			{
				photo.getViewers().add(member);
				member.getViewedPhotos().add(photo);
				photoDao.update(photo);
				photoDao.incrementViews(photoID);
			}
		}
	}

	public void likePhoto(Long photoID, Long memberID)
	{
		Photo photo = photoDao.find(photoID);

		boolean exists = false;
		for(Member member: photo.getLikers())
		{
			if (member.getMemberID() == memberID)
			{
				exists = true;
				break;
			}
		}

		if (!exists)
		{
			Member member = memberDao.find(memberID);
			if (member != null)
			{
				photo.getLikers().add(member);
				member.getLikedPhotos().add(photo);
				photoDao.update(photo);
				photoDao.incrementLikes(photoID);
			}
		}
	}

	public void unlikePhoto(Long photoID, Long memberID)
	{
		Photo photo = photoDao.find(photoID);

		boolean exists = false;
		for(Member member: photo.getLikers())
		{
			if (member.getMemberID() == memberID)
			{
				exists = true;
				break;
			}
		}

		if (exists)
		{
			Member member = memberDao.find(memberID);
			if (member != null)
			{
				photo.getLikers().remove(member);
				member.getLikedPhotos().remove(photo);
				photoDao.update(photo);
				photoDao.decrementLikes(photoID);
			}
		}
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
