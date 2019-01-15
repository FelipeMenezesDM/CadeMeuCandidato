package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
	public static Parlamentar getParlamentar() {
		HttpSession session = request.getSession();
		Map<Integer, Parlamentar> parlamentares = new HashMap<Integer, Parlamentar>();
		
		int id = 0;
		
		// Obter id do parlamentar.
		try {
			id = Integer.parseInt( request.getParameter( "id" ).toString() );
		} catch (Exception e) {
			System.out.println( "Error -> " + e.getMessage() + "; " + Utils.class.getCanonicalName() + ".getParlamentar();" );
		}
		
		// Verificar se o parlamentar está em cache.
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
			
			// Verificar se parlamentar existe na base de dados.
			if( result.next() ) {
				parlamentar = new Parlamentar();
				parlamentar.setId( result.getInt( "id" ) );
				parlamentar.setNomeParlamentarAtual( result.getString( "nomeParlamentarAtual" ) );
				parlamentar.setDataNascimento( result.getString( "dataNascimento" ) );
				parlamentar.setSexo( result.getString( "sexo" ) );
				parlamentar.setPartidoAtual( result.getString( "partidoAtual" ) );
				parlamentar.setSituacaoNaLegislaturaAtual( result.getString( "situacaoNaLegislaturaAtual" ) );
				parlamentar.setFoto( "" );
				
				// Armazenar informações do parlamentar em cache.
				parlamentares.put( id, parlamentar );
				session.setAttribute( "parlamentares" , parlamentares );
			}
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println( "Error -> " + e.getMessage() + "; " + Utils.class.getCanonicalName() + ".getParlamentar();" );
		}
		
		// Obter informações a partir de arquivo XML.
		parlamentar = getParlamentarFromXML( id );
		
		// Armazenar informações do parlamentar em cache.
		if( parlamentar != null ) {
			parlamentares.put( id, parlamentar );
			session.setAttribute( "parlamentares" , parlamentares );
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
	
	/**
	 * Obter infomações do parlamentar a partir de arquivo XML.
	 * 
	 * @param id
	 * @return
	 */
	private static Parlamentar getParlamentarFromXML( int id ) {
		try {
			String url = "http://www.camara.leg.br/SitCamaraWS/Deputados.asmx/ObterDetalhesDeputado?ideCadastro=" + id + "&numLegislatura=";
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.parse( new URL( url ).openStream() );
			document.getDocumentElement().normalize();
			
			NodeList deputado = document.getElementsByTagName( "Deputado" );
			Element element = (Element) deputado.item(0);
			Element partido = (Element) element.getElementsByTagName( "partidoAtual" ).item(0);
			
			String nomeParlamentarAtual = element.getElementsByTagName( "nomeParlamentarAtual" ).item(0).getTextContent(),
				   dataNascimento = element.getElementsByTagName( "dataNascimento" ).item(0).getTextContent(),
				   partidoNome = partido.getElementsByTagName( "nome" ).item(0).getTextContent(),
				   partidoSigla = partido.getElementsByTagName( "sigla" ).item(0).getTextContent(),
				   sexo =  element.getElementsByTagName( "sexo" ).item(0).getTextContent(),
				   situacaoNaLegislaturaAtual = element.getElementsByTagName( "situacaoNaLegislaturaAtual" ).item(0).getTextContent();
			
			parlamentar = new Parlamentar();
			parlamentar.setId( id );
			parlamentar.setNomeParlamentarAtual( nomeParlamentarAtual );
			parlamentar.setDataNascimento( dataNascimento );
			parlamentar.setPartidoAtual( partidoNome + " - " + partidoSigla );
			parlamentar.setSexo( sexo );
			parlamentar.setSituacaoNaLegislaturaAtual( situacaoNaLegislaturaAtual );
			parlamentar.setFoto( "" );
			
			/**
			 * Cadastrar parlamentar na base de dados.
			 */
			try {
				// ( id, nomeParlamentarAtual, dataNascimento, sexo, partidoAtual, situacaoNaLegislaturaAtual, foto )
				JSONObject config = Utils.getConfig( "DBConnection" );
				Connection conn = new Conn( config.getString( "dbName" ), config.getString( "user" ), config.getString( "password" ), config.getString( "host" ), config.getInt( "port" ) ).getConnection();
				PreparedStatement stm = conn.prepareStatement( "INSERT INTO parlamentares VALUES ( ?, ?, ?, ?, ?, ?, ? )" );
				
				stm.setInt( 1, id );
				stm.setString( 2, nomeParlamentarAtual );
				stm.setString( 3, dataNascimento );
				stm.setString( 4, sexo );
				stm.setString( 5, ( partidoSigla + " - " + partidoNome ) );
				stm.setString( 6, situacaoNaLegislaturaAtual );
				stm.setString( 7, "" );
				
				stm.executeUpdate();
			} catch (Exception e) {
				System.out.println( "Error -> " + e.getMessage() + "; " + Utils.class.getCanonicalName() + ".getParlamentarFromXML();" );
			}
			
			return parlamentar;
		}catch( Exception e ) {
			System.out.println( "Error -> " + e.getMessage() + "; " + Utils.class.getCanonicalName() + ".getParlamentarFromXML();" );
			return null;
		}
	}
}