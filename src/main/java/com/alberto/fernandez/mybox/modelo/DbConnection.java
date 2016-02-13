package com.alberto.fernandez.mybox.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class manages the opening and closing of connections
 * @author Alberto
 *
 */
public class DbConnection {
	
	/** Connection parameteres de conexion */
	private static final String BD = "mybox";
	private static final String LOGIN = "root";
	private static final String PASSWORD = "";
	private static final String URL = "jdbc:mysql://localhost/" + BD;

	// private final static Logger LOG = Logger.getLogger(DBListener.class);
	
	private Connection connection = null;

	/**
	 * Constructor of DbConnection
	 *
	 * @throws SQLException
	 */
	public DbConnection() throws SQLException {
		try {
			// obtenemos el driver de para mysql
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// LOG.error("Falta el driver mysql");
			throw new SQLException("MYSQL driver unavailable");
		}
		// obtenemos la conexión
		connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		// LOG.trace("Conexión a base de datos " + BD + " OK\n");
	}

	/**
	 * Returns a working connection
	 * @return <code>Connection</code> The working connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Closes the connection
	 */
	public void closeConnection() {
		connection = null;
	}
}
