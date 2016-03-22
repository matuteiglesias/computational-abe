package parameters;

public class Parameters {

	// SimulationController
	
	public final static int AGENT_FIRM_CAPITAL = 10;
	public final static int AGENT_FIRM_CAPITAL_NW = 1000;
	public final static float AGENT_FIRM_CAPITAL_MARGIN = 1.2F;
	public final static float AGENT_FIRM_CAPITAL_RD_PROPENSITY = 0.04F;
	public final static float AGENT_FIRM_CAPITAL_FRACTION_X = 0.5F;
	public final static float AGENT_FIRM_CAPITAL_Z_IN = 0.3F;
	public final static float AGENT_FIRM_CAPITAL_Z_IM = 0.3F;
	public final static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN = 5.0F;
	public final static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX = 15.0F;
	public final static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN = 5.0F;
	public final static float AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX = 10.0F;
	
	public final static int AGENT_FIRM_CONSUMER = 40;
	public final static int AGENT_FIRM_CONSUMER_STOCK_SPARE = 100;
	public final static float AGENT_FIRM_CONSUMER_MARGIN = 1.2F;
	public final static float AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY = 0.4F;
	public final static int AGENT_FIRM_CONSUMER_NW = 4000;
	public final static int AGENT_FIRM_CONSUMER_PAYBACK_PERIOD = 10;
	public final static int AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 = 1;
	public final static int AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2 = 1;
	public final static int AGENT_FIRM_CONSUMER_OBSOLETE = 20;
	public final static float COMPETITIVITY_MARKETSHARE = (float) 0.1;
	
	
	public final static int AGENT_PERSON = 150;
	public final static float AGENT_PERSON_EXPEND = 0.8F;
	
	// AgentGovernment
	public final static float AGENT_GOVERNMENT_EMPLOYEE_TAX = 0.1F;
	public final static float AGENT_GOVERNMENT_FIRM_TAX = 0.1F;
	public final static float AGENT_GOVERNMENT_UNEMPLOYED_WAGE = 0.4F;
	
	public final static int WORLD_WAGE = 30;
	public final static int CYCLES = 450;
	public final static int SIMULATIONS = 100;
	
	// AgentFirmCapital
	public final static int SEND_BROCHURE = 10;

	
	public final static boolean PRINT_DEBUG = false;

}
