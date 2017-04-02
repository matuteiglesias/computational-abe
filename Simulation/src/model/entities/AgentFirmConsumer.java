package model.entities;

import java.security.Policy.Parameters;
import java.util.ArrayList;
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

	private int stockSpare;

	private int unfilledDemandCycle;

	private List<Integer> unfilledDemandHistory = new ArrayList<Integer>();

	private int demandUnitsCycle;

	private List<Integer> demandUnitsHistory =  new ArrayList<Integer>();

	private float investmentCycle;

	private List<Float> investmentHistory = new ArrayList<Float>();

	private List<Integer> stockHistory = new ArrayList<Integer>();

	private List<Integer> stockDeltaHistory = new ArrayList<Integer>();

	private List<Float> stockDeltaNominalHistory = new ArrayList<Float>();
	

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

	public int getStockSpare() {
		return stockSpare;
	}

	public void setStockSpare(int stockSpare) {
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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*******/
	@Override
	public AgentDTO runCycle() {
		this.runPayroll();
		this.processEmployees();
		this.brochuresProcess();
		this.processFabrication();
		this.updateStockHistory();
		return null;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void processEmployees() {

		int employeesGap = (int) Math.round((this.getQ0() - this.getQl()) / this.getMachinesProductivityTotal());
		

		if(employeesGap > 0){//employeesGap is positive when people are missing 
			//Contratar

			List<AgentPerson> employees = this.world.getEmployees(employeesGap);

			for(int i = 0; i < employees.size(); i++){
				AgentPerson employee = employees.get(i);
				employee.setEmployer(this);
				this.employees.add(employee);
			}
		}else if(employees.size() + employeesGap > 1){//At least one employee per firm is kept
			//Despedir
			int pending = -employeesGap;
			for(int i = 0; i < this.employees.size() && pending > 0; i++){
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
		if(index == 0){
			response = this.world.getParameters().WORLD_WAGE * this.world.getParameters().AGENT_PERSON / this.world.getParameters().AGENT_FIRM_CONSUMER_Q;
		}
		if(index == 1){
			response = this.demandUnitsHistory.get(index);
		}else if(index > 1){
			response = this.demandUnitsHistory.get(index)+Math.round((this.demandUnitsHistory.get(index)-this.demandUnitsHistory.get(index-1)));
		}else{
			response = 0;
		}


		return Math.max(response, 0);
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

	public float getQk(){
		return this.world.getParameters().AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY * getMachinesCapital();

	}

	
	private float getMachinesCapital(){
		float acum = 0F;
		for(int i = 0; i < this.machines.size(); i++){
			acum = acum + this.machines.get(i).getGoodCapitalVintage().getPrice();
			
		}

		return acum;
	}
	
	public float getQl(){
		return this.getMachinesProductivityTotal() * this.employees.size();
	}
	
	public float getMachinesScoreAverage(){
		float average = 0F;
		float acum = 0F;
		int quantity = this.machines.size();

		if(quantity > 0){
			for(int i = 0; i < this.machines.size(); i++){
				acum = acum + this.machines.get(i).getGoodCapitalVintage().score();
			}

			average = (acum / quantity);

		}else{
			average = this.world.averageScore();
		}

		return average;
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
	
	public float getMachinesProductivityTotal(){
		float acum = 0F;
		int quantity = this.machines.size();
		if(quantity > 0){
			for(int i = 0; i < this.machines.size(); i++){
				acum = acum + this.machines.get(i).getGoodCapitalVintage().getProductivityA();
			}
		}else{
			acum = this.world.averageProductivityACapital();
		}
		return acum;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	private void brochuresProcess(){

		if(this.brochures.size() < 1)
			return;

		//logger.info("PROCESSING BROCHURES");
		// NEW PROVIDERS
		float investExpansion = 0F;

		if(true){
			//logger.info("FirmConsumer Brochure NewProvider");
			BrochureDTO brochure = this.goodCapitalOptimalNewProviders();

			if(brochure != null){
				float investment = 0F;

				if(this.machines.size() > 0){
//					logger.info("Machines size = "+this.machines.size());
					investment = this.getI();

				}else{
					investment = brochure.vintage.getPrice();
				}
				//logger.info("ID "+this.id+" Investment "+investment);
//				logger.info("Score avg "+this.getMachinesScoreAverage()+" and Score is "+brochure.vintage.score());
				
				
//				float score = brochure.vintage.score();
				float score = brochure.vintage.getProductivityA();
//				if(score >= this.getMachinesScoreAverage()){
				if(score >= this.getMachinesProductivityAverage()){


//					logger.info("FirmConsumer Brochure NewProvider Investment");
					while(investment >= brochure.vintage.getPrice()){
						//logger.info("ID "+this.id+" Iteration "+p++);
						//logger.info("BUYING NEW MACHINE FROM NEW PROVIDER "+investment+" price "+brochure.vintage.getPrice());
						OrderRequestDTO request = new OrderRequestDTO();
						request.consumer = this;
						request.vintage = brochure.vintage;
//						logger.info("FirmConsumer Brochure Create Order");
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
	
	private BrochureDTO goodCapitalOptimalNewProviders(){

		float score = Float.MIN_VALUE;
		
		BrochureDTO selected = null;
		for(int i = 0; i < this.brochures.size(); i++){
//			logger.info("Loop for 1");

			BrochureDTO brochure = this.brochures.get(i);
//			if(brochure.vintage.score() > score){
//				
//				logger.info("Loop if 2");
//				selected = brochure;
//				score = brochure.vintage.score();
//			}
			
			if(brochure.vintage.getProductivityA() > score){
				
//				logger.info("Loop if 2");
				selected = brochure;
				score = brochure.vintage.getProductivityA();
			}
			
//			if(brochure.consumer == null){
//				
//				logger.info("Loop if 1");
//				logger.info("is prod A ("+brochure.vintage.getProductivityA()+") > productivity? ("+productivity+")");
//
//				if(brochure.vintage.getProductivityA() > productivity){
//					logger.info("Loop if 2");
//
//					selected = brochure;
//					productivity = brochure.vintage.getProductivityA();
//				}
//			}

		}
		return selected;


}
	
//			if(brochure.vintage.score() > score){
//				logger.info("LOOP C");
//				selected = brochure;
//				score = brochure.vintage.score();
//			}
//			
////			if(brochure.consumer == null){
////				logger.info("LOOP B");
////
////			}
//
//		}
//		return selected;
//	}
//	
	public float getI(){
		float response = 0F;
		response = (int) Math.min(this.getQ0(),this.getQl()) - this.getMachinesCapital()*this.world.getParameters().AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;

//		int i = world.getCycle()-2;
//		if(i > 1 &&	world.getUnemployedHistory().get(i)*world.getPersonAgents().size() < 3){
//			response = (int) this.employees.size() * this.getMachinesProductivityAverage() - this.getQk() / this.world.getParameters().AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
//		}else{
//			response = (int) this.getQ0() * this.getPrice() - this.getQk() / this.world.getParameters().AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
//		}
//		logger.info("Price of Best Vint = "+this.world.getPriceofBestVintage());
//		response = Math.max(response,this.world.getPriceofBestVintage());
		return Math.max(response,0);
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
		return this.world.getWageCycle() / this.getMachinesProductivityTotal();

	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void processFabrication(){
		float q0 = this.getQ0();
		float ql = this.getQl();
		float qk = this.getQk();

		int qs = (int) Math.floor(Math.min(Math.min(q0, ql), qk));
		int stockAvailable = this.stock.getStockAvailable();

		int fabricate = qs + this.stockSpare - stockAvailable;

		this.fabricatedCycle = fabricate;

		if(this.machines.size() > 0){

			for(int i = 0; i < fabricate; i++){
				GoodConsumer good = new GoodConsumer();
				good.setAgent(this);
				good.setManufacturer(this);

				this.stock.getGoods().add(good);
			}

		}
	}
	
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

//		this.world.ipc();
		
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

		//logger.info("COMP="+competitivity+" COMP_AVG="+competitivityAverage+" PSI="+psi+" COMPARE_FIRST="+compareFirst+" FINAL="+newMarketShare);

		this.marketShares.add(newMarketShare);
		this.marketShareCycle = newMarketShare;
		if(newMarketShare < 0.002){
			this.world.replaceConsumerFirm(this);
		}

	}

	
	public float getCompetitivity(){
		float E = -this.world.getParameters().AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 * this.getPrice() - 
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