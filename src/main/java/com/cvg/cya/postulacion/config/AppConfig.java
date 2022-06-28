package com.cvg.cya.postulacion.config;

import com.cvg.cya.postulacion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger( AppConfig.class );
    @Autowired private UserService userService;

    /**
     * DEFINIMOS EL BEAN PARA PODER INYECTAR PASSWORD ENCODER CON EL HASH BCRYPT Y ASIGNARSELO A LA CLAVE DE AUTENTICACION
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore( new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/register**", "/js/**", "/css/**", "/img/**").permitAll()
                .antMatchers("/api/menu").permitAll()
                .antMatchers("/api/users").anonymous()
                .antMatchers("/api/roles").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    class LoginPageFilter extends GenericFilterBean {

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            if (
                    SecurityContextHolder.getContext().getAuthentication() != null &&
                            SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                            ((HttpServletRequest) servletRequest).getRequestURI().equals("/login")
            ) {
                LOG.info("attemp login detected but is authenticated... Redirec...");
                ((HttpServletResponse)servletResponse).sendRedirect("/");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
