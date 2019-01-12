package control;

import java.sql.*;

/**
 * Classe de conexão com o Banco de Dados.
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
	 * Conexão com host e porta padrão.
	 * 
	 * @param instance Nome da instância
	 * @param user     Nome de usuário da instância.
	 * @param password Senha para a instância.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Conection( String instance, String user, String password ) throws ClassNotFoundException, SQLException {
		this( instance, user, password, "localhost", 1433 );
	}

	/**
	 * Conexão com host e porta informados.
	 * 
	 * @param instance Nome da instância
	 * @param user     Nome de usuário da instância.
	 * @param password Senha para a instância.
	 * @param host     Nome do host.
	 * @param port     Porta da instância.
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
	 * Criar conexão com a instância.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void makeConnection() throws SQLException, ClassNotFoundException {
		Class.forName( DRIVER );
		connection = DriverManager.getConnection( URL + instance, user, password );
	}
	
	/**
	 * Obter objeto da conexão com a instância.
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Encerrar a conecão com a instância.
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connection.close();
	}
}