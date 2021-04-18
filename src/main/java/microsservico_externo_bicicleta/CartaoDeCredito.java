package microsservico_externo_bicicleta;

import java.util.ArrayList;

public class CartaoDeCredito {
	private String nomeTitular;
	private String numero;
	private String validade;
	private String cvv;
	
	public CartaoDeCredito() {
		
	}
	public CartaoDeCredito(String nomeTitular, String numero, String validade, String cvv) {
		this.nomeTitular = nomeTitular;
		this.numero = numero;
		this.validade =  validade;
		this.cvv = cvv;
	}
	public String getNomeTitular() {
		return nomeTitular;
	}
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public static ArrayList<CartaoDeCredito> retornarCartoes() {
		ArrayList<CartaoDeCredito> cartoes = new ArrayList<CartaoDeCredito>();
		cartoes.add(new CartaoDeCredito("Henrique Rodrigues", "0000000000000001", "12/2024", "123"));
		cartoes.add(new CartaoDeCredito("Thiago Parracho", "0000000000000002", "12/2024", "456"));
		cartoes.add(new CartaoDeCredito("Barbara Beato", "0000000000000003", "12/2024", "789"));
		return cartoes;
	}
	public static CartaoDeCredito consultarCartoes(String titular) throws CartaoNaoEncontrado{
		ArrayList<CartaoDeCredito> cartoes = retornarCartoes();
		CartaoDeCredito cartao = null;
		for(int i = 0; i<cartoes.size(); i++) {
			if(cartoes.get(i).getNomeTitular().equals(titular)) {
				cartao = cartoes.get(i);
				break;
			}
		}
		if(cartao != null) {
			return cartao;
		}
		else {
			throw new CartaoNaoEncontrado("Cartao inexistente");
		}
		
		
	}

}
