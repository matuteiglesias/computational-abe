package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import model.dtos.AgentDTO;
import model.dtos.BrochureDTO;
import model.dtos.OrderRequestDTO;
import model.parameters.ModelParametersSimulation;
import model.utils.StdRandom;


public class AgentFirmCapital extends AgentFirm {
	private static final Logger logger = Logger.getLogger( AgentFirmCapital.class.getName() );

	private List<GoodCapital> capitalGoods = new ArrayList<GoodCapital>();

	private List<GoodCapitalVintage> capitalGoodVintage = new ArrayList<GoodCapitalVintage>();

	private List<AgentFirmConsumer> consumers = new ArrayList<AgentFirmConsumer>();

	private GoodCapitalVintage lastVintage = new GoodCapitalVintage();

	private List<AgentFirmCapitalOrderRequest> requestOrders = new ArrayList<AgentFirmCapitalOrderRequest>();

	private float productivityB;


	/********/

	public List<GoodCapital> getCapitalGoods() {
		return capitalGoods;
	}

	public void setCapitalGoods(List<GoodCapital> capitalGoods) {
		this.capitalGoods = capitalGoods;
	}

	public List<GoodCapitalVintage> getCapitalGoodVintage() {
		return capitalGoodVintage;
	}

	public void setCapitalGoodVintage(List<GoodCapitalVintage> capitalGoodVintage) {
		this.capitalGoodVintage = capitalGoodVintage;
	}

	public GoodCapitalVintage getLastVintage() {
		return lastVintage;
	}

	public void setLastVintage(GoodCapitalVintage lastVintage) {
		this.lastVintage = lastVintage;
	}

	public List<AgentFirmConsumer> getConsumers() {
		return consumers;
	}

	public void setConsumers(List<AgentFirmConsumer> consumers) {
		this.consumers = consumers;
	}

	public List<AgentFirmCapitalOrderRequest> getRequestOrders() {
		return requestOrders;
	}

	public void setRequestOrders(List<AgentFirmCapitalOrderRequest> requestOrders) {
		this.requestOrders = requestOrders;
	}

	public float getProductivityB() {
		return productivityB;
	}

	public void setProductivityB(float productivityB) {
		this.productivityB = productivityB;
	}

	public void runResearch(){

		GoodCapital newCapital = null;

		boolean haveToCreateNewCapital = false;

		if(haveToCreateNewCapital){
			newCapital = new GoodCapital();
			this.capitalGoods.add(newCapital);                                     
		}

	}

	public List<BrochureDTO> brochuresGet(){

		List<BrochureDTO> response = new ArrayList<BrochureDTO>();

		if(this.world.getEmployees(1).size() > 0){
			int max = this.getMaxQForCycle() * 5;
			int act = this.requestOrders.size();

			if(act <= max){
				for(int i = 0; i < this.consumers.size(); i++){
					AgentFirmConsumer consumer = this.consumers.get(i);
					BrochureDTO dto = new BrochureDTO();
					dto.consumer = consumer;
					dto.manufacturer = this;
					dto.vintage = this.lastVintage;
					response.add(dto);
				}

				for(int i = 0; i < ModelParametersSimulation.AGENT_FIRM_CAPITAL_BROCHURES; i++){
					BrochureDTO dto = new BrochureDTO();
					dto.manufacturer = this;
					dto.vintage = this.lastVintage;
					response.add(dto);
				}
			}
		}
		return response;

	}

	public void runPromote(){

	}



