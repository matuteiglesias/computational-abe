package engine.controller;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import dao.ConnectionPool;
import dao.SimulationDAO;
import engine.entities.World;
import engine.parameters.ParametersConfiguration;
import engine.parameters.ParametersLoader;
import model.parameters.ModelParametersLoader;

public abstract class SimulationController {

	private static final Logger logger = Logger.getLogger( SimulationController.class.getName() );

	//hay que sacarlo con todas sus dependencias?
	protected static World world;

	private static SimulationDAO dao;
	
	protected static List<ParametersConfiguration> configRuns = null;
	
	protected static ParametersLoader parametersC = new ModelParametersLoader();
	
	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
		logger.info("Initializing system");

		int experimentId = 0;

		//DB - START
//		dao = SimulationDAO.getInstance();
//		dao.connect();
		dao = new SimulationDAO();
		
		experimentId = dao.insertExperiment();

		System.out.println("New Experiment ID is "+experimentId);
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
		dao.checkLabel("SalesCapitalTotal");
		dao.checkLabel("SalesConsumerTotal");

		dao.close();
		//DB - END

		//		if(true)
		//			return;

		try{
			configRuns = parametersC.readConfigFile();
			if(configRuns == null){
				logger.info("Error with config file");
				return;
			}else{
				logger.info("Config file loaded");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		BlockingQueue<ParametersConfiguration> sharedQueue = new LinkedBlockingQueue<ParametersConfiguration>();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        Runnable producer = new ConfigurationProducer(sharedQueue, experimentId, configRuns);
        Thread thread = new Thread(producer);
        thread.start();
//		executor.execute(producer); 
		
        for(int i = 0; i < 2; i++){
    		Runnable consumer = new ConfigurationConsumer(sharedQueue);
    		executor.execute(consumer); 	
        }
			
		
		ConnectionPool.getConnectionPool().realeaseConnection(dao.getConnection());

//		try{
//			for(int k = 0; k < ParametersExperiment.CONFIGURATIONS; k++){
//				int configurationId = k;
//				
//				ParametersConfiguration thisRun = configRuns.get(configurationId);
//				thisRun.CONFIGURATION_ID = configurationId;
//				thisRun.EXPERIMENT_ID = experimentId;
//				
//				Runnable worker = new ConfigurationConsumer(sharedQueue, thisRun);
//				executorConsumer.execute(worker);
//				
//			}//CONFIGURACION
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
				
		//DB - START
//		executor.shutdown();
//		while(executor.isTerminated()){
//			ConnectionPool.getConnectionPool().closeConnections();
//		}
		//DB - END
		logger.info("\n\nExiting system");
	}

	// marga
//	private static void runSimulation(int experimentId, int configurationId, ParametersConfiguration thisRun){
//		for(int j = 1; j <= ModelParametersSimulation.SIMULATIONS; j++){
//			long startTime = System.nanoTime();
//
//
//			dao.insertSimulation(experimentId, j, thisRun.INDEX);
//
////			margarita 20-11-2016
////			createWorld();
//			world = new model.world.ModelWorld();
////			margarita 20-11-2016
//
////			dao.createBatchCycle();
//			for(int i = 0; i < ModelParametersSimulation.CYCLES_PER_SIMULATION; i++){
////				logger.info("RUNNING CYCLE "+i);
//				world.runCycle();
//				dao.insertCycle(experimentId, j, thisRun.INDEX, i, (ModelWorld) world);
//				
////				DESCOMENTAR LAS TRES SIGUIENTES LINEAS PARA DEBUG DE TIEMPO QUE TOMA CADA UNO DE LOS CICLOS
////				long endTime = System.nanoTime();
////				long duration = (endTime - startTime) / 1000000;  //divide by 1000000
////				logger.info("\t\t RUNNING CYCLE "+j+" TOOK "+duration+" msecs");
//			
//			}//CICLOS DE SIMULACION
//
//			long endTime = System.nanoTime();
//			long duration = (endTime - startTime) / 1000000;  //divide by 1000000
//			logger.info("\t RUNNING CONF "+configurationId+" SIM "+j+" TOOK "+duration+" msecs");
//
//		}
//		
//	}
	
	//marga
	
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




}
