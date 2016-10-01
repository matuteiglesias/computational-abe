package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import parameters.Parameters;
import controller.SimulationController;
import dtos.AgentDTO;
import dtos.BrochureDTO;
import entities.Agent;
import entities.AgentFirm;
import entities.AgentFirmCapital;
import entities.AgentFirmConsumer;
import entities.AgentFirmConsumerStock;
import entities.AgentGovernment;
import entities.AgentPerson;
import entities.GoodCapital;
import entities.GoodCapitalVintage;
import entities.GoodConsumer;

public class World {
	private static final Logger logger = Logger.getLogger( World.class.getName() );

	private int cycle = 1;
	private int idAgentCounter = 0;

	private List<Agent> agents = new ArrayList<Agent>();

	private List<AgentFirm> firmAgents = new ArrayList<AgentFirm>();

	private List<AgentFirmCapital> capitalFirmAgents = new ArrayList<AgentFirmCapital>();

	private List<AgentFirmConsumer> consumerFirmAgents = new ArrayList<AgentFirmConsumer>();

	private List<AgentPerson> personAgents = new ArrayList<AgentPerson>();

	private List<AgentFirmConsumerStock> stocks = new ArrayList<AgentFirmConsumerStock>();

	private AgentGovernment government;

	private List<WorldCycle> worldCycles = new ArrayList<WorldCycle>();

	protected float wage;

	public float consumptionCycle;

	private List<Float> consumptionHistory = new ArrayList<Float>();

	private List<Float> employedHistory = new ArrayList<Float>();

	private List<Float> unemployedHistory = new ArrayList<Float>();
	
	private int capitalBankruptCount;
	
	private List<Integer> capitalBankruptHistory = new ArrayList<Integer>();

	private int consumerBankruptCount;
	
	private List<Integer> consumerBankruptHistory = new ArrayList<Integer>();

	private float competitivityAverageCycle;

	private List<Float> competitivityAverageHistory = new ArrayList<Float>();

	/*******/

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public List<AgentFirm> getFirmAgents() {
		return firmAgents;
	}

	public void setFirmAgents(List<AgentFirm> firmAgents) {
		this.firmAgents = firmAgents;
	}

	public int getCapitalBankruptCount() {
		return capitalBankruptCount;
	}

	public void setCapitalBankruptCount(int capitalBankruptCount) {
		this.capitalBankruptCount = capitalBankruptCount;
	}

	public List<Integer> getCapitalBankruptHistory() {
		return capitalBankruptHistory;
	}

	public void setCapitalBankruptHistory(List<Integer> capitalBankruptHistory) {
		this.capitalBankruptHistory = capitalBankruptHistory;
	}

	public int getConsumerBankruptCount() {
		return consumerBankruptCount;
	}

	public void setConsumerBankruptCount(int consumerBankruptCount) {
		this.consumerBankruptCount = consumerBankruptCount;
	}

	public List<Integer> getConsumerBankruptHistory() {
		return consumerBankruptHistory;
	}

	public void setConsumerBankruptHistory(List<Integer> consumerBankruptHistory) {
		this.consumerBankruptHistory = consumerBankruptHistory;
	}

	public List<AgentFirmCapital> getCapitalFirmAgents() {
		return capitalFirmAgents;
	}

	public void setCapitalFirmAgents(List<AgentFirmCapital> capitalFirmAgents) {
		this.capitalFirmAgents = capitalFirmAgents;
	}

	public List<AgentFirmConsumer> getConsumerFirmAgents() {
		return consumerFirmAgents;
	}

	public void setConsumerFirmAgents(List<AgentFirmConsumer> consumerFirmAgents) {
		this.consumerFirmAgents = consumerFirmAgents;
	}

	public List<AgentPerson> getPersonAgents() {
		return personAgents;
	}

