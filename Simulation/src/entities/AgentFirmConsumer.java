package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import parameters.Parameters;
import utils.StdRandom;
import dtos.AgentDTO;
import dtos.BrochureDTO;
import dtos.OrderRequestDTO;

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

	@Override
	public AgentDTO runCycle() {
		// recibe capital goods
		// produce (evalua demanda)
		// evaluate new models (compra)
		// wages
		// fire or hire
		//		//logger.info("FirmConsumer Payroll");
		this.runPayroll();

		//		//logger.info("FirmConsumer Employees");
		this.processEmployees();
		// sell
		// bankrupt
		//		//logger.info("FirmConsumer Brochure");
		this.brochuresProcess();
		//		//logger.info("FirmConsumer Fabrication");
		this.processFabrication();

		return null;
	}

	public void processPL(){

		this.soldUnitsHistory.add(this.soldUnitsCycle);
		this.salesHistory.add(this.salesCycle);
		this.costHistory.add(this.costCycle);
		this.fabricatedHistory.add(this.fabricatedCycle);

		float totalCost = this.costCycle * this.fabricatedCycle;
		float profit = this.salesCycle - totalCost;

		this.profitHistory.add(profit);

		if(profit > 0){
			float taxes = profit * Parameters.AGENT_GOVERNMENT_FIRM_TAX;
			this.liquidAssets = this.liquidAssets - taxes;
			this.world.getGovernment().payFirmTax(taxes);
			//			//logger.info("PAY TAXES");
		}

		this.soldUnitsCycle = 0;
		this.salesCycle = 0;
		this.costCycle = 0;
		this.fabricatedCycle = 0;
	//	this.investmentCycle = 0;

	}
	public void updateMarketShare(){
		float competitivity = this.getCompetitivity();
		float competitivityAverage = this.world.getCompetitivityAverageCycle();
		float psi = Parameters.COMPETITIVITY_MARKETSHARE* (competitivityAverage - competitivity) / competitivityAverage;
		float compareFirst = this.marketShareCycle * ( 1 + psi);

		float newMarketShare = Math.max(compareFirst,0);

		//		//logger.info("COMP="+competitivity+" COMP_AVG="+competitivityAverage+" PSI="+psi+" COMPARE_FIRST="+compareFirst+" FINAL="+newMarketShare);

		this.marketShares.add(newMarketShare);
		this.marketShareCycle = newMarketShare;
		if(newMarketShare < 0.002){
			this.world.replaceConsumerFirm(this);
		}

	}

	private void processFabrication(){
		float q0 = this.getQ0();
		float ql = this.getQl();
		float qk = this.getQk();

		int qs = (int) Math.floor(Math.min(Math.min(q0, ql), qk));
		int stockAvailable = this.stock.getStockAvailable();

		int fabricate = qs + this.stockSpare - stockAvailable;


		this.fabricatedCycle = fabricate;

		if(this.machines.size() > 0){
			float costAcum = 0F;
			float costUnit = this.getCost();
			//		this.costCycle = this.getCost();


			for(int i = 0; i < fabricate; i++){
				GoodConsumer good = new GoodConsumer();
				good.setAgent(this);
				good.setManufacturer(this);

				this.stock.getGoods().add(good);
				costAcum = costAcum + costUnit;
				//			this.liquidAssets = this.liquidAssets - this.costCycle;
			}

			this.costCycle = costAcum;
			this.liquidAssets = this.liquidAssets - costCycle;
		}
	}

	public float getCost(){
		return this.world.getWage() / this.getMachinesProductivityAverage();

	}
	public float getPrice(){

		float cost = this.getCost();
		float price = cost * (1 + Parameters.AGENT_FIRM_CAPITAL_MARGIN);

		return price;
	}

	public float getQk(){
		return Parameters.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY * getMachinesCapital();

	}

	public float getQl(){
		return this.getMachinesProductivityAverage() * this.employees.size();
	}

	private float getMachinesCapital(){
		float acum = 0F;

		for(int i = 0; i < this.machines.size(); i++){
			acum = acum + this.machines.get(i).getGoodCapitalVintage().getPrice();
		}

		return acum;
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

	private BrochureDTO goodCapitalMostProductiveNewProviders(){
		float productivity = Float.MIN_VALUE;
		BrochureDTO selected = null;
		for(int i = 0; i < this.brochures.size(); i++){
			BrochureDTO brochure = this.brochures.get(i);
			if(brochure.consumer == null){

				if(brochure.vintage.getProductivityA() > productivity){
					selected = brochure;
					productivity = brochure.vintage.getProductivityA();
				}
			}

		}
		return selected;


	}

	private List<BrochureDTO> goodCapitalFromProviders(){
		//		float productivity = Float.MIN_VALUE;
		List<BrochureDTO> response = new ArrayList<BrochureDTO>();
		for(int i = 0; i < this.brochures.size(); i++){
			BrochureDTO brochure = this.brochures.get(i);
			if(brochure.consumer != null && brochure.consumer.equals(this)){
				response.add(brochure);
				//				if(brochure.vintage.getProductivityA() > productivity){
				//					selected = brochure;
				//					productivity = brochure.vintage.getProductivityA();
				//				}
			}
		}
		return response;
	}

	@Override
	public String getCode() {
		return "GF-"+String.valueOf(this.id);
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
	private void brochuresProcess(){

		if(this.brochures.size() < 1)
			return;

		//logger.info("PROCESSING BROCHURES");

		// NEW PROVIDERS
		float investReplace = 0F;

		if(true){
			//logger.info("PROCESSING BROCHURES NEW PROVIDER");
			//logger.info("FirmConsumer Brochure NewProvider");
			BrochureDTO brochure = this.goodCapitalMostProductiveNewProviders();
			if(brochure != null){
				float investment = 0F;

				if(this.machines.size() > 0){
					investment = this.getI();

				}else{
					investment = brochure.vintage.getPrice();
				}
				//logger.info("ID "+this.id+" Investment "+investment);
				//logger.info("Prod avg "+this.getMachinesProductivityAverage()+" and prodA is "+brochure.vintage.getProductivityA());

				if(brochure.vintage.getProductivityA() >= this.getMachinesProductivityAverage()){


					//logger.info("FirmConsumer Brochure NewProvider Investment");
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
						investReplace = investReplace + brochure.vintage.getPrice();
						//logger.info("Investment "+investment);

					}

				}
			}
		}
		// EXISTING PROVIDERS
		float investExpansion = 0F;

		if(true){
			//logger.info("FirmConsumer Brochure ExistingProvider");
			//logger.info("PROCESSING BROCHURES EXISTING PROVIDERS");
			List<BrochureDTO> brochuresProviders = this.goodCapitalFromProviders();
			for(int i = 0; i < brochuresProviders.size(); i++){

				BrochureDTO brochure = brochuresProviders.get(i);
				GoodCapitalVintage newVintage = brochure.vintage;
				List<GoodCapital> goods = this.goodCapitalsFromProvider(brochure.manufacturer);

				float newPrice = newVintage.getPrice();
				float newCost = this.world.getWage() / newVintage.getProductivityA();

				//logger.info("FirmConsumer Brochure ExistingProvider Machines");
				for(int j = 0; j< goods.size(); j++){
					GoodCapital existingGood = goods.get(j);
					float existingCost = this.world.getWage() / existingGood.getGoodCapitalVintage().getProductivityA();

					float factor = newPrice / (existingCost - newCost);


					if(factor <= Parameters.AGENT_FIRM_CONSUMER_PAYBACK_PERIOD){
						// REPLACE MACHINE
						//logger.info("REPLACING MACHINE FROM EXISTING PROVIDER");
						OrderRequestDTO request = new OrderRequestDTO();
						request.consumer = this;
						request.vintage = brochure.vintage;
						request.toReplace = existingGood;
						brochure.manufacturer.requestOrderCreate(request);
						investExpansion = investExpansion + brochure.vintage.getPrice();
						
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

	private GoodCapitalVintage getMostProductiveGood(){
		GoodCapitalVintage machine = null;
		float productivity = Float.MIN_VALUE;
		for(int i = 0; i < this.machines.size(); i++){
			GoodCapital aux = this.machines.get(i);
			if(aux.getGoodCapitalVintage().getProductivityA() > productivity){
				machine = aux.getGoodCapitalVintage();
				productivity = aux.getGoodCapitalVintage().getProductivityA();
			}
		}

		return machine;
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

	@Override
	protected void processEmployees() {

		int employeesGap = (int) Math.round((Math.min(this.getQ0(), this.getQk()) - this.getQl()) / this.getMachinesProductivityAverage());

		if(employeesGap > 0){
			//Contratar

			List<AgentPerson> employees = this.world.getEmployees(employeesGap);

			for(int i = 0; i < employees.size(); i++){
				AgentPerson employee = employees.get(i);
				employee.setEmployer(this);
				this.employees.add(employee);
			}
		}else if(employees.size() + employeesGap > 1){
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

		if(index == 1){
			response = Math.max(this.stockSpare - this.stock.getStockAvailable(), 0) + this.demandUnitsHistory.get(index);
		}else if(index > 1){
			response = (int) Math.max(this.stockSpare - this.stock.getStockAvailable(), 0) + this.demandUnitsHistory.get(index)+Math.round((this.demandUnitsHistory.get(index)-this.demandUnitsHistory.get(index-1)));
		}else{

			response = Math.max(this.stockSpare - this.stock.getStockAvailable(), 0);
		}


		return response;
	}

	public float getI(){
		float response = 0F;
		int i = world.getCycle()-2;
		if(i > 1 &&	world.getUnemployedHistory().get(i)*world.getPersonAgents().size() < 3){
			response = (int) this.employees.size() * this.getMachinesProductivityAverage() - this.getQk() / Parameters.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
		}else{
			response = (int) this.getQ0() * this.getPrice() - this.getQk() / Parameters.AGENT_FIRM_CONSUMER_CAPITAL_INTENSITY;
		}
		response = Math.max(response,0);
		return response;
	}

	public float getCompetitivity(){
		float E = -Parameters.AGENT_FIRM_CONSUMER_COMPETITIVITY_PRICE_W1 * this.getPrice() - 
				Parameters.AGENT_FIRM_CONSUMER_COMPETITIVITY_UNFILLED_W2 * this.unfilledDemandCycle;

		return E;
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

	public void removeOldCapital(){
		for(int i = 0; i < this.machines.size() ; i++){
			GoodCapital good = this.machines.get(i);
			int gap = this.world.getCycle() - good.getCycle();
			int rand = (int) Math.round(StdRandom.gaussian(0, 1 * Parameters.AGENT_FIRM_CONSUMER_OBSOLETE));
			if(gap >= Parameters.AGENT_FIRM_CONSUMER_OBSOLETE + rand){
				this.machines.remove(good);
			}
		}
	}
}
