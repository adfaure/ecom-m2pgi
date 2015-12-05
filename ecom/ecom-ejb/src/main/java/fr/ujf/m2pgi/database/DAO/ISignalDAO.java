package fr.ujf.m2pgi.database.DAO;

import javax.ejb.Local;
import fr.ujf.m2pgi.database.entities.Signal;

@Local
public interface ISignalDAO extends IGeneriqueDAO<Signal> {

	void deletePhotoReports(Long id);

}
