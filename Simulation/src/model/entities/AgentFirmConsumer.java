package model.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import model.dtos.AgentDTO;
import model.dtos.BrochureDTO;
import model.dtos.OrderRequestDTO;

public class AgentFirmConsumer extends AgentFirm {
	private static final Logger logger = Logger.getLogger( AgentFirmConsumer.class.getName() );

	private AgentFirmConsumerStock stock = new AgentFirmConsumerStock();

	private List<AgentFirmCapital> manufacturers = new ArrayList<AgentFirmCapital>();

	private List<BrochureDTO> brochures = new ArrayList<BrochureDTO>();

	private List<GoodCapital> machines = new ArrayList<GoodCapital>();

	private List<GoodCapital> machinesDeprecated = new ArrayList<GoodCapital>();

	private float marketShareCycle;

	private List<Float> marketShares = new ArrayList<Float>();

	private float stockSpare;

	private int unfilledDemandCycle;

	private List<Integer> unfilledDemandHistory = new ArrayList<Integer>();

	private int demandUnitsCycle;

	private List<Integer> demandUnitsHistory =  new ArrayList<Integer>();

	private float investmentCycle;

	private List<Float> investmentHistory = new ArrayList<Float>();

	private List<Integer> stockHistory = new ArrayList<Integer>();

	private List<Integer> stockDeltaHistory = new ArrayList<Integer>();

	private List<Float> stockDeltaNominalHistory = new ArrayList<Float>();
	
	private int employeeGap;
	
	private float actualAvgProductivityA;
	
	public float getInvestmentCycle() {
		return investmentCycle;
	}

	public void setInvestmentCycle(float investmentCycle) {
		this.investmentCycle = investmentCycle;
	}

	public List<Float> getInvestmentHistory() {
		return investmentHistory;
	}

	public void setInvestmentHistory(List<Float> investmentHistory) {
		this.investmentHistory = investmentHistory;
	}

	public AgentFirmConsumerStock getStock() {
		return stock;
	}

	public void setStock(AgentFirmConsumerStock stock) {
		this.stock = stock;
	}

	public AgentFirmConsumer(){
		this.stock.setConsumer(this);

	}

	public List<AgentFirmCapital> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(List<AgentFirmCapital> manufacturers) {
		this.manufacturers = manufacturers;
	}

	public List<BrochureDTO> getBrochures() {
		return brochures;
	}

	public void setBrochures(List<BrochureDTO> brochures) {
		this.brochures = brochures;
	}

	public float getMarketShareCycle() {
		return marketShareCycle;
	}

	public void setMarketShareCycle(float marketShareCycle) {
		this.marketShareCycle = marketShareCycle;
	}

	public List<GoodCapital> getMachines() {
		return machines;
	}

	public void setMachines(List<GoodCapital> machines) {
		this.machines = machines;
	}

	public List<GoodCapital> getMachinesDeprecated() {
		return machinesDeprecated;
	}

	public void setMachinesDeprecated(List<GoodCapital> machinesDeprecated) {
		this.machinesDeprecated = machinesDeprecated;
	}

	public List<Float> getMarketShares() {
		return marketShares;
	}

	public void setMarketShares(List<Float> marketShares) {
		this.marketShares = marketShares;
	}

	public float getStockSpare() {
		return stockSpare;
	}

	public void setStockSpare(float stockSpare) {
		this.stockSpare = stockSpare;
	}

	public int getUnfilledDemandCycle() {
		return unfilledDemandCycle;
	}

	public void setUnfilledDemandCycle(int unfilledDemandCycle) {
		this.unfilledDemandCycle = unfilledDemandCycle;
	}

	public List<Integer> getUnfilledDemandHistory() {
		return unfilledDemandHistory;
	}

	public void setUnfilledDemandHistory(List<Integer> unfilledDemandHistory) {
		this.unfilledDemandHistory = unfilledDemandHistory;
	}

	public int getDemandUnitsCycle() {
		return demandUnitsCycle;
	}

	public void setDemandUnitsCycle(int demandUnitsCycle) {
		this.demandUnitsCycle = demandUnitsCycle;
	}

