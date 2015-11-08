package fr.ujf.m2pgi.database.DAO;


/**
 * 
 * @author FAURE Adrien
 *
 * Generique inteface for queriying enitites
 *
 * @param <entityType>
 */

public interface IGeneriqueDAO<entityType> {

	/**
	 * 
	 * @param t
	 * @return
	 */
	entityType create(entityType t);

	/**
	 * 
	 * @param id
	 */
	void delete(Object id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	entityType find(Object id);

	/**
	 * 
	 * @param t
	 * @return
	 */
	entityType update(entityType t);

	/**
	 *
	 * @param t
	 * @return
     */
	entityType getReference(Object t);
}
