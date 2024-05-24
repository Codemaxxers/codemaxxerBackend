package com.nighthawk.spring_portfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nighthawk.spring_portfolio.mvc.jwt.JwtAuthenticationEntryPoint;
import com.nighthawk.spring_portfolio.mvc.jwt.JwtRequestFilter;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PersonDetailsService personDetailsService;

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
<<<<<<< HEAD

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate", "/signout").permitAll()
                .requestMatchers("/reading/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/mvc/person/update/**", "/mvc/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/questions/deleteQuestion/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/questions/makeQuestion/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/**").permitAll()
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .headers(headers -> headers
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "https://codemaxxers.github.io"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "DELETE", "GET", "OPTIONS", "HEAD"))
            )
            .formLogin(form -> form.loginPage("/login"))
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
            )
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://codemaxxers.github.io"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type", "Authorization", "x-csrf-token"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

//<------------------ORIGINAL SECURITY CONFIG BREAK COMMENT IF NEEDED------------------>
// package com.nighthawk.spring_portfolio;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.header.writers.StaticHeadersWriter;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// import com.nighthawk.spring_portfolio.mvc.jwt.JwtAuthenticationEntryPoint;
// import com.nighthawk.spring_portfolio.mvc.jwt.JwtRequestFilter;
// import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;


// /*
// * To enable HTTP Security in Spring
// */
// @Configuration
// @EnableWebSecurity  // Beans to enable basic Web security
// @EnableMethodSecurity(prePostEnabled = true)
// public class SecurityConfig {

//     @Autowired
// 	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

// 	@Autowired
// 	private JwtRequestFilter jwtRequestFilter;

// 	@Autowired
// 	private PersonDetailsService personDetailsService;

//     // @Bean  // Sets up password encoding style
//     PasswordEncoder passwordEncoder(){
//         return new BCryptPasswordEncoder();
//     }

// 	@Autowired
// 	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// 		// configure AuthenticationManager so that it knows from where to load
// 		// user for matching credentials
// 		// Use BCryptPasswordEncoder
// 		auth.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder());
// 	}

// 	@Bean
// 	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
// 		return authenticationConfiguration.getAuthenticationManager();
// 	}
=======
>>>>>>> 33aaaa2 ( Changes to be committed:)

<<<<<<< HEAD
	
