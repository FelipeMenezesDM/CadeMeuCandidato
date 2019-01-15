package controller.servlets.users;

import java.io.IOException;
import java.sql.Connection;
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
import controller.user.insertUser;
import controller.user.validateEmail;
import controller.user.validateUsername;
import model.Conn;
import utils.Utils;

/**
 * Servlet implementation class Signup
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
@WebServlet("/cadastro")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( "text/html; charset=UTF-8" );
		
		// Se existir sessão de usuário, redirecionar para a página inicial.
		if( Utils.checkUserSession(request) ) {
			response.sendRedirect( request.getContextPath() );
			return;
		}
		
		Utils.setTitle( "signup" );
		request.getRequestDispatcher( "/pages/signup.jsp" ).include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession( true );
		String name = request.getParameter( "name" ).toString().trim(),
			   username = request.getParameter( "username" ).toString().trim(),
			   email = request.getParameter( "email" ).toString().trim(),
			   password = request.getParameter( "password" ).toString().trim(),
			   rtPassword = request.getParameter( "rtPassword" ).toString().trim();
		
		// Lista de erros.
		Map<String, String> errors = new HashMap<String, String>();
		
		/*
		 *  Todos os campos são obrigatórios.
		 */
		if( name.isEmpty() )
			errors.put( "name", "Você deve preencher este campo obrigatório." );
		
		if( username.isEmpty() )
			errors.put( "username", "Você deve preencher este campo obrigatório." );
		
		if( email.isEmpty() )
			errors.put( "email", "Você deve preencher este campo obrigatório." );
		else if( !email.contains( "@" ) )
			errors.put( "email", "Formato de e-mail inválido." );
		
		if( password.isEmpty() )
			errors.put( "password", "Você deve preencher este campo obrigatório." );
		
		if( rtPassword.isEmpty() )
			errors.put( "rtPassword", "Você deve preencher este campo obrigatório." );
		else if( !rtPassword.equals( password ) )
			errors.put( "rtPassword", "Os campos de senha devem coincidir." );
		
		// Verificar informações na base de dados.
		if( errors.isEmpty() ) {
			try {
				JSONObject config = Utils.getConfig( "DBConnection" );
				Connection conn = new Conn( config.getString( "dbName" ), config.getString( "user" ), config.getString( "password" ), config.getString( "host" ), config.getInt( "port" ) ).getConnection();
				validateEmail vEmail = new validateEmail( conn, email );
				validateUsername vUsername = new validateUsername( conn, username );
				
				// Não deve haver duplicidade de e-mails na base.
				if( !vEmail.getResult() )
					errors.put( "email", "Este e-mail já está cadastrado no sistema." );
				
				// Não deve haver duplicidade de nomes de usuário na base.
				if( !vUsername.getResult() )
					errors.put( "username", "Este nome de usuário já está cadastrado no sistema." );
				
				if( errors.isEmpty() ) {
					password = Utils.md5(password); // MD5 encoder
					insertUser insert = new insertUser( conn, name, email, username, password );
					
					// Se o usuário e email ainda não existirem na base.
					if( insert.getStatus() ) {
						Login user = new Login();
						
						user.setUsername(username);
						user.setPassword(password);
						
						// Iniciar sessão do usuário.
						session.setAttribute( "user", user );
						
						// Persistir sessão do usuário
						if( request.getAttribute( "remember" ) != null ) {
							Cookie usernameCookie = new Cookie( "username", username );
							Cookie passwordCookie = new Cookie( "password", password );
							
							usernameCookie.setMaxAge( 60*60*24 );
							passwordCookie.setMaxAge( 60*60*24 );
							response.addCookie( usernameCookie );
							response.addCookie( passwordCookie );
						}
					}else {
						errors.put( "form", "Não foi possível cadastrar o usuário. Por favor, verifique os dados informados e tente novamente." );
					}
				}
				
				conn.close();
			} catch ( Exception e ) {
				System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".doPost();" );
				errors.put( "form", "Não foi possível cadastrar o usuário. Por favor, verifique os dados informados e tente novamente." );
			}
		}
		
		request.setAttribute( "errors", errors );
		doGet(request, response);
	}
}