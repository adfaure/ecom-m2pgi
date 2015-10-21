package fr.ujf.m2pgi.database.DAO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings("unchecked")
public abstract class GeneriqueDAOImpl<entityType> implements IGeneriqueDAO<entityType> {

	protected Class<entityType> entityClass;
	
	@PersistenceContext(unitName = "EjbComponentPU")
	protected EntityManager entityManager;

	public GeneriqueDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = ((Class<entityType>) pt.getActualTypeArguments()[0]);
	}
	
	@Override
	public entityType create(final entityType entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public void delete(Object id) {
		entityManager.remove( entityManager.getReference(entityClass, id));
	}

	@Override
	public entityType find(Object id) {
		return (entityType) entityManager.find(entityClass, id);
		
	}

	@Override
	public entityType update(entityType entity) {
		return entityManager.merge(entity);
	}

}
