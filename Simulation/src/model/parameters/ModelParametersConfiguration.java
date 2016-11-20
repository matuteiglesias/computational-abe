package model.parameters;

public class ModelParametersConfiguration extends engine.parameters.ParametersConfiguration{
	

	// SimulationController
	public int SIMULATIONS;
	public int CYCLES_PER_SIMULATION;

	public int AGENT_FIRM_CAPITAL_Q;
	public int AGENT_FIRM_CAPITAL_NW;
	public float AGENT_FIRM_CAPITAL_MARGIN;
	public float AGENT_FIRM_CAPITAL_RD_PROPENSITY;
	public float AGENT_FIRM_CAPITAL_FRACTION_X;
	public float AGENT_FIRM_CAPITAL_Z_IN;
	public float AGENT_FIRM_CAPITAL_Z_IM;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX;
	public int AGENT_FIRM_CAPITAL_BROCHURES;
	
	public int AGENT_FIRM_CONSUMER_Q;
	public int AGENT_FIRM_CONSUMER_STOCK_SPARE;
	public float AGENT_FIRM_CONSUMER_MARGIN;
	public float AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
	public int AGENT_FIRM_CONSUMER_NW;
	public int AGENT_FIRM_CONSUMER_PAYBACK_PERIOD;
	public int AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1;
	public int AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2;
	public int AGENT_FIRM_CONSUMER_OBSOLETE;
	public float COMPETITIVITY_MARKETSHARE;
	
	
	public int AGENT_PERSON;
	public float AGENT_PERSON_EXPEND;
	
	// AgentGovernment
	public float AGENT_GOVERNMENT_EMPLOYEE_TAX;
	public float AGENT_GOVERNMENT_FIRM_TAX;
	public float AGENT_GOVERNMENT_UNEMPLOYED_WAGE;
	public int WORLD_WAGE;
	public float PS1;
	public float PS2;

	
	// AgentFirmCapital

	public boolean PRINT_DEBUG;
//	public final static String PATH = "/home/miglesia/Documents/Econ/Simu/";
	public String PATH;

	public void setSimulationParameters(){
		ModelParametersSimulation.INDEX = this.INDEX;
		ModelParametersSimulation.CYCLES_PER_SIMULATION = this.CYCLES_PER_SIMULATION;
		ModelParametersSimulation.SIMULATIONS = this.SIMULATIONS;
		ModelParametersSimulation.PRINT_DEBUG = this.PRINT_DEBUG;
		ModelParametersSimulation.PATH = this.PATH;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_Q = this.AGENT_FIRM_CAPITAL_Q;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_NW = this.AGENT_FIRM_CAPITAL_NW;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_MARGIN = this.AGENT_FIRM_CAPITAL_MARGIN;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_RD_PROPENSITY = this.AGENT_FIRM_CAPITAL_RD_PROPENSITY;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_FRACTION_X = this.AGENT_FIRM_CAPITAL_FRACTION_X;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_Z_IN = this.AGENT_FIRM_CAPITAL_Z_IN;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_Z_IM = this.AGENT_FIRM_CAPITAL_Z_IM;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN = this.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX = this.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN = this.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX = this.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX;
		ModelParametersSimulation.AGENT_FIRM_CAPITAL_BROCHURES = this.AGENT_FIRM_CAPITAL_BROCHURES;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_Q = this.AGENT_FIRM_CONSUMER_Q;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_STOCK_SPARE = this.AGENT_FIRM_CONSUMER_STOCK_SPARE;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_MARGIN = this.AGENT_FIRM_CONSUMER_MARGIN;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY = this.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_NW = this.AGENT_FIRM_CONSUMER_NW;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_PAYBACK_PERIOD = this.AGENT_FIRM_CONSUMER_PAYBACK_PERIOD;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 = this.AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2 = this.AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2;
		ModelParametersSimulation.AGENT_FIRM_CONSUMER_OBSOLETE = this.AGENT_FIRM_CONSUMER_OBSOLETE;
		ModelParametersSimulation.COMPETITIVITY_MARKETSHARE = this.COMPETITIVITY_MARKETSHARE;
		ModelParametersSimulation.AGENT_PERSON = this.AGENT_PERSON;
		ModelParametersSimulation.AGENT_PERSON_EXPEND = this.AGENT_PERSON_EXPEND;
		ModelParametersSimulation.AGENT_GOVERNMENT_EMPLOYEE_TAX = this.AGENT_GOVERNMENT_EMPLOYEE_TAX;
		ModelParametersSimulation.AGENT_GOVERNMENT_FIRM_TAX = this.AGENT_GOVERNMENT_FIRM_TAX;
		ModelParametersSimulation.AGENT_GOVERNMENT_UNEMPLOYED_WAGE = this.AGENT_GOVERNMENT_UNEMPLOYED_WAGE;
		ModelParametersSimulation.WORLD_WAGE = this.WORLD_WAGE;
		ModelParametersSimulation.PS1 = this.PS1;
		ModelParametersSimulation.PS2 = this.PS2;

	}

}