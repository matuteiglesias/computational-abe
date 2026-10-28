package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class ConnectionPool{
	private Vector <Connection> connections = new Vector<Connection>();
	protected int connectionsQ;
	protected String driver;
	protected String jdbc;
	protected String user;
	protected String password;
	
	private static ConnectionPool pool;
	
	private ConnectionPool()
	{
		getConfiguration();
		for (int i= 0; i< connectionsQ;i++)
			connections.add(this.connect());
	}

	public static ConnectionPool getConnectionPool()
	{
		if (pool == null)
			pool = new ConnectionPool();
		return pool;
	}
	private Connection connect()
	{
		try
		{
//			//Setear driver
			Class.forName (driver).newInstance ();
			String dbConnectString = jdbc; 
			Connection connection = DriverManager.getConnection (dbConnectString, user, password);
			
//			HikariConfig config = new HikariConfig();
//			config.addDataSourceProperty("dataSourceClassName", driver);
//			config.setJdbcUrl(jdbc);
//			config.setUsername(user);
//			config.setPassword(password);
//			config.addDataSourceProperty("cachePrepStmts", "true");
//			config.addDataSourceProperty("prepStmtCacheSize", "250");
//			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//
//			HikariDataSource ds = new HikariDataSource(config);
//			
//			Connection connection = ds.getConnection(); 
			connection.setAutoCommit(false);

//			ds.close();
			return connection;
		}
		catch (SQLException e)
		{
			System.out.println("Mensaje Error: " + e.getMessage());
			System.out.println("Stack Trace: " + e.getStackTrace());
			return null;
		}
		catch (Exception ex)
		{
			System.out.println("Mensaje Error: " + ex.getMessage());
			System.out.println("Stack Trace: " + ex.getStackTrace());
			return null;
		}
	}
	
	public void getConfiguration()
	{
		String configuration = "ConfigBD.txt";
		Properties propiedades;

		// Carga del fichero de propiedades 
		try 
		{
			FileInputStream f = new FileInputStream(configuration);	 
			propiedades = new Properties();
			propiedades.load(f);
			f.close();

			// Leo los valores de configuracion
			jdbc = propiedades.getProperty("jdbc"); 
			driver = propiedades.getProperty("driver");
			user = propiedades.getProperty("usuario");
			password = propiedades.getProperty("password");
			connectionsQ = Integer.parseInt(propiedades.getProperty("conexiones"));
		} 
		catch (Exception e) 
		{
			System.out.println("Mensaje Error: " + e.getMessage());
			System.out.println("Stack Trace: " + e.getStackTrace());
		}
	}
	public void closeConnections()
	{
		for (int i = 0; i < connections.size();i++)
		{
			try
			{
				connections.elementAt(i).close();
			}
			catch(Exception e)
			{
				System.out.println("Mensaje Error: " + e.getMessage());
				System.out.println("Stack Trace: " + e.getStackTrace());
			}
		}
	}
	public  Connection getConnection()
	{
		Connection connection = null;
		if (connections.size() > 0){
			connection = connections.remove(0);
//			System.out.println("Se tomo una conexión del pool");

		}else{
			connection = connect();
			System.out.println("Se ha creado una nueva conexion fuera de los parametros de configuracion");
		}
		return connection;
	}
	public void realeaseConnection(Connection c)
	{
		connections.add(c);
	}
}
