package dtos;

import entities.AgentFirmConsumer;
import entities.GoodCapital;
import entities.GoodCapitalVintage;

public class OrderRequestDTO {
	public AgentFirmConsumer consumer;
	public GoodCapitalVintage vintage;
	public GoodCapital toReplace;
	public GoodCapital newGood;
}
