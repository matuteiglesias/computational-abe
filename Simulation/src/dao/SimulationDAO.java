package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Logger;

import model.world.ModelWorld;

public class SimulationDAO {
	private static final Logger logger = Logger.getLogger( SimulationDAO.class.getName() );

//	private static SimulationDAO instance = null;
	private Connection connection = ConnectionPool.getConnectionPool().getConnection();
//	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
//	private static final String DB_URL = "jdbc:mysql://localhost/simulations";

	private PreparedStatement psCycleValues;
	private PreparedStatement psCycle;

	//  Database credentials
//	private static final String USER = "simulations";
//	private static final String PASS = "simulations";

//	public static SimulationDAO getInstance(){
//		if(instance == null){
//			instance = new SimulationDAO();
//		}
//		return instance;
//
//	}
//
	public SimulationDAO(){

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void prepare(){
		//		   Statement stmt = null;
		try{
			if(this.connection.isClosed()){
				logger.info(Thread.currentThread().getName()+" CONEXION CERRADA");
			}
//			//STEP 2: Register JDBC driver
//			Class.forName(JDBC_DRIVER);
//
//			//STEP 3: Open a connection
//			System.out.println("Connecting to database...");
//			connection = DriverManager.getConnection(DB_URL,USER,PASS);
//			connection.setAutoCommit(false);
//			connection = ConnectionPool.getConnectionPool().getConnection();
			String statement = "INSERT INTO cycles (id_experiment, id_configuration, id_simulation, id) VALUES(?, ?, ?, ?)";
			psCycle = connection.prepareStatement(statement);
			statement = "INSERT INTO results (id_experiment, id_configuration, id_simulation, id_cycle, name, value) VALUES(?, ?, ?, ?, ?, ?)";
			psCycleValues = connection.prepareStatement(statement);
			
			
			//			//STEP 4: Execute a query
			//			System.out.println("Creating statement...");
			//			stmt = connection.createStatement();
			//			String sql;
			//			sql = "SELECT * FROM experiment1";
			//			ResultSet rs = stmt.executeQuery(sql);
			//
			//			//STEP 5: Extract data from result set
			//			while(rs.next()){
			//
			//				//Retrieve by column name
			//				String id  = rs.getString("field1");
			//				//		         int age = rs.getInt("age");
			//				//		         String first = rs.getString("first");
			//				//		         String last = rs.getString("last");
			//				//
			//				//		         //Display values
			//				System.out.print("ID: " + id + "\n");
			//				//		         System.out.print(", Age: " + age);
			//				//		         System.out.print(", First: " + first);
			//				//		         System.out.println(", Last: " + last);
			//			}
			//STEP 6: Clean-up environment
			//			rs.close();
			//			stmt.close();
			//			connection.close();
		}catch(SQLException se){
			System.out.println("SQLException at SimulationDAO connect ["+se.getMessage()+"]");
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			System.out.println("Exception at SimulationDAO connect");
			//Handle errors for Class.forName
			e.printStackTrace();
		}//end try
	}

	public void finish(){
		try {
			psCycleValues.executeBatch();
			this.psCycle.close();
			this.psCycleValues.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			connection.commit();
			ConnectionPool.getConnectionPool().realeaseConnection(connection);
//			connection.commit();
//			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int insertExperiment(){

		Timestamp date = new Timestamp(Calendar.getInstance().getTimeInMillis());
		int experimentId = 0;
		try {
			String statement = "INSERT INTO experiments (date) VALUES(?)";
			PreparedStatement ps = null;

			ps = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

			ps.setTimestamp(1, date);
			ps.execute();
			
			connection.commit();

			ResultSet rs = ps.getGeneratedKeys();

			while(rs.next()){
				experimentId = rs.getInt(1);
			}
			
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException at SimulationDAO insertExperiment");
			e.printStackTrace();
		}
		return experimentId;

	}

	public void insertConfiguration(int experimentId, int configurationId, String name){
		try {
			String statement = "INSERT INTO configurations (id, name, id_experiment) VALUES(?, ?, ?)";
			PreparedStatement ps = null;

			ps = connection.prepareStatement(statement);
			ps.setInt(1, configurationId);
			ps.setString(2, name);
			ps.setInt(3, experimentId);
			ps.execute();
			ps.close();
			connection.commit();

		} catch (SQLException e) {
			System.out.println("SQLException at SimulationDAO insertConfiguration [[[[[[[[[[");
			e.printStackTrace();
			System.out.println("]]]]]]]]]]]]]]]");
		}
	}

	public void insertSimulation(int experimentId, int configurationId, int simulationId){
		try {
			String statement = "INSERT INTO simulations (id_experiment, id_configuration, id) VALUES(?, ?, ?)";
			PreparedStatement ps = null;

			ps = connection.prepareStatement(statement);

			//			System.out.println("INDEX ES "+Integer.getInteger(thisRun.INDEX));
			ps.setInt(1, experimentId);
			ps.setInt(2, configurationId);
			ps.setInt(3, simulationId);
			ps.execute();
			ps.close();
			connection.commit();

		} catch (SQLException e) {
			System.out.println("SQLException at SimulationDAO insertSimulation");
			e.printStackTrace();
		}
	}

	public void insertCycle(int experimentId, int configurationId, int simulationId, int cycleId, ModelWorld world){
		try {
//			String statement = "INSERT INTO cycles (id_experiment, id_configuration, id_simulation, id) VALUES(?, ?, ?, ?)";
//			PreparedStatement psCycle = connection.prepareStatement(statement);
//			statement = "INSERT INTO results (id_experiment, id_configuration, id_simulation, id_cycle, name, value) VALUES(?, ?, ?, ?, ?, ?)";
//			PreparedStatement psCycleValues = connection.prepareStatement(statement);
//			
			psCycle.setInt(1, experimentId);
			psCycle.setInt(2, configurationId);
			psCycle.setInt(3, simulationId);
			psCycle.setInt(4, cycleId);
			psCycle.addBatch();
//			logger.info(Thread.currentThread().getName()+" QUERY"+ psCycle.toString());
			psCycle.executeBatch();
			
			//INSERTO VALUES
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "Consumption", String.valueOf(world.getConsumptionHistory().get(cycleId)));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "Employed", String.valueOf(world.getEmployedHistory().get(cycleId)));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "FabricatedCapitalTotal", String.valueOf(world.fabricatedCapitalTotal()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "FabricatedConsumerTotal", String.valueOf(world.fabricatedConsumerTotal()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "SalesCapitalTotal", String.valueOf(world.salesCapitalCycleTotal()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "SalesConsumerTotal", String.valueOf(world.salesConsumerCycleTotal()));		
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "InvestmentCapital", String.valueOf(world.investmentCapitalTotal()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "AvgProdA_MS", String.valueOf(world.averageProdAMS()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "AvgProdA_EMP", String.valueOf(world.averageProdAEMP()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "AvgProdB_MS", String.valueOf(world.averageProdBMS()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "AvgProdB_EMP", String.valueOf(world.averageProdBEMP()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "Consumer_EMP", String.valueOf(world.consumerEMP()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "Capital_EMP", String.valueOf(world.capitalEMP()));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "IPC", String.valueOf(world.getIpcHistory().get(cycleId)));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "Capital_Bankrupts", String.valueOf(world.getCapitalBankruptHistory().get(cycleId)));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "Consumer_Bankrupts", String.valueOf(world.getConsumerBankruptHistory().get(cycleId)));
			this.insertValue(psCycleValues, experimentId, configurationId, simulationId, cycleId, "WAGE", String.valueOf(world.getWageHistory().get(cycleId)));
//			logger.info(Thread.currentThread().getName()+" QUERY"+ psCycleValues.toString());
//			psCycleValues.executeBatch();

//			psCycle.close();
//			psCycleValues.close();
		} catch (SQLException e) {
			System.out.println("SQLException at SimulationDAO insertCycle");
			e.printStackTrace();
		}

	}

//	DEPRECADO PERO GUARDADO POR LAS DUDAS
//	public void createBatchCycle(){
//		try {
//			String statement = "INSERT INTO cycles (id_experiment, id_configuration, id_simulation, id) VALUES(?, ?, ?, ?)";
//			psCycle = connection.prepareStatement(statement);
//			
//			statement = "INSERT INTO results (id_experiment, id_configuration, id_simulation, id_cycle, name, value) VALUES(?, ?, ?, ?, ?, ?)";
//			psCycleValues = connection.prepareStatement(statement);
//			
//		} catch (SQLException e) {
//			System.out.println("SQLException at SimulationDAO executeBatchCycle");
//			e.printStackTrace();
//		}
//	}

//	DEPRECADO PERO GUARDADO POR LAS DUDAS
//	public void executeBatchCycle(){
//		try {
//			this.psCycle.executeBatch();
//			this.psCycleValues.executeBatch();
//			connection.commit();
//		} catch (SQLException e) {
//			System.out.println("SQLException at SimulationDAO executeBatchCycle");
//			e.printStackTrace();
//		}
//	}

	public void insertValue(PreparedStatement ps, int experimentId, int configurationId, int simulationId, int cycleId, String name, String value){
//		Simulation,Cycle,Consumption,Employed,FabricatedCapital,FabricatedConsumer,InvestmentCapital,InvestmentConsumer,AvgProdA_MS,AvgProdA_EMP,AvgProdB_MS,AvgProdB_EMP,Consumer_EMP,Capital_EMP,IPC,Capital bankrupts,Consumer bankrupts,WAGE
		try {
//			logger.info(experimentId+" "+configurationId+" "+simulationId+" "+cycleId+" "+name+" "+value);

			ps.setInt(1, experimentId);
			ps.setInt(2, configurationId);
			ps.setInt(3, simulationId);
			ps.setInt(4, cycleId);
			ps.setString(5, name);
			ps.setString(6, value);
			ps.addBatch();


		} catch (SQLException e) {
			System.out.println("SQLException at SimulationDAO insertValue");
			e.printStackTrace();
		}
	}

	public void checkLabel(String label){
		try {
			PreparedStatement ps = null;
			String statement = "SELECT name FROM labels WHERE name=?";
			ps = connection.prepareStatement(statement);
			ps.setString(1, label);
			ResultSet rs = ps.executeQuery();

			if(!rs.next()){
				statement = "INSERT INTO labels (name) VALUES(?)";

				ps = connection.prepareStatement(statement);

				ps.setString(1, label);
				ps.execute();
			}
			rs.close();

			//STEP 6: Clean-up environment
		} catch (SQLException e) {
			System.out.println("SQLException at SimulationDAO checkLabel");
			e.printStackTrace();
		}
	}
	
	
}
