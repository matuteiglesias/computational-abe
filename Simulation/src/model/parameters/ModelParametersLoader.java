package model.parameters;

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

import engine.parameters.ParametersConfiguration;

public class ModelParametersLoader extends engine.parameters.ParametersLoader {
	
	public List<ParametersConfiguration> readConfigFile(){
		List<ParametersConfiguration> response = new ArrayList<ParametersConfiguration>();

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
					ModelParametersConfiguration parametersConfig = new ModelParametersConfiguration();
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

			ModelParametersSimulation.CONFIGURATIONS = counter - 1;

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
			PrintWriter pwP = new PrintWriter(new File(ModelParametersSimulation.PATH+"params-config-"+ModelParametersSimulation.INDEX+"_"+dateFormat.format(date)+".txt"));
			StringBuilder sbP = new StringBuilder();
			sbP.append("AGENT_FIRM_CAPITAL_Q="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_Q+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_NW="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_NW+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_MARGIN="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_MARGIN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_RD_PROPENSITY="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_RD_PROPENSITY+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_FRACTION_X="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_FRACTION_X+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_Z_IN="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_Z_IN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_Z_IM="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_Z_IM+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_Q="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_Q+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_STOCK_SPARE="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_STOCK_SPARE+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_MARGIN="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_MARGIN+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_NW="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_NW+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_PAYBACK_PERIOD="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_PAYBACK_PERIOD+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2+"\n");
			sbP.append("AGENT_FIRM_CONSUMER_OBSOLETE="+ModelParametersSimulation.AGENT_FIRM_CONSUMER_OBSOLETE+"\n");
			sbP.append("COMPETITIVITY_MARKETSHARE="+ModelParametersSimulation.COMPETITIVITY_MARKETSHARE+"\n");
			sbP.append("AGENT_PERSON="+ModelParametersSimulation.AGENT_PERSON+"\n");
			sbP.append("AGENT_PERSON_EXPEND="+ModelParametersSimulation.AGENT_PERSON_EXPEND+"\n");
			sbP.append("AGENT_GOVERNMENT_EMPLOYEE_TAX="+ModelParametersSimulation.AGENT_GOVERNMENT_EMPLOYEE_TAX+"\n");
			sbP.append("AGENT_GOVERNMENT_FIRM_TAX="+ModelParametersSimulation.AGENT_GOVERNMENT_FIRM_TAX+"\n");
			sbP.append("AGENT_GOVERNMENT_UNEMPLOYED_WAGE="+ModelParametersSimulation.AGENT_GOVERNMENT_UNEMPLOYED_WAGE+"\n");
			sbP.append("WORLD_WAGE="+ModelParametersSimulation.WORLD_WAGE+"\n");
			sbP.append("CYCLES_PER_SIMULATION="+ModelParametersSimulation.CYCLES_PER_SIMULATION+"\n");
			sbP.append("SIMULATIONS="+ModelParametersSimulation.SIMULATIONS+"\n");
			sbP.append("AGENT_FIRM_CAPITAL_BROCHURES="+ModelParametersSimulation.AGENT_FIRM_CAPITAL_BROCHURES+"\n");
			sbP.append("PRINT_DEBUG="+ModelParametersSimulation.PRINT_DEBUG+"\n");
			pwP.write(sbP.toString());
			pwP.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