	public void setPersonAgents(List<AgentPerson> personAgents) {
		this.personAgents = personAgents;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public int getIdAgentCounter() {
		return idAgentCounter;
	}

	public void setIdAgentCounter(int idAgentCounter) {
		this.idAgentCounter = idAgentCounter;
	}

	public List<AgentFirmConsumerStock> getStocks() {
		return stocks;
	}

	public void setStocks(List<AgentFirmConsumerStock> stocks) {
		this.stocks = stocks;
	}

	public AgentGovernment getGovernment() {
		return government;
	}

	public void setGovernment(AgentGovernment government) {
		this.government = government;
	}

	public List<WorldCycle> getWorldCycles() {
		return worldCycles;
	}

	public void setWorldCycles(List<WorldCycle> worldCycles) {
		this.worldCycles = worldCycles;
	}

	public float getWage() {
		return wage;
	}

	public void setWage(float wage) {
		this.wage = wage;
	}

	public float getConsumptionCycle() {
		return consumptionCycle;
	}

	public void setConsumptionCycle(float consumptionCycle) {
		this.consumptionCycle = consumptionCycle;
	}

	public List<Float> getConsumptionHistory() {
		return consumptionHistory;
	}

	public void setConsumptionHistory(List<Float> consumptionHistory) {
		this.consumptionHistory = consumptionHistory;
	}

	public List<Float> getEmployedHistory() {
		return employedHistory;
	}

	public void setEmployedHistory(List<Float> employedHistory) {
		this.employedHistory = employedHistory;
	}

	public List<Float> getUnemployedHistory() {
		return unemployedHistory;
	}

	public void setUnemployedHistory(List<Float> unemployedHistory) {
		this.unemployedHistory = unemployedHistory;
	}

	public float getCompetitivityAverageCycle() {
		return competitivityAverageCycle;
	}

	public void setCompetitivityAverageCycle(float competitivityAverageCycle) {
		this.competitivityAverageCycle = competitivityAverageCycle;
	}

	public List<Float> getCompetitivityAverageHistory() {
		return competitivityAverageHistory;
	}

	public void setCompetitivityAverageHistory(
			List<Float> competitivityAverageHistory) {
		this.competitivityAverageHistory = competitivityAverageHistory;
	}

	/********/

	public World(){
		this.government = new AgentGovernment();
		this.government.setId(idAgentCounter++);
		this.government.setWorld(this);
		this.agents.add(government);

	}

	public AgentFirmCapital addAgentFirmCapital(){
		AgentFirmCapital agentCapital = new AgentFirmCapital();
		agentCapital.setId(idAgentCounter++);
		agentCapital.setWorld(this);
		this.agents.add(agentCapital);
		this.firmAgents.add(agentCapital);
		this.capitalFirmAgents.add(agentCapital);
		return agentCapital;
	}

	public AgentFirmConsumer addAgentFirmConsumer(){
		AgentFirmConsumer agentConsumer = new AgentFirmConsumer();
		agentConsumer.setId(idAgentCounter++);
		agentConsumer.setWorld(this);
		float marketShare = (float) (1 / (float) Parameters.AGENT_FIRM_CONSUMER);
		//		logger.info("MAKR "+marketShare);
		agentConsumer.setMarketShareCycle(marketShare);
		agentConsumer.getMarketShares().add(marketShare);
		agentConsumer.setStockSpare(Parameters.AGENT_FIRM_CONSUMER_STOCK_SPARE);
		this.stocks.add(agentConsumer.getStock());
		this.agents.add(agentConsumer);
		this.firmAgents.add(agentConsumer);
		this.consumerFirmAgents.add(agentConsumer);
		return agentConsumer;
	}

	public AgentPerson addAgentPerson(){
		AgentPerson person = new AgentPerson();
		person.setId(idAgentCounter++);
		person.setWorld(this);
		this.agents.add(person);
		this.personAgents.add(person);
		return person;
	}

	public void employPeople(){

		List<AgentFirm> listFirm = new ArrayList<AgentFirm>(this.firmAgents);
		Collections.shuffle(listFirm);

		for(int i = 0; i < this.personAgents.size() && i < listFirm.size(); i++){
			AgentPerson person = this.personAgents.get(i);
			AgentFirm firm = this.firmAgents.get(i);
			person.setEmployer(firm);
			firm.getEmployees().add(person);
		}

	}

	public List<AgentPerson> getEmployees(int quantity){
		List<AgentPerson> employees = new ArrayList<AgentPerson>();

		for(int i = 0; i < this.personAgents.size() && employees.size() < quantity; i++){
			AgentPerson person = this.personAgents.get(i);
			if(person.getEmployer() == null){
				employees.add(person);
			}
		}

		return employees;
	}

	private void processBrochures(List<BrochureDTO> brochures){
		int max = this.consumerFirmAgents.size() - 1;
		int min = 0;

		for(int i = 0; i < brochures.size(); i++){
			BrochureDTO dto = brochures.get(i);

			AgentFirmConsumer consumer = dto.consumer;
			if(consumer == null){

				int index = min + (int)(Math.random() * ((max - min) + 1));
				consumer = this.consumerFirmAgents.get(index);
			}

			consumer.getBrochures().add(dto);
		}


	}

	
	public WorldCycle runCycle(){

		WorldCycle worldCycle = new WorldCycle();

		logger.info("RUNNING CYCLE "+this.cycle);


		for(int i = 0; i < this.agents.size(); i++){
			Agent agent = this.agents.get(i);
			//			logger.info("RUNNING CYCLE FOR AGENT "+agent.getCode());

			/* FIRST ITERATION AGENT CAPITAL */
			if(agent instanceof AgentFirmCapital){
				//				logger.info("FirmCapital 1");
				AgentFirmCapital agentFirm = (AgentFirmCapital) agent;

				//				logger.info("FirmCapital 1 2");

				AgentDTO agentDTO = agent.runCycle();
				worldCycle.agents.add(agentDTO);

				//				logger.info("FirmCapital 1 3");


				List<BrochureDTO> brochures = agentFirm.brochuresGet();
				this.processBrochures(brochures);

				//				logger.info("RECEIVED BROCHURES "+brochures.size());

				//				logger.info("FirmCapital 1 4");


				/* FIRST ITERATION AGENT CONSUMER */
			}else if(agent instanceof AgentFirmConsumer){
				//				logger.info("FirmConsumer 1 1");
				AgentFirmConsumer agentFirm = (AgentFirmConsumer) agent;

				AgentDTO agentDTO = agentFirm.runCycle();
				//				logger.info("FirmConsumer 1 2");

				worldCycle.agents.add(agentDTO);

				//				logger.info("FirmConsumer 1 3");




				/* FIRST ITERATION AGENT PERSON*/
			}else if(agent instanceof AgentPerson){
				//				logger.info("Person 1");
				AgentPerson agentPerson = (AgentPerson) agent;

				AgentDTO agentDTO = agentPerson.runCycle();
				worldCycle.agents.add(agentDTO);

				/* FIRST ITERATION AGENT GOVERNMENT*/
			}else if(agent instanceof AgentGovernment){
				//				logger.info("Government 1");
				AgentGovernment agentGovernment = (AgentGovernment) agent;
				AgentDTO agentDTO = agent.runCycle();
				worldCycle.agents.add(agentDTO);
				if(Parameters.PRINT_DEBUG){

					logger.info("AGENT_GOVERNMENT="+agentGovernment.getCode()+
							" LIQUID_ASSETS=$"+agentGovernment.getLiquidAssets()
							);
				}
			}


		}
		// SECOND ITERATION FOR CAPITAL FIRM
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			//			logger.info("FirmCapital 2");
			AgentFirmCapital agentFirm = this.capitalFirmAgents.get(i);
			agentFirm.requestOrdersProcess();
			agentFirm.processPL();
			//			agentFirm.setRequestOrders(new ArrayList<AgentFirmCapitalOrderRequest>());
			if(Parameters.PRINT_DEBUG){
				logger.info("AGENT_CAPITAL="+agentFirm.getCode()+
						" LIQUID_ASSETS=$"+agentFirm.getLiquidAssets()+
						" EMPLOYEES_Q= "+agentFirm.getEmployees().size()+
						" VINTAGE_Q="+agentFirm.getCapitalGoodVintage().size()+
						" VINTAGE_PROD_A="+agentFirm.getLastVintage().getProductivityA()+
						" GOODS_MAX="+agentFirm.getMaxQForCycle()+
						" ORDERS_TOTAL= "+agentFirm.getRequestOrders().size()+
						" ORDERS_WAITING="+agentFirm.getRequestOrdersUnprocessed()+
						" ORDERS_DELIVERED="+agentFirm.getRequestOrdersDelivered()

						);
			}
		}

		// CONSUMPTION UPDATE AND SALE
		this.consumptionUpdate();
		this.sellConsumerGoods();
		this.updateAverageCompetitivity();

		// SECOND ITERATION FOR CONSUMER FIRM
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			//			logger.info("FirmConsumer 2");
			AgentFirmConsumer agentFirm = this.consumerFirmAgents.get(i);

			agentFirm.processPL();
			agentFirm.updateMarketShare();
			agentFirm.removeOldCapital();

			if(Parameters.PRINT_DEBUG){

				logger.info("AGENT_CONSUMER="+agentFirm.getCode()+
						" LIQUID_ASSETS=$"+agentFirm.getLiquidAssets()+
						" EMPLOYEES_Q= "+agentFirm.getEmployees().size()+
						" Q0/QL/QK="+agentFirm.getQ0()+"/"+agentFirm.getQl()+"/"+agentFirm.getQk()+
						" MARKET_SHARE="+agentFirm.getMarketShareCycle()+
						" UD="+agentFirm.getUnfilledDemandCycle()+
						" COMP="+agentFirm.getCompetitivity()+
						" BROCHURES_NEW="+agentFirm.getBrochures().size()+
						" MACHINES_Q="+agentFirm.getMachines().size()+
						" MACHINES_DEPRECATED="+agentFirm.getMachinesDeprecated().size()+
						" STOCK="+agentFirm.getStock().getStockAvailable()+
						" PRICE="+agentFirm.getStock().getPrice()

						);
			}
			agentFirm.setUnfilledDemandCycle(0);
		}

