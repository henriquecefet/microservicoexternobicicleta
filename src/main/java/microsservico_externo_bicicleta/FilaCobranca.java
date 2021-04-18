package microsservico_externo_bicicleta;

import java.io.IOException;
import java.util.Queue;

import cieloecommerce.sdk.ecommerce.request.CieloRequestException;

public class FilaCobranca {
	private static Queue<Cobranca> filaCobranca;
	
	public static void inserir(Cobranca cobranca) {
		filaCobranca.add(cobranca);
	}
	public static void cobrar() throws CieloRequestException, IOException, CartaoNaoEncontrado, CiclistaNaoEncontrado {
		Cobranca.realizarCobranca(filaCobranca.remove());
	}
}
