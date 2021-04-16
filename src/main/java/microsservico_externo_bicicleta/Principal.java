package microsservico_externo_bicicleta;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import static io.javalin.apibuilder.ApiBuilder.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
			   EnviarEmail.enviarEmail(requisicao.queryParam("email"), requisicao.queryParam("mensagem"));
			   requisicao.result("Externo solicitado.");
			   requisicao.status(200);
			} catch (AddressException excessaoEndereco) {
				excessaoEndereco.printStackTrace();
				requisicao.status(404);
				requisicao.result("Email com formato invalido.");
			} catch (MessagingException excessaoEmail) {
				excessaoEmail.printStackTrace();
				requisicao.status(500);
			}
           
            
        });
	}
	// Configuracao do servidor.
	  private static OpenApiPlugin configurarOpenApiPlugin() {
	        Info info = new Info().version("1.0").description("Bicicletar API");
	        OpenApiOptions opcoes = new OpenApiOptions(info)
	                .activateAnnotationScanningFor("io.javalin.example.java")
	                .path("/swagger-docs") 
	                .swagger(new SwaggerOptions("/swagger-ui"))
	                .reDoc(new ReDocOptions("/redoc")) 
	                .defaultDocumentation(doc -> {
	                    doc.json("500", ErrorResponse.class);
	                    doc.json("503", ErrorResponse.class);
	                });
	        return new OpenApiPlugin(opcoes);
	    }

}