	private void researchCapitalGoodVintage(){
		//	//logger.info(this.id+" investment: "+this.getI());


		float IN = model.parameters.ModelParametersSimulation.AGENT_FIRM_CAPITAL_FRACTION_X * this.getI();
		float pIN = (float) (1 - Math.exp(- model.parameters.ModelParametersSimulation.AGENT_FIRM_CAPITAL_Z_IN * IN));
		float IM = (1-model.parameters.ModelParametersSimulation.AGENT_FIRM_CAPITAL_FRACTION_X)* this.getI();		
		float pIM = (float) (1 - Math.exp(- model.parameters.ModelParametersSimulation.AGENT_FIRM_CAPITAL_Z_IM * IM));

//		logger.info("pIN = "+pIN+"pIM = "+pIM);
		
		this.liquidAssets = this.liquidAssets - this.getI();


		float vintageI = 0F;
		if(StdRandom.bernoulli(pIN)){
			//		//logger.info(this.id+" INNOV");

			float min = 0.9F;
			float max = 1.1F;
			float sigma = (float) (0.2 * (max - min));

			vintageI = (1 + (float) StdRandom.gaussian(0, sigma)) * this.lastVintage.getProductivityA();
			}
		
		float vintageIM = 0F;
		if(StdRandom.bernoulli(pIM)){

			float aux = 0F;
			List<AgentFirmCapital> capitals = this.world.getCapitalFirmAgents();
			//		//logger.info(this.id+" IMIT");

			boolean success = false;
			int iter = 0;
			while(!success && iter < ModelParametersSimulation.AGENT_FIRM_CAPITAL_Q){
				iter++;
				int minIndex = 0;
				int maxIndex = capitals.size() - 1;
				int index = minIndex + (int)(Math.random() * ((maxIndex - minIndex) + 1));
				AgentFirmCapital capital = capitals.get(index);

				if(capital != this){
					aux = capital.getLastVintage().getProductivityA();
					success = true;
				}
			}

			if(aux > 0){
				float ratio = aux / this.lastVintage.getProductivityA();

				if(ratio > 1F /*&& ratio < 1.2F*/){
					vintageIM = aux;
				}
			}

			float productivityFinal = Math.max(vintageI, vintageIM);

			if(productivityFinal > this.lastVintage.getProductivityA()){
				GoodCapitalVintage vintage = new GoodCapitalVintage();
				vintage.setProductivityA(productivityFinal);

				float cost = this.world.getWageCycle() / this.productivityB;
				float price = cost * (1 + ModelParametersSimulation.AGENT_FIRM_CAPITAL_MARGIN);
				vintage.setPrice(price);

				this.capitalGoodVintage.add(vintage);
				this.lastVintage = vintage;
			}
		}

	}

	private void researchProductivityB(){

		float min = 0.98F;
		float max = 1.02F;
		float sigma = (float) (0.2 * (max - min));

		float improvement = 1 + (float) StdRandom.gaussian(0, sigma);

		if(improvement * this.productivityB  > this.productivityB){
			this.productivityB = improvement * this.productivityB;
		}
	}

	@Override
	public AgentDTO runCycle() {

		this.runPayroll();
		this.processEmployees();
		this.researchCapitalGoodVintage();
		this.researchProductivityB();
		// bankrupt

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
			float taxes = profit * ModelParametersSimulation.AGENT_GOVERNMENT_FIRM_TAX;
			this.liquidAssets = this.liquidAssets - taxes;
			this.world.getGovernment().payFirmTax(taxes);
			//			//logger.info("PAY TAXES");
		}

		this.soldUnitsCycle = 0;
		this.salesCycle = 0;
		this.costCycle = 0;
		this.fabricatedCycle = 0;

