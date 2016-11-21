package model.parameters;

public class ModelParametersSimulation extends engine.parameters.ParametersSimulation{

//	// Configuraciones
//	public static String INDEX;
	public int CYCLES = 250;
//	public static int SIMULATIONS = 20;

	public int AGENT_FIRM_CAPITAL_Q = 10;
	public int AGENT_FIRM_CAPITAL_NW = 1000;
	public float AGENT_FIRM_CAPITAL_MARGIN = 1.5F;
	public float AGENT_FIRM_CAPITAL_RD_PROPENSITY = 0.04F;
	public float AGENT_FIRM_CAPITAL_FRACTION_X = 0.5F;
	public float AGENT_FIRM_CAPITAL_Z_IN = 0.3F;
	public float AGENT_FIRM_CAPITAL_Z_IM = 0.3F;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN = 5.0F;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX = 15.0F;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN = 5.0F;
	public float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX = 10.0F;
	public int AGENT_FIRM_CAPITAL_BROCHURES = 10;
	
	public int AGENT_FIRM_CONSUMER_Q = 40;
	public int AGENT_FIRM_CONSUMER_STOCK_SPARE = 100;
	public float AGENT_FIRM_CONSUMER_MARGIN = 1.5F;
	public float AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY = 0.4F;
	public int AGENT_FIRM_CONSUMER_NW = 4000;
	public int AGENT_FIRM_CONSUMER_PAYBACK_PERIOD = 3;
	public int AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 = 1;
	public int AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2 = 1;
	public int AGENT_FIRM_CONSUMER_OBSOLETE = 20;
	public float COMPETITIVITY_MARKETSHARE = (float) 1;
	
	public int AGENT_PERSON = 150;
	public float AGENT_PERSON_EXPEND = 0.8F;
	
	// AgentGovernment
	public float AGENT_GOVERNMENT_EMPLOYEE_TAX = 0.1F;
	public float AGENT_GOVERNMENT_FIRM_TAX = 0.1F;
	public float AGENT_GOVERNMENT_UNEMPLOYED_WAGE = 0.4F;
	public int WORLD_WAGE = 30;
	public float PS1 = 1F;
	public float PS2 = 1F;
	
	// AgentFirmCapital

	public boolean PRINT_DEBUG = false;
//	public final static String PATH = "/home/miglesia/Documents/Econ/Simu/";
	public String PATH = "EMI-";


}
