package microsservico_externo_bicicleta;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

import org.eclipse.jetty.util.ajax.JSON;
import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.CreditCard;
import cieloecommerce.sdk.ecommerce.Customer;
import cieloecommerce.sdk.ecommerce.CieloEcommerce;
import cieloecommerce.sdk.ecommerce.Environment;

import cieloecommerce.sdk.ecommerce.request.CieloError;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;
public class Cobranca {
	private static final String MerchantId = "abcbe5bc-890a-4633-873a-df2cd1e4bec1";
	private static final String MerchantKey = "XYOSMOPFAKGDSLRUHMUGICXLZFIMHRXNMPFEBMGS";
	private int valor;
	private String ciclista;
	private String id;
	private Status status;
	private String horaSolicitacao;
	private String horaFinalizacao;
	
	public Cobranca() {
		
	}
	public Cobranca(String ciclista, int valor ) {
		this.id = ((int)(Math.random()*500))+"";
		this.valor = valor;
		this.ciclista = ciclista;
		this.status = Status.PENDENTE;
		this.horaSolicitacao = obterDataHora();
		this.horaFinalizacao = ""; 
	}
	public Cobranca(String id, Status status, String horaSolicitacao, String horaFinalizacao, int valor, String ciclista) {
		this.id = id;
		this.status = status;
		this.horaSolicitacao = horaSolicitacao;
		this.horaFinalizacao = horaFinalizacao;
		this.valor = valor;
		this.ciclista = ciclista;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getHoraSolicitacao() {
		return horaSolicitacao;
	}
	public void setHoraSolicitacao(String horaSolicitacao) {
		this.horaSolicitacao = horaSolicitacao;
	}
	public String getHoraFinalizacao() {
		return horaFinalizacao;
	}
	public void setHoraFinalizacao(String horaFinalizacao) {
		this.horaFinalizacao = horaFinalizacao;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public String getCiclista() {
		return ciclista;
	}
	public void setCiclista(String ciclista) {
		this.ciclista = ciclista;
	}
	public static void cobrarCiclista() {
		
	}
	public static Cobranca consultarCobranca(String id) throws CobrancaNaoEncontrada {
		ArrayList<Cobranca> cobrancas = retornarCobranca();
		Cobranca cobranca = null;
		for(int i = 0; i <cobrancas.size(); i++) {
			if(cobrancas.get(i).getId().equals(id)) {
				cobranca = cobrancas.get(i);
				break;
			}
		}
		if(cobranca != null) {
			return cobranca;
		}
		else {
			throw new CobrancaNaoEncontrada("Nao foi possivel encontrar a cobranca");
		}
	}
	private static ArrayList<Cobranca> retornarCobranca() {
		ArrayList<Cobranca> bancoCobranca = new ArrayList<Cobranca>();
		bancoCobranca.add(new Cobranca("1", Status.PAGA, "17-04-2021-13:00", "17-04-2021-13:05", 30, "210"));
		bancoCobranca.add(new Cobranca("2", Status.PAGA, "17-04-2021-13:07", "17-04-2021-13:09", 5, "123"));
		bancoCobranca.add(new Cobranca("3", Status.PENDENTE, "17-04-2021-13:00", "", 35, "124"));
		bancoCobranca.add(new Cobranca("4", Status.CANCELADA, "17-04-2021-13:00", "", 5, "532"));
		return bancoCobranca;
	}
	public static Cobranca realizarCobranca(Cobranca cobranca) throws CieloRequestException,  IOException, CartaoNaoEncontrado, CiclistaNaoEncontrado{
		Ciclista ciclista = Ciclista.consultarCiclista(cobranca.getCiclista());
		Merchant merchant = new Merchant(MerchantId, MerchantKey);
		Sale sale = new Sale(((int)(Math.random()*500))+"");
		Customer customer = sale.customer(ciclista.getNome());
		Payment payment = sale.payment(cobranca.getValor());
		payment.creditCard(ciclista.getCartao().getCvv(), "Visa").setExpirationDate(ciclista.getCartao().getValidade())
		                                 .setCardNumber(ciclista.getCartao().getNumero())
		                                 .setHolder(ciclista.getNome());
        sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);
        String paymentId = sale.getPayment().getPaymentId();
        cobranca.setId("5");
        cobranca.setStatus(Status.PAGA);
        cobranca.setHoraFinalizacao(obterDataHora());
        return cobranca;
 
	}
	public static String obterDataHora() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return formatter.format(date);
	}
	
}
