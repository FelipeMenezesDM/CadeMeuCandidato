package controller.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Validar login do usuário.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 *
 */
public class validateUserLogin {
	private ResultSet results = null;
	
	public validateUserLogin( Connection conn, String username, String password ) {
		try {
			PreparedStatement stm = conn.prepareStatement( "SELECT * FROM users WHERE ( LOWER( email ) = LOWER( ? ) OR LOWER( username ) = LOWER( ? ) ) AND password = ?" );
			
			stm.setString( 1, username );
			stm.setString( 2, username );
			stm.setString( 3, password );
			
			results = stm.executeQuery();
		} catch( Exception e ) {
			System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".validateUserLogin();" );
		}
	}
	
	public ResultSet getResults() {
		return results;
	}
}
