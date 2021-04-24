package microsservico_externo_bicicleta;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartaoDeCreditoTeste {
	private CartaoDeCredito cartao;
	@BeforeEach                                         
    public void setUp() throws Exception {
        cartao  = new CartaoDeCredito();
    }
	@Test
	public void testeGetBandeira() {
		cartao.setNumero("4111111145551141");
		assertEquals("Visa", cartao.getBandeira());
	}
	@Test 
	public void testeConsultarCartoes() throws CartaoNaoEncontrado {
		assertEquals("4111111145551141", CartaoDeCredito.consultarCartoes("Henrique Soares Rodrigues").getNumero());
	}
}
