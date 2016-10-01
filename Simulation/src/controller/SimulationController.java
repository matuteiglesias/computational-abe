package controller;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import parameters.Parameters;
import world.World;
import entities.AgentFirmCapital;
import entities.AgentFirmConsumer;
import entities.GoodCapitalVintage;



public class SimulationController {

	private static final Logger logger = Logger.getLogger( SimulationController.class.getName() );

	private static World world;

	public static float getGoodCapitalVintageProductivityA(){
		float min = Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN;
		float max = Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX;

		float random = min + (float)(Math.random() * (max - min));
		return random;
	}

	public static float getGoodCapitalProductivityB(){
		float min = Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN;
		float max = Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX;

		float random = min + (float)(Math.random() * (max - min));
		return random;
	}

	public static float getCapitalFirmNW(){
		return Parameters.AGENT_FIRM_CAPITAL_NW;
	}

	public static float getConsumerFirmNW(){
		return Parameters.AGENT_FIRM_CONSUMER_NW;
	}

	private static void createWorld(){
		world = new World();
		world.setWageCycle(Parameters.WORLD_WAGE);
		/*** BEGIN WORLD STARTUP ****/
		for(int i = 0; i < Parameters.AGENT_FIRM_CAPITAL; i++){
			AgentFirmCapital agentCapital = world.addAgentFirmCapital();
			//			agentCapital.setWage(Parameters.AGENT_FIRM_WAGE);
			agentCapital.setLiquidAssets(getCapitalFirmNW()*(0.5 + Math.random()));
			agentCapital.setProductivityB(getGoodCapitalProductivityB());
			GoodCapitalVintage vintage = new GoodCapitalVintage();

			vintage.setPrice((world.getWageCycle() / agentCapital.getProductivityB()) * (1 + Parameters.AGENT_FIRM_CAPITAL_MARGIN));
			vintage.setProductivityA(getGoodCapitalVintageProductivityA());
			agentCapital.setLastVintage(vintage);
			agentCapital.getCapitalGoodVintage().add(vintage);
		}

		for(int i = 0; i < Parameters.AGENT_FIRM_CONSUMER; i++){
			AgentFirmConsumer agentConsumer = world.addAgentFirmConsumer();
			//			agentConsumer.setWage(Parameters.AGENT_FIRM_WAGE);
			agentConsumer.setLiquidAssets(getConsumerFirmNW()*(0.75 + 0.5*Math.random()));
		}

		for(int i = 0; i < Parameters.AGENT_PERSON; i++){
			world.addAgentPerson();
		}

		world.employPeople();
	}
	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
		logger.info("Initializing system");


		/*** END WORLD STARTUP ****/
		try{
			printParametersFile();
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//<<<<<<< HEAD
//			PrintWriter pw = new PrintWriter(new File("/home/miglesia/Documents/Economia/Simu/simulation-"+dateFormat.format(date)+".csv"));
//=======
			PrintWriter pw = new PrintWriter(new File(Parameters.PATH+"simulation-"+dateFormat.format(date)+".csv"));
//>>>>>>> branch 'master' of ssh://git@github.com/matuteiglesias/computational-abe.git
			StringBuilder sb = new StringBuilder();

			sb.append("Simulation");
			sb.append(',');
			sb.append("Cycle");
			sb.append(',');
			sb.append("Consumption");
			sb.append(',');
			sb.append("Employed");
			sb.append(',');
			sb.append("FabricatedCapital");
			sb.append(',');
			sb.append("FabricatedConsumer");
			sb.append(',');
			sb.append("InvestmentCapital");
			sb.append(',');
			sb.append("InvestmentConsumer");
			sb.append(',');
			sb.append("AvgProdA_MS");
			sb.append(',');
			sb.append("AvgProdA_EMP");
			sb.append(',');
			sb.append("AvgProdB_MS");
			sb.append(',');
			sb.append("AvgProdB_EMP");
			sb.append(',');
			sb.append("Consumer_EMP");
			sb.append(',');
			sb.append("Capital_EMP");
			sb.append(',');
			sb.append("IPC");
			sb.append(',');
			sb.append("Capital bankrupts");
			sb.append(',');
			sb.append("Consumer bankrupts");
			sb.append(',');
			sb.append("WAGE");
			sb.append('\n');
			pw.write(sb.toString());

			for(int j = 1; j <= Parameters.SIMULATIONS; j++){
				logger.info("\n\nRUNNING SIMULATION "+j);
				createWorld();

				for(int i = 0; i < Parameters.CYCLES; i++){
					world.runCycle();
					sb = new StringBuilder();

					sb.append(j);
					sb.append(',');
					sb.append(i);
					sb.append(',');
					sb.append(world.getConsumptionHistory().get(i));
					sb.append(',');
					sb.append(world.getEmployedHistory().get(i));
					sb.append(',');
					sb.append(world.fabricatedCapitalTotal());
					sb.append(',');
					sb.append(world.fabricatedConsumerTotal());
					sb.append(',');
					sb.append(world.investmentCapitalTotal());
					sb.append(',');
					sb.append(world.investmentConsumerTotal());
					sb.append(',');
					sb.append(world.averageProdAMS());
					sb.append(',');
					sb.append(world.averageProdAEMP());
					sb.append(',');
					sb.append(world.averageProdBMS());
					sb.append(',');
					sb.append(world.averageProdBEMP());
					sb.append(',');
					sb.append(world.consumerEMP());
					sb.append(',');
					sb.append(world.capitalEMP());
					sb.append(',');
					sb.append(world.getIpcHistory().get(i));
					sb.append(',');
					sb.append(world.getCapitalBankruptHistory().get(i));
					sb.append(',');
					sb.append(world.getConsumerBankruptHistory().get(i));
					sb.append(',');
					sb.append(world.getWageHistory().get(i));
					sb.append('\n');
					pw.write(sb.toString());

				}
				

				logger.info("\n\nPrinting summary SIMMULATION "+j);

				//				world.printSummary();

			}

			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("\n\nExiting system");
	}

