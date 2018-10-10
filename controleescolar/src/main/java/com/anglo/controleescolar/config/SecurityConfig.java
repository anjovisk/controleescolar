package com.anglo.controleescolar.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.anglo.controleescolar.config.object.AuthenticationService;
import com.anglo.controleescolar.security.AuthenticationSuccessHandlerImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackages = { "com.anglo.controleescolar.repository" })
@ComponentScan(basePackages = { "com.anglo.controleescolar.security", "com.anglo.controleescolar.config.object"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	protected AuthenticationService authenticationService;
	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;
	
	@Bean
	public AuthenticationSuccessHandler AuthenticationSuccessHandler() {
		return authenticationSuccessHandlerImpl;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return authenticationService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.headers()
					.frameOptions()
					.sameOrigin()
					.and()
				.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/*").permitAll()
					.antMatchers("/resources/**").permitAll()
					.antMatchers("/app/controleescolar/authorized/**").permitAll()
					.antMatchers("/app/controleescolar/authenticated/**").authenticated()
					.antMatchers("/app/controleescolar/area/**").authenticated()
					.anyRequest().denyAll()
					.and()
				.formLogin()
					.loginPage("/app/controleescolar/authorized/authenticationController/showAuthenticationView")
					.defaultSuccessUrl("/app/controleescolar/authorized/authenticationController/redirect")
					.usernameParameter("username")
					.passwordParameter("password")
					.successHandler(AuthenticationSuccessHandler())
					.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/app/controleescolar/authorized/authenticationController/showAuthenticationView")
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.permitAll()
					.and()
				.exceptionHandling()
					.authenticationEntryPoint(new AjaxAwareLoginUrlAuthenticationEntryPoint("/app/controleescolar/authorized/authenticationController/showAuthenticationView"))
					.accessDeniedPage("/app/controleescolar/authorized/authenticationController/accessDenied")
					.and()
				.cors()
					.and()
				.csrf()
					.disable()
					.sessionManagement()
					.maximumSessions(20);
	}
}
