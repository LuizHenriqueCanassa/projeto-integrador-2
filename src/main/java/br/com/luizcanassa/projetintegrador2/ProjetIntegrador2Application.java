package br.com.luizcanassa.projetintegrador2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class ProjetIntegrador2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetIntegrador2Application.class, args);
	}

}
