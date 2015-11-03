package fr.ujf.m2pgi.facades;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.Service.IFileService;
import fr.ujf.m2pgi.database.Service.IPhotoService;
import fr.ujf.m2pgi.properties.IProperties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Stateless
public class FacadePhoto {

    @EJB // TODO for the moment its better than hardcode paths
    private IProperties applicationProperties;

    @EJB
    private IFileService fileService;

    @EJB
    private IPhotoService photoService;

    public PhotoDTO savePhoto(InputStream photo, PhotoDTO photoDTO) {
        Properties prop    = applicationProperties.getApplicationProperties();
        String storageDir  = prop.getProperty("jboss.server.data.dir");
        String location    = storageDir + prop.getProperty("file.separator") + photoDTO.getName();
        String weblocation = applicationProperties.STATIC_URL + prop.getProperty("file.separator") + photoDTO.getName();
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

    public List<PhotoDTO> getUserPhotos(Long id) {
        return photoService.getUserPhotos(id);
    }

    public List<PhotoDTO> getUserPhotos(String login) {
        return photoService.getUserPhotos(login);
    }

    public PhotoDTO deletePhoto(Long id) {
        return photoService.deletePhoto(id);
    }
}
