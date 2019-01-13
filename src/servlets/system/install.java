package servlets.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Utils;

/**
 * Servlet implementation class install
 */
@WebServlet("/install")
public class install extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public install() {
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
		request.getRequestDispatcher( "/includes/install.jsp" ).include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hostApp = request.getParameter( "host" ).toString().trim(),
			   portApp = request.getParameter( "port" ).toString().trim(),
			   dnName = request.getParameter( "dbName" ).toString().trim(),
			   user = request.getParameter( "user" ).toString().trim(),
			   password = request.getParameter( "password" ).toString().trim();
		
		int port = 1433;
		String host = "localhost";
			   
		Map<String, String> errors = new HashMap<String, String>();
		
		if( !hostApp.isEmpty() )
			host = hostApp;
		
		if( !portApp.isEmpty() ) {
			try {
				port = Integer.parseInt( portApp );
			} catch (Exception e) {
				errors.put( "port", "O valor informado é inválido." );
			}
		}
		
		if( dnName.isEmpty() )
			errors.put( "dbName", "Você deve preencher este campo obrigatório." );
		
		if( user.isEmpty() )
			errors.put( "user", "Você deve preencher este campo obrigatório." );
		
		if( password.isEmpty() )
			errors.put( "password", "Você deve preencher este campo obrigatório." );
		
		
		
		
		request.setAttribute( "errors", errors );
		doGet(request, response);
	}
}