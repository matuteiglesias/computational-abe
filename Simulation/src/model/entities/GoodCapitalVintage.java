package model.entities;

import model.world.ModelWorld;

public class GoodCapitalVintage {
	
	private AgentFirmCapital manufacturer;

	private float productivityA;

	private float price;

	
	public AgentFirmCapital getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(AgentFirmCapital manufacturer) {
		this.manufacturer = manufacturer;
	}

	public float getProductivityA() {
		return productivityA;
	}

	public void setProductivityA(float productivityA) {
		this.productivityA = productivityA;
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public GoodCapitalVintage(){		
	}
	
	public GoodCapitalVintage(AgentFirmCapital agent){
		this.manufacturer = agent;
	}
	public float score(){
		Float response;
		
//		if(this.manufacturer.world == null)
//			System.out.println("mundo es null");
		
		if(this.manufacturer ==null)
				System.out.println("manufacturer es null");
	
		ModelWorld world = this.manufacturer.world;
		response = this.price + world.getParameters().AGENT_FIRM_CONSUMER_PAYBACK_PERIOD * world.getWageCycle() / this.productivityA;
		return response;
	}
}
