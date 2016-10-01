package entities;

import java.util.List;

import parameters.Parameters;
import dtos.AgentDTO;

public class AgentGovernment extends Agent {
	
	@Override
	public AgentDTO runCycle() {
		
//		AgentDTO dto = new AgentDTO();
				// TODO Auto-generated method stub
		// Levies taxes
		//pay unemployment benefits
		this.runPayroll();
		return null;
	}

	@Override
	public String getCode() {
		return "G-"+String.valueOf(this.id);
	}
	
	public float getEmployeeTax(){
		return Parameters.AGENT_GOVERNMENT_EMPLOYEE_TAX;
	}
	

	public float getFirmProfitTax(){
		return Parameters.AGENT_GOVERNMENT_FIRM_TAX;
	}
	
	public void payEmployeeTax(float taxes){
		this.liquidAssets = this.liquidAssets + taxes;
	}

	public void payFirmTax(float taxes){
		this.liquidAssets = this.liquidAssets + taxes;
	}
	
	public void runPayroll(){
		List<AgentPerson> unemployed = this.world.unemployedGet();
		for(int i = 0; i < unemployed.size(); i++){
			
			AgentPerson person = unemployed.get(i);
			
			try{
				float salaryGross = this.world.getWageCycle() * Parameters.AGENT_GOVERNMENT_UNEMPLOYED_WAGE;
//				float taxes = salaryGross * this.world.getGovernment().getEmployeeTax();
				float taxes = 0;
				float salaryNet = salaryGross - taxes; 
				this.liquidAssets = this.liquidAssets - salaryGross;
				person.receiveWage(salaryNet);
//				this.world.getGovernment().payEmployeeTax(taxes);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
