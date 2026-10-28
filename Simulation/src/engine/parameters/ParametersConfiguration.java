package engine.parameters;

import model.parameters.ModelParametersSimulation;

public abstract class ParametersConfiguration {
	public String CONFIGURATION_NAME;
	public int EXPERIMENT_ID;
	public int CONFIGURATION_ID;
	public int SIMULATIONS;
	public int CYCLES_PER_SIMULATION;
	
	public abstract ModelParametersSimulation setSimulationParameters();

}
