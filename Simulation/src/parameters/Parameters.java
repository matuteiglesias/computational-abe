package parameters;

public class Parameters {

	public static int RUNS;
	public static String INDEX;

	// SimulationController

	public static int AGENT_FIRM_CAPITAL_Q = 10;
	public static int AGENT_FIRM_CAPITAL_NW = 1000;
	public static float AGENT_FIRM_CAPITAL_MARGIN = 1.5F;
	public static float AGENT_FIRM_CAPITAL_RD_PROPENSITY = 0.04F;
	public static float AGENT_FIRM_CAPITAL_FRACTION_X = 0.5F;
	public static float AGENT_FIRM_CAPITAL_Z_IN = 0.3F;
	public static float AGENT_FIRM_CAPITAL_Z_IM = 0.3F;
	public static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN = 5.0F;
	public static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX = 15.0F;
	public static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN = 5.0F;
	public static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX = 10.0F;
	public static int AGENT_FIRM_CAPITAL_BROCHURES = 10;
	
	public static int AGENT_FIRM_CONSUMER_Q = 40;
	public static int AGENT_FIRM_CONSUMER_STOCK_SPARE = 100;
	public static float AGENT_FIRM_CONSUMER_MARGIN = 1.5F;
	public static float AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY = 0.4F;
	public static int AGENT_FIRM_CONSUMER_NW = 4000;
	public static int AGENT_FIRM_CONSUMER_PAYBACK_PERIOD = 3;
	public static int AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 = 1;
	public static int AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2 = 1;
	public static int AGENT_FIRM_CONSUMER_OBSOLETE = 20;
	public static float COMPETITIVITY_MARKETSHARE = (float) 1;
	
	
	public static int AGENT_PERSON = 150;
	public static float AGENT_PERSON_EXPEND = 0.8F;
	
	// AgentGovernment
	public static float AGENT_GOVERNMENT_EMPLOYEE_TAX = 0.1F;
	public static float AGENT_GOVERNMENT_FIRM_TAX = 0.1F;
	public static float AGENT_GOVERNMENT_UNEMPLOYED_WAGE = 0.4F;
	public static int WORLD_WAGE = 30;
	public static float PS1 = 1F;
	public static float PS2 = 1F;

	public static int CYCLES_PER_SIMULATION = 250;
	public static int SIMULATIONS = 20;

	
	// AgentFirmCapital

	public static boolean PRINT_DEBUG = false;
//	public final static String PATH = "/home/miglesia/Documents/Econ/Simu/";
	public static String PATH = "EMI-";


}
