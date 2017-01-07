package model.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import model.dtos.AgentDTO;
import model.dtos.BrochureDTO;
import model.entities.Agent;
import model.entities.AgentFirm;
import model.entities.AgentFirmCapital;
import model.entities.AgentFirmConsumer;
import model.entities.AgentFirmConsumerStock;
import model.entities.AgentGovernment;
import model.entities.AgentPerson;
import model.entities.GoodCapital;
import model.entities.GoodCapitalVintage;
import model.entities.GoodConsumer;
import model.parameters.ModelParametersSimulation;

public class ModelWorld extends engine.entities.World {
	private static final Logger logger = Logger.getLogger( ModelWorld.class.getName() + " Model" );

	private List<AgentFirm> firmAgents = new ArrayList<AgentFirm>();

	private List<AgentFirmCapital> capitalFirmAgents = new ArrayList<AgentFirmCapital>();

	private List<AgentFirmConsumer> consumerFirmAgents = new ArrayList<AgentFirmConsumer>();

	private List<AgentPerson> personAgents = new ArrayList<AgentPerson>();

	private List<AgentFirmConsumerStock> stocks = new ArrayList<AgentFirmConsumerStock>();

	private AgentGovernment government;

	private List<ModelWorldCycle> worldCycles = new ArrayList<ModelWorldCycle>();

	protected float wageCycle;

	private List<Float> wageHistory = new ArrayList<Float>();

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

	private float ipcCycle;

	private List<Float> ipcHistory = new ArrayList<Float>();

	private float prodABCycle;

	private List<Float> prodABHistory = new ArrayList<Float>();
	
	private Map<ProductivityEnum, Integer> productivityVector = new HashMap<ProductivityEnum, Integer>();
	
	public enum ProductivityEnum{
//		NO10,
		NO01,
		IN100,
//		IN010,
		IN001,
		IM100,
//		IM010,
		IM001
	}
	

	/*******/

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

	public List<ModelWorldCycle> getWorldCycles() {
		return worldCycles;
	}

	public void setWorldCycles(List<ModelWorldCycle> worldCycles) {
		this.worldCycles = worldCycles;
	}

	public float getWageCycle() {
		return wageCycle;
	}

	public void setWageCycle(float wage) {
		this.wageCycle = wage;
	}

	public List<Float> getWageHistory() {
		return wageHistory;
	}

