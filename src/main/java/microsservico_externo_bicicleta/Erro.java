package microsservico_externo_bicicleta;
import java.security.SecureRandom;
public class Erro {
	private String id;
	private String codigo;
	private String mensagem;
	
	public Erro() {
			
	}
	public Erro(String codigo, String mensagem) {
		SecureRandom rand = new SecureRandom();
		this.id = rand.nextInt(1000)+"";
		this.codigo = codigo;
		this.mensagem = mensagem;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String toString() {
		return this.mensagem;
	}
}
