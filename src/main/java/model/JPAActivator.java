package model;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPAActivator {
	   @Produces
	   @PersistenceContext
	   private EntityManager em;
}