	public void setWageHistory(List<Float> wageHistory) {
		this.wageHistory = wageHistory;
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

	public float getIpcCycle() {
		return ipcCycle;
	}

	public void setIpcCycle(float ipcCycle) {
		this.ipcCycle = ipcCycle;
	}

	public List<Float> getIpcHistory() {
		return ipcHistory;
	}

	public void setIpcHistory(List<Float> ipcHistory) {
		this.ipcHistory = ipcHistory;
	}
	public float getProdABCycle() {
		return prodABCycle;
	}

	public void setProdABCycle(float prodABCycle) {
		this.prodABCycle = prodABCycle;
	}

	public List<Float> getProdABHistory() {
		return prodABHistory;
	}

	public void setProdABHistory(List<Float> prodABHistory) {
		this.prodABHistory = prodABHistory;
	}

	public Map<ProductivityEnum, Integer> getProductivityVector() {
		return productivityVector;
	}

	public void setProductivityVector(Map<ProductivityEnum, Integer> productivityVector) {
		this.productivityVector = productivityVector;
	}

	/********/

	public ModelWorld(ModelParametersSimulation parameters){
		
		this.parameters = parameters;
		this.government = new AgentGovernment();
		this.government.setId(idAgentCounter++);
		this.government.setWorld(this);
		this.agents.add(government);


		this.setWageCycle(parameters.WORLD_WAGE);
		/*** BEGIN WORLD STARTUP ****/
		for(int i = 0; i < parameters.AGENT_FIRM_CAPITAL_Q; i++){
			AgentFirmCapital agentCapital = this.addAgentFirmCapital();
			//			agentCapital.setWage(Parameters.AGENT_FIRM_WAGE);
			agentCapital.setLiquidAssets(getCapitalFirmNW()*(0.5 + Math.random()));
			agentCapital.setProductivityB(this.getGoodCapitalProductivityB());
			GoodCapitalVintage vintage = new GoodCapitalVintage();

			vintage.setPrice((this.getWageCycle() / agentCapital.getProductivityB()) * (1 + parameters.AGENT_FIRM_CAPITAL_MARGIN));
			vintage.setProductivityA(this.getGoodCapitalVintageProductivityA());
			agentCapital.setLastVintage(vintage);
			agentCapital.getCapitalGoodVintage().add(vintage);
		}

		for(int i = 0; i < parameters.AGENT_FIRM_CONSUMER_Q; i++){
			AgentFirmConsumer agentConsumer = this.addAgentFirmConsumer();
			//			agentConsumer.setWage(Parameters.AGENT_FIRM_WAGE);
			agentConsumer.setLiquidAssets(getConsumerFirmNW()*(0.75 + 0.5*Math.random()));
		}

		for(int i = 0; i < parameters.AGENT_PERSON; i++){
			this.addAgentPerson();
		}

		this.employPeople();

	}

	public float getGoodCapitalVintageProductivityA(){
		float min = parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MIN;
		float max = parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_A_MAX;

		float random = min + (float)(Math.random() * (max - min));
		return random;
	}

	public float getGoodCapitalProductivityB(){
		float min = parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MIN;
		float max = parameters.AGENT_FIRM_CAPITAL_PRODUCTIVITY_B_MAX;

		float random = min + (float)(Math.random() * (max - min));
		return random;
	}

	public float getCapitalFirmNW(){
		return parameters.AGENT_FIRM_CAPITAL_NW;
	}

	public float getConsumerFirmNW(){
		return parameters.AGENT_FIRM_CONSUMER_NW;
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
		float marketShare = (float) (1 / (float) parameters.AGENT_FIRM_CONSUMER_Q);
		//		logger.info("MAKR "+marketShare);
		agentConsumer.setMarketShareCycle(marketShare);
		agentConsumer.getMarketShares().add(marketShare);
		agentConsumer.setStockSpare(parameters.AGENT_FIRM_CONSUMER_STOCK_SPARE);
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
		int m = this.consumerFirmAgents.size();

		for(int i = 0; i < brochures.size(); i++){
			BrochureDTO dto = brochures.get(i);

			AgentFirmConsumer consumer = dto.consumer;
			if(consumer == null){
				int index = (int)(Math.random() * m);
				consumer = this.consumerFirmAgents.get(index);
			}

			consumer.getBrochures().add(dto);
		}


	}


	public ModelWorldCycle runCycle(){
		
		productivityVector.put(ProductivityEnum.NO01, 0);
		productivityVector.put(ProductivityEnum.IN100, 0);
		productivityVector.put(ProductivityEnum.IN001, 0);
		productivityVector.put(ProductivityEnum.IM100, 0);
		productivityVector.put(ProductivityEnum.IM001, 0);
		
		ModelWorldCycle worldCycle = new ModelWorldCycle();
		//		logger.info("RUNNING CYCLE "+this.cycle);
		for(int i = 0; i < this.agents.size(); i++){
			Agent agent = this.agents.get(i);
			//			logger.info("RUNNING CYCLE FOR AGENT "+agent.getCode());
			/* FIRST ITERATION AGENT CAPITAL */
			if(agent instanceof AgentFirmCapital){
				AgentFirmCapital agentFirm = (AgentFirmCapital) agent;

				AgentDTO agentDTO = agent.runCycle();
				worldCycle.agents.add(agentDTO);

				List<BrochureDTO> brochures = agentFirm.brochuresGet();
				this.processBrochures(brochures);

				//				logger.info("RECEIVED BROCHURES "+brochures.size());

				/* FIRST ITERATION AGENT CONSUMER */
			}else if(agent instanceof AgentFirmConsumer){
				AgentFirmConsumer agentFirm = (AgentFirmConsumer) agent;
				AgentDTO agentDTO = agentFirm.runCycle();
				worldCycle.agents.add(agentDTO);

				/* FIRST ITERATION AGENT PERSON*/
			}else if(agent instanceof AgentPerson){
				AgentPerson agentPerson = (AgentPerson) agent;
				AgentDTO agentDTO = agentPerson.runCycle();
				worldCycle.agents.add(agentDTO);

				/* FIRST ITERATION AGENT GOVERNMENT*/
			}else if(agent instanceof AgentGovernment){
				AgentGovernment agentGovernment = (AgentGovernment) agent;
				AgentDTO agentDTO = agent.runCycle();
				worldCycle.agents.add(agentDTO);
				if(parameters.PRINT_DEBUG){
					logger.info("AGENT_GOVERNMENT="+agentGovernment.getCode()+
							" LIQUID_ASSETS=$"+agentGovernment.getLiquidAssets()
							);
				}
			}
		}

		// SECOND ITERATION FOR CAPITAL FIRM
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			AgentFirmCapital agentFirm = this.capitalFirmAgents.get(i);
			agentFirm.requestOrdersProcess();
			agentFirm.processPL();
			if(parameters.PRINT_DEBUG){
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

			if(parameters.PRINT_DEBUG){

				logger.info("AGENT_CONSUMER="+agentFirm.getCode()+
						" LIQUID_ASSETS=$"+agentFirm.getLiquidAssets()+
						" EMPLOYEES_Q= "+agentFirm.getEmployees().size()+
						" Q0/QL/QK="+agentFirm.getQ0()+"/"+agentFirm.getQl()+"/"+agentFirm.getQk()+
						" Investment "+agentFirm.getI()+
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

		// COMPUTE EMPLOYMENT
		int employed = 0;
		int unemployed = 0;
		for(int i = 0; i < this.personAgents.size(); i++){
			AgentPerson agentPerson = this.personAgents.get(i);
			if(agentPerson.isEmployed()){
				employed++;
			}else{
				unemployed++;
			}
		}
		this.employedHistory.add((float)(employed / (float) this.personAgents.size()));
		this.unemployedHistory.add((float)(unemployed / (float) this.personAgents.size()));


		//Start 1-10-16
		this.ipc();
		this.prodAB();
		this.updateWage();
		this.wageHistory.add(this.wageCycle);
		// Es necesario que updateWage se corra antes de agregar otro elemento al history de IPC
		this.ipcHistory.add(this.ipcCycle);
		this.ipcCycle = 0;
		//End 1-10-16
		this.prodABHistory.add(this.prodABCycle);
		this.prodABCycle = 1;

		this.consumerBankruptHistory.add(this.consumerBankruptCount);
		this.consumerBankruptCount = 0;
		this.capitalBankruptHistory.add(this.capitalBankruptCount);
		this.capitalBankruptCount = 0;
		this.worldCycles.add(worldCycle);
		this.cycle++;
		return worldCycle;

	}

	public int stockConsumerCycle(){
		int acum = 0;

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			acum = acum + this.consumerFirmAgents.get(i).stockCycle();
		}


		return acum;
	}
	
	public int stockConsumerDeltaCycle(){
		int acum = 0;

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			acum = acum + this.consumerFirmAgents.get(i).stockDeltaCycle();
		}


		return acum;
	}
	
	public float stockConsumerDeltaNominalCycle(){
		float acum = 0;

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			acum = acum + this.consumerFirmAgents.get(i).stockDeltaNominalCycle();
		}


		return acum;
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
		float peopleLiquidAssets = this.consumptionCycle;
		float marketShareSum = this.marketShareSumGet();
		List<GoodConsumer> goods = new ArrayList<GoodConsumer>();

		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer consumer = this.consumerFirmAgents.get(i);
			float norm = 0F;
			norm = consumer.getMarketShareCycle() / marketShareSum;
			consumer.setMarketShareCycle(norm);
			float demand = (peopleLiquidAssets * consumer.getMarketShareCycle()) ;

			int demandUnits = (int) (demand / consumer.getStock().getPrice());
			//		logger.info("LA="+peopleLiquidAssets+" MS_S="+marketShareSum+" MS_CS="+consumer.getMarketShareCycle()+" DEMAND="+demand+" DEMANDUN="+demandUnits);
			//			List<GoodConsumer> aux = consumer.getStock().getGoodsConsumer(demandUnits);
			List<GoodConsumer> aux = consumer.getGoodsConsumer(demandUnits);
			if(aux != null){
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
		if(this.cycle == 1)
			return;
		float prevProdAB = this.prodABHistory.get(this.prodABHistory.size() - 1); 
		float prevIpc = this.ipcHistory.get(this.ipcHistory.size() - 1); 

		float DAB = (this.prodABCycle - prevProdAB ) / prevProdAB;
		float Dipc = (this.ipcCycle - prevIpc ) / prevIpc;

		this.wageCycle = this.wageCycle * (1 + parameters.PS1 * DAB + parameters.PS2 * Dipc);

		//		logger.info("WAGE="+this.wageCycle+" DAB="+DAB+" inflation="+Dipc);
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
			acum = acum + this.capitalFirmAgents.get(i).getFabricatedLastCycle();

		}
		return acum;
	}

	public int fabricatedConsumerTotal(){
		int acum = 0;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			acum = acum + this.consumerFirmAgents.get(i).getFabricatedLastCycle();
		}
		return acum;
	}