	public static void printParametersFile(){

		try{
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//<<<<<<< HEAD
//			PrintWriter pwP = new PrintWriter(new File("/home/miglesia/Documents/Economia/Simu/simulation-"+dateFormat.format(date)+"-Params.txt"));
//=======
			PrintWriter pwP = new PrintWriter(new File(Parameters.PATH+"simulation-"+dateFormat.format(date)+"-Params.txt"));
//>>>>>>> branch 'master' of ssh://git@github.com/matuteiglesias/computational-abe.git
			StringBuilder sbP = new StringBuilder();
			sbP.append("AGENT_FIRM_CAPITAL="+Parameters.AGENT_FIRM_CAPITAL+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_NW="+Parameters.AGENT_FIRM_CAPITAL_NW+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_MARGIN="+Parameters.AGENT_FIRM_CAPITAL_MARGIN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_RD_PROPENSITY="+Parameters.AGENT_FIRM_CAPITAL_RD_PROPENSITY+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_FRACTION_X="+Parameters.AGENT_FIRM_CAPITAL_FRACTION_X+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_Z_IN="+Parameters.AGENT_FIRM_CAPITAL_Z_IN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_Z_IM="+Parameters.AGENT_FIRM_CAPITAL_Z_IM+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN="+Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX="+Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN="+Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX="+Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX+"\n");
			sbP.append("AGENT_FIRM_CONSUMER="+Parameters.AGENT_FIRM_CONSUMER+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_STOCK_SPARE="+Parameters.AGENT_FIRM_CONSUMER_STOCK_SPARE+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_MARGIN="+Parameters.AGENT_FIRM_CONSUMER_MARGIN+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY="+Parameters.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_NW="+Parameters.AGENT_FIRM_CONSUMER_NW+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_PAYBACK_PERIOD="+Parameters.AGENT_FIRM_CONSUMER_PAYBACK_PERIOD+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1="+Parameters.AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2="+Parameters.AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_OBSOLETE="+Parameters.AGENT_FIRM_CONSUMER_OBSOLETE+"\n");
			sbP.append("COMPETITIVITY_MARKETSHARE="+Parameters.COMPETITIVITY_MARKETSHARE+"\n");
			sbP.append("AGENT_PERSON="+Parameters.AGENT_PERSON+"\n");
			sbP.append("AGENT_PERSON_EXPEND="+Parameters.AGENT_PERSON_EXPEND+"\n");
			sbP.append("AGENT_GOVERNMENT_EMPLOYEE_TAX="+Parameters.AGENT_GOVERNMENT_EMPLOYEE_TAX+"\n");
			sbP.append("AGENT_GOVERNMENT_FIRM_TAX="+Parameters.AGENT_GOVERNMENT_FIRM_TAX+"\n");
			sbP.append("AGENT_GOVERNMENT_UNEMPLOYED_WAGE="+Parameters.AGENT_GOVERNMENT_UNEMPLOYED_WAGE+"\n");
			sbP.append("WORLD_WAGE="+Parameters.WORLD_WAGE+"\n");
			sbP.append("CYCLES="+Parameters.CYCLES+"\n");
			sbP.append("SIMULATIONS="+Parameters.SIMULATIONS+"\n");
			sbP.append("SEND_BROCHURE="+Parameters.SEND_BROCHURE+"\n");
			sbP.append("PRINT_DEBUG="+Parameters.PRINT_DEBUG+"\n");
			pwP.write(sbP.toString());
			pwP.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
