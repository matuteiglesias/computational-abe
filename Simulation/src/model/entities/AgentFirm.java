package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AgentFirm extends Agent {
	private static final Logger logger = Logger.getLogger( AgentFirm.class.getName() );

	protected List<AgentPerson> employees = new ArrayList<AgentPerson>();
	
	protected float salesCycle;
	
	protected List<Float> salesHistory = new ArrayList<Float>();
	
	protected float costCycle;
	
	protected List<Float> costHistory = new ArrayList<Float>();
	
	protected int fabricatedCycle;
	
	protected int fabricatedLastCycle;

	protected List<Integer> fabricatedHistory = new ArrayList<Integer>();

	protected int soldUnitsCycle;
	
	protected List<Integer> soldUnitsHistory = new ArrayList<Integer>();
	
	protected List<Float> profitHistory = new ArrayList<Float>();

	public List<AgentPerson> getEmployees() {
		return employees;
	}

	public void setEmployees(List<AgentPerson> employees) {
		this.employees = employees;
	}

	public float getSalesCycle() {
		return salesCycle;
	}

	public void setSalesCycle(float salesCycle) {
		this.salesCycle = salesCycle;
	}

	public List<Float> getSalesHistory() {
		return salesHistory;
	}

	public void setSalesHistory(List<Float> salesHistory) {
		this.salesHistory = salesHistory;
	}

	public float getCostCycle() {
		return costCycle;
	}

	public void setCostCycle(float costCycle) {
		this.costCycle = costCycle;
	}

	public List<Float> getCostHistory() {
		return costHistory;
	}

	public void setCostHistory(List<Float> costHistory) {
		this.costHistory = costHistory;
	}

	public int getFabricatedCycle() {
		return fabricatedCycle;
	}

	public void setFabricatedCycle(int fabricatedCycle) {
		this.fabricatedCycle = fabricatedCycle;
	}
	
	public int getFabricatedLastCycle() {
		return fabricatedLastCycle;
	}

	public void setFabricatedLastCycle(int fabricatedLastCycle) {
		this.fabricatedLastCycle = fabricatedLastCycle;
	}

	public List<Integer> getFabricatedHistory() {
		return fabricatedHistory;
	}

	public void setFabricatedHistory(List<Integer> fabricatedHistory) {
		this.fabricatedHistory = fabricatedHistory;
	}

	public int getSoldUnitsCycle() {
		return soldUnitsCycle;
	}

	public void setSoldUnitsCycle(int soldUnitsCycle) {
		this.soldUnitsCycle = soldUnitsCycle;
	}

	public List<Integer> getSoldUnitsHistory() {
		return soldUnitsHistory;
	}

	public void setSoldUnitsHistory(List<Integer> soldUnitsHistory) {
		this.soldUnitsHistory = soldUnitsHistory;
	}
	
	public List<Float> getProfitHistory() {
		return profitHistory;
	}

	public void setProfitHistory(List<Float> profitHistory) {
		this.profitHistory = profitHistory;
	}

	public void runPayroll(){
	
		for(int i = 0; i < this.employees.size(); i++){
			
			AgentPerson person = this.employees.get(i);
			
			try{
				float salaryGross = this.world.getWageCycle();
				float taxes = salaryGross * this.world.getGovernment().getEmployeeTax();
				float salaryNet = salaryGross - taxes; 
				this.liquidAssets = this.liquidAssets - salaryGross;
				person.receiveWage(salaryNet);
				this.world.getGovernment().payEmployeeTax(taxes);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	protected abstract void processEmployees();

	public int fabricatedTotal(){		
		int acum = 0;
		for(int i = 0; i < this.fabricatedHistory.size(); i++){
			acum = acum + this.fabricatedHistory.get(i);
		}
		return acum;
	}
	
	public void unemployAll(){
		for(int i = 0; i < this.employees.size(); i++){
			this.employees.get(i).setEmployer(null);
		}
		
		this.employees.removeAll(this.employees);
		
	}
	
}
