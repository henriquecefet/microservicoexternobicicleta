package microsservico_externo_bicicleta;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import cieloecommerce.sdk.ecommerce.request.CieloRequestException;
public class Principal {
// Criacao do servidor e tratamento das requisicoes
	public static void main(String[] args) {
		Javalin servidor = Javalin.create(configuracao -> {
            configuracao.registerPlugin(configurarOpenApiPlugin());
            configuracao.defaultContentType = "application/json";
        }).start(7001);
		// requisicao para enviar e-mail
		servidor.post("/enviarEmail", requisicao -> {
			try {
				Email e_mail = new Email(requisicao.queryParam("email"), requisicao.queryParam("mensagem"));
			    Email.enviarEmail(e_mail);
			    requisicao.json(e_mail);
			    requisicao.status(200);
			} catch (AddressException excessaoEndereco) {
				excessaoEndereco.printStackTrace();
				Erro erro = new Erro("404", "Email com formato invalido.");
				requisicao.status(Integer.parseInt(erro.getCodigo()));
				requisicao.json(erro);
			} catch (MessagingException excessaoEmail) {
				excessaoEmail.printStackTrace();
				Erro erro = new Erro("500", "Email com formato invalido.");
				requisicao.status(Integer.parseInt(erro.getCodigo()));
				requisicao.json(erro);
			}
           
            
        });
		servidor.post("/cobranca", requisicao -> {
			try {
				Cobranca cobranca = new Cobranca(requisicao.queryParam("ciclista"),Integer.parseInt(requisicao.queryParam("valor")));
				cobranca = Cobranca.realizarCobranca(cobranca);
				requisicao.json(cobranca);
			}catch(CieloRequestException erroRequest) {
				requisicao.json(erroRequest.getError().getMessage());
			}catch(IOException erroIO) {
				requisicao.json(erroIO.getMessage());
			}
			catch(CiclistaNaoEncontrado ciclistaNaoEncontado) {
				Erro erro = new Erro("422", "Dados Invalios.");
				requisicao.status(Integer.parseInt(erro.getCodigo()));
				requisicao.json(erro);
			}
			catch(CartaoNaoEncontrado cartaoNaoEncontado) {
				Erro erro = new Erro("422", "Dados Invalios.");
				requisicao.status(Integer.parseInt(erro.getCodigo()));
				requisicao.json(erro);
			}
        });
		servidor.post("/filaCobranca", requisicao -> {
			try {
				Cobranca cobranca = new Cobranca(requisicao.queryParam("ciclista"),Integer.parseInt(requisicao.queryParam("valor")));
				FilaCobranca.inserir(cobranca);
			}catch(Exception excessao) {
				Erro erro = new Erro("422", "Dados Invalios.");
				requisicao.status(Integer.parseInt(erro.getCodigo()));
				requisicao.json(erro);
			}
			
        });
		servidor.get("/cobranca/:idCobranca", requisicao -> {
		       try {
		    	   Cobranca cobranca = Cobranca.consultarCobranca(requisicao.pathParam("idCobranca"));
		    	   requisicao.status(200);
		    	   requisicao.json(cobranca);
		    	   
		       }catch(CobrancaNaoEncontrada cobrancaNaoEncontrada) {
		    	   	Erro erro = new Erro("404", cobrancaNaoEncontrada.getMessage());
					requisicao.status(Integer.parseInt(erro.getCodigo()));
					requisicao.json(erro);
		       }
        });
	}
	// Configuracao do servidor.
	  private static OpenApiPlugin configurarOpenApiPlugin() {
	        Info info = new Info().version("1.0").description("Bicicleta API");
	        OpenApiOptions opcoes = new OpenApiOptions(info)
	                .activateAnnotationScanningFor("io.javalin.example.java")
	                .path("/swagger-docs") 
	                .swagger(new SwaggerOptions("/swagger-ui"))
	                .reDoc(new ReDocOptions("/redoc")) 
	                .defaultDocumentation(doc -> {
	                    doc.json("500", Erro.class);
	                    doc.json("503", Erro.class);
	                });
	        return new OpenApiPlugin(opcoes);
	    }

}
