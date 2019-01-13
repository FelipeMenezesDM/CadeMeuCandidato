package controller.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Verificar nome de usuários válidos.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 *
 */
public class validateUsername {
	private boolean status = true;
	
	public validateUsername( Connection conn, String username ) {
		try {
			PreparedStatement stm = conn.prepareStatement( "SELECT * FROM users WHERE LOWER( username ) = LOWER( ? )" );
			stm.setString( 1, username );
			
			ResultSet result = stm.executeQuery();
			
			if( result.next() )
                status = false;
        } catch ( Exception e ) {
        	System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".validateUsername();" );
        }
    }
	
	public boolean getResult() {
		return status;
	}
}
