package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dao.AdminDao;
import dao.ClientDao;
import dao.ClientEventDao;
import dao.ServiceDao;
import dto.Admin;
import dto.Service;

public class EventManagement 
{
	AdminDao adao = new AdminDao();
	ServiceDao sdao = new ServiceDao();
	ClientDao cdao = new ClientDao();
	ClientEventDao ceDao = new ClientEventDao();
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("maran");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	
	public static void main(String[] args) 
	{
		EventManagement em = new EventManagement();
		
		em.saveService();
		System.out.println(em.getAllServices());
//		em.saveService();
	}
	
	public Admin saveAdmin()
	{
		Admin admin = new Admin();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Admin Name");
		admin.setAdminName(sc.next());
		System.out.println("Enter Admin Email");
		admin.setAdminMail(sc.next());
		System.out.println("Enter Admin Password");
		admin.setAdminPassword(sc.next());
		System.out.println("Enter Admin Contact Number");
		admin.setAdminContact(sc.nextLong());
		
		return adao.saveAdmin(admin);
	}
	public Admin adminLogin()
	{
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the Email");
		String email = sc.next();
		System.out.print("Enter the Password");
		String password = sc.next();
		
		Query query = Persistence.createEntityManagerFactory("maran").createEntityManager().createQuery("select a from Admin a where a.adminMail=?1");
		query.setParameter(1, email);
		Admin exAdmin = (Admin)query.getSingleResult();
		if(exAdmin != null)
		{
			if(exAdmin.getAdminPassword().equals(password))
			{
				return exAdmin;
			}
			return null;
		}
		return null;		
	}
	public Service saveService()
	{
		Admin exAdmin = adminLogin();
		if(exAdmin!=null)
		{
			Service s = new Service();
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Service Name");
			s.setServiceName(sc.next());
			System.out.println("Enter Service Cost Per Person");
			s.setServiceCostPerDay(sc.nextDouble());
			System.out.println("Enter Service Cost Per Day");
			s.setServiceCostPerPerson(sc.nextDouble());
			Service savedService = sdao.saveService(s);
			
			exAdmin.getServices().add(savedService);
			adao.updateAdmin(exAdmin, exAdmin.getAdminId());
			return s;
		}
		return null;
	}
	public List<Service> getAllServices()
	{
		Query query = em.createQuery("select s from Service s");
		List<Service> listOfServices= query.getResultList();
		return listOfServices;
	}
	public String updateService()
	{
		Scanner sc = new Scanner(System.in);
		List<Service> services=getAllServices();
		for(Service s : services)
		{
			System.out.println("serviceId  " + "serviceName  " + "serviceCostPerDay  " + "serviceCostPerPerson  ");
			System.out.println("  " + s.getServiceId()+"         "+s.getServiceName()+"         "+s.getServiceCostPerDay()+"      "+s.getServiceCostPerPerson());
			
		}
		System.out.println("Choose Service Id to Update The Service");
		int id = sc.nextInt();
		Service tobeUpdated = sdao.findService(id);
		System.out.println("Enter Updated cost per person");
		double costperperson = sc.nextDouble();
		System.out.println("Enter Updated cost per day");
		double costperday = sc.nextDouble();
		tobeUpdated.setServiceCostPerDay(costperday);
		tobeUpdated.setServiceCostPerPerson(costperperson);
		
		Service updated =sdao.updateService(tobeUpdated, id);
		if(updated!=null)
		{
			return "service update success";
		}
		return null;
	}
	public List<Service> deleteService()
	{
		Scanner sc = new Scanner(System.in);
		Admin exAdmin = adminLogin();
		
		if(exAdmin!= null)
		{
			List<Service> services=exAdmin.getServices();
			for(Service s : services)
			{
				System.out.println("serviceId  " + "serviceName  " + "serviceCostPerDay  " + "serviceCostPerPerson  ");
				System.out.println("  " + s.getServiceId()+"         "+s.getServiceName()+"         "+s.getServiceCostPerDay()+"      "+s.getServiceCostPerPerson());
				
			}
			System.out.println("Choose Service Id to Delete The Service");
			int id = sc.nextInt();
			List<Service> newList = new ArrayList<Service>();
			for (Service service : services) 
			{
				if(service.getServiceId()!=id)
				{
					newList.add(service);
				}
			}
			exAdmin.setServices(newList);
			adao.updateAdmin(exAdmin, exAdmin.getAdminId());
			sdao.deleteService(id);
			
			return services;
		}
		return null;
	}
	 
	
	
	
}
