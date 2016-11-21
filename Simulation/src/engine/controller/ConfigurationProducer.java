package engine.controller;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import engine.parameters.ParametersConfiguration;
import engine.parameters.ParametersExperiment;

public class ConfigurationProducer implements Runnable{
	private static final Logger logger = Logger.getLogger( ConfigurationProducer.class.getName() );

	private final BlockingQueue<ParametersConfiguration> sharedQueue;
	protected List<ParametersConfiguration> configRuns;
	private int experimentId;

	public ConfigurationProducer(BlockingQueue<ParametersConfiguration> sharedQueue, int experimentId, List<ParametersConfiguration> configRuns) {
		this.sharedQueue = sharedQueue;
		this.experimentId = experimentId;
		this.configRuns = configRuns;
	}

	@Override
	public void run() {
		try {
			for(int k = 0; k < ParametersExperiment.CONFIGURATIONS; k++){
//				logger.info(Thread.currentThread().getName()+" STEP 1 RUNNING CONF "+k);

				int configurationId = k;

				ParametersConfiguration thisRun = configRuns.get(configurationId);
				thisRun.CONFIGURATION_ID = configurationId;
				thisRun.EXPERIMENT_ID = experimentId;

				sharedQueue.put(thisRun);

				//			logger.info("Configuracion "+ configurationId);

			}//CONFIGURACION
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(ConfigurationProducer.class.getName()).log(Level.SEVERE, null, e);

		}

	}



}
