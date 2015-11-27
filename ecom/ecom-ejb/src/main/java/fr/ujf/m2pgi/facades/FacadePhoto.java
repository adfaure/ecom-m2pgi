package fr.ujf.m2pgi.facades;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.DTO.WishListPhotoDTO;
import fr.ujf.m2pgi.database.DTO.UpdatePhotoDTO;
import fr.ujf.m2pgi.database.Service.IFileService;
import fr.ujf.m2pgi.database.Service.IPhotoService;
import fr.ujf.m2pgi.properties.IProperties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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

    public PhotoDTO savePhoto(InputStream photo, PhotoDTO photoDTO) {
        Properties prop    = applicationProperties.getApplicationProperties();
        String storageDir  = prop.getProperty("data_dir");
        String location    = storageDir + prop.getProperty("file.separator") + photoDTO.getName();
        String weblocation = prop.getProperty("static_hostname") + prop.getProperty("file.separator") + photoDTO.getName();
        fileService.saveFile(photo, location );
        photoDTO.setFileLocation(storageDir);
        photoDTO.setWebLocation(weblocation);
        return photoService.createPhoto(photoDTO);
    }

    public PhotoDTO getPhotoById(long id) {
        return photoService.getPhotoById(id);
    }

    public List<PhotoDTO> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    public List<PhotoDTO> getPhotosSortByPrice(boolean ascending) {
      return photoService.getPhotosSortByPrice(ascending);
    }

    public List<PhotoDTO> getPhotosSortByViews(boolean ascending) {
      return photoService.getPhotosSortByViews(ascending);
    }

    public List<PhotoDTO> getPhotosSortByLikes(boolean ascending) {
      return photoService.getPhotosSortByLikes(ascending);
    }

    public List<PhotoDTO> getPhotosSortByDate(boolean ascending) {
      return photoService.getPhotosSortByDate(ascending);
    }

    public List<PhotoDTO> getUserPhotos(Long id) {
        return photoService.getUserPhotos(id);
    }

    public List<PhotoDTO> getUserPhotos(String login) {
        return photoService.getUserPhotos(login);
    }

    public List<WishListPhotoDTO> getUserWishedPhotos(Long id) {
        return photoService.getUserWishedPhotos(id);
    }

    public List<PhotoDTO> getUserWishedPhotos(String login) {
        return photoService.getUserWishedPhotos(login);
    }

    public PhotoDTO deletePhoto(Long id) {
        return photoService.deletePhoto(id);
    }

    public PhotoDTO updatePhoto(UpdatePhotoDTO photo) {
        return photoService.updatePhoto(photo);
    }

    public Long getPhotoCount(){
    	return photoService.getPhotoCount();
    }

    public List<PhotoDTO> getAllAvailablePhotos() {
        return photoService.getAllAvailablePhotos();
    }

    public void viewPhoto(Long photoID, Long memberID) {
      photoService.viewPhoto(photoID, memberID);
    }

    public void likePhoto(Long photoID, Long memberID) {
      photoService.likePhoto(photoID, memberID);
    }

    public void unlikePhoto(Long photoID, Long memberID) {
      photoService.likePhoto(photoID, memberID);
    }

    public void addPhotoToWishList(Long photoID, Long memberID) {
      photoService.addPhotoToWishList(photoID, memberID);
    }

    public void removePhotoFromWishList(Long photoID, Long memberID) {
      photoService.removePhotoFromWishList(photoID, memberID);
    }
}
