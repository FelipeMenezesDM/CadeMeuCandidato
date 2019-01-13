package controller.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Verificar emails válidos.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 *
 */
public class validateEmail {
	private boolean status = true;
	
	public validateEmail( Connection conn, String email ) {
		try {
			PreparedStatement stm = conn.prepareStatement( "SELECT * FROM users WHERE LOWER( email ) = LOWER( ? )" );
			stm.setString( 1, email );
			
			ResultSet result = stm.executeQuery();
			
			if( result.next() )
                status = false;
        } catch ( Exception e ) {
        	System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".validateEmail();" );
        }
    }
	
	public boolean getResult() {
		return status;
	}
}
