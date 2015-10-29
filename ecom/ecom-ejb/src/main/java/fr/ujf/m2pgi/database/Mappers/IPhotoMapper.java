package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;

import javax.ejb.Local;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
@Local
public interface IPhotoMapper extends IGeneriqueMapper<PhotoDTO, Photo> {
}
