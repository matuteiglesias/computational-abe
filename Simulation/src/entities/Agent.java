package entities;

import world.World;
import dtos.AgentDTO;

public abstract class Agent {

	protected World world;
	
	protected int id;
		
	protected float liquidAssets;
	
	public abstract AgentDTO runCycle();
	public abstract String getCode();

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
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
