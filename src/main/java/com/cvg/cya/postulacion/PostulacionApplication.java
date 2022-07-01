package com.cvg.cya.postulacion;

import com.cvg.cya.postulacion.models.entity.*;
import com.cvg.cya.postulacion.models.repository.*;
import com.cvg.cya.postulacion.service.MenuService;
import com.cvg.cya.postulacion.service.RoleService;
import com.cvg.cya.postulacion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PostulacionApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger( PostulacionApplication.class );
	private final MenuRepository menuService;
	private final RoleRepository roleService;
	private final UserRepository userService;
	private final SessionRepository sessionRepository;
	private final DisplayRepository displayRepository;
	public PostulacionApplication(MenuRepository menuService, RoleRepository roleService, UserRepository userService, SessionRepository sessionRepository, DisplayRepository displayRepository) {
		this.menuService = menuService;
		this.roleService = roleService;
		this.userService = userService;
		this.sessionRepository = sessionRepository;
		this.displayRepository = displayRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(PostulacionApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		/**
		 * CONFIGURACIONES POR DEFECTO-------------------------------------
		 */
		Display display = new Display(0L, "Buenas tardes C&A");
		this.displayRepository.save(display);

		Session session = new Session(0L, 60);
		this.sessionRepository.save(session);
		/**
		 * ------------------------------------------------------------------
		 */

		List<UserMenu> menu = Arrays.asList(
				new UserMenu(0L, "C&A", "/cya"),
				new UserMenu(0L, "Users", "/users"),
				new UserMenu(0L, "Roles", "/roles"),
				new UserMenu(0L, "Menú", "/menu"),
				new UserMenu(0L, "Sesión", "/sesion")
		);
		this.menuService.saveAll( menu );

		List<Role> roles = Arrays.asList(
				new Role(0L, "ADMIN", this.menuService.findAll().stream().collect(Collectors.toSet())),
				new Role(0L, "BASIC", this.menuService.findAll().stream().filter(item -> item.getId() < 3).collect(Collectors.toSet()))
		);
		List<Role> rolesSaved = this.roleService.saveAll(roles);


		/**
		 * DEFAULT
		 */
		LocalDateTime now = LocalDateTime.now();
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(rolesSaved.get(0));

		LOG.info( adminRoles.toString() );

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("12345");
		User user = new User(
				0L,
				adminRoles,
				"CRISTHIAN",
				"VILLEGAS GARCIA",
				"admin@admin.com", encodedPassword, "parangamicutirimicuaro",
				now);
		this.userService.save(user);

		/**
		 * ---------------------------------------------------------------------------------------------------------------------------
		 */
		String message = "La sesión tiene una duración de %d segundos máximo para usuarios inactivos, después de ese tiempo se cerrará.";
		String newMessage = String.format(message, session.getDuration());
		LOG.info( newMessage );
		/**
		 * ---------------------------------------------------------------------------------------------------------------------------
		 */
	}
}
