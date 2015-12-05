package fr.ujf.m2pgi.database.DAO;

import fr.ujf.m2pgi.database.entities.Signal;

import javax.persistence.Query;

public class SignalDAOImpl extends GeneriqueDAOImpl<Signal> implements ISignalDAO {

  public void deletePhotoReports(Long id) {
    Query query = entityManager.createQuery("DELETE FROM Signal s WHERE s.photo.photoID = :id");
    query.setParameter("id", id);
    query.executeUpdate();
  }
}
