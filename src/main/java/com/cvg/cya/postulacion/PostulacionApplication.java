package com.cvg.cya.postulacion;

import com.cvg.cya.postulacion.models.entity.UserMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PostulacionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PostulacionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<UserMenu> menu = Arrays.asList(
				new UserMenu(0L, "USERS", "/users"),
				new UserMenu(0L, "ROLES", "/roles"),
				new UserMenu(0L, "HOME", "/home"),
				new UserMenu(0L, "MENU", "/menu")
		);

	}
}
