package dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClientService 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientServiceId;
	private String clientServiceName;
	private double clientServiceCost;
	private int clientServiceNoOfdays;
	private double clientServiceCostPerPerson;
	public int getClientServiceId() {
		return clientServiceId;
	}
	public void setClientServiceId(int clientServiceId) {
		this.clientServiceId = clientServiceId;
	}
	public String getClientServiceName() {
		return clientServiceName;
	}
	public void setClientServiceName(String clientServiceName) {
		this.clientServiceName = clientServiceName;
	}
	public double getClientServiceCost() {
		return clientServiceCost;
	}
	public void setClientServiceCost(double clientServiceCost) {
		this.clientServiceCost = clientServiceCost;
	}
	public int getClientServiceNoOfdays() {
		return clientServiceNoOfdays;
	}
	public void setClientServiceNoOfdays(int clientServiceNoOfdays) {
		this.clientServiceNoOfdays = clientServiceNoOfdays;
	}
	public double getClientServiceCostPerPerson() {
		return clientServiceCostPerPerson;
	}
	public void setClientServiceCostPerPerson(double clientServiceCostPerPerson) {
		this.clientServiceCostPerPerson = clientServiceCostPerPerson;
	}
	@Override
	public String toString() {
		return "ClientService [clientServiceId=" + clientServiceId + ", clientServiceName=" + clientServiceName
				+ ", clientServiceCost=" + clientServiceCost + ", clientServiceNoOfdays=" + clientServiceNoOfdays
				+ ", clientServiceCostPerPerson=" + clientServiceCostPerPerson + "]";
	}
	
	
}
