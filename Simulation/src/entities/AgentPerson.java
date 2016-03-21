package entities;

import java.util.ArrayList;
import java.util.List;

import dtos.AgentDTO;

public class AgentPerson extends Agent {

	private AgentFirm employer;

	private List<GoodConsumer> goods = new ArrayList<GoodConsumer>();

	public AgentFirm getEmployer() {
		return employer;
	}

	public void setEmployer(AgentFirm employer) {
		this.employer = employer;
	}

	public List<GoodConsumer> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodConsumer> goods) {
		this.goods = goods;
	}

	public void receiveWage(float wage){
		this.liquidAssets = this.liquidAssets + wage;
	}

	@Override
	public AgentDTO runCycle() {
		// cobra
		// compra

		return null;
	}

	@Override
	public String getCode() {
		return "P-"+String.valueOf(this.id);
	}

	public void buy(GoodConsumer good){
		good.getManufacturer().sell(good);
		good.setAgent(this);
		this.goods.add(good);
		this.liquidAssets = this.liquidAssets - good.getManufacturer().getStock().getPrice();
	}
	
	public boolean isEmployed(){
		if(this.employer != null)
			return true;
		return false;
		
	}
	// NO SE USA
//	private void processBuy(){
//		float toExpend = this.liquidAssets * Parameters.AGENT_PERSON_EXPEND;
//
//		List<AgentFirmConsumerStock> stocks = this.world.getStocks();
//
//			float cheapPrice = Float.MAX_VALUE;
//			AgentFirmConsumerStock cheapStock = null;
//			for(int i = 0; i < stocks.size(); i++){
//				AgentFirmConsumerStock aux = stocks.get(i);
//				if(aux.getPrice() < cheapPrice && aux.getGoods().size() > 0){
//					cheapStock = aux;
//				}
//			}
//			if(cheapStock != null){
//				int quantityToBuy = (int) Math.floor((toExpend / cheapStock.getPrice()));
//
//				while(cheapStock.getStockAvailable() > 0 && quantityToBuy > 0){
//					GoodConsumer good = this.world.buyConsumerGood(cheapStock.getConsumer());
//					if(good != null){
//						good.setAgent(this);
//						this.goods.add(good);
//						this.liquidAssets = this.liquidAssets - cheapStock.getPrice();
//						toExpend = toExpend - cheapStock.getPrice();
//						quantityToBuy--;
//					}
//				}
//		}
//
//	}
}
