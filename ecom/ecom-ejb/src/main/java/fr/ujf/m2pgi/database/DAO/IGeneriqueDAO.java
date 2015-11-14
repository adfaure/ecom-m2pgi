package fr.ujf.m2pgi.database.DAO;


/**
 * @param <entityType>
 * @author FAURE Adrien
 *         <p>
 *         Generique inteface for queriying enitites
 */

public interface IGeneriqueDAO<entityType> {

	/**
	 * 
	 * @param t
	 * @return
	 */
	entityType create(entityType t);

    /**
     * @param id
     */
    void delete(Object id, boolean flush);

	/**
	 * 
	 * @param id
	 */
	void delete(Object id);

	/**
     * @param id
     * @return
     */
    entityType find(Object id);

    /**
     * @param id
     * @param flush
     * @return
     */
    public entityType find(Object id, boolean flush);

	/**
	 * 
	 * @param t
	 * @return
	 */
	entityType update(entityType t);
	
	
	Long getEntityCount();


}
