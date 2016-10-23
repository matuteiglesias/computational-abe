package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import parameters.Parameters;
import parameters.ParametersConfig;
import world.World;
import dao.SimulationDAO;
import entities.AgentFirmCapital;
import entities.AgentFirmConsumer;
import entities.GoodCapitalVintage;



public class SimulationController {

	private static final Logger logger = Logger.getLogger( SimulationController.class.getName() );

	private static World world;
	
	private static SimulationDAO dao;

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
		for(int i = 0; i < Parameters.AGENT_FIRM_CAPITAL_Q; i++){
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

		for(int i = 0; i < Parameters.AGENT_FIRM_CONSUMER_Q; i++){
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

		List<ParametersConfig> configRuns = null;
		
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		
		
		int experimentId = 0;
		
		//DB - START
		dao = SimulationDAO.getInstance();
		dao.connect();
		
		experimentId = dao.insertExperiment();
		
		System.out.println("New Experiment ID is "+experimentId);
		dao.checkLabel("MATIAS");
		dao.checkLabel("Consumption");			
		dao.checkLabel("Employed");
		dao.checkLabel("FabricatedCapital");	
		dao.checkLabel("FabricatedConsumer");
		dao.checkLabel("InvestmentCapital");
		dao.checkLabel("AvgProdA_MS");
		dao.checkLabel("AvgProdA_EMP");
		dao.checkLabel("AvgProdB_MS");
		dao.checkLabel("AvgProdB_EMP");
		dao.checkLabel("Consumer_EMP");
		dao.checkLabel("Capital_EMP");
		dao.checkLabel("IPC");
		dao.checkLabel("Capital_Bankrupts");
		dao.checkLabel("Consumer_Bankrupts");
		dao.checkLabel("WAGE");
		//DB - END
		
//		if(true)
//			return;
		
		try{
			configRuns = readConfigFile();
			if(configRuns == null){
				logger.info("Error with config file");
				return;
			}else{
				logger.info("Config file loaded");
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			for(int k = 0; k < Parameters.RUNS; k++){
				ParametersConfig thisRun = configRuns.get(k);
				thisRun.setSimulationParameters();
//				printParametersFile();
				
				dao.insertConfiguration(thisRun, experimentId);

			
				//			PrintWriter pw = new PrintWriter(new File("/home/miglesia/Documents/Economia/Simu/simulation-"+dateFormat.format(date)+".csv"));
				
					
//				PrintWriter pw = new PrintWriter(new File(Parameters.PATH+"sim-config-"+Parameters.INDEX+"_"+dateFormat.format(date)+".csv"));
//				StringBuilder sb = new StringBuilder();
//				sb.append("Simulation");
//				sb.append(',');
//				sb.append("Cycle");
//				sb.append(',');
//				sb.append("Consumption");
//				sb.append(',');
//				sb.append("Employed");
//				sb.append(',');
//				sb.append("FabricatedCapital");
//				sb.append(',');
//				sb.append("FabricatedConsumer");
//				sb.append(',');
//				sb.append("InvestmentCapital");
//				sb.append(',');
//				sb.append("InvestmentConsumer");
//				sb.append(',');
//				sb.append("AvgProdA_MS");
//				sb.append(',');
//				sb.append("AvgProdA_EMP");
//				sb.append(',');
//				sb.append("AvgProdB_MS");
//				sb.append(',');
//				sb.append("AvgProdB_EMP");
//				sb.append(',');
//				sb.append("Consumer_EMP");
//				sb.append(',');
//				sb.append("Capital_EMP");
//				sb.append(',');
//				sb.append("IPC");
//				sb.append(',');
//				sb.append("Capital bankrupts");
//				sb.append(',');
//				sb.append("Consumer bankrupts");
//				sb.append(',');
//				sb.append("WAGE");
//				sb.append('\n');
//				pw.write(sb.toString());

				for(int j = 1; j <= Parameters.SIMULATIONS; j++){
					logger.info("\n\nRUNNING SIMULATION "+j);
					
					dao.insertSimulation(experimentId, j, thisRun.INDEX);
					
					createWorld();
					System.gc();
					for(int i = 0; i < Parameters.CYCLES_PER_SIMULATION; i++){
						world.runCycle();
						long startTime = System.nanoTime();
						dao.insertCycle(experimentId, j, thisRun.INDEX, i, world);
						long endTime = System.nanoTime();

						long duration = (endTime - startTime) / 1000000;  //divide by 1000000
						System.out.println("INSERT CYCLE DURATION IN Msecs "+duration);
//
//						sb = new StringBuilder();
//
//						sb.append(j);
//						sb.append(',');
//						sb.append(i);
//						sb.append(',');
//						sb.append(world.getConsumptionHistory().get(i));
//						sb.append(',');
//						sb.append(world.getEmployedHistory().get(i));
//						sb.append(',');
//						sb.append(world.fabricatedCapitalTotal());
//						sb.append(',');
//						sb.append(world.fabricatedConsumerTotal());
//						sb.append(',');
//						sb.append(world.investmentCapitalTotal());
//						sb.append(',');
//						sb.append(world.investmentConsumerTotal());
//						sb.append(',');
//						sb.append(world.averageProdAMS());
//						sb.append(',');
//						sb.append(world.averageProdAEMP());
//						sb.append(',');
//						sb.append(world.averageProdBMS());
//						sb.append(',');
//						sb.append(world.averageProdBEMP());
//						sb.append(',');
//						sb.append(world.consumerEMP());
//						sb.append(',');
//						sb.append(world.capitalEMP());
//						sb.append(',');
//						sb.append(world.getIpcHistory().get(i));
//						sb.append(',');
//						sb.append(world.getCapitalBankruptHistory().get(i));
//						sb.append(',');
//						sb.append(world.getConsumerBankruptHistory().get(i));
//						sb.append(',');
//						sb.append(world.getWageHistory().get(i));
//						sb.append('\n');
//						pw.write(sb.toString());

					}

					logger.info("\n\nPrinting summary SIMMULATION "+j);

					//				world.printSummary();

				}

//				pw.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//DB - START
		dao.close();
		//DB - END
		logger.info("\n\nExiting system");
	}

//	public static void main(String[] args) {
//		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
//		logger.info("Initializing system");
//
//		List<ParametersConfig> configRuns = null;
//		
//		Date date = new Date() ;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//		
//		
//		int experimentId = 0;
//		
//		//DB - START
//		dao = SimulationDAO.getInstance();
//		dao.connect();
//		experimentId = dao.insertExperiment();
//		System.out.println("New Experiment ID is "+experimentId);
//		//DB - END
//		
//		
//		try{
//			configRuns = readConfigFile();
//			if(configRuns == null){
//				logger.info("Error with config file");
//				return;
//			}else{
//				logger.info("Config file loaded");
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//
//		try{
//			for(int k = 0; k < Parameters.RUNS; k++){
//				configRuns.get(k).setSimulationParameters();
//
//				printParametersFile();
//			
//				//			PrintWriter pw = new PrintWriter(new File("/home/miglesia/Documents/Economia/Simu/simulation-"+dateFormat.format(date)+".csv"));
//				
//					
//				PrintWriter pw = new PrintWriter(new File(Parameters.PATH+"sim-config-"+Parameters.INDEX+"_"+dateFormat.format(date)+".csv"));
//				StringBuilder sb = new StringBuilder();
//				sb.append("Simulation");
//				sb.append(',');
//				sb.append("Cycle");
//				sb.append(',');
//				sb.append("Consumption");
//				sb.append(',');
//				sb.append("Employed");
//				sb.append(',');
//				sb.append("FabricatedCapital");
//				sb.append(',');
//				sb.append("FabricatedConsumer");
//				sb.append(',');
//				sb.append("InvestmentCapital");
//				sb.append(',');
//				sb.append("InvestmentConsumer");
//				sb.append(',');
//				sb.append("AvgProdA_MS");
//				sb.append(',');
//				sb.append("AvgProdA_EMP");
//				sb.append(',');
//				sb.append("AvgProdB_MS");
//				sb.append(',');
//				sb.append("AvgProdB_EMP");
//				sb.append(',');
//				sb.append("Consumer_EMP");
//				sb.append(',');
//				sb.append("Capital_EMP");
//				sb.append(',');
//				sb.append("IPC");
//				sb.append(',');
//				sb.append("Capital bankrupts");
//				sb.append(',');
//				sb.append("Consumer bankrupts");
//				sb.append(',');
//				sb.append("WAGE");
//				sb.append('\n');
//				pw.write(sb.toString());
//
//				for(int j = 1; j <= Parameters.SIMULATIONS; j++){
//					logger.info("\n\nRUNNING SIMULATION "+j);
//					createWorld();
//
//					for(int i = 0; i < Parameters.CYCLES_PER_SIMULATION; i++){
//						world.runCycle();
//						sb = new StringBuilder();
//
//						sb.append(j);
//						sb.append(',');
//						sb.append(i);
//						sb.append(',');
//						sb.append(world.getConsumptionHistory().get(i));
//						sb.append(',');
//						sb.append(world.getEmployedHistory().get(i));
//						sb.append(',');
//						sb.append(world.fabricatedCapitalTotal());
//						sb.append(',');
//						sb.append(world.fabricatedConsumerTotal());
//						sb.append(',');
//						sb.append(world.investmentCapitalTotal());
//						sb.append(',');
//						sb.append(world.investmentConsumerTotal());
//						sb.append(',');
//						sb.append(world.averageProdAMS());
//						sb.append(',');
//						sb.append(world.averageProdAEMP());
//						sb.append(',');
//						sb.append(world.averageProdBMS());
//						sb.append(',');
//						sb.append(world.averageProdBEMP());
//						sb.append(',');
//						sb.append(world.consumerEMP());
//						sb.append(',');
//						sb.append(world.capitalEMP());
//						sb.append(',');
//						sb.append(world.getIpcHistory().get(i));
//						sb.append(',');
//						sb.append(world.getCapitalBankruptHistory().get(i));
//						sb.append(',');
//						sb.append(world.getConsumerBankruptHistory().get(i));
//						sb.append(',');
//						sb.append(world.getWageHistory().get(i));
//						sb.append('\n');
//						pw.write(sb.toString());
//
//					}
//
//					logger.info("\n\nPrinting summary SIMMULATION "+j);
//
//					//				world.printSummary();
//
//				}
//
//				pw.close();
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		//DB - START
//		dao.close();
//		//DB - END
//		logger.info("\n\nExiting system");
//	}

	
	
	public static List<ParametersConfig> readConfigFile(){
		List<ParametersConfig> response = new ArrayList<ParametersConfig>();

		// AQUI CAMBIAR EL PATH DEL ARCHIVO DE CONFIGURACION CSV QUE AHORA ESTA EN EL ECLIPSE.
//		String csvFile = "/home/miglesia/Simulaciones/SimConfig.csv";
		String csvFile = "SimConfig.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int counter = 0;

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(counter != 0)
				{
					ParametersConfig parametersConfig = new ParametersConfig();
					// use comma as separator
					String[] config = line.split(cvsSplitBy);

					parametersConfig.INDEX = config[0];
					parametersConfig.CYCLES_PER_SIMULATION = Integer.parseInt(config[1]);
					parametersConfig.SIMULATIONS = Integer.parseInt(config[2]);
					parametersConfig.PRINT_DEBUG = Boolean.parseBoolean(config[3]);
					parametersConfig.PATH = config[4];
					parametersConfig.AGENT_FIRM_CAPITAL_Q = Integer.parseInt(config[5]);
					parametersConfig.AGENT_FIRM_CAPITAL_NW = Integer.parseInt(config[6]);
					parametersConfig.AGENT_FIRM_CAPITAL_MARGIN = Float.parseFloat(config[7]);
					parametersConfig.AGENT_FIRM_CAPITAL_RD_PROPENSITY = Float.parseFloat(config[8]);
					parametersConfig.AGENT_FIRM_CAPITAL_FRACTION_X = Float.parseFloat(config[9]);
					parametersConfig.AGENT_FIRM_CAPITAL_Z_IN = Float.parseFloat(config[10]);
					parametersConfig.AGENT_FIRM_CAPITAL_Z_IM = Float.parseFloat(config[11]);
					parametersConfig.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN = Float.parseFloat(config[12]);
					parametersConfig.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX = Float.parseFloat(config[13]);
					parametersConfig.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN = Float.parseFloat(config[14]);
					parametersConfig.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX = Float.parseFloat(config[15]);
					parametersConfig.AGENT_FIRM_CAPITAL_BROCHURES = Integer.parseInt(config[16]);
					parametersConfig.AGENT_FIRM_CONSUMER_Q = Integer.parseInt(config[17]);
					parametersConfig.AGENT_FIRM_CONSUMER_STOCK_SPARE = Integer.parseInt(config[18]);
					parametersConfig.AGENT_FIRM_CONSUMER_MARGIN = Float.parseFloat(config[19]);
					parametersConfig.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY = Float.parseFloat(config[20]);
					parametersConfig.AGENT_FIRM_CONSUMER_NW = Integer.parseInt(config[21]);
					parametersConfig.AGENT_FIRM_CONSUMER_PAYBACK_PERIOD = Integer.parseInt(config[22]);
					parametersConfig.AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 = Integer.parseInt(config[23]);
					parametersConfig.AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2 = Integer.parseInt(config[24]);
					parametersConfig.AGENT_FIRM_CONSUMER_OBSOLETE = Integer.parseInt(config[25]);
					parametersConfig.COMPETITIVITY_MARKETSHARE = Float.parseFloat(config[26]);
					parametersConfig.AGENT_PERSON = Integer.parseInt(config[27]);
					parametersConfig.AGENT_PERSON_EXPEND = Float.parseFloat(config[28]);
					parametersConfig.AGENT_GOVERNMENT_EMPLOYEE_TAX = Float.parseFloat(config[29]);
					parametersConfig.AGENT_GOVERNMENT_FIRM_TAX = Float.parseFloat(config[30]);
					parametersConfig.AGENT_GOVERNMENT_UNEMPLOYED_WAGE = Float.parseFloat(config[31]);
					parametersConfig.WORLD_WAGE = Integer.parseInt(config[32]);
					parametersConfig.PS1 = Float.parseFloat(config[33]);
					parametersConfig.PS2 = Float.parseFloat(config[34]);

					response.add(parametersConfig);
				}
				counter++;
			}

			Parameters.RUNS = counter - 1;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;

	}
	public static void printParametersFile(){

		try{
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			//			PrintWriter pwP = new PrintWriter(new File("/home/miglesia/Documents/Economia/Simu/simulation-"+dateFormat.format(date)+"-Params.txt"));
			PrintWriter pwP = new PrintWriter(new File(Parameters.PATH+"params-config-"+Parameters.INDEX+"_"+dateFormat.format(date)+".txt"));
			StringBuilder sbP = new StringBuilder();
			sbP.append("AGENT_FIRM_CAPITAL_Q="+Parameters.AGENT_FIRM_CAPITAL_Q+"\n");
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
			sbP.append("AGENT_FIRM_CONSUMER_Q="+Parameters.AGENT_FIRM_CONSUMER_Q+"\n");
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
			sbP.append("CYCLES_PER_SIMULATION="+Parameters.CYCLES_PER_SIMULATION+"\n");
			sbP.append("SIMULATIONS="+Parameters.SIMULATIONS+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_BROCHURES="+Parameters.AGENT_FIRM_CAPITAL_BROCHURES+"\n");
			sbP.append("PRINT_DEBUG="+Parameters.PRINT_DEBUG+"\n");
			pwP.write(sbP.toString());
			pwP.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
