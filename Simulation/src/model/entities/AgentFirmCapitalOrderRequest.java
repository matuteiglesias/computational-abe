package model.entities;

public class AgentFirmCapitalOrderRequest {

	private AgentFirmConsumer consumer;
	private GoodCapitalVintage goodCapitalVintage;
	private GoodCapital toReplace;
	private GoodCapital newGood;
	
	private Status status = Status.UNPROCESSED;
	
	public enum Status{
		UNPROCESSED,
		PROCESSED,
		DELIVERED
	}

	public AgentFirmConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(AgentFirmConsumer consumer) {
		this.consumer = consumer;
	}

	public GoodCapitalVintage getGoodCapitalVintage() {
		return goodCapitalVintage;
	}

	public void setGoodCapitalVintage(GoodCapitalVintage goodCapitalVintage) {
		this.goodCapitalVintage = goodCapitalVintage;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public GoodCapital getToReplace() {
		return toReplace;
	}

	public void setToReplace(GoodCapital toReplace) {
		this.toReplace = toReplace;
	}

	public GoodCapital getNewGood() {
		return newGood;
	}

	public void setNewGood(GoodCapital newGood) {
		this.newGood = newGood;
	}
	
}
