package fr.ujf.m2pgi.database.DAO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings("unchecked")
/**
 *  Created by FAURE Adrien on 22/10/15.
 */
public abstract class GeneriqueDAOImpl<entityType> implements IGeneriqueDAO<entityType> {

	/**
	 *
	 */
	protected Class<entityType> entityClass;

	/**
	 *
	 */
	@PersistenceContext(unitName = "EjbComponentPU")
	protected EntityManager entityManager;

	/**
	 *
	 */
	public GeneriqueDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = ((Class<entityType>) pt.getActualTypeArguments()[0]);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public entityType create(final entityType entity) {
		entityManager.persist(entity);
		return entity;
	}

	/**
	 *
	 * @param id
	 */
	@Override
	public void delete(Object id) {
		entityManager.remove( entityManager.getReference(entityClass, id));
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@Override
	public entityType find(Object id) {
		return (entityType) entityManager.find(entityClass, id);
		
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public entityType update(entityType entity) {
		return entityManager.merge(entity);
	}

}