	public float salesCapitalCycleTotalNominal(){
		float acum = 0;
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			AgentFirmCapital agent = this.capitalFirmAgents.get(i);
			acum = acum + agent.soldUnitsCycle() * agent.getLastVintage().getPrice();
		}
		return acum;
	}

	public float salesConsumerCycleTotalNominal(){
		float acum = 0;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer agent = this.consumerFirmAgents.get(i);
			acum = acum + agent.soldUnitsCycle() * agent.getPrice() ;
		}
		return acum;
	}

	public float salesCapitalCycleTotal(){
		float acum = 0;
		for(int i = 0; i < this.capitalFirmAgents.size(); i++){
			AgentFirmCapital agent = this.capitalFirmAgents.get(i);
			acum = acum + agent.soldUnitsCycle();
		}
		return acum;
	}

	public float salesConsumerCycleTotal(){
		float acum = 0;
		for(int i = 0; i < this.consumerFirmAgents.size(); i++){
			AgentFirmConsumer agent = this.consumerFirmAgents.get(i);
			acum = acum + agent.soldUnitsCycle();
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

		int index = (int) Math.floor(Math.random() * parameters.AGENT_FIRM_CONSUMER_Q);
		List<GoodCapital> machines = this.consumerFirmAgents.get(index).getMachines();
		this.agents.remove(consumer);
		this.firmAgents.remove(consumer);
		this.consumerFirmAgents.remove(consumer);
		AgentFirmConsumer consumerNew = this.addAgentFirmConsumer();
		consumerNew.setMachines(machines);
		consumerNew.setLiquidAssets(getConsumerFirmNW()*(0.75 + 0.5*Math.random()));

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

		index = min + (int)(Math.random() * ((max - min) + 1));
		AgentFirmCapital original2 = this.capitalFirmAgents.get(index);
		GoodCapitalVintage vint = original2.getLastVintage();

		vint.setProductivityA((float) (vint.getProductivityA() * (1F + (float) Math.random() * 0.01 )));
		this.agents.remove(capital);
		this.firmAgents.remove(capital);
		this.capitalFirmAgents.remove(capital);

		AgentFirmCapital capitalNew = this.addAgentFirmCapital();
		capitalNew.setProductivityB(prodB);
		capitalNew.setLastVintage(vint);
		capitalNew.getCapitalGoodVintage().add(vint);
		capitalNew.setLiquidAssets(getCapitalFirmNW()*(0.5 + Math.random()));

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
		this.ipcCycle = response;
		return response;
	}

	public float prodAB(){
		float response = 0F;

		response = (this.consumerEMP() * this.averageProdAEMP() + this.capitalEMP() * this.averageProdBEMP())/(this.consumerEMP() + this.capitalEMP());
		this.prodABCycle = response;
		return response;
	}
	
	public String getProductivitySum(){
		StringBuilder response = new StringBuilder();
		response.append("{");
		response.append(this.productivityVector.get(ProductivityEnum.NO01)+",");
		response.append(this.productivityVector.get(ProductivityEnum.IN100)+",");
		response.append(this.productivityVector.get(ProductivityEnum.IN001)+",");
		response.append(this.productivityVector.get(ProductivityEnum.IM100)+",");
		response.append(this.productivityVector.get(ProductivityEnum.IM001));
		response.append("}");
		return response.toString();
	}
}