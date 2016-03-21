package entities;

public class GoodCapital extends Good {
	
	private String uuid;
	
	private int cycle;

	private GoodCapitalVintage goodCapitalVintage;
	
	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	private AgentFirmCapital manufacturer;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public GoodCapitalVintage getGoodCapitalVintage() {
		return goodCapitalVintage;
	}

	public void setGoodCapitalVintage(GoodCapitalVintage goodCapitalVintage) {
		this.goodCapitalVintage = goodCapitalVintage;
	}

	public AgentFirmCapital getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(AgentFirmCapital manufacturer) {
		this.manufacturer = manufacturer;
	}

	
	

}
