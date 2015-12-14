package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.database.DTO.*;

import javax.ejb.Local;
import java.io.*;
import java.util.List;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Local
public interface IPhotoService {

    List<PhotoContextSmallDTO> searchPhotosContext(String text, Long memberID);

    PublicPhotoDTO deletePhoto(Long id);

    PublicPhotoDTO getPhotoById(Long id);

    PhotoContextBigDTO getPhotoById(Long photoID, Long memberID);

    PublicPhotoDTO createPhoto(FullPhotoDTO photo);

    PublicPhotoDTO updatePhoto(UpdatePhotoDTO photo);

    List<PublicPhotoDTO> getAllPhotos();

    List<PublicPhotoDTO> getReportedPhotos();

    List<PhotoContextSmallDTO> getAllPhotosContext(Long memberID);

    List<PublicPhotoDTO> getTop10Photos();

    List<PublicPhotoDTO> getPhotosSortByPrice(boolean ascending);

    List<PublicPhotoDTO> getPhotosSortByViews(boolean ascending);

    List<PublicPhotoDTO> getPhotosSortByLikes(boolean ascending);

    List<PublicPhotoDTO> getPhotosSortByDate(boolean ascending);

    List<ManagePhotoDTO> getUserPhotos(Long id);

    List<ManagePhotoDTO> getUserPhotos(String login);

    List<PublicPhotoDTO> getUserWishedPhotos(String login);

    List<WishListPhotoDTO> getUserWishedPhotos(Long id);

    void saveFile(InputStream uploadedInputStream, String serverLocation);

    Long getPhotoCount();

    List<PublicPhotoDTO> getAllAvailablePhotos();

    void viewPhoto(Long photoID, Long memberID);

    void likePhoto(Long photoID, Long memberID);

    void unlikePhoto(Long photoID, Long memberID);

    void addPhotoToWishList(Long photoID, Long memberID);

    void removePhotoFromWishList(Long photoID, Long memberID);

    SignalDTO signalPhoto(SignalDTO signalDTO);

    void validateReportedPhoto(Long id);

    void deleteReportedPhoto(Long id);
}
