package fr.ujf.m2pgi.database.DAO;


import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;

/**
 * 
 * @author AZOUZI Marwen
 *
 */

public class PhotoDAOImpl extends GeneriqueDAOImpl<Photo> implements IPhotoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getUserPhotos(Long id) {
		Query query = entityManager.createQuery("SELECT p FROM Photo p left join p.author s WHERE s.memberID=:id");
		query.setParameter("id", id);
		return (List<Photo>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getUserPhotos(String login) {
		Query query = entityManager.createQuery("SELECT p FROM Photo p left join p.author s WHERE s.login=:login");
		query.setParameter("login", login);
		return (List<Photo>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getAllPhotos() {
		Query query = entityManager.createQuery("SELECT p FROM Photo p");
	    return (List<Photo>)query.getResultList();
	}
	
	
}