	public List<Integer> getDemandUnitsHistory() {
		return demandUnitsHistory;
	}

	public void setDemandUnitsHistory(List<Integer> demandUnitsHistory) {
		this.demandUnitsHistory = demandUnitsHistory;
	}

	public List<Integer> getStockHistory() {
		return stockHistory;
	}

	public void setStockHistory(List<Integer> stockHistory) {
		this.stockHistory = stockHistory;
	}

	public List<Integer> getStockDeltaHistory() {
		return stockDeltaHistory;
	}

	public void setStockDeltaHistory(List<Integer> stockDeltaHistory) {
		this.stockDeltaHistory = stockDeltaHistory;
	}

	public List<Float> getStockDeltaNominalHistory() {
		return stockDeltaNominalHistory;
	}

	public void setStockDeltaNominalHistory(List<Float> stockDeltaNominalHistory) {
		this.stockDeltaNominalHistory = stockDeltaNominalHistory;
	}
	
	public int getEmployeeGap() {
		return employeeGap;
	}

	public void setEmployeeGap(int employeeGap) {
		this.employeeGap = employeeGap;
	}

	public float getActualAvgProductivityA() {
		return actualAvgProductivityA;
	}

	public void setActualAvgProductivityA(float actualAvgProductivityA) {
		this.actualAvgProductivityA = actualAvgProductivityA;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	/*******/
	@Override
	public AgentDTO runCycle() {
		this.runPayroll();
		this.brochuresProcess();
		this.processFabrication();
		this.processEmployees();
		this.updateStockHistory();
		return null;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void processEmployees() {

//		int employeesGap = (int) Math.round((Math.min(this.getQ0(), this.getQk()) - this.getQl()) / this.getMachinesProductivityAverage());

		if(this.employeeGap < 0){//employeesGap is positive when people are missing 
			//Contratar

//			logger.info("Hire "+this.employeeGap+" people");

			List<AgentPerson> employees = this.world.getEmployees(this.employeeGap);

			for(int i = 0; i < employees.size(); i++){
				AgentPerson employee = employees.get(i);
				employee.setEmployer(this);
				this.employees.add(employee);
			}
		}else if(this.employeeGap > 0){
			
//			logger.info("Fire "+this.employeeGap+" people. (min Emp is 1)");

			//Despedir
			int pending = -this.employeeGap;
			for(int i = 0; i < this.employees.size() - 1 && pending > 0; i++){//At least one employee per firm is kept
				AgentPerson employee = this.employees.get(i);
				employee.setEmployer(null);
				this.employees.remove(i);
			}
		}


	}

	public float getQ0(){
		float response = 0F;
		
		int index = this.demandUnitsHistory.size();
		index--;

		if(index >= 1){
			response = (int) Math.round((1 + this.stockSpare)*(this.demandUnitsHistory.get(index)));
		}else{
			response = 1;
		}
		

		
//		Commented 2/6/17. Expectation is equal to level of 1 past period (instead of using 2 past periods)
//		if(index == 1){
//			response = this.demandUnitsHistory.get(index);
//		}else if(index > 1){
//			response = this.demandUnitsHistory.get(index)+Math.round((this.demandUnitsHistory.get(index)-this.demandUnitsHistory.get(index-1)));
//		}else{
//			response = 0;
//		}


		return response;
	}
	
	public List<GoodConsumer> getGoodsConsumer(int quantity){
		List<GoodConsumer> response = this.stock.getGoodsConsumer(quantity);

		this.demandUnitsCycle = quantity;
		this.demandUnitsHistory.add(quantity);
		if(response.size() < quantity){
			int gap = quantity - response.size();
			//		//logger.info(this.getCode()+" DEMAND="+quantity+" FILLED="+response.size()+" GAP="+gap);
			this.unfilledDemandCycle = gap;
			this.unfilledDemandHistory.add(gap);

		}

		return response;

	}

	public int getQk(){
		return (int) Math.ceil(this.getQ0() / this.world.getParameters().AGENT_FIRM_CONSUMER_MACHINE_CAPACITY);

	}

	
	private float getMachinesCapital(){
		float acum = 0F;
		for(int i = 0; i < this.machines.size(); i++){
			acum = acum + this.machines.get(i).getGoodCapitalVintage().getPrice();
		}

		return acum;
	}
	
	public float getQl(){
		return this.getMachinesProductivityAverage() * this.employees.size();
	}
	
	public float getMachinesProductivityAverage(){
		float average = 0F;
		float acum = 0F;
		int quantity = this.machines.size();

		if(quantity > 0){
			for(int i = 0; i < this.machines.size(); i++){
				acum = acum + this.machines.get(i).getGoodCapitalVintage().getProductivityA();
			}

			average = (acum / quantity);

		}else{
			average = this.world.averageProductivityACapital();
		}

		return average;
	}
	

	public float getMachinesScoreAverage(){
		float average = 0F;
		float acum = 0F;
		int quantity = this.machines.size();

		if(quantity > 0){
			for(int i = 0; i < this.machines.size(); i++){
				GoodCapital machine = this.machines.get(i);
//				acum = acum + this.machines.get(i).getGoodCapitalVintage().getProductivityA();
				acum = acum + machine.getGoodCapitalVintage().getPrice() + (this.world.getParameters().AGENT_FIRM_CONSUMER_PAYBACK_PERIOD * this.world.getWageCycle() / machine.getGoodCapitalVintage().getProductivityA());
			}

			average = (acum / quantity);

		}else{
			average = Float.MAX_VALUE;
		}

		return average;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void brochuresProcess(){

		//IF NO BROCHURES, FINISH.
		if(this.brochures.size() < 1)
			return;		
		
		
		// NEW PROVIDERS
		float investExpansion = 0F;

		if(true){
			//logger.info("PROCESSING BROCHURES NEW PROVIDER");
			BrochureDTO brochure = this.goodCapitalOptimalNewProviders();
			if(brochure != null){
				float investment = 0F;
				
				
				investment = Math.max(0, this.getQk() - this.machines.size()) * brochure.vintage.getPrice();

//				logger.info("ID "+this.id+" Investment "+investment);
//				logger.info("ID "+this.id+" vintage price: "+brochure.vintage.getPrice()+" qk: "+this.getQk()+", machines: "+this.machines.size());

//				logger.info("Optimal vintage score: "+this.vintageScoreCalc(brochure)+", avg score: "+this.getMachinesScoreAverage());

				if(this.vintageScoreCalc(brochure) <= 
						this.getMachinesScoreAverage()){

//					logger.info("FirmConsumer Brochure NewProvider Investment");
					while(investment >= brochure.vintage.getPrice()){
						//logger.info("ID "+this.id+" Iteration "+p++);
						//logger.info("BUYING NEW MACHINE FROM NEW PROVIDER "+investment+" price "+brochure.vintage.getPrice());
						OrderRequestDTO request = new OrderRequestDTO();
						request.consumer = this;
						request.vintage = brochure.vintage;
						//logger.info("FirmConsumer Brochure Create Order");
						brochure.manufacturer.requestOrderCreate(request);
						//logger.info("FirmConsumer Brochure Rest Investment");
						investment = investment - brochure.vintage.getPrice();
						investExpansion = investExpansion + brochure.vintage.getPrice();
						//logger.info("Investment "+investment);

					}

				}
			}
		}
		// EXISTING PROVIDERS
		// CONS GOODS FIRM WILL REPLACE ALL MACHINES FROM CAPITAL FIRMS THAT PRESENTED BETTER VINTAGES, TELLING FROM THE PAYBACK RULE
		
		float investReplace = 0F;

		if(true){
			//logger.info("FirmConsumer Brochure ExistingProvider");
			//logger.info("PROCESSING BROCHURES EXISTING PROVIDERS");
			List<BrochureDTO> brochuresProviders = this.goodCapitalFromProviders();
			for(int i = 0; i < brochuresProviders.size(); i++){

				BrochureDTO brochure = brochuresProviders.get(i);
				GoodCapitalVintage newVintage = brochure.vintage;
				List<GoodCapital> goods = this.goodCapitalsFromProvider(brochure.manufacturer);

				float newPrice = newVintage.getPrice();
				float newCost = this.world.getWageCycle() / newVintage.getProductivityA();

				//logger.info("FirmConsumer Brochure ExistingProvider Machines");
				for(int j = 0; j< goods.size(); j++){
					GoodCapital existingGood = goods.get(j);
					float existingCost = this.world.getWageCycle() / existingGood.getGoodCapitalVintage().getProductivityA();

					float factor = newPrice / (existingCost - newCost);

					if(factor <= this.world.getParameters().AGENT_FIRM_CONSUMER_PAYBACK_PERIOD){
						// REPLACE MACHINE
						//logger.info("REPLACING MACHINE FROM EXISTING PROVIDER");
						OrderRequestDTO request = new OrderRequestDTO();
						request.consumer = this;
						request.vintage = brochure.vintage;
						request.toReplace = existingGood;
						brochure.manufacturer.requestOrderCreate(request);
						investReplace = investReplace + brochure.vintage.getPrice();

					}

				}

			}

			float investGross = investExpansion + investReplace;
			this.liquidAssets = this.liquidAssets - investGross;
			this.investmentCycle = investGross;
			this.investmentHistory.add(investGross);

		}
		this.brochures = new ArrayList<BrochureDTO>();

	}

	
//	private BrochureDTO goodCapitalMostProductiveNewProviders(){
//		float productivity = Float.MIN_VALUE;
//		BrochureDTO selected = null;
//		for(int i = 0; i < this.brochures.size(); i++){
//			BrochureDTO brochure = this.brochures.get(i);
//			if(brochure.consumer == null){
//
//				if(brochure.vintage.getProductivityA() > productivity){
//					selected = brochure;
//					productivity = brochure.vintage.getProductivityA();
//				}
//			}
//
//		}
//		return selected;
//	}
	
	
	private Float vintageScoreCalc(BrochureDTO brochure ){
		
		return brochure.vintage.getPrice() + 
		this.world.getParameters().AGENT_FIRM_CONSUMER_PAYBACK_PERIOD * this.world.getWageCycle() / brochure.vintage.getProductivityA();
		
	}
//	Get the brochure with MIN score (i.e. optimal)
	private BrochureDTO goodCapitalOptimalNewProviders(){
		float score = Float.MAX_VALUE;
		BrochureDTO selected = null;
		for(int i = 0; i < this.brochures.size(); i++){
			BrochureDTO brochure = this.brochures.get(i);
			if(brochure.consumer == null){
				if(this.vintageScoreCalc(brochure) < score){
					selected = brochure;
					score = this.vintageScoreCalc(brochure);
				}
			}

		}
		return selected;
	}
	
	public float getI(){
		float response = 0F;
		response = (int) Math.min(this.getQ0(),this.getQl()) - this.getMachinesCapital()*this.world.getParameters().AGENT_FIRM_CONSUMER_MACHINE_CAPACITY;

//		int i = world.getCycle()-2;
//		if(i > 1 &&	world.getUnemployedHistory().get(i)*world.getPersonAgents().size() < 3){
//			response = (int) this.employees.size() * this.getMachinesProductivityAverage() - this.getQk() / this.world.getParameters().AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
//		}else{
//			response = (int) this.getQ0() * this.getPrice() - this.getQk() / this.world.getParameters().AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
//		}
		
		response = Math.max(response,0);
		return response;
	}
	
	
	private List<BrochureDTO> goodCapitalFromProviders(){

		List<BrochureDTO> response = new ArrayList<BrochureDTO>();
		for(int i = 0; i < this.brochures.size(); i++){
			BrochureDTO brochure = this.brochures.get(i);
			if(brochure.consumer != null && brochure.consumer.equals(this)){
				response.add(brochure);
			}
		}
		return response;
	}
	
	private List<GoodCapital> goodCapitalsFromProvider(AgentFirmCapital provider){
		List<GoodCapital> goods = new ArrayList<GoodCapital>();

		for(int i = 0; i < this.machines.size(); i++){
			GoodCapital machine = this.machines.get(i);
			if(machine.getManufacturer() != null && machine.getManufacturer() == provider){
				goods.add(machine);
			}
		}
		return goods;
	}
	
	public float getPrice(){

		float cost = this.getCostUnit();
		float price = cost * (1 + this.world.getParameters().AGENT_FIRM_CONSUMER_MARGIN);

		return price;
	}
	
	public float getCostUnit(){
		return this.world.getWageCycle() / this.getMachinesProductivityAverage();

	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	private void processFabrication(){
//		float q0 = this.getQ0();
//		float ql = this.getQl();
		float qk = this.getQk();

//		List<GoodCapital> sortedProducitvities = new ArrayList<GoodCapital>();
//		List<GoodCapital> arrayCopy = new ArrayList<GoodCapital>(this.machines);
		
		List<Float> machinesProductivity = new ArrayList<Float>(); 
		List<Float> machinesProductivityAcum = new ArrayList<Float>(); 
		List<Float> machinesInverseProductivity = new ArrayList<Float>(); 
		List<Float> machinesInverseProductivityAcum = new ArrayList<Float>(); 

		for(int i = 0; i < this.machines.size(); i++){
			GoodCapital machine = this.machines.get(i);
			machinesInverseProductivity.add(1 / machine.getGoodCapitalVintage().getProductivityA());
		}

		for(int i = 0; i < this.machines.size(); i++){
			GoodCapital machine = this.machines.get(i);
			machinesProductivity.add(machine.getGoodCapitalVintage().getProductivityA());
		}
		Collections.sort(machinesProductivity);
		Collections.reverse(machinesProductivity);

//		logger.info("There are ="+this.machines.size()+" machines, you'd need to use "+qk);
		int m = (int) Math.min(qk, machines.size()-1);


//		logger.info("Productivities: "+machinesProductivity);
		Collections.sort(machinesInverseProductivity);
//		logger.info("Sorted  productivities: "+machinesProductivity);


		if(this.machines.size() > 0){
			
			
			Float acum = 0F;
			for(int i = 0; i < machinesInverseProductivity.size();i++){
				Float labor = machinesInverseProductivity.get(i);
				acum = acum + labor;
				machinesInverseProductivityAcum.add(acum);
			}
			
			Float acum2 = 0F;
			for(int i = 0; i < machinesProductivity.size();i++){
				Float labor = machinesProductivity.get(i);
				acum2 = acum2 + labor;
				machinesProductivityAcum.add(acum2);
			}
			
//			logger.info("Labor needs: "+machinesInverseProductivityAcum);

			
			Integer indexMachinesProductivityAcum = 0;
			
			
			Integer employees = this.employees.size();
	
			Boolean complete = false;
			for(int i = 0; machinesInverseProductivityAcum.size() > i && !complete ;i++){
				if(employees > machinesInverseProductivityAcum.get(i)){
//					logger.info("Employees: "+employees+", inverse prod Acum loop "+i+", "+machinesInverseProductivityAcum.get(i));
					indexMachinesProductivityAcum = i;
				}else{
					complete = true;
				}
					
			}
			
//			how many people you need to fabricate what you wanted (slash the max your machines can give you) minus actual employees you have.
			this.employeeGap = (int) Math.ceil(machinesInverseProductivityAcum.get(m)) - employees; 

//			logger.info("There are ="+this.employees.size()+" employees, and you need  ="+Math.ceil(machinesInverseProductivityAcum.get(m))+" people to operate, so empgap is "+this.employeeGap);
			

		
			//Limit machines used, by labor.
			
//			logger.info("You were planning to use "+m+" machines, having "+employees+" workers allows you to use "+(indexMachinesProductivityAcum + 1)+" machines.");

			m = Math.min(m, indexMachinesProductivityAcum + 1);
			
//			logger.info("Total prodA is: "+machinesProductivityAcum.get(m)+", divide into "+(m+1)+" machines you used");
			this.actualAvgProductivityA =  machinesProductivityAcum.get(m) / (m+1);
			
			int qs = Math.round(m * this.world.getParameters().AGENT_FIRM_CONSUMER_MACHINE_CAPACITY);			
				
			int fabricate = qs;
	
			this.fabricatedCycle = fabricate;



			for(int i = 0; i < fabricate; i++){
				GoodConsumer good = new GoodConsumer();
				good.setAgent(this);
				good.setManufacturer(this);

				this.stock.getGoods().add(good);
			}

		}
	}
	
//	
//	public float getQl(){
//		
//		List<Float> machinesProductivity = new ArrayList<Float>(); 
//		List<Float> machinesProductivityAcum = new ArrayList<Float>(); 
//
//		for(int i = 0; i < this.machines.size(); i++){
//			GoodCapital machine = this.machines.get(i);
//			machinesProductivity.add(1 / machine.getGoodCapitalVintage().getProductivityA());
//
//		}
//		
//		Collections.sort(machinesProductivity);
//		int m = (int) Math.min(qk, machines.size());
//		
//		if(this.machines.size() > 0){
//			
//			Float acum = 0F;
//			for(int i = 0;machinesProductivity.size() < i;i++){
//				Float labor = machinesProductivity.get(i);
//				acum = acum + labor;
//				machinesProductivityAcum.add(acum);
//			}
//			
//			Integer indexMachinesProductivityAcum = 0;
//			Integer employees = this.employees.size();
//	
//	
//			Boolean complete = false;
//			for(int i = 0; machinesProductivityAcum.size() > i && !complete ;i++){
//				if(employees > machinesProductivityAcum.get(i)){
//					indexMachinesProductivityAcum = i;
//					complete = true;
//				}
//					
//			}
//			
//				}
//	}
	

	
//	private void processFabrication(){
//		float q0 = this.getQ0();
//		float ql = this.getQl();
//		float qk = this.getQk();
//
//		int qs = (int) Math.floor(Math.min(Math.min(q0, ql), qk));
//		int stockAvailable = this.stock.getStockAvailable();
//
//		int fabricate = qs + this.stockSpare - stockAvailable;
//
//		this.fabricatedCycle = fabricate;
//
//		if(this.machines.size() > 0){
//
//			for(int i = 0; i < fabricate; i++){
//				GoodConsumer good = new GoodConsumer();
//				good.setAgent(this);
//				good.setManufacturer(this);
//
//				this.stock.getGoods().add(good);
//			}
//
//		}
//	}
//	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void updateStockHistory(){
		this.stockHistory.add(this.stock.getStockAvailable());
			
		int size = this.stockHistory.size();
		if(size > 1){
			int delta =  this.stockHistory.get(size - 1) - this.stockHistory.get(size - 2);
			this.stockDeltaHistory.add(delta);
			
			float deltaNominal = Math.max(0F, delta * this.getCostUnit());
			this.stockDeltaNominalHistory.add(deltaNominal);
		}

	}
	
	
	
	public int stockCycle(){
		return this.stockHistory.get(this.stockHistory.size());
	}

	public int stockDeltaCycle(){
		int response = 0;
		int size = this.stockDeltaHistory.size();
		if(size > 0){
			response = this.stockDeltaHistory.get(size - 1);
		}

		return response;
	}

	public float stockDeltaNominalCycle(){
		int response = 0;
		int size = this.stockDeltaNominalHistory.size();
		if(size > 0){
		return this.stockDeltaNominalHistory.get(size - 1);
		}
		
		return response;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SECOND ITERATION//
		
	//agentFirm.processPL();
	//agentFirm.updateMarketShare();
	//agentFirm.removeOldCapital();


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	public void processPL(){
		

		this.soldUnitsHistory.add(this.soldUnitsCycle);
		this.salesHistory.add(this.salesCycle);
		this.costHistory.add(this.costCycle);
		this.fabricatedHistory.add(this.fabricatedCycle);

		float profit = this.salesCycle - this.costCycle;

		this.profitHistory.add(profit);

		if(profit > 0){
			float taxes = profit * this.world.getParameters().AGENT_GOVERNMENT_FIRM_TAX;
			this.liquidAssets = this.liquidAssets - taxes;
			this.world.getGovernment().payFirmTax(taxes);
			//			//logger.info("PAY TAXES");
		}

		this.fabricatedLastCycle = this.fabricatedCycle;

		float salesproy = this.getSalesCycle();

		this.soldUnitsCycle = 0;
		this.salesCycle = 0;
		this.costCycle = 0;
		this.fabricatedCycle = 0;

		float NW = this.getLiquidAssets();

		if(NW + 2 * salesproy < 0){
			//logger.info("CREANDO FIRM CAPITAL");
			this.world.replaceConsumerFirm(this);
		}

	}
	
	public float getLiquidAssets() {
		return liquidAssets;
	}
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	public void updateMarketShare(){
		float competitivity = this.getCompetitivity();
		float competitivityAverage = this.world.getCompetitivityAverageCycle();
		float psi = this.world.getParameters().COMPETITIVITY_MARKETSHARE* (competitivityAverage - competitivity) / competitivityAverage;
		float compareFirst = this.marketShareCycle * ( 1 + psi);

		float newMarketShare = Math.max(compareFirst,0);

		//		//logger.info("COMP="+competitivity+" COMP_AVG="+competitivityAverage+" PSI="+psi+" COMPARE_FIRST="+compareFirst+" FINAL="+newMarketShare);

		this.marketShares.add(newMarketShare);
		this.marketShareCycle = newMarketShare;
		if(newMarketShare < 0.002){
			this.world.replaceConsumerFirm(this);
		}

	}

	
	public float getCompetitivity(){
		float E = -this.world.getParameters().AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 * this.getPrice() -  // (price - price)
				this.world.getParameters().AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2 * this.unfilledDemandCycle;
		return E;
	}


	
	public void removeOldCapital(){
		for(int i = 0; i < this.machines.size() ; i++){
			GoodCapital good = this.machines.get(i);
			int gap = this.world.getCycle() - good.getCycle();
			int rand = (int) Math.round(this.world.getParameters().AGENT_FIRM_CONSUMER_OBSOLETE * (0.8 + 0.4 * Math.random()));
			if(gap >= rand){
				this.machines.remove(good);
			}
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	@Override
	public String getCode() {
		return "GF-"+String.valueOf(this.id);
	}

	
	public float receiveGoodCapital(AgentFirmCapitalOrderRequest order){
		float pay = 0F;
		GoodCapital goodCapital = order.getNewGood();
		this.machines.add(goodCapital);

		//		pay = goodCapital.getGoodCapitalVintage().getPrice();
		//		this.liquidAssets = this.liquidAssets - pay;
		goodCapital.setAgent(this);

		if(order.getToReplace() != null){
			//			//logger.info("AAAAAAA REPLACING MACHINE FROM EXISTING PROVIDER");
			this.machines.remove(order.getToReplace());
			this.machinesDeprecated.add(order.getToReplace());
		}

		return pay;
	}

	
	public GoodConsumer sell(GoodConsumer good){

		this.stock.getGoods().remove(good);

		Float sale = this.stock.getPrice();
		this.salesCycle = this.salesCycle + sale;

		this.liquidAssets = this.liquidAssets + sale;

		this.soldUnitsCycle++;

		return good;
	}


//
//	private GoodCapitalVintage getMostProductiveGood(){
//		GoodCapitalVintage machine = null;
//		float productivity = Float.MIN_VALUE;
//		for(int i = 0; i < this.machines.size(); i++){
//			GoodCapital aux = this.machines.get(i);
//			if(aux.getGoodCapitalVintage().getProductivityA() > productivity){
//				machine = aux.getGoodCapitalVintage();
//				productivity = aux.getGoodCapitalVintage().getProductivityA();
//			}
//		}
//
//		return machine;
//	}



//	public void setLiquidAssets(double d) {
//		this.liquidAssets = (float) d;
//	}
	
}