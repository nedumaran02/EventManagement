package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dto.Client;

public class ClientDao 
{
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("maran");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public Client saveClient(Client c)
	{
		et.begin();
		em.persist(c);
		et.commit();
		
		return c;
	}
	public Client findAdmin(int clientId)
	{
		Client client = em.find(Client.class, clientId);
		if(client != null)
		{
			return client;
		}
		return null;
	}
	public Client updateClient(Client client,int id)
	{
		Client exClient = em.find(Client.class, id);
		if(exClient != null)
		{
			client.setClientId(id);
			et.begin();
			em.merge(client);
			et.commit();
			return client;
		}
		return null;
	}
	public Client deleteClient(int id)
	{
		Client exClient = em.find(Client.class, id);
		if(exClient != null)
		{
			et.begin();
			em.remove(exClient);
			et.commit();
			return exClient;
		}
		return null;
	}
}