<<<<<<< HEAD
//     // Provide security configuration
// 		@Bean
// 		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// 			http
// 				.csrf(csrf -> csrf
// 					.disable()
// 				)
// 				// list the requests/endpoints need to be authenticated
// 				.authorizeHttpRequests(auth -> auth
// 					.requestMatchers("/authenticate", "/signout").permitAll()
// 					.requestMatchers("/reading/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/mvc/person/update/**", "/mvc/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/api/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/api/questions/deleteQuestion/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/api/questions/makeQuestion/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/**").permitAll()
// 				)
// 				// support cors
// 				.cors(Customizer.withDefaults())
// 				.headers(headers -> headers
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "https://codemaxxers.github.io"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "GET", "OPTIONS", "HEAD", "DELETE"))
// 				)
// 				.formLogin(form -> form 
// 					.loginPage("/login")
// 				)
// 				.logout(logout -> logout
// 					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// 					.logoutSuccessUrl("/")
// 				)
// 				// make sure we use stateless session; 
// 				// session won't be used to store user's state.
// 				.exceptionHandling(exceptions -> exceptions
// 					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
// 				)
// 				.sessionManagement(session -> session
// 					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
// 				)
// 				// Add a filter to validate the tokens with every request
// 				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
// 			return http.build();
// 	}
// }
=======
    // Provide security configuration
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
				.csrf(csrf -> csrf
					.disable()
				)
				// list the requests/endpoints need to be authenticated
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/authenticate", "/signout").permitAll()
					.requestMatchers("/reading/**").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/mvc/person/update/**", "/mvc/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/api/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/api/questions/deleteQuestion/**").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/api/questions/makeQuestion/**").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/**").permitAll()
				)
				// support cors
				.cors(Customizer.withDefaults())
				.headers(headers -> headers
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "http://127.0.0.1:4100", "https://codemaxxers.stu.nighthawkcodingsociety.com", "https://codemaxxers.github.io", "https://vivanknee.github.io"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "GET", "OPTIONS", "HEAD"))
				)
				.formLogin(form -> form 
					.loginPage("/login")
				)
				.logout(logout -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/")
				)
				// make sure we use stateless session; 
				// session won't be used to store user's state.
				.exceptionHandling(exceptions -> exceptions
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				)
				.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				// Add a filter to validate the tokens with every request
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
			return http.build();
	}
=======
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate", "/signout").permitAll()
                .requestMatchers("/reading/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/mvc/person/update/**", "/mvc/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/questions/deleteQuestion/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/questions/makeQuestion/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/**").permitAll()
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .headers(headers -> headers
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "https://codemaxxers.github.io"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "DELETE", "GET", "OPTIONS", "HEAD"))
            )
            .formLogin(form -> form.loginPage("/login"))
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
            )
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://codemaxxers.github.io"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type", "Authorization", "x-csrf-token"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
>>>>>>> 73ae742 ( Changes to be committed:)
}
<<<<<<< HEAD
>>>>>>> 33aaaa2 ( Changes to be committed:)
=======

//<------------------ORIGINAL SECURITY CONFIG BREAK COMMENT IF NEEDED------------------>
// package com.nighthawk.spring_portfolio;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.header.writers.StaticHeadersWriter;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// import com.nighthawk.spring_portfolio.mvc.jwt.JwtAuthenticationEntryPoint;
// import com.nighthawk.spring_portfolio.mvc.jwt.JwtRequestFilter;
// import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;


// /*
// * To enable HTTP Security in Spring
// */
// @Configuration
// @EnableWebSecurity  // Beans to enable basic Web security
// @EnableMethodSecurity(prePostEnabled = true)
// public class SecurityConfig {

//     @Autowired
// 	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

// 	@Autowired
// 	private JwtRequestFilter jwtRequestFilter;

// 	@Autowired
// 	private PersonDetailsService personDetailsService;

//     // @Bean  // Sets up password encoding style
//     PasswordEncoder passwordEncoder(){
//         return new BCryptPasswordEncoder();
//     }

// 	@Autowired
// 	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// 		// configure AuthenticationManager so that it knows from where to load
// 		// user for matching credentials
// 		// Use BCryptPasswordEncoder
// 		auth.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder());
// 	}

// 	@Bean
// 	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
// 		return authenticationConfiguration.getAuthenticationManager();
// 	}

	
//     // Provide security configuration
// 		@Bean
// 		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// 			http
// 				.csrf(csrf -> csrf
// 					.disable()
// 				)
// 				// list the requests/endpoints need to be authenticated
// 				.authorizeHttpRequests(auth -> auth
// 					.requestMatchers("/authenticate", "/signout").permitAll()
// 					.requestMatchers("/reading/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/mvc/person/update/**", "/mvc/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/api/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/api/questions/deleteQuestion/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/api/questions/makeQuestion/**").hasAnyAuthority("ROLE_ADMIN")
// 					.requestMatchers("/**").permitAll()
// 				)
// 				// support cors
// 				.cors(Customizer.withDefaults())
// 				.headers(headers -> headers
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "https://codemaxxers.github.io"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
// 					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "GET", "OPTIONS", "HEAD", "DELETE"))
// 				)
// 				.formLogin(form -> form 
// 					.loginPage("/login")
// 				)
// 				.logout(logout -> logout
// 					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// 					.logoutSuccessUrl("/")
// 				)
// 				// make sure we use stateless session; 
// 				// session won't be used to store user's state.
// 				.exceptionHandling(exceptions -> exceptions
// 					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
// 				)
// 				.sessionManagement(session -> session
// 					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
// 				)
// 				// Add a filter to validate the tokens with every request
// 				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
// 			return http.build();
// 	}
// }
>>>>>>> 2585d01 (original security config)
