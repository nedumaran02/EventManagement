package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dto.ClientEvent;

public class ClientEventDao 
{
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("maran");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public ClientEvent saveClientEvent(ClientEvent ce)
	{
		et.begin();
		em.persist(ce);
		et.commit();
		
		return ce;
	}
	public ClientEvent findAdmin(int clientEventId)
	{
		ClientEvent clientEvent = em.find(ClientEvent.class, clientEventId);
		if(clientEvent != null)
		{
			return clientEvent;
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
