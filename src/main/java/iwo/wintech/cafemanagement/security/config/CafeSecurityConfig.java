package iwo.wintech.cafemanagement.security.config;

import iwo.wintech.cafemanagement.security.auth.CafeMgmtAuthConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CafeSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final CafeMgmtAuthConfigurer customAuthConfigurer) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        r -> r.requestMatchers("/api/cafe/v1/user/auth/login").permitAll()
                                .requestMatchers("/api/cafe/v1/user/auth/sign-up").permitAll()
                                .requestMatchers("/api/cafe/v1/user/auth/forgot-password").permitAll()
                                .requestMatchers("/api/cafe/v1/user/auth/check-token").permitAll()
                                .requestMatchers("/api/cafe/v1/admin/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .with(customAuthConfigurer, Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passWordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider... authProviders) {
        return new ProviderManager(authProviders);
    }

}
