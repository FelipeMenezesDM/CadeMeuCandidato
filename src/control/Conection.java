package control;

import java.sql.*;

/**
 * Classe de conex�o com o Banco de Dados.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class Conection {
	private final String DRIVER;
	private final String URL;
	private String host;
	private String instance;
	private String user;
	private String password;
	private int port;
	private Connection connection = null;
	
	/**
	 * Conex�o com host e porta padr�o.
	 * 
	 * @param instance Nome da inst�ncia
	 * @param user     Nome de usu�rio da inst�ncia.
	 * @param password Senha para a inst�ncia.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Conection( String instance, String user, String password ) throws ClassNotFoundException, SQLException {
		this( instance, user, password, "localhost", 1433 );
	}

	/**
	 * Conex�o com host e porta informados.
	 * 
	 * @param instance Nome da inst�ncia
	 * @param user     Nome de usu�rio da inst�ncia.
	 * @param password Senha para a inst�ncia.
	 * @param host     Nome do host.
	 * @param port     Porta da inst�ncia.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Conection( String instance, String user, String password, String host, int port ) throws ClassNotFoundException, SQLException {
		this.instance = instance;
		this.user = user;
		this.password = password;
		this.host = host;
		this.port = port;
		
		DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		URL = "jdbc:sqlserver://" + host + ":" + port + ";";
		
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
		connection = DriverManager.getConnection( URL + instance, user, password );
	}
	
	/**
	 * Obter objeto da conex�o com a inst�ncia.
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Encerrar a conec�o com a inst�ncia.
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connection.close();
	}
}