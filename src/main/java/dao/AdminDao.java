package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dto.Admin;

public class AdminDao 
{
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("maran");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public Admin saveAdmin(Admin a)
	{
		et.begin();
		em.persist(a);
		et.commit();
		
		return a;
	}
	public Admin findAdmin(int adminId)
	{
		Admin admin = em.find(Admin.class, adminId);
		if(admin != null)
		{
			return admin;
		}
		return null;
	}
	public Admin updateAdmin(Admin admin,int id)
	{
		Admin exAdmin = em.find(Admin.class, id);
		if(exAdmin != null)
		{
			admin.setAdminId(id);
			et.begin();
			em.merge(admin);
			et.commit();
			return admin;
		}
		return null;
	}
	public Admin deleteAdmin(int id)
	{
		Admin exAdmin = em.find(Admin.class, id);
		if(exAdmin != null)
		{
			et.begin();
			em.remove(exAdmin);
			et.commit();
			return exAdmin;
		}
		return null;
	}
}
