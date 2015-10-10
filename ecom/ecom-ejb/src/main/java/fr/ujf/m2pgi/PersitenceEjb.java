package fr.ujf.m2pgi;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersitenceEjb {
	
   //pass persistence unit to entityManager.
   @PersistenceContext(unitName="EjbComponentPU")
   private EntityManager entityManager;         

   public void saveEntity(ClassEjbEntity e)  {
	   entityManager.persist(e);
   }
}