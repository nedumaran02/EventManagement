package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dto.ClientEvent;
import dto.ClientService;

public class ClientServiceDao 
{
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("maran");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public ClientService saveClientEvent(ClientService ce)
	{
		et.begin();
		em.persist(ce);
		et.commit();
		
		return ce;
	}
	public ClientService findAdmin(int clientServiceId)
	{
		ClientService clientService = em.find(ClientService.class, clientServiceId);
		if(clientService != null)
		{
			return clientService;
		}
		return null;
	}
	public ClientEvent updateClientEvent(ClientEvent clientEvent,int id)
	{
		ClientEvent exClientEvent = em.find(ClientEvent.class, id);
		if(exClientEvent != null)
		{
			clientEvent.setClientEventId(id);
			et.begin();
			em.merge(clientEvent);
			et.commit();
			return clientEvent;
		}
		return null;
	}
	public ClientEvent deleteClientEvent(int id)
	{
		ClientEvent exClientEvent = em.find(ClientEvent.class, id);
		if(exClientEvent != null)
		{
			et.begin();
			em.remove(exClientEvent);
			et.commit();
			return exClientEvent;
		}
		return null;
	}
}
