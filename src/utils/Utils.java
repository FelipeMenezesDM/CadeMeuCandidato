package utils;

import java.io.File;
import java.io.FileReader;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.security.*;
import java.math.*;
import model.Conn;

/**
 * Métodos auxiliares da aplicação.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class Utils {
	private static String title = "noTitle";
	
	/**
	 * Verificar se um arquivo existe a partir do diretório do projeto no servidor.
	 * 
	 * @param filePath Nome do arquivo. Ex. pages/dashboard.jsp
	 * @return string
	 */
	public static boolean fileExists( String filePath ) {
		return fileExists( filePath, getWorkDir() );
	}
	
	/**
	 * Verificar se um arquivo existe informando seu caminho completo no servidor.
	 * 
	 * @param filePath Nome do arquivo. Ex. pages/dashboard.jsp
	 * @param workDir  Caminho do arquivo no servidor. Ex: D:/MeuApp/
	 * @return boolean
	 */
	public static boolean fileExists( String filePath, String workDir ) {
		filePath = filePath.trim().replace("/", System.getProperty("file.separator") );
		workDir = workDir.trim().replace("/", System.getProperty("file.separator") );
		
		if( filePath.isEmpty() )
			return false;
		
		File file = new File( workDir + filePath );
		
		return file.exists();
	}
	
	/**
	 * Obter caminho completo do projeto no servidor.
	 * @return
	 */
	public static String getWorkDir() {
		return Utils.class.getClassLoader().getResource("../../").getPath();
	}
	
	/**
	 * Obter configurações em formato JSON.
	 * 
	 * @param configKey
	 * @return JSONObject
	 */
	public static JSONObject getConfig( String configKey ) {
		return getConfig( configKey, "config.init" );
	}
	
	/**
	 * Obter configurações em formato JSON, informando o arquivo de configuração.
	 * 
	 * @param configKey
	 * @param configFile
	 * @return JSONObject
	 */
	public static JSONObject getConfig( String configKey, String configFile ) {
		String json = "{}";
		
		try {
			json = new JSONParser().parse( new FileReader( getWorkDir() + configFile + ".json" ) ).toString();
		} catch ( Exception e ) {}
		
		JSONObject config = new JSONObject( json ).getJSONObject( configKey );
		
		return config;
	}
	
	/**
	 * Verificar conexão com a base de dados.
	 * @return boolean
	 */
	public static boolean checkDBConnection() {
		// Verificar se o arquivo de conexão existe.
		if( !fileExists( "config.init.json" ) )
			return false;
		
		// Realizar conexão com a base com os parâmetros de configuração do arquivo.
		try {
			JSONObject config = getConfig( "DBConnection" );
			Conn conn = new Conn( config.getString( "dbName" ), config.getString( "user" ), config.getString( "password" ), config.getString( "host" ), config.getInt( "port" ) );
			
			if( !conn.getConnectionStatus() )
				return false;
			
			conn.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Obter URL principal do projeto.
	 * 
	 * @param request
	 * @return string
	 */
	public static String getHomeUrl( HttpServletRequest request ) {
		return getHomeUrl( request, "" );
	}
	
	/**
	 * Obter URL principal do projeto concatenada com contexto de arquivos.
	 * 
	 * @param request
	 * @return string
	 */
	public static String getHomeUrl( HttpServletRequest request, String fileContext ) {
		String url = request.getRequestURL().toString().replaceFirst( request.getRequestURI(), "" );
		String context = request.getContextPath();
		
		return url + context + "/" + fileContext;
	}
	
	/**
	 * Definir título de páginas.
	 * 
	 * @param titlePage
	 */
	public static void setTitle( String titlePage ) {
		title = titlePage;
	}
	
	/**
	 * Obter título atual da página.
	 * 
	 * @return string
	 */
	public static String getTitle() {
		JSONObject config = getConfig( "pageTitles", "config.titles" );
		
		if( config.has( title ) )
			return config.getString( title );
		
		return config.getString( "noTitle" );
	}
	
	/**
	 * Codificar string para MD5
	 * 
	 * @param string
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5( String string ) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance( "MD5" );
		md5.update( string.getBytes(), 0, string.length() );
		
		return new BigInteger( 1, md5.digest() ).toString( 16 );
	}
}