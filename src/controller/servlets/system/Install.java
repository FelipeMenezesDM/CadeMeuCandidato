package controller.servlets.system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import model.Conn;
import utils.Utils;

/**
 * Servlet implementation class install
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
@WebServlet("/instalacao")
public class Install extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Install() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( Utils.fileExists( "config.init.json" ) )
			response.sendRedirect( request.getContextPath() );
		
		response.setContentType( "text/html; charset=UTF-8" );
		
		String page;
		HttpSession session = request.getSession( true );
		
		// Verificar etapa da instala��o.
		if( session.getAttribute( "step" ) != null ) {
			page = "createUser";
		} else {
			page = "install";
		}
		
		Utils.setTitle( page );
		request.getRequestDispatcher( "/pages/" + page + ".jsp" ).include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession( true );
		
		// Voltar para o formul�rio anterior.
		if( request.getParameter( "back" ) != null ) {
			session.setAttribute( "step", null );
			doGet( request, response );
			return;
		}
		
		// Verificar etapa da instala��o e aplicar m�todo de valida��o.
		if( session.getAttribute( "step" ) != null )
			validateUser( request, response );
		else
			validateConnection( request, response );
	}
	
	/**
	 * Validar informa��es de conex�o com a base de dados.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void validateConnection( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String hostApp = request.getParameter( "host" ).toString().trim(),
			   portApp = request.getParameter( "port" ).toString().trim(),
			   dbName = request.getParameter( "dbName" ).toString().trim(),
			   user = request.getParameter( "user" ).toString().trim(),
			   password = request.getParameter( "password" ).toString().trim();
		
		int port = 1433;
		String host = "localhost";
		
		// Lista de erros.
		Map<String, String> errors = new HashMap<String, String>();
		
		if( !hostApp.isEmpty() )
			host = hostApp;
		
		// Validar porta informada, que deve ser um n�mero inteiro.
		if( !portApp.isEmpty() ) {
			try {
				port = Integer.parseInt( portApp );
			} catch (Exception e) {
				errors.put( "port", "O valor informado � inv�lido." );
			}
		}
		
		/*
		 *  Os demais campos s�o obrigat�rios.
		 */
		if( dbName.isEmpty() )
			errors.put( "dbName", "Voc� deve preencher este campo obrigat�rio." );
		
		if( user.isEmpty() )
			errors.put( "user", "Voc� deve preencher este campo obrigat�rio." );
		
		if( password.isEmpty() )
			errors.put( "password", "Voc� deve preencher este campo obrigat�rio." );
		
		// Testar conex�o com a base de dados.
		if( errors.isEmpty() ) {
			try {
				Conn conn = new Conn( dbName, user, password, host, port );
				
				if( !conn.getConnectionStatus() ) {
					errors.put("form", "N�o foi poss�vel estabelecer conex�o com a Base de Dados usando as informa��es abaixo. Por favor, verifique se estes dados est�o corretos e tente novamente.");
				}else {
					// Criar tabelas necess�rias, se n�o existirem.
					if( createTables( conn.getConnection() ) ) {
						// Salvar configura��es de conex�o na sess�o.
						Map<String, String> config = new HashMap<String, String>();
						HttpSession session = request.getSession();
						
						config.put( "host", host );
						config.put( "port", port + "" );
						config.put( "dbName", dbName );
						config.put( "user", user );
						config.put( "password", password );
						
						session.setAttribute( "config", config );
						session.setAttribute( "step", 2 );
					}else{
						errors.put( "form", "As tabelas necess�rias para o funcionamento da aplica��o n�o puderam ser criadas. Por favor, verifique os dados informados e tente novamente." );
					}
				}
				
				conn.close();
			} catch (Exception e) {
				System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".validateConnection();" );
				errors.put("form", "N�o foi poss�vel estabelecer conex�o com a Base de Dados usando as informa��es abaixo. Por favor, verifique se estes dados est�o corretos e tente novamente.");
			}
		}
		
		request.setAttribute( "errors", errors );
		doGet(request, response);
	}
	
	/**
	 * Validar cadastro de usu�rio.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void validateUser( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String name = request.getParameter( "name" ).toString().trim(),
			   username = request.getParameter( "username" ).toString().trim(),
			   email = request.getParameter( "email" ).toString().trim(),
			   password = request.getParameter( "password" ).toString().trim(),
			   rtPassword = request.getParameter( "rtPassword" ).toString().trim();
		
		// Lista de erros.
		Map<String, String> errors = new HashMap<String, String>();
		
		/*
		 *  Todos os campos s�o obrigat�rios.
		 */
		if( name.isEmpty() )
			errors.put( "name", "Voc� deve preencher este campo obrigat�rio." );
		
		if( username.isEmpty() )
			errors.put( "username", "Voc� deve preencher este campo obrigat�rio." );
		
		if( email.isEmpty() )
			errors.put( "email", "Voc� deve preencher este campo obrigat�rio." );
		
		if( password.isEmpty() )
			errors.put( "password", "Voc� deve preencher este campo obrigat�rio." );
		
		if( rtPassword.isEmpty() )
			errors.put( "rtPassword", "Voc� deve preencher este campo obrigat�rio." );
		else if( !rtPassword.equals( password ) )
			errors.put( "rtPassword", "Os campos de senha devem coincidir." );
		
		// Verificar se existe usu�rio com mesmo email ou nome de usu�rio cadastrado no sistema.
		if( errors.isEmpty() ) {
			try {
				HttpSession session = request.getSession( true );
				Map<String, String> config = (Map) session.getAttribute( "config" );
				Connection conn = new Conn( config.get( "dbName" ).toString(), config.get( "user" ).toString(), config.get( "password" ).toString(), config.get( "host" ).toString(), Integer.parseInt(config.get( "port" ).toString())).getConnection();
				PreparedStatement stm = conn.prepareStatement( "SELECT id FROM USERS WHERE LOWER( email ) = LOWER( ? ) OR LOWER( username ) = LOWER( ? )" );
				
				stm.setString( 1, email );
				stm.setString( 2, username );
				
				ResultSet result = stm.executeQuery();
				
				if( result.next() ) {
					errors.put( "form", "J� existe um usu�rio cadastrado com este e-mail ou com este nome de usu�rio." );
				} else {
					// Criar arquivo de conex�o com a base de dados.
					createConnectionFile( config );
					
					// Enviar usu�rio para cadastro.
					request.getRequestDispatcher("/cadastro").forward(request, response);
					return;
				}
				
				conn.close();
			} catch (Exception e) {
				System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".validateUser();" );
				errors.put( "form", "N�o foi poss�vel concluir o cadastro por um motivo desconhecido. Por favor, tente novamente." );
			}
		}
		
		request.setAttribute( "errors", errors );
		doGet(request, response);
	}
	
	/**
	 * Criar tabelas necess�rias para o sistema, caso n�o existam.
	 * 
	 * @param conn
	 * @return boolean
	 */
	private boolean createTables( Connection conn ) {
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'users' AND TABLE_NAME = N'parlamentares'";
			
			// Verificar se tabela de usu�rios j� existe.
			if( stm.executeQuery( sql ).next() )
				return true;
			
			// Criar tabelas, caso n�o existam.
			sql = "IF NOT EXISTS ( SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'users' ) " +
				  "BEGIN " +
					  "CREATE TABLE users ( " +
						  "id INT IDENTITY(1, 1) PRIMARY KEY, " +
						  "name VARCHAR(120) NOT NULL, " +
						  "email VARCHAR(70) NOT NULL CONSTRAINT uq_email UNIQUE, " +
						  "username VARCHAR(30) NOT NULL CONSTRAINT uq_username UNIQUE, " +
						  "password VARCHAR(32) NOT NULL " +
					  ") " +
				  "END ";
			
			sql += "IF NOT EXISTS ( SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'parlamentares' ) " +
				   "BEGIN " +
						"CREATE TABLE parlamentares ( " +
							"id INT PRIMARY KEY, " +
							"nomeParlamentarAtual VARCHAR(120) NOT NULL, " +
							"dataNascimento VARCHAR(10) NOT NULL, " +
							"sexo CHAR NOT NULL, " +
							"partidoAtual VARCHAR(70) NOT NULL, " +
							"situacaoNaLegislaturaAtual VARCHAR(20) NOT NULL, " +
							"foto TEXT NOT NULL " +
						") " +
					"END";
			
			stm.executeUpdate( sql );
			return true;
		} catch( Exception e ) {
			System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".createTables();" );
			return false;
		}
	}
	
	/**
	 * Criar arquivo de conex�o com a base de dados.
	 * 
	 * @param config
	 */
	public void createConnectionFile( Map<String, String> config ) {
		JSONObject json = new JSONObject();
		json.put( "DBConnection", config );

		try {
			FileOutputStream configInit = new FileOutputStream(Utils.getWorkDir() + "config.init.json");
			
			configInit.write( json.toString( 5 ).getBytes() );
			configInit.flush();
			configInit.close();
		} catch (Exception e) {
			System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".createConnectionFile();" );
		}
	}
}