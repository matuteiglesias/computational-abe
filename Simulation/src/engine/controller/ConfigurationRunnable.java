package engine.controller;

import java.util.logging.Logger;

import dao.SimulationDAO;
import engine.entities.World;
import engine.parameters.ParametersConfiguration;
import model.parameters.ModelParametersSimulation;
import model.world.ModelWorld;

public class ConfigurationRunnable implements Runnable {
	private static final Logger logger = Logger.getLogger( ConfigurationRunnable.class.getName() );

	private SimulationDAO dao = SimulationDAO.getInstance();
	protected World world;

	
	private int experimentId;
	private int configurationId;
//	private int simulationId;
	private ParametersConfiguration thisRun;
	
	
	
	public ConfigurationRunnable(int experimentId, int configurationId, ParametersConfiguration thisRun){
		this.experimentId = experimentId;
		this.configurationId = configurationId;
		this.thisRun = thisRun;
	}
	
	@Override
	public void run() {
		for(int j = 1; j <= ModelParametersSimulation.SIMULATIONS; j++){
			long startTime = System.nanoTime();

			int simulationId = j;

			dao.insertSimulation(experimentId, simulationId, thisRun.INDEX);
//			public void insertSimulation(int experimentId, int simulationId, String configurationId){

//			margarita 20-11-2016
//			createWorld();
			world = new model.world.ModelWorld();
//			margarita 20-11-2016

//			dao.createBatchCycle();
			for(int i = 0; i < ModelParametersSimulation.CYCLES_PER_SIMULATION; i++){
				logger.info("RUNNING EXPERIMENT "+experimentId+" CONFIGURATION "+configurationId+" SIMULATION "+simulationId+" CYCLE "+i);
				world.runCycle();
				dao.insertCycle(experimentId, simulationId, thisRun.INDEX, i, (ModelWorld) world);
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
