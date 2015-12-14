package fr.ujf.m2pgi.facades;

import fr.ujf.m2pgi.database.DTO.*;
import fr.ujf.m2pgi.database.Service.IFileService;
import fr.ujf.m2pgi.database.Service.IPhotoService;
import fr.ujf.m2pgi.properties.IProperties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Stateless
public class FacadePhoto {

    @Inject // TODO for the moment its better than hardcode paths
    private IProperties applicationProperties;

    @Inject
    private IFileService fileService;

    @Inject
    private IPhotoService photoService;

    public PublicPhotoDTO savePhoto(FullPhotoDTO publicPhotoDTO) {
        return photoService.createPhoto(publicPhotoDTO);
    }

    public PublicPhotoDTO getPhotoById(long id) {
        return photoService.getPhotoById(id);
    }

	public PhotoContextBigDTO getPhotoById(Long photoID, Long memberID) {
		return photoService.getPhotoById(photoID, memberID);
	}

    public List<PublicPhotoDTO> getAllPhotos() {
        return photoService.getAllPhotos();
    }

  public List<PublicPhotoDTO> getReportedPhotos() {
    return photoService.getReportedPhotos();
  }

    public List<PublicPhotoDTO> getTop10Photos() {
        return photoService.getTop10Photos();
    }

	public List<PhotoContextSmallDTO> getAllPhotosContext(Long memberID) {
		return photoService.getAllPhotosContext(memberID);
	}

    public List<PublicPhotoDTO> getPhotosSortByPrice(boolean ascending) {
      return photoService.getPhotosSortByPrice(ascending);
    }

    public List<PublicPhotoDTO> getPhotosSortByViews(boolean ascending) {
      return photoService.getPhotosSortByViews(ascending);
    }

    public List<PublicPhotoDTO> getPhotosSortByLikes(boolean ascending) {
      return photoService.getPhotosSortByLikes(ascending);
    }

    public List<PublicPhotoDTO> getPhotosSortByDate(boolean ascending) {
      return photoService.getPhotosSortByDate(ascending);
    }

    public List<PublicPhotoDTO> getUserPhotos(Long id) {
        return photoService.getUserPhotos(id);
    }

    public List<PublicPhotoDTO> getUserPhotos(String login) {
        return photoService.getUserPhotos(login);
    }

    public List<WishListPhotoDTO> getUserWishedPhotos(Long id) {
        return photoService.getUserWishedPhotos(id);
    }

    public List<PublicPhotoDTO> getUserWishedPhotos(String login) {
        return photoService.getUserWishedPhotos(login);
    }
    
    public List<LastPhotosDTO> getLastPhotosFromSellers(Long followerID, int numberOfPhotos) {
        return photoService.getLastPhotosFromSellers(followerID, numberOfPhotos);
    }

    public PublicPhotoDTO deletePhoto(Long id) {
        return photoService.deletePhoto(id);
    }

    public PublicPhotoDTO updatePhoto(UpdatePhotoDTO photo) {
        return photoService.updatePhoto(photo);
    }

    public Long getPhotoCount(){
    	return photoService.getPhotoCount();
    }

    public List<PublicPhotoDTO> getAllAvailablePhotos() {
        return photoService.getAllAvailablePhotos();
    }

    public void viewPhoto(Long photoID, Long memberID) {
      photoService.viewPhoto(photoID, memberID);
    }

    public void likePhoto(Long photoID, Long memberID) {
      photoService.likePhoto(photoID, memberID);
    }

    public void unlikePhoto(Long photoID, Long memberID) {
      photoService.unlikePhoto(photoID, memberID);
    }

    public void addPhotoToWishList(Long photoID, Long memberID) {
      photoService.addPhotoToWishList(photoID, memberID);
    }

    public void removePhotoFromWishList(Long photoID, Long memberID) {
      photoService.removePhotoFromWishList(photoID, memberID);
    }

    public SignalDTO signalPhoto (SignalDTO signalDTO){
    	return photoService.signalPhoto(signalDTO);
    }

    public void deleteReportedPhoto(Long id){
      photoService.deleteReportedPhoto(id);
    }

    public void validateReportedPhoto(Long id){
      photoService.validateReportedPhoto(id);
    }
}
