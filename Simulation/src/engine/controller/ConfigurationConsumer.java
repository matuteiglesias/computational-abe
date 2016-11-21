package engine.controller;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import dao.ConnectionPool;
import dao.SimulationDAO;
import engine.parameters.ParametersConfiguration;
import model.parameters.ModelParametersSimulation;
import model.world.ModelWorld;

public class ConfigurationConsumer implements Runnable {
	private static final Logger logger = Logger.getLogger( ConfigurationConsumer.class.getName() );
    private BlockingQueue<ParametersConfiguration> sharedQueue;

	private SimulationDAO dao = new SimulationDAO();
	protected ModelWorld world;

	private int experimentId;
	private int configurationId;
	private String configurationName;
	private ParametersConfiguration thisRun;
	
	public ConfigurationConsumer(BlockingQueue<ParametersConfiguration> sharedQueue){
		this.sharedQueue = sharedQueue;

	}
	
	@Override
	public void run() {
		try {
			
			while(sharedQueue.size() > 0){

				this.thisRun = sharedQueue.take();
				this.experimentId = thisRun.EXPERIMENT_ID;
				this.configurationId = thisRun.CONFIGURATION_ID;
				this.configurationName = thisRun.CONFIGURATION_NAME;
				
				dao.insertConfiguration(experimentId, configurationId, configurationName);
				
				for(int j = 1; j <= thisRun.SIMULATIONS; j++){
					long startTime = System.nanoTime();
					dao.prepare();
					int simulationId = j;

					ModelParametersSimulation parameters = thisRun.setSimulationParameters();
					
//					logger.info(Thread.currentThread().getName()+" insertSimulation(expId "+experimentId+", ConfId "+configurationId+")");
					dao.insertSimulation(experimentId, configurationId, simulationId);

//					long endTime = System.nanoTime();
//					long duration = (endTime - startTime) / 1000000;  //divide by 1000000
//					logger.info(Thread.currentThread().getName()+" STOP 1 TOOK "+duration+" msecs");

					world = new model.world.ModelWorld(parameters);

					for(int i = 0; i < parameters.CYCLES; i++){
						int cycleId = i;
						world.runCycle();
//						long endTime = System.nanoTime();
//						long duration = (endTime - startTime) / 1000000;  //divide by 1000000
//						logger.info(Thread.currentThread().getName()+" STEP 2 TOOK "+duration+" msecs");
						
//						logger.info(Thread.currentThread().getName()+" insertCycle(expId "+experimentId+", ConfId "+configurationId+", simId "+simulationId+", cycleId "+cycleId+")");
						dao.insertCycle(experimentId, configurationId, simulationId, cycleId, world);

//						endTime = System.nanoTime();
//						duration = (endTime - startTime) / 1000000;  //divide by 1000000
//						logger.info(Thread.currentThread().getName()+" STEP 3 TOOK "+duration+" msecs");
						
//						DESCOMENTAR LAS TRES SIGUIENTES LINEAS PARA DEBUG DE TIEMPO QUE TOMA CADA UNO DE LOS CICLOS
//						long endTime = System.nanoTime();
//						long duration = (endTime - startTime) / 1000000;  //divide by 1000000
//						logger.info("\t\t RUNNING CYCLE "+j+" TOOK "+duration+" msecs");
					}//CICLOS DE SIMULACION

//					endTime = System.nanoTime();
//					duration = (endTime - startTime) / 1000000;  //divide by 1000000
//					logger.info(Thread.currentThread().getName()+" STEP 4 TOOK "+duration+" msecs");
					
					dao.finish();
					dao.close();
					long endTime = System.nanoTime();
					long duration = (endTime - startTime) / 1000000;  //divide by 1000000
					logger.info(Thread.currentThread().getName()+" RUNNING CONF "+configurationId+" SIM "+j+" CYCLES "+parameters.CYCLES+" TOOK "+duration+" msecs");

				}
			}
			ConnectionPool.getConnectionPool().realeaseConnection(dao.getConnection());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
