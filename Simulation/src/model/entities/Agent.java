package model.entities;

import model.dtos.AgentDTO;
import model.world.ModelWorld;

public abstract class Agent extends engine.entities.Agent {
	protected ModelWorld world;
	
	protected int id;
		
	protected float liquidAssets;
	@Override
	public AgentDTO runCycle() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract String getCode();

	public ModelWorld getWorld() {
		return world;
	}

	public void setWorld(ModelWorld world) {
		this.world = world;
	}

	public float getLiquidAssets() {
		return liquidAssets;
	}

	public void setLiquidAssets(double d) {
		this.liquidAssets = (float) d;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
