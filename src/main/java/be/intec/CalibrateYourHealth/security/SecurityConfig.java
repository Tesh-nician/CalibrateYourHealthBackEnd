package be.intec.CalibrateYourHealth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/register", "/login", "/search", "/css/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/afterlogin", true)
                                .permitAll()
                )

                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")
                                .successHandler(oauth2SuccessHandler())
                                .failureHandler(oauth2FailureHandler())
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public AuthenticationSuccessHandler oauth2SuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("redirect:/afterlogin");
        };
    }

    @Bean
    public AuthenticationFailureHandler oauth2FailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login?error=true");
        };
    }


    //TODO: Add
    // import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
    //import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
    //import org.springframework.security.oauth2.client.registration.ClientRegistration;

    //Modify application properties:
    //spring.security.oauth2.client.registration.google.client-id=your-client-id
    //spring.security.oauth2.client.registration.google.client-secret=your-client-secret
    //spring.security.oauth2.client.registration.google.scope=openid,profile,email
    //spring.security.oauth2.client.provider.google.authorization-uri=https://accounts.google.com/o/oauth2/auth
    //spring.security.oauth2.client.provider.google.token-uri=https://oauth2.googleapis.com/token
    //spring.security.oauth2.client.provider.google.user-info-uri=https://www.googleapis.com/oauth2/v3/userinfo
    //spring.security.oauth2.client.provider.google.user-name-attribute=sub
    //spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}

    //

}
