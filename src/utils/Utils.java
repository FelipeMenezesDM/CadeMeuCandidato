package utils;

import java.io.File;

/**
 * Métodos auxiliares da aplicação.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class Utils {
	
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
	 * @return
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
}