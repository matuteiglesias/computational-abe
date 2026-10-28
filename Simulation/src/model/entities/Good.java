package model.entities;

import engine.entities.Agent;

public abstract class Good {
	private Agent agent;
	
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	
}
