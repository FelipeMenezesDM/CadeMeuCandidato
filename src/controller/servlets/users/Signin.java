package controller.servlets.users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import controller.javabeans.Login;
import controller.user.validateUserLogin;
import model.Conn;
import utils.Utils;

/**
 * Servlet implementation class Signin
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
@WebServlet("/entrar")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( "text/html; charset=UTF-8" );
		
		// Se existir sess�o de usu�rio, redirecionar para a p�gina inicial.
		if( Utils.checkUserSession(request) ) {
			response.sendRedirect( request.getContextPath() );
			return;
		}
		
		Utils.setTitle( "signin" );
		request.getRequestDispatcher( "/pages/signin.jsp" ).include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter( "username" ).toString().trim(),
			   password = request.getParameter( "password" ).toString().trim();
		
		// Lista de erros.
		Map<String, String> errors = new HashMap<String, String>();
        HttpSession session = request.getSession( true );
        
        /*
		 *  Todos os campos s�o obrigat�rios.
		 */
        if( username.isEmpty() )
			errors.put( "username", "Voc� deve preencher este campo obrigat�rio." );
		
		if( password.isEmpty() )
			errors.put( "password", "Voc� deve preencher este campo obrigat�rio." );
		
		// Verificar informa��es na base de dados.
		if( errors.isEmpty() ) {
			try {
				JSONObject config = Utils.getConfig( "DBConnection" );
				Connection conn = new Conn( config.getString( "dbName" ), config.getString( "user" ), config.getString( "password" ), config.getString( "host" ), config.getInt( "port" ) ).getConnection();
				validateUserLogin userLogin = new validateUserLogin( conn, username, Utils.md5( password ) );
				
				ResultSet login = userLogin.getResults();
				
				if( login.next()  ) {
					Login user = new Login();
					
					username = login.getString( "username" );
					password = login.getString( "password" );
					
					user.setUsername(username);
					user.setPassword(password);
					
					// Iniciar sess�o do usu�rio.
					session.setAttribute( "user", user );
					
					// Persistir sess�o do usu�rio
					if( request.getParameter( "remember" ) != null ) {
						Cookie usernameCookie = new Cookie( "username", username );
						Cookie passwordCookie = new Cookie( "password", password );
						
						usernameCookie.setMaxAge( 60*60*24 );
						passwordCookie.setMaxAge( 60*60*24 );
						response.addCookie( usernameCookie );
						response.addCookie( passwordCookie );
					}
				} else {
					errors.put( "form", "N�o foi poss�vel realizar login, verifique os dados informados e tente novamente." );
				}
				
				conn.close();
			} catch (Exception e) {
				System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".doPost();" );
				errors.put( "form", "N�o foi poss�vel realizar login, verifique os dados informados e tente novamente." );
			}
		}
		
		request.setAttribute( "errors", errors );
		doGet(request, response);
	}

}