package com.assinatura.xades.assinatura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackageClasses = {AssinaturaXades.class})
@SpringBootApplication
public class AssinaturaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssinaturaApplication.class, args);
	}

}
