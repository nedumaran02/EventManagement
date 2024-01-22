package Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Persistence;
import javax.persistence.Query;

import dao.AdminDao;
import dao.ClientDao;
import dao.ClientEventDao;
import dao.ClientServiceDao;
import dao.ServiceDao;
import dto.Admin;
import dto.Client;
import dto.ClientEvent;
import dto.ClientService;
import dto.EventType;
import dto.Service;

public class EventManagementClient 
{
	AdminDao adao = new AdminDao();
	ServiceDao sdao = new ServiceDao();
	ClientDao cdao = new ClientDao();
	ClientEventDao ceDao = new ClientEventDao();
	ClientServiceDao csdao = new ClientServiceDao();
	EventManagement em = new EventManagement();
	
	
	
	public static void main(String[] args) 
	{
		EventManagementClient em = new EventManagementClient();
		System.out.println(em.createClientEvent());
		
	}
	
	public Client saveClient()
	{
		Client client = new Client();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Client Name");
		client.setClientName(sc.next());
		System.out.println("Enter client Email");
		client.setClientMail(sc.next());
		System.out.println("Enter client Password");
		client.setClientPassword(sc.next());
		System.out.println("Enter Admin Contact Number");
		client.setClientContact(sc.nextLong());
		
		return cdao.saveClient(client);
	}
	public Client clientLogin()
	{
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the Email");
		String email = sc.next();
		System.out.print("Enter the Password");
		String password = sc.next();
		
		Query query = Persistence.createEntityManagerFactory("maran").createEntityManager().createQuery("select c from Client c where c.clientMail=?1");
		query.setParameter(1, email);
		Client exClient = (Client)query.getSingleResult();
		if(exClient != null)
		{
			if(exClient.getClientPassword().equals(password))
			{
				return exClient;
			}
			return null;
		}
		return null;		
	}
	public String createClientEvent()
	{
		Client exClient = clientLogin();
		List<ClientEvent> clientEvents = new ArrayList<ClientEvent>();
		if(exClient != null)
		{
			ClientEvent ce = new ClientEvent();
			EventType[] et = EventType.values();
			int i=1;
			for (EventType eventType : et) {	
				System.out.println((i++) + " " + eventType);
			}
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the key ");
			int key = sc.nextInt();
			switch (key) {
			case 1:ce.setEventType(EventType.Marriage);break;
			case 2:ce.setEventType(EventType.Engagement);break;
			case 3:ce.setEventType(EventType.BirthDay);break;
			case 4:ce.setEventType(EventType.Anniversary);break;
			case 5:ce.setEventType(EventType.babyShower);break;
			case 6:ce.setEventType(EventType.Reunion);break;
			case 7:ce.setEventType(EventType.NamingCeremony);break;
			case 8:ce.setEventType(EventType.BachelorParty);break;
			}
			System.out.println("Enter the Number of People attending the Event");
			ce.setClientEventNoOfPeople(sc.nextInt());
			System.out.println("Enter the Number of Days for the Event");
			ce.setClientEventNoOfDays(sc.nextInt());
			System.out.println("Enter the location for the Event");
			ce.setClienteventLocation(sc.next());
			System.out.println("Enter the start date for the Event like 2007-12-03");
			ce.setStartDate(LocalDate.parse(sc.next()));
			
			ce.setClient(exClient);
			System.out.println("Enter Adding Count of Services");
			int count = sc.nextInt();
			double eventCost=0;
			
			List<ClientService> clientServices = new ArrayList<ClientService>();
			for (int j = 0; j < count; j++) 
			{
				ClientService cs = new ClientService();
				List<Service> services = em.getAllServices();
				
				System.out.println("\t ----- Service Lists -----");
				for (Service service : services) {
					System.out.println(service);
				}
				
				System.out.println("Enter Service Id");
				int value = sc.nextInt();
				Service s1 = sdao.findService(value);
				cs.setClientServiceName(s1.getServiceName());
				cs.setClientServiceNoOfdays(ce.getClientEventNoOfDays());
				cs.setClientServiceCostPerPerson(s1.getServiceCostPerPerson());
				cs.setClientServiceCost(ce.getClientEventNoOfPeople() * cs.getClientServiceCostPerPerson() *  cs.getClientServiceNoOfdays());
				eventCost = eventCost + cs.getClientServiceCost();
				clientServices.add(cs);
				ClientService cs1= csdao.saveClientEvent(cs);
				//System.out.println(cs1);
			}
			ce.setClientEventCost(eventCost);
			ce.setClientServices(clientServices);
			clientEvents.add(ce);
			exClient.setClientEvents(clientEvents);
			Client updatedClient = cdao.updateClient(exClient, exClient.getClientId());
			if(updatedClient != null)
			{
				return "Client Event Added";
			}
			
			ClientEvent savedClientEvent=ceDao.saveClientEvent(ce);
			exClient.getClientEvents().add(savedClientEvent);
			cdao.updateClient(exClient, exClient.getClientId());
		}
		return "Client Not Added";
	}
	public String addClientServices()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("------Add Client Service--------");
		Client exClient = clientLogin();
		if(exClient != null)
		{
			List<ClientEvent> exClientEvents = exClient.getClientEvents();
			System.out.println("Enter Client Event Id : "); int exClientEventId = sc.nextInt();
			int count = 0;
			for(ClientEvent events : exClientEvents)
			{
				if(events.getClientEventId() == exClientEventId)
				{
					count ++;
					double eventCost = events.getClientEventCost();
					List<ClientService> exClientServices = events.getClientServices();
					System.out.println("Enter Service Adding Count : "); int serviceCount = sc.nextInt();
					for(int i=1;i<=serviceCount;i++)
					{
						ClientService cs = new ClientService();
						List<Service> listOfServices = em.getAllServices();
						System.out.println("\t ----- Service Lists -----");
						for (Service service : listOfServices) 
						{
							System.out.println(service);
						}
						System.out.print("Enter Service Id :");
						int svalue = sc.nextInt();
						Service s1 = sdao.findService(svalue);
						cs.setClientServiceName(s1.getServiceName());
						cs.setClientServiceNoOfdays(events.getClientEventNoOfDays());
						cs.setClientServiceCostPerPerson(s1.getServiceCostPerPerson());
						cs.setClientServiceCost(events.getClientEventNoOfPeople() * cs.getClientServiceCostPerPerson() * cs.getClientServiceNoOfdays());
						eventCost = eventCost + cs.getClientServiceCost();
						exClientServices.add(cs);
						ClientService cs1= csdao.saveClientEvent(cs);
					}
					
					events.setClientEventCost(eventCost);
					events.setClientServices(exClientServices);
					ClientEvent ce1 = ceDao.updateClientEvent(events, events.getClientEventId());   
					if(ce1 != null)
					{
						return "Client Service Added";
					}
				}
			}
			if(count == 0)
				return "Invalid Id Event Not Found";
		}
		return "Client Service Not Added";
	}
}
	