		// SECOND ITERATION FOR PERSON
		int employed = 0;
		int unemployed = 0;
		for(int i = 0; i < this.personAgents.size(); i++){
			//			logger.info("Person 2");
			AgentPerson agentPerson = this.personAgents.get(i);
			if(agentPerson.isEmployed()){
				employed++;
			}else{
				unemployed++;
			}
			if(Parameters.PRINT_DEBUG){

				//				logger.info("AGENT_PERSON="+agentPerson.getCode()+
				//						" LIQUID_ASSETS=$"+agentPerson.getLiquidAssets()+
				//						" PRODUCTS="+agentPerson.getGoods().size()
				//
				//						);
			}
		}
		//		logger.info("EMPLOYED UNEMPLOYED "+employed+" "+unemployed);
		this.employedHistory.add((float)(employed / (float) this.personAgents.size()));
		this.unemployedHistory.add((float)(unemployed / (float) this.personAgents.size()));
		
		// FINAL OPERATIONS
		this.consumerBankruptHistory.add(this.consumerBankruptCount);
		this.consumerBankruptCount = 0;
		this.capitalBankruptHistory.add(this.capitalBankruptCount);
		this.capitalBankruptCount = 0;
		this.updateWage();
		this.worldCycles.add(worldCycle);
		this.cycle++;
		return worldCycle;
		
	}

	private void updateAverageCompetitivity(){
		float acum = 0F;

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			acum = acum + this.consumerFirmAgents.get(i).getCompetitivity();
		}

		float average = acum / this.consumerFirmAgents.size();

		this.competitivityAverageCycle = average;
		this.competitivityAverageHistory.add(average);
	}
	private void sellConsumerGoods(){
		float peopleLiquidAssers = this.consumptionCycle;
		float marketShareSum = this.marketShareSumGet();
		//		logger.info("MS_S="+marketShareSum);
		List<GoodConsumer> goods = new ArrayList<GoodConsumer>();

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer consumer = this.consumerFirmAgents.get(i);
			float norm = 0F;
			norm = consumer.getMarketShareCycle() / marketShareSum;
			consumer.setMarketShareCycle(norm);
			float demand = (peopleLiquidAssers * consumer.getMarketShareCycle()) ;

			int demandUnits = (int) (demand / consumer.getStock().getPrice());
			//		logger.info("LA="+peopleLiquidAssers+" MS_S="+marketShareSum+" MS_CS="+consumer.getMarketShareCycle()+" DEMAND="+demand+" DEMANDUN="+demandUnits);
			//			List<GoodConsumer> aux = consumer.getStock().getGoodsConsumer(demandUnits);

			List<GoodConsumer> aux = consumer.getGoodsConsumer(demandUnits);
			if(aux != null){
				//				if(aux.size() < demandUnits){
				//					int UnfilledDemand = (int) demandUnits - aux.size();
				//				}

				goods.addAll(aux);

			}

		}


		List<AgentPerson> persons = new ArrayList<AgentPerson>(this.personAgents);
		Collections.shuffle(persons);

		while(goods.size() > 0){
			for(int i = 0; i < persons.size() && goods.size() > 0; i++){
				AgentPerson person = persons.get(i);

				person.buy(goods.get(0));
				goods.remove(0);

			}
		}



	}

	private float marketShareSumGet(){
		float response = 0F;

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			response = response + this.consumerFirmAgents.get(i).getMarketShareCycle();
		}

		return response;
	}

	private float consumptionUpdate(){
		float response = 0F;

		for(int i = 0; i < this.personAgents.size(); i++){
			response = response + this.personAgents.get(i).getLiquidAssets();
		}

		this.consumptionCycle = response;
		this.consumptionHistory.add(response);
		return response;
	}

	private void updateWage(){
		//		float sum = 0;
		//		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
		//			AgentFirmConsumer consumer = this.consumerFirmAgents.get(i);
		//			sum = sum + consumer.getMachinesProductivityAverage();
		//			float demand = (peopleLiquidAssers * consumer.getMarketShareCycle()) / marketShareSum;
		//
		//			}
		this.wage = this.wage * 1; //(1 + Parameters.PS2*this.ipc());
		logger.info("WAGE="+this.wage+" IPC="+this.ipc());


	}

	public void printSummary(){

		for(int i = 0; i < this.cycle - 1; i++){
			Float consumption = this.consumptionHistory.get(i);
			logger.info("CYCLE="+i+" CONSUMPTION="+consumption+" EMPLOYED="+this.employedHistory.get(i)+" COMP_AVG="+this.competitivityAverageHistory.get(i));
		}

	}

	public List<AgentPerson> unemployedGet(){
		List<AgentPerson> unemployed = new ArrayList<AgentPerson>();

		for(int i = 0; i < this.personAgents.size(); i++){
			AgentPerson person = this.personAgents.get(i);

			if(!person.isEmployed()){
				unemployed.add(person);
			}
		}
		return unemployed;
	}

	public float averageProductivityACapital(){
		float acum = 0F;
		float average = 0F;

		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			AgentFirmCapital agentFirm = this.capitalFirmAgents.get(i);

			acum = acum + agentFirm.getLastVintage().getProductivityA();
		}

		average = acum / this.capitalFirmAgents.size();

		return average;
	}

	public float averageProductivityAConsumer(){
		float acum = 0F;
		float average = 0F;

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer agentFirm = this.consumerFirmAgents.get(i);

			acum = acum + agentFirm.getMachinesProductivityAverage();
		}

		average = acum / this.consumerFirmAgents.size();

		return average;
	}

	public int fabricatedCapitalTotal(){
		int acum = 0;
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			acum = acum + this.capitalFirmAgents.get(i).fabricatedTotal();
		}
		return acum;
	}

	public int fabricatedConsumerTotal(){
		int acum = 0;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			acum = acum + this.consumerFirmAgents.get(i).fabricatedTotal();
		}
		return acum;
	}

	public float investmentCapitalTotal(){
		float acum = 0;
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			acum = acum + this.capitalFirmAgents.get(i).getI();
		}
		return acum;
	}
	public float investmentConsumerTotal(){
		float acum = 0;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			acum = acum + this.consumerFirmAgents.get(i).getInvestmentCycle();
		}
		return acum;
	}
	
	
	public void addConsumerBankruptCount(){
		this.consumerBankruptCount++;
	}
	
	public void addCapitalBankruptCount(){
		this.capitalBankruptCount++;
	}

	public void replaceConsumerFirm(AgentFirmConsumer consumer){
		this.addConsumerBankruptCount();
		consumer.unemployAll();
		//		int max = this.firmAgents.size() - 1;
		//		int min = 0;
		//		int index = min + (int)(Math.random() * ((max - min) + 1));

		int index = (int) Math.floor(Math.random() * Parameters.AGENT_FIRM_CONSUMER);
		List<GoodCapital> machines = this.consumerFirmAgents.get(index).getMachines();
		this.agents.remove(consumer);
		this.firmAgents.remove(consumer);
		this.consumerFirmAgents.remove(consumer);
		AgentFirmConsumer consumerNew = this.addAgentFirmConsumer();
		consumerNew.setMachines(machines);
		consumerNew.setLiquidAssets(SimulationController.getConsumerFirmNW()*(0.75 + 0.5*Math.random()));

		List<AgentPerson> employees = this.getEmployees(1);

		if(employees != null && employees.size() > 0){
			AgentPerson employee = employees.get(0);
			employee.setEmployer(consumerNew);
			consumerNew.getEmployees().add(employee);
			
			
			
		}
	}
	public void replaceCapitalFirm(AgentFirmCapital capital){
		this.addCapitalBankruptCount();
		capital.unemployAll();

		int max = this.capitalFirmAgents.size() - 1;
		int min = 0;
		int index = min + (int)(Math.random() * ((max - min) + 1));

		AgentFirmCapital original = this.capitalFirmAgents.get(index);
		float prodB = original.getProductivityB();
		//	float prodB = (float) (Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN + Math.random() * (Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX - Parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN));

		index = min + (int)(Math.random() * ((max - min) + 1));
		AgentFirmCapital original2 = this.capitalFirmAgents.get(index);
		GoodCapitalVintage vint = original2.getLastVintage();

		vint.setProductivityA(vint.getProductivityA() + ( (float) Math.random() ));
		this.agents.remove(capital);
		this.firmAgents.remove(capital);
		this.capitalFirmAgents.remove(capital);

		AgentFirmCapital capitalNew = this.addAgentFirmCapital();
		capitalNew.setProductivityB(prodB);
		capitalNew.setLastVintage(vint);
		capitalNew.getCapitalGoodVintage().add(vint);
		capitalNew.setLiquidAssets(SimulationController.getCapitalFirmNW()*(0.5 + Math.random()));



		List<AgentPerson> employees = this.getEmployees(1);

		if(employees != null && employees.size() > 0){
			AgentPerson employee = employees.get(0);
			employee.setEmployer(capitalNew);
			capitalNew.getEmployees().add(employee);


		}
	}

	public float averageProdAMS(){
		float response = 0F;

		float averageAConsume = this.averageProductivityAConsumer();

		float acum = 0F;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer consumer = this.consumerFirmAgents.get(i);

			acum = acum + (consumer.getMarketShareCycle() * averageAConsume);
		}

		response = acum / this.marketShareSumGet();
		return response;
	}

	public float averageProdAEMP(){
		float response = 0F;

		float averageAConsume = this.averageProductivityAConsumer();

		float acum = 0F;
		float total = 0F;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer consumer = this.consumerFirmAgents.get(i);
			total = total + consumer.getEmployees().size();
			acum = acum + (consumer.getEmployees().size() * averageAConsume);
		}

		response = acum / total;
		return response;
	}

	public float averageProdBMS(){
		float response = 0F;

		float acum = 0F;
		float total = 0F;
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			AgentFirmCapital capital = this.capitalFirmAgents.get(i);
			total = total + capital.getRequestOrdersUnprocessed();
			acum = acum + (capital.getRequestOrdersUnprocessed() * capital.getProductivityB());
		}

		response = acum / total;
		return response;
	}

	public float averageProdBEMP(){
		float response = 0F;

		float acum = 0F;
		float total = 0F;
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			AgentFirmCapital capital = this.capitalFirmAgents.get(i);
			total = total + capital.getEmployees().size();
			acum = acum + (capital.getEmployees().size() * capital.getProductivityB());
		}

		response = acum / total;
		return response;
	}

	public float consumerEMP(){
		float response = 0F;

		float total = 0F;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer consumer = this.consumerFirmAgents.get(i);
			total = total + consumer.getEmployees().size();
		}

		response =  total;
		return response;
	}

	public float capitalEMP(){
		float response = 0F;

		float total = 0F;
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			AgentFirmCapital capital = this.capitalFirmAgents.get(i);
			total = total + capital.getEmployees().size();
		}

		response = total;
		return response;
	}
	
	public float ipc(){
		float response = 0F;

		float acum = 0F;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer consumer = this.consumerFirmAgents.get(i);

			acum = acum + (consumer.getMarketShareCycle() * consumer.getPrice());
		}

		response = acum / this.marketShareSumGet();
		return response;
	}
}
