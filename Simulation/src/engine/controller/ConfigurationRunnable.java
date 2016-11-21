package engine.controller;

import java.util.logging.Logger;

import dao.SimulationDAO;
import engine.parameters.ParametersConfiguration;
import model.parameters.ModelParametersConfiguration;
import model.parameters.ModelParametersSimulation;
import model.world.ModelWorld;

public class ConfigurationRunnable implements Runnable {
	private static final Logger logger = Logger.getLogger( ConfigurationRunnable.class.getName() );

	private SimulationDAO dao = new SimulationDAO();
	protected ModelWorld world;

	
	private int experimentId;
	private int configurationId;
	private ParametersConfiguration thisRun;
	
	
	
	public ConfigurationRunnable(int experimentId, int configurationId, ParametersConfiguration thisRun){
		this.experimentId = experimentId;
		this.configurationId = configurationId;
		this.thisRun = thisRun;
	}
	
	@Override
	public void run() {
		for(int j = 1; j <= ModelParametersConfiguration.SIMULATIONS; j++){
			long startTime = System.nanoTime();

			dao.prepare();
			int simulationId = j;

			ModelParametersSimulation parameters = thisRun.setSimulationParameters();
			dao.insertConfiguration(experimentId, configurationId, thisRun.NAME);
			
			logger.info(Thread.currentThread().getName()+" insertSimulation(expId "+experimentId+", ConfId "+configurationId+")");
			dao.insertSimulation(experimentId, configurationId, simulationId);

//			public void insertSimulation(int experimentId, int simulationId, String configurationId){

//			margarita 20-11-2016
//			createWorld();
			world = new model.world.ModelWorld(parameters);
//			margarita 20-11-2016

//			dao.createBatchCycle();
			logger.info("CYCLES "+parameters.CYCLES);
			for(int i = 0; i < parameters.CYCLES; i++){
				int cycleId = i;
//				logger.info(Thread.currentThread().getName()+ "RUNNING EXPERIMENT "+experimentId+" CONFIGURATION "+configurationId+" SIMULATION "+simulationId+" CYCLE "+i);
				world.runCycle();
//				logger.info(Thread.currentThread().getName()+" insertCycle(expId "+experimentId+", ConfId "+configurationId+", simId "+simulationId+", cycleId "+cycleId+")");
				dao.insertCycle(experimentId, configurationId, simulationId, cycleId, world);
//				public void insertCycle(int experimentId, int simulationId, String configurationId, int cycleId, ModelWorld world){

//				DESCOMENTAR LAS TRES SIGUIENTES LINEAS PARA DEBUG DE TIEMPO QUE TOMA CADA UNO DE LOS CICLOS
//				long endTime = System.nanoTime();
//				long duration = (endTime - startTime) / 1000000;  //divide by 1000000
//				logger.info("\t\t RUNNING CYCLE "+j+" TOOK "+duration+" msecs");
			}//CICLOS DE SIMULACION

			long endTime = System.nanoTime();
			long duration = (endTime - startTime) / 1000000;  //divide by 1000000
			logger.info("\t RUNNING CONF "+configurationId+" SIM "+j+" TOOK "+duration+" msecs");

		}
	}

}
