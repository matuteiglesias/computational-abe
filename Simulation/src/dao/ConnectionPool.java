package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class ConnectionPool{
	private Vector <Connection> connections = new Vector<Connection>();
	protected String driver;
	protected String jdbc;
	protected String usuario;
	protected String password;
	protected int cantCon;
	
	private static ConnectionPool pool;
	
	private ConnectionPool()
	{
		getConfiguration();
		for (int i= 0; i< cantCon;i++)
			connections.add(connect());
	}

	public static ConnectionPool getConnectionPool()
	{
		if (pool== null)
			pool = new ConnectionPool();
		return pool;
	}
	private Connection connect()
	{
		try
		{
			//Setear driver
			Class.forName (driver).newInstance ();
			String dbConnectString = jdbc; 
			Connection connection = DriverManager.getConnection (dbConnectString, usuario, password);
			connection.setAutoCommit(false);

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
		String configuracion = "ConfigBD.txt";
		Properties propiedades;

		// Carga del fichero de propiedades 
		try 
		{
			FileInputStream f = new FileInputStream(configuracion);	 
			propiedades = new Properties();
			propiedades.load(f);
			f.close();

			// Leo los valores de configuracion
			jdbc = propiedades.getProperty("jdbc"); 
			driver = propiedades.getProperty("driver");
//			servidor = propiedades.getProperty("servidor");
			usuario = propiedades.getProperty("usuario");
			password = propiedades.getProperty("password");
			cantCon = Integer.parseInt(propiedades.getProperty("conexiones"));
		} 
		catch (Exception e) 
		{
			System.out.println("Mensaje Error: " + e.getMessage());
			System.out.println("Stack Trace: " + e.getStackTrace());
		}
	}
	public void closeConnections()
	{
		for (int i=0; i<connections.size();i++)
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
		Connection c = null;
		if (connections.size()>0)
			c = connections.remove(0);
		else
		{
			c = connect();
			System.out.println("Se ha creado una nueva conexion fuera de los parametros de configuracion");
		}
		return c;
	}
	public void realeaseConnection(Connection c)
	{
		connections.add(c);
	}
}
