package controller.javabeans;

/**
 * Propriedades do parlamentar.
 * 
 * @author Felipe Menezes <contato@felipemenezes.com.br>
 *
 */
public class Parlamentar {
	private int id;
	private String nomeParlamentarAtual;
	private String dataNascimento;
	private String sexo;
	private String situacaoNaLegislaturaAtual;
	private String partidoAtual;
	private String foto;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNomeParlamentarAtual() {
		return nomeParlamentarAtual;
	}
	
	public void setNomeParlamentarAtual(String nomeParlamentarAtual) {
		this.nomeParlamentarAtual = nomeParlamentarAtual;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getSituacaoNaLegislaturaAtual() {
		return situacaoNaLegislaturaAtual;
	}
	
	public void setSituacaoNaLegislaturaAtual(String situacaoNaLegislaturaAtual) {
		this.situacaoNaLegislaturaAtual = situacaoNaLegislaturaAtual;
	}
	
	public String getPartidoAtual() {
		return partidoAtual;
	}
	
	public void setPartidoAtual(String partidoAtual) {
		this.partidoAtual = partidoAtual;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
}
