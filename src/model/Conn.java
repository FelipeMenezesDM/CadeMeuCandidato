package model;

import java.sql.*;

/**
 * Classe de conexão com o Banco de Dados.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class Conn {
	private final String DRIVER;
	private final String URL;
	private Connection connection = null;
	
	/**
	 * Conexão com host e porta padrão.
	 * 
	 * @param dbName   Nome da base de dados.
	 * @param user     Nome de usuário da instância.
	 * @param password Senha para a instância.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Conn( String dbName, String user, String password ) throws ClassNotFoundException, SQLException {
		this( dbName, user, password, "localhost", 1433 );
	}

	/**
	 * Conexão com host e porta informados.
	 * 
	 * @param instance Nome da base de dados.
	 * @param user     Nome de usuário da instância.
	 * @param password Senha para a instância.
	 * @param host     Nome do host.
	 * @param port     Porta da instância.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Conn( String dbName, String user, String password, String host, int port ) throws ClassNotFoundException, SQLException {
		DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		URL = "jdbc:sqlserver://" + host + ":" + port + "; databaseName=" + dbName + "; user=" + user + "; password=" + password + ";";
		
		makeConnection();
	}
	
	/**
	 * Criar conexão com a instância.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void makeConnection() throws SQLException, ClassNotFoundException {
		Class.forName( DRIVER );
		connection = DriverManager.getConnection( URL );
	}
	
	/**
	 * Obter objeto da conexão com a instância.
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Estado da conexão com a base de dados.
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
	 * Encerrar a conecão com a instância.
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connection.close();
	}
}