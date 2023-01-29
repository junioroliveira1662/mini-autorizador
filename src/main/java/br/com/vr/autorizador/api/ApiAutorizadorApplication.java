package br.com.vr.autorizador.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

@EntityScan("br.com.vr.autorizador.api.model")
@ComponentScan("br.com.vr.autorizador.api.repository")
@ComponentScan("br.com.vr.autorizador.api.service")
@ComponentScan("br.com.vr.autorizador.api.controller")
@ComponentScan("br.com.vr.autorizador.api.core.exception")
@Controller
@SpringBootApplication
public class ApiAutorizadorApplication {

	@RequestMapping("/")
	public String home() {
		return "redirect:/swagger-ui.html";
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiAutorizadorApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
		Locale.setDefault(new Locale("pt", "BR"));
		LocaleContextHolder.setDefaultLocale(new Locale("pt", "BR"));
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}