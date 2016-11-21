package engine.parameters;

import model.parameters.ModelParametersSimulation;

public abstract class ParametersConfiguration {
	public String NAME;
	public int SIMULATIONS;
	public int CYCLES_PER_SIMULATION;
	
	public abstract ModelParametersSimulation setSimulationParameters();

}
