package br.com.api_fipe.APITabelaFIPE;

import br.com.api_fipe.APITabelaFIPE.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiTabelaFipeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiTabelaFipeApplication.class, args);
		new Principal().ExibeMenu();
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
