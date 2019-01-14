package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import controller.javabeans.Parlamentar;
import model.Conn;
import utils.Utils;

/**
 * Gerar conteúdo para a aplicação.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class BuildContent {
	private static JSONObject partidos;
	private static JSONObject ufs;
	private static HttpServletRequest request;
	private static Parlamentar parlamentar;
	
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
	 * @throws UnsupportedEncodingException 
	 */
	public static JSONObject getParlamentares() throws UnsupportedEncodingException {
		String nome = "", partido = "", uf = "";
		
		if( request.getParameter( "nome" ) != null )
			nome = URLEncoder.encode( request.getParameter( "nome" ), "UTF-8" );
		
		if( request.getParameter( "partido" ) != null )
			partido = URLEncoder.encode( request.getParameter( "partido" ), "UTF-8" );
		
		if( request.getParameter( "uf" ) != null )
			uf = URLEncoder.encode( request.getParameter( "uf" ), "UTF-8" );
		
		JSONObject parlamentares = getJSONData( "https://dadosabertos.camara.leg.br/api/v2/deputados?nome=" + nome + "&siglaUf=" + uf + "&siglaPartido=" + partido + "&pagina=" + Utils.getCurrentPage(request) + "&itens=5&ordem=ASC&ordenarPor=nome" );
		
		return parlamentares;
	}
	
	/**
	 * Obter informações do parlamentar.
	 * 
	 * @param id
	 * @return
	 */
	public static Parlamentar getParlamentar( int id ) {
		HttpSession session = request.getSession();
		Map<Integer, Parlamentar> parlamentares = null;
		
		if( session.getAttribute( "parlamentares" ) != null ) {
			parlamentares = (Map) session.getAttribute( "parlamentares" );
			
			if( parlamentares.containsKey( id ) )
				return (Parlamentar) parlamentares.get( id );
		}
		
		/**
		 * Obter parlamentar a partir da base de dados.
		 */
		try {
			JSONObject config = Utils.getConfig( "DBConnection" );
			Connection conn = new Conn( config.getString( "dbName" ), config.getString( "user" ), config.getString( "password" ), config.getString( "host" ), config.getInt( "port" ) ).getConnection();
			PreparedStatement stm = conn.prepareStatement( "SELECT * FROM parlamentares WHERE id = ? " );
			
			stm.setInt( 1, id );
			
			ResultSet result = stm.executeQuery();
			
			if( result.next() ) {
				parlamentar = new Parlamentar();
				parlamentar.setId( result.getInt( "id" ) );
				parlamentar.setNomeParlamentarAtual( result.getString( "nomeParlamentarAtual" ) );
				parlamentar.setDataNascimento( result.getString( "dataNascimento" ) );
				parlamentar.setSexo( result.getString( "sexo" ) );
				parlamentar.setSituacaoNaLegislaturaAtual( result.getString( "situacaoNaLegislaturaAtual" ) );
				parlamentar.setFoto( "" );
				
				parlamentares.put( id, parlamentar );
				session.setAttribute( "parlamentares" , parlamentares );
				
				return parlamentar;
			} else {
			}
			
			conn.close();
			
		} catch (Exception e) {}
		
		try {
			File fXmlFile = new File( "http://www.camara.leg.br/SitCamaraWS/Deputados.asmx/ObterDetalhesDeputado?ideCadastro=178957&numLegislatura=" );
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		}catch( Exception e ) {
			
		}
		
		return parlamentar;
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