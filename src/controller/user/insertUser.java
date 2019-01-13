package controller.user;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Inserção de usuário na base de dados.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 *
 */
public class insertUser {
	private final Connection conn;
	private final String name;
	private final String email;
	private final String username;
	private final String password;
	private boolean status = false;

	public insertUser( Connection conn, String name, String email, String username, String password ) {
		this.conn = conn;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		
		insertNewUser();
    }
	
	/**
	 * Inserir novo usuário
	 */
	private void insertNewUser() {
		try {
			PreparedStatement stm = conn.prepareStatement( "INSERT INTO users (name, email, username, password) VALUES (?, ?, ?, ?)" );
			
			stm.setString( 1, name );
			stm.setString( 2, email);
			stm.setString( 3, username );
			stm.setString( 4, password );
			
			stm.executeUpdate();
			
			status = true;
		} catch (Exception e) {
			System.out.println( "Error -> " + e.getMessage() + "; " + this.getClass().getCanonicalName() + ".insertNewUser();" );
		}
	}
	
	public boolean getStatus() {
		return status;
	}
}