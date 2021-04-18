package microsservico_externo_bicicleta;

import java.util.ArrayList;

public class Ciclista {
	private String nome;
	private String id;
	private CartaoDeCredito cartao;
	public Ciclista() {
		
	}
	public Ciclista(String id, String nome, CartaoDeCredito cartao) {
		this.id = id;
		this.nome = nome;
		this.cartao = cartao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CartaoDeCredito getCartao() {
		return cartao;
	}
	public void setCartao(CartaoDeCredito cartao) {
		this.cartao = cartao;
	}
	public static ArrayList<Ciclista> retornarCiclista() throws CartaoNaoEncontrado {
		ArrayList<Ciclista> ciclistas = new ArrayList<Ciclista>();
		ciclistas.add(new Ciclista("Henrique Rodrigues", "1", CartaoDeCredito.consultarCartoes("Henrique Rodrigues")));
		ciclistas.add(new Ciclista("Thiago Parracho", "2", CartaoDeCredito.consultarCartoes("Thiago Parracho")));
		ciclistas.add(new Ciclista("Barbara Beato", "3", CartaoDeCredito.consultarCartoes("Barbara Beato")));
		return ciclistas;
	}
	public static Ciclista consultarCiclista(String id) throws CartaoNaoEncontrado, CiclistaNaoEncontrado{
		ArrayList<Ciclista> ciclistas = retornarCiclista();
		Ciclista ciclista = null;
		for(int i = 0; i< ciclistas.size(); i++) {
			if(ciclistas.get(i).getId().equals(id)) {
				ciclista = ciclistas.get(i);
				break;
			}
		}
		if(ciclista != null) {
			return ciclista;
		}
		else {
			throw new CiclistaNaoEncontrado("Ciclista nao encontrado");
		}
	}
}