		float NW = this.getLiquidAssets();
		if(NW < 0){
			//logger.info("CREANDO FIRM CAPITAL");
			this.world.replaceCapitalFirm(this);
		}

	}
	@Override
	public String getCode() {
		return "CF-"+String.valueOf(this.id);
	}




	public void requestOrdersProcess(){

		int quantity = this.getMaxQForCycle();
		int processedOrders = 0;

		for(int i = 0; i < this.requestOrders.size() && processedOrders < quantity; i++){
			//					logger.info("primer if=");

			AgentFirmCapitalOrderRequest order = this.requestOrders.get(i);
			if(order.getStatus() == AgentFirmCapitalOrderRequest.Status.UNPROCESSED){
				//				logger.info("segundo if=");

				// CONSTRUYE LA MAQUINA

				GoodCapital goodCapital = new GoodCapital();
				goodCapital.setGoodCapitalVintage(order.getGoodCapitalVintage());
				goodCapital.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
				goodCapital.setCycle(world.getCycle());
				goodCapital.setAgent(this);
				goodCapital.setManufacturer(this);
				this.capitalGoods.add(goodCapital);
				order.setNewGood(goodCapital);
				order.setStatus(AgentFirmCapitalOrderRequest.Status.PROCESSED);

				// AGREGA SI ES NUEVO CLIENTE
				if(!isCustomer(order.getConsumer())){
					this.consumers.add(order.getConsumer());
				}

				// ENVIA LA MAQUINA AL CLIENTE
				this.requestOrders.remove(order);

				//				float earnings = order.getConsumer().receiveGoodCapital(order);
				order.getConsumer().receiveGoodCapital(order);
				float cost = this.world.getWageCycle() / this.productivityB;
				//
				this.costCycle = this.costCycle + cost;
				//				this.salesCycle = this.salesCycle + earnings;
				//				this.soldUnitsCycle++;
				this.fabricatedCycle++;

				//				float cashflow = earnings - cost;
				this.liquidAssets = this.liquidAssets  - cost;
				order.setStatus(AgentFirmCapitalOrderRequest.Status.DELIVERED);

				processedOrders++;
			}
		}
	}

	public int getMaxQForCycle(){
		int quantity = 0;

		quantity = (int) Math.floor(this.productivityB * this.employees.size());

		return quantity;
	}
	private boolean isCustomer(AgentFirmConsumer consumer){
		boolean exists = false;

		for(int i = 0; i < this.consumers.size(); i++){
			AgentFirmConsumer aux = this.consumers.get(i);

			if(aux.equals(consumer)){
				exists = true;
			}
		}
		return exists;
	}
	public void requestOrderCreate(OrderRequestDTO request){
		AgentFirmCapitalOrderRequest order = new AgentFirmCapitalOrderRequest();
		order.setConsumer(request.consumer);
		order.setGoodCapitalVintage(request.vintage);
		//logger.info("Vintage Requested to "+this.id);

		if(request.toReplace != null)
			order.setToReplace(request.toReplace);
		this.requestOrders.add(order);


		float earnings = request.vintage.getPrice();
		this.salesCycle = this.salesCycle + earnings;
		this.soldUnitsCycle++;

		this.liquidAssets = this.liquidAssets  + earnings;
	}

	public int getRequestOrdersUnprocessed(){
		int response = 0;

		for(int i = 0; i < this.requestOrders.size(); i++){
			AgentFirmCapitalOrderRequest order = this.requestOrders.get(i);

			if(order.getStatus().equals(AgentFirmCapitalOrderRequest.Status.UNPROCESSED)){
				response++;
			}
		}
		return response;
	}

	public int getRequestOrdersDelivered(){
		int response = 0;

		for(int i = 0; i < this.requestOrders.size(); i++){
			AgentFirmCapitalOrderRequest order = this.requestOrders.get(i);

			if(order.getStatus().equals(AgentFirmCapitalOrderRequest.Status.DELIVERED)){
				response++;
			}
		}
		return response;
	}

	@Override
	protected void processEmployees() {
		int employeesGap = (int) Math.round(this.getRequestOrdersUnprocessed() / (2 * this.productivityB))-this.employees.size();		
		if(employeesGap > 0){
			//Contratar

			List<AgentPerson> employees = this.world.getEmployees(employeesGap);

			for(int i = 0; i < employees.size(); i++){
				AgentPerson employee = employees.get(i);
				employee.setEmployer(this);
				this.employees.add(employee);
			}
		}else if(employeesGap < 0){
			//Despedir
			int pending = -employeesGap;
			for(int i = 0; i < this.employees.size() && pending > 0; i++){
				if(this.employees.size() > 1){
					AgentPerson employee = this.employees.get(i);
					employee.setEmployer(null);
					this.employees.remove(i);
					pending--;
				}
			}
		}


	}
	public float getI(){
		float response = 0F;
//		Prueba
		if(this.salesHistory.size() > 0){
			int index = this.salesHistory.size()-1;
			response = this.salesHistory.get(index)*model.parameters.ModelParametersSimulation.AGENT_FIRM_CAPITAL_RD_PROPENSITY;
		}else{
			response = 0;
		}
		return response;
	}


	public float getLiquidAssets() {
		return liquidAssets;
	}

	public void setLiquidAssets(double d) {
		this.liquidAssets = (float) d;
	}
}
