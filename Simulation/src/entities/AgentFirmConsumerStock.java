package entities;

import java.util.ArrayList;
import java.util.List;

public class AgentFirmConsumerStock {

	private AgentFirmConsumer consumer;

	private List<GoodConsumer> goods = new ArrayList<GoodConsumer>();

	public List<GoodConsumer> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodConsumer> goods) {
		this.goods = goods;
	}

	public AgentFirmConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(AgentFirmConsumer consumer) {
		this.consumer = consumer;
	}

	public int getStockAvailable(){
		return goods.size();
	}

	public float getPrice(){
		return consumer.getPrice();
	}

	public GoodConsumer buy(){
		GoodConsumer good = null;
		if(this.goods.size() > 0){
			good = this.goods.get(0);
			this.goods.remove(0);
		}

		return good;
	}

	public List<GoodConsumer> getGoodsConsumer(int quantity){
		List<GoodConsumer> response = new ArrayList<GoodConsumer>();
		int pending = quantity;

		for(int i = 0; i < this.goods.size() && pending > 0; i++){
			response.add(this.goods.get(i));
			pending--;			
		}

		this.goods.removeAll(response);
		return response;

	}

}
