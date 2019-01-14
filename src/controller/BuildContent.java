package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Gerar conteúdo para a aplicação.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class BuildContent {
	private static JSONObject partidos;
	private static JSONObject ufs;
	private static HttpServletRequest request;
	
	/**
	 * Definir objeto de requisição.
	 * 
	 * @param requestObj
	 */
	public static void setRequest( HttpServletRequest requestObj ) {
		request = requestObj;
	}
	
	/**
	 * Obter lista de partidos.
	 * 
	 * @return
	 */
	public static JSONObject getPartidos() {
		HttpSession session = request.getSession( true ); 
		
		if( session.getAttribute( "partidos" ) != null )
			return (JSONObject) session.getAttribute( "partidos" );
		
		// Obter partidos a partir de serviço público.
		partidos = getJSONData( "https://dadosabertos.camara.leg.br/api/v2/partidos?itens=300&ordem=ASC&ordenarPor=sigla" );
		session.setAttribute( "partidos", partidos );
		
		return partidos;
	}
	
	/**
	 * Obter lista de Unidades Federativas.
	 * 
	 * @return
	 */
	public static JSONObject getUFs() {
		HttpSession session = request.getSession( true ); 
		
		if( session.getAttribute( "ufs" ) != null )
			return (JSONObject) session.getAttribute( "ufs" );
		
		// Obter partidos a partir de serviço público.
		ufs = getJSONData( "https://dadosabertos.camara.leg.br/api/v2/referencias/uf" );
		session.setAttribute( "ufs", ufs );
		
		return ufs;
	}
	
	/**
	 * Obter lista de parlamentares.
	 * 
	 * @return
	 */
	public static JSONObject getParlamentares() {
		String nome = "", partido = "", uf = "";
		
		if( request.getParameter( "nome" ) != null )
			nome = request.getParameter( "nome" );
		
		if( request.getParameter( "partido" ) != null )
			partido = request.getParameter( "partido" );
		
		if( request.getParameter( "uf" ) != null )
			uf = request.getParameter( "uf" );
		
		JSONObject parlamentares = getJSONData( "https://dadosabertos.camara.leg.br/api/v2/deputados?nome=" + nome + "&siglaUf=" + uf + "&siglaPartido=" + partido + "&pagina=1&itens=5&ordem=ASC&ordenarPor=nome" );
		
		return parlamentares;
	}
	
	/**
	 * Obter objeto JSON a partir de uma URL.
	 * 
	 * @param url
	 * @return
	 */
	private static JSONObject getJSONData( String url ) {
		String json = "{}";
		
		try {
			json = new JSONParser().parse( new InputStreamReader( new URL( url ).openStream(), "UTF-8" ) ).toString();
		}catch( Exception e ) {
			System.out.println( "Alert -> " + e.getMessage() + "; " + BuildContent.class.getCanonicalName() + ".getJSONData();" );
		}
		
		JSONObject data = new JSONObject( json );
		return data;
	}
}