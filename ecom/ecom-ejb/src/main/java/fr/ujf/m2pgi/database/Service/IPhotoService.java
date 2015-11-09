package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.DTO.UpdatePhotoDTO;

import javax.ejb.Local;
import java.io.*;
import java.util.List;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Local
public interface IPhotoService {

    PhotoDTO deletePhoto(Long id);

    PhotoDTO getPhotoById(Long id);

    PhotoDTO createPhoto(PhotoDTO photo);

    PhotoDTO updatePhoto(UpdatePhotoDTO photo);

    List<PhotoDTO> getAllPhotos();

    List<PhotoDTO> getUserPhotos(Long id);

    List<PhotoDTO> getUserPhotos(String login);

    void saveFile(InputStream uploadedInputStream, String serverLocation);
}
