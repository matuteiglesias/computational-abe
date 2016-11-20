package engine.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.entities.Agent;

public abstract class World {
	
	private static final Logger logger = Logger.getLogger( World.class.getName() + " Engine" );

	protected int cycle = 1;

	protected int idAgentCounter = 0;

	protected List<Agent> agents = new ArrayList<Agent>();

	public abstract WorldCycle runCycle();

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	
	public int getIdAgentCounter() {
		return idAgentCounter;
	}

	public void setIdAgentCounter(int idAgentCounter) {
		this.idAgentCounter = idAgentCounter;
	}


}
