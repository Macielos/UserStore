package userstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//.anyRequest().permitAll();
				.antMatchers("/user").hasRole("USER")
				.antMatchers("/authenticate").permitAll()//.hasRole("USER")
				.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.csrf().disable()
				.formLogin().permitAll();
	}

	@Bean
	public UserDetailsService userDetailsService(UserConfig userConfig) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		UserDetails[] userDetails = userConfig.getUsers().stream()
				.map(user -> User.withUsername(user.getName())
						.passwordEncoder(encoder::encode)
						.password(user.getPassword())
						.roles("USER")
						.build()).toArray(UserDetails[]::new);

		return new InMemoryUserDetailsManager(userDetails);
	}
}
