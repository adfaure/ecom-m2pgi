package fr.ujf.m2pgi.database.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IOrderDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DAO.ISignalDAO;
import fr.ujf.m2pgi.database.DAO.ITagDAO;
import fr.ujf.m2pgi.database.DTO.*;
import fr.ujf.m2pgi.database.Mappers.IPhotoMapper;
import fr.ujf.m2pgi.database.Mappers.IPublicPhotoMapper;
import fr.ujf.m2pgi.database.Mappers.ISignalMapper;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Signal;
import fr.ujf.m2pgi.database.entities.SignalID;
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

	@Inject
	private ISignalMapper signalMapper;

	@Inject
	private IPublicPhotoMapper publicPhotoMapper;

	/**
	 *
	 */
	@Inject
	private IPhotoDAO photoDao;


	/**
	 *
	 */
	@Inject
	private IOrderDAO orderDAO;

	@Inject
	private ISignalDAO signalDAO;

	/**
	 *
	 */
	@Inject
	private ITagDAO tagDAO;

	@Inject
	private IMemberDAO memberDAO;


	/**
	 *
	 */
	@Inject
 	private ElasticsearchDao photoDaoES;

  public List<PhotoContextSmallDTO> searchPhotosContext(String text, Long memberID) {
		return photoDao.getPhotosContext(memberID, photoDaoES.searchIds(text));
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public PublicPhotoDTO deletePhoto(Long id) {
		Photo photo = photoDao.find(id);
		if (photo != null) {
			photo.setAvailable(false);
			photoDao.update(photo);
			if (!photoDaoES.delete(String.valueOf(id))) {
				return null;// The photo couldn't be deleted from ES.
			}
			return publicPhotoMapper.getDTO(photo);
		}
		return null;// The photo doesn't exist in our DB.
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public PublicPhotoDTO getPhotoById(Long id) {
		Photo photoEntity = photoDao.find(id);
		if(photoEntity != null) {
			PublicPhotoDTO dto = publicPhotoMapper.getDTO(photoEntity);
			return dto;
		}
		return null;
	}

	public PhotoContextBigDTO getPhotoById(Long photoID, Long memberID) {
		PhotoContextBigDTO photo = photoDao.getPhotoContext(photoID, memberID);
		if (photo != null)
		{
			viewPhoto(photoID, memberID);
		}
		return photo;
	}

	/**
	 *
	 * @param photo
	 * @return
	 */
	public PublicPhotoDTO createPhoto(FullPhotoDTO photo) {

		  Member seller = memberDAO.find(photo.getSellerID());
		  if (seller == null) {
				return null;
			}
		  Photo photoEntity = photoMapper.getentity(photo);
		  photoEntity.setAuthor(seller);
			photoEntity.setTags(tagDAO.getTags(photo.getTags()));
		  PublicPhotoDTO created = publicPhotoMapper.getDTO(photoDao.create(photoEntity));
		  PhotoDocument doc = new PhotoDocument();
		  doc.setPhotoId(created.getPhotoID());
		  doc.setName(created.getName());
		  doc.setDescription(created.getDescription());
			StringBuilder sb = new StringBuilder();
			for (String tag : photo.getTags()) {
				sb.append(tag.toLowerCase()).append(' ');
			}
			doc.setTags(sb.toString());
		  doc.setThumbnail(created.getThumbnail());
			doc.setPrice(created.getPrice());
			doc.setViews(0);
			doc.setLikes(0);
		  try {
			    photoDaoES.index(doc);
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	    return created;
	}


	public PublicPhotoDTO updatePhoto(UpdatePhotoDTO photo) {

    Photo photoEntity = photoDao.find(photo.getPhotoId());

		if(photoEntity == null) return null;

		photoEntity.setPrice(photo.getPrice());
		photoEntity.setDescription(photo.getDescription());
		photoEntity.setName(photo.getName());
		photoEntity.setTags(tagDAO.getTags(Arrays.asList(photo.getTags().split(" "))));

		PublicPhotoDTO updated = publicPhotoMapper.getDTO(photoDao.update(photoEntity));

		PhotoDocument doc = new PhotoDocument();
		doc.setPhotoId(photo.getPhotoId());
		doc.setName(photo.getName());
		doc.setDescription(photo.getDescription());
		doc.setTags(photo.getTags());
		doc.setPrice(photo.getPrice());

		try {// Needs better handling!
			photoDaoES.update(doc);
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
	public List<PublicPhotoDTO> getAllPhotos() {
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo: photoDao.getAllPhotos()) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	public List<ReportedPhotoDTO> getReportedPhotos() {
		return photoDao.getReportedPhotos();
	}

	public List<PublicPhotoDTO> getTop10Photos() {
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo: photoDao.getTop10Photos()) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	public List<PhotoContextSmallDTO> getAllPhotosContext(Long memberID) {
		List<PhotoContextSmallDTO> photos = photoDao.getAllPhotosContext(memberID);
		/*for(PhotoContextSmallDTO photo : photos) {
			photo.setBought(orderDAO.isPhotoBought(memberID, photo.getPhotoId()));
		}*/
		return photos;
	}

	public List<PublicPhotoDTO> getPhotosSortByPrice(boolean ascending) {
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByPrice(ascending)) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	public List<PublicPhotoDTO> getPhotosSortByViews(boolean ascending) {
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByViews(ascending)) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	public List<PublicPhotoDTO> getPhotosSortByLikes(boolean ascending) {
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByLikes(ascending)) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	public List<PublicPhotoDTO> getPhotosSortByDate(boolean ascending) {
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo: photoDao.getPhotosSortByDate(ascending)) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	/**
	 *
	 * @return
	 */
	public List<PublicPhotoDTO> getAllAvailablePhotos() {
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo : photoDao.getAllAvailablePhotos()) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	/**
	 *
	 * @param id
	 * @return
   */
	public List<ManagePhotoDTO> getUserPhotos(Long id) {
		return photoDao.getUserPhotos(id);
	}

	public List<WishListPhotoDTO> getUserWishedPhotos(Long id) {
		return photoDao.getUserWishedPhotos(id);
	}

	public List<PublicPhotoDTO> getUserWishedPhotos(String login) {
		Member member = memberDAO.findMemberByLogin(login);
		if (member == null) return null;
		List<PublicPhotoDTO> result = new ArrayList<PublicPhotoDTO>();
		for(Photo photo: member.getWishedPhotos()) {
			result.add(publicPhotoMapper.getDTO(photo));
		}
		return result;
	}

	/**
	 *
	 * @param login
	 * @return
     */
	public List<ManagePhotoDTO> getUserPhotos(String login) {
		return photoDao.getUserPhotos(login);
	}

	public List<LastPhotosDTO> getLastPhotosFromSellers(Long followerID, int numberOfPhotos) {

		List<LastPhotosDTO> result = new ArrayList<LastPhotosDTO>();
    	LastPhotosDTO lastphotos;
    	Collection<PhotoContextSmallDTO> photos;

    	for(Member seller: memberDAO.getSellersFollowedBy(followerID)) {
    		lastphotos=new LastPhotosDTO(seller.getLogin());
    		photos = photoDao.getLastPhotosContext(followerID, seller.getMemberID(), numberOfPhotos);
    		lastphotos.setPhotos(photos);
    		result.add(lastphotos);
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


	public void viewPhoto(Long photoID, Long memberID)
	{
		Photo photo = photoDao.find(photoID);

		if (photo == null) return;

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
			Member member = memberDAO.find(memberID);
			if (member != null)
			{
				photo.getViewers().add(member);
				photo.setViews(photo.getViews() + 1);
				try {// Needs better handling!
					photoDaoES.updateViews(photoID, photo.getViews());
				} catch (IOException | InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				member.getViewedPhotos().add(photo);
				photoDao.update(photo);
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
			Member member = memberDAO.find(memberID);
			if (member != null)
			{
				photo.getLikers().add(member);
				photo.setLikes(photo.getLikes() + 1);
				try {// Needs better handling!
					photoDaoES.updateLikes(photoID, photo.getLikes());
				} catch (IOException | InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				member.getLikedPhotos().add(photo);
				photoDao.update(photo);
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
			Member member = memberDAO.find(memberID);
			if (member != null)
			{
				photo.getLikers().remove(member);
				photo.setLikes(photo.getLikes() - 1);
				try {// Needs better handling!
					photoDaoES.updateLikes(photoID, photo.getLikes());
				} catch (IOException | InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				member.getLikedPhotos().remove(photo);
				photoDao.update(photo);
			}
		}
	}

	public void addPhotoToWishList(Long photoID, Long memberID)
	{
		Photo photo = photoDao.find(photoID);

		boolean exists = false;
		for(Member member: photo.getWishers())
		{
			if (member.getMemberID() == memberID)
			{
				exists = true;
				break;
			}
		}

		if (!exists)
		{
			Member member = memberDAO.find(memberID);
			if (member != null)
			{
				photo.getWishers().add(member);
				photo.setWishes(photo.getWishes() + 1);
				member.getWishedPhotos().add(photo);
				photoDao.update(photo);
			}
		}
	}

	public void removePhotoFromWishList(Long photoID, Long memberID)
	{
		Member member = memberDAO.find(memberID);
		if (member != null) {// If member exists in our DB

			Photo photo = photoDao.find(photoID);

			if (photo != null) {// If photo exists in our DB

				boolean exists = false;
				for(Photo p: member.getWishedPhotos())
				{
					if (p.getPhotoID() == photoID)
					{
						exists = true;
						break;
					}
				}

				if(exists) {
					photo.getWishers().remove(member);
					photo.setWishes(photo.getWishes() - 1);
					member.getWishedPhotos().remove(photo);
					photoDao.update(photo);
				}
			}
		}
	}

	public SignalDTO signalPhoto(SignalDTO signalDTO){

		Photo photo = photoDao.find(signalDTO.getPhotoID());
		Member member = memberDAO.find(signalDTO.getMemberID());
	    if (photo != null && member != null) {
	    	Signal signal = signalDAO.find(new SignalID(photo,member));
			if(signal==null){
		    	Signal signalEntity = signalMapper.getentity(signalDTO);
		    	signalEntity.setMember(member);
					photo.setReports(photo.getReports() + 1);
		    	signalEntity.setPhoto(photo);
		    	SignalDTO created = signalMapper.getDTO(signalDAO.create(signalEntity));
		    	return created;
			}
		}
		   return null;
	}

	public void validateReportedPhoto(Long id) {
		Photo photo = photoDao.find(id);
		if(photo != null) {
			// On mets le conteur à zero.
			photo.setReports(0);
			photoDao.update(photo);
			// On suppprime les signalements.
			signalDAO.deletePhotoReports(id);
			// Ici on doit faire en sorte qu'on puisse plus signaler cette photo.
			// Peut être un booléen "conforme = true"
		}
	}

	public void deleteReportedPhoto(Long id) {
		// Pour le moment rien de spécial.
		deletePhoto(id);
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
