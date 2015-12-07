package fr.ujf.m2pgi.database.DAO;

import java.util.List;

import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.PhotoContextBigDTO;
import fr.ujf.m2pgi.database.DTO.PhotoContextSmallDTO;
import fr.ujf.m2pgi.database.DTO.WishListPhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Member;

/**
 *
 * @author AZOUZI Marwen
 *
 */
public class PhotoDAOImpl extends GeneriqueDAOImpl<Photo> implements IPhotoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getUserPhotos(Long id) {
		Query query = entityManager.createQuery("SELECT p FROM Photo p left join p.author s WHERE s.memberID=:id AND p.available = true");
		query.setParameter("id", id);
		return (List<Photo>)query.getResultList();
	}

	@Override
	public List<WishListPhotoDTO> getUserWishedPhotos(Long id) {
		String str = "SELECT NEW fr.ujf.m2pgi.database.DTO.WishListPhotoDTO" +
		"(p.photoID, p.description, p.name, p.webLocation," +
		"CASE WHEN EXISTS (SELECT c FROM Cart c WHERE p.photoID = c.photo.photoID AND c.member.memberID = :id)" +
		"THEN true ELSE false END AS inCart) " +
		"FROM Photo p LEFT JOIN p.wishers m " +
		"WHERE p.available = true AND m.memberID = :id";
		Query query = entityManager.createQuery(str, WishListPhotoDTO.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getUserPhotos(String login) {
		Query query = entityManager.createQuery("SELECT p FROM Photo p left join p.author s WHERE s.login=:login and p.available = true");
		query.setParameter("login", login);
		return (List<Photo>)query.getResultList();
	}

	@Override
	public Long getPhotoCount() {
		String q = "SELECT count(p) FROM Photo p where p.available = 'TRUE'";
		Query query = entityManager.createQuery(q);
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getAllPhotos() {
		Query query = entityManager.createQuery("SELECT p FROM Photo p");
	    return (List<Photo>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getReportedPhotos() {
		Query query = entityManager.createQuery("SELECT p FROM Photo p WHERE p.available = true " +
		"AND EXISTS (SELECT r FROM Signal r WHERE r.photo.photoID = p.photoID)");
	  return (List<Photo>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhotoContextSmallDTO> getAllPhotosContext(Long memberID) {
		String str = "SELECT NEW fr.ujf.m2pgi.database.DTO.PhotoContextSmallDTO" +
		"(p.photoID, p.name, p.webLocation, p.thumbnail, p.price, p.views, p.likes, " +
		"CASE WHEN EXISTS (SELECT w FROM Wish w WHERE p.photoID = w.photo.photoID AND w.member.memberID = :id)" +
		"THEN true ELSE false END AS wishlisted," +
		"CASE WHEN EXISTS (SELECT c FROM Cart c WHERE p.photoID = c.photo.photoID AND c.member.memberID = :id)" +
		"THEN true ELSE false END AS inCart, " +
		"CASE WHEN EXISTS (SELECT l FROM Like l WHERE p.photoID = l.photo.photoID AND l.member.memberID = :id)" +
		"THEN true ELSE false END AS liked, " +
		"CASE WHEN EXISTS (SELECT s FROM Signal s WHERE p.photoID = s.photo.photoID AND s.member.memberID = :id)" +
		"THEN true ELSE false END AS flagged) " +
		"FROM Photo p WHERE p.available = true";
		Query query = entityManager.createQuery(str, PhotoContextSmallDTO.class);
		query.setParameter("id", memberID);
		return query.getResultList();
	}

	@Override
	public PhotoContextBigDTO getPhotoContext(Long photoID, Long memberID) {
		String str = "SELECT NEW fr.ujf.m2pgi.database.DTO.PhotoContextBigDTO" +
		"(p.photoID, p.name, p.description, p.webLocation, p.thumbnail, p.price, p.author.memberID, p.sales, p.dateCreated, p.views, p.likes, " +
		"CASE WHEN EXISTS (SELECT w FROM Wish w WHERE p.photoID = w.photo.photoID AND w.member.memberID = :id)" +
		"THEN true ELSE false END AS wishlisted," +
		"CASE WHEN EXISTS (SELECT c FROM Cart c WHERE p.photoID = c.photo.photoID AND c.member.memberID = :id)" +
		"THEN true ELSE false END AS inCart, " +
		"CASE WHEN EXISTS (SELECT l FROM Like l WHERE p.photoID = l.photo.photoID AND l.member.memberID = :id)" +
		"THEN true ELSE false END AS liked, " +
		"CASE WHEN EXISTS (SELECT s FROM Signal s WHERE p.photoID = s.photo.photoID AND s.member.memberID = :id)" +
		"THEN true ELSE false END AS flagged) " +
		"FROM Photo p WHERE p.available = true AND p.photoID = :photoid";
		Query query = entityManager.createQuery(str, PhotoContextBigDTO.class);
		query.setParameter("id", memberID);
		query.setParameter("photoid", photoID);
		return (PhotoContextBigDTO) query.getSingleResult();
	}

	public List<Photo> getTop10Photos() {
		Query query = entityManager.createQuery("SELECT p FROM Photo p WHERE  p.available = true ORDER BY p.sales DESC").setMaxResults(10);
	    return (List<Photo>) query.getResultList();
	}

	@Override
	public List<Photo> getPhotosSortByPrice(boolean ascending) {
		String order = ascending == true ? "ASC" : "DESC";

		Query query = entityManager.createQuery
		("SELECT p FROM Photo p WHERE p.available = true ORDER BY p.price " + order);
		return (List<Photo>)query.getResultList();
	}

	@Override
	public List<Photo> getPhotosSortByViews(boolean ascending) {
		String order = ascending == true ? "ASC" : "DESC";

		Query query = entityManager.createQuery
		("SELECT p FROM Photo p WHERE p.available = true ORDER BY p.views " + order);
		return (List<Photo>)query.getResultList();
	}

	@Override
	public List<Photo> getPhotosSortByLikes(boolean ascending) {
		String order = ascending == true ? "ASC" : "DESC";

		Query query = entityManager.createQuery
		("SELECT p FROM Photo p WHERE p.available = true ORDER BY p.likes " + order);
		return (List<Photo>)query.getResultList();
	}

	@Override
	public List<Photo> getPhotosSortByDate(boolean ascending) {
		String order = ascending == true ? "ASC" : "DESC";

		Query query = entityManager.createQuery
		("SELECT p FROM Photo p WHERE p.available = true ORDER BY p.dateCreated " + order);
		return (List<Photo>)query.getResultList();
	}

	@Override
	public void delete(Object id) {
		Photo photo = find(id);

		for(Member member: photo.getBuyers())
		{
			member.getCart().remove(photo);
		}

		super.delete(id);
	}

	@Override
	public List<Photo> getAllAvailablePhotos() {
		Query query = entityManager.createQuery("SELECT p FROM Photo p where p.available = true");
		return (List<Photo>)query.getResultList();

	}

	public void incrementViews(Long id) {
		Query query = entityManager.createQuery("UPDATE Photo p SET p.views = p.views + 1 WHERE p.photoID = :id");
		query.setParameter("id", id);
		int updateCount = query.executeUpdate();
	}

	@Override
	public void incrementLikes(Long id) {
		Query query = entityManager.createQuery("UPDATE Photo p SET p.likes = p.likes + 1 WHERE p.photoID = :id");
		query.setParameter("id", id);
		int updateCount = query.executeUpdate();
	}

	@Override
	public void decrementLikes(Long id) {
		Query query = entityManager.createQuery("UPDATE Photo p SET p.likes = p.likes - 1 WHERE p.photoID = :id");
		query.setParameter("id", id);
		int updateCount = query.executeUpdate();
	}

}
