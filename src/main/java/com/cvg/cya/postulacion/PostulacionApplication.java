package com.cvg.cya.postulacion;

import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.Session;
import com.cvg.cya.postulacion.models.entity.User;
import com.cvg.cya.postulacion.models.entity.UserMenu;
import com.cvg.cya.postulacion.models.repository.MenuRepository;
import com.cvg.cya.postulacion.models.repository.RoleRepository;
import com.cvg.cya.postulacion.models.repository.SessionRepository;
import com.cvg.cya.postulacion.models.repository.UserRepository;
import com.cvg.cya.postulacion.service.MenuService;
import com.cvg.cya.postulacion.service.RoleService;
import com.cvg.cya.postulacion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
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

	public PostulacionApplication(MenuRepository menuService, RoleRepository roleService, UserRepository userService, SessionRepository sessionRepository) {
		this.menuService = menuService;
		this.roleService = roleService;
		this.userService = userService;
		this.sessionRepository = sessionRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PostulacionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Session session = new Session(0L, 60);
		this.sessionRepository.save(session);

		List<UserMenu> menu = Arrays.asList(
				new UserMenu(0L, "users", "/users"),
				new UserMenu(0L, "roles", "/roles"),
				new UserMenu(0L, "menu", "/menu"),
				new UserMenu(0L, "home", "/home"),
				new UserMenu(0L, "c&a", "/")
		);
		this.menuService.saveAll( menu );

		Role role = new Role(0L, "ADMIN", this.menuService.findAll().stream().collect(Collectors.toSet()));
		Role saved = this.roleService.save(role);

		/**
		 * DEFAULT
		 */
		LocalDateTime now = LocalDateTime.now();
		Set<Role> roles = new HashSet<>();
		roles.add(saved);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("12345");
		User user = new User(0L, roles, "CRISTHIAN", "VILLEGAS GARCIA", "admin@admin.com", encodedPassword, now);
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
