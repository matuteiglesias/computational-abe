package model.entities;

public class GoodConsumer extends Good {
	private AgentFirmConsumer manufacturer;

	public AgentFirmConsumer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(AgentFirmConsumer manufacturer) {
		this.manufacturer = manufacturer;
	}
		
}
