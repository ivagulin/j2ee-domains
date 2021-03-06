package rest;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.TransactionManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.DnsRecord;
import model.DnsRecord_;
import model.Domain;

@Stateless
@Path("/domains")
@RolesAllowed("domain-view")
@PermitAll
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DomainService {
	@Inject
	EntityManager em;

	@Resource(mappedName = "java:/TransactionManager")
	private TransactionManager tm;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Domain> listDomains() {
		CriteriaQuery<Domain> q = em.getCriteriaBuilder().createQuery(Domain.class);
		Root<Domain> root = q.from(Domain.class);
		q.select(root);
		return em.createQuery(q).getResultList();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Domain getDomain(@PathParam("id") Integer id){
		Domain d = em.find(Domain.class, id);
		return d;
	}
	
	@GET
	@Path("/{id}/records")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<DnsRecord> getRecords(@PathParam("id") Integer id)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DnsRecord> q = cb.createQuery(DnsRecord.class);
		Root<DnsRecord> recordRoot = q.from(DnsRecord.class);
		q.select(recordRoot).where(cb.equal(recordRoot.get(DnsRecord_.domainId), id));
		return em.createQuery(q).getResultList();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	@PermitAll
	public void createDomain(Domain d) throws Exception {
		String name = d.getDomainName();
		for(int i=0; i<10; i++){
			d.setId(null);
			d.setDomainName(Integer.toString(i)+name);
			tm.begin();
			em.persist(d);
			tm.commit();
		}
	}
}
