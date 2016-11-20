package model.dtos;

import model.entities.AgentFirmConsumer;
import model.entities.GoodCapital;
import model.entities.GoodCapitalVintage;

public class OrderRequestDTO {
	public AgentFirmConsumer consumer;
	public GoodCapitalVintage vintage;
	public GoodCapital toReplace;
	public GoodCapital newGood;
}
