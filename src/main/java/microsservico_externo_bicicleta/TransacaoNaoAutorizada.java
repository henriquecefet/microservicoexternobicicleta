package microsservico_externo_bicicleta;

public class TransacaoNaoAutorizada extends Exception {
	public TransacaoNaoAutorizada(String mensagem) {
		super(mensagem);
	}

}
