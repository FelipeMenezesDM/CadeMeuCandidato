package controller.javabeans;

/**
 * Informações do usuário do sistema.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 */
public class User {
	private String name;
	private String username;
	private String email;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}