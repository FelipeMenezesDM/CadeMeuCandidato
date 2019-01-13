package model;

import java.sql.*;

/**
 * Classe de conex�o com o Banco de Dados.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class Conn {
	private final String DRIVER;
	private final String URL;
	private Connection connection = null;
	
	/**
	 * Conex�o com host e porta padr�o.
	 * 
	 * @param dbName   Nome da base de dados.
	 * @param user     Nome de usu�rio da inst�ncia.
	 * @param password Senha para a inst�ncia.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Conn( String dbName, String user, String password ) throws ClassNotFoundException, SQLException {
		this( dbName, user, password, "localhost", 1433 );
	}

	/**
	 * Conex�o com host e porta informados.
	 * 
	 * @param instance Nome da base de dados.
	 * @param user     Nome de usu�rio da inst�ncia.
	 * @param password Senha para a inst�ncia.
	 * @param host     Nome do host.
	 * @param port     Porta da inst�ncia.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Conn( String dbName, String user, String password, String host, int port ) throws ClassNotFoundException, SQLException {
		DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		URL = "jdbc:sqlserver://" + host + ":" + port + "; databaseName=" + dbName + "; user=" + user + "; password=" + password + ";";
		
		makeConnection();
	}
	
	/**
	 * Criar conex�o com a inst�ncia.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void makeConnection() throws SQLException, ClassNotFoundException {
		Class.forName( DRIVER );
		connection = DriverManager.getConnection( URL );
	}
	
	/**
	 * Obter objeto da conex�o com a inst�ncia.
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Estado da conex�o com a base de dados.
	 * 
	 * @return boolean
	 */
	public boolean getConnectionStatus() {
		try {
			if( !connection.isClosed() )
				return true;
		} catch (Exception e) {}
		
		return false;
	}
	
	/**
	 * Encerrar a conec�o com a inst�ncia.
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connection.close();
	}
}