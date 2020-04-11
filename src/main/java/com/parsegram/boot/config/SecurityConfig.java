package com.parsegram.boot.config;


import com.parsegram.boot.security.AuthManager;
import com.parsegram.boot.security.HeaderExchangeMatcher;
import com.parsegram.boot.security.UnauthorizedAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/health",
            "/user",
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, UnauthorizedAuthenticationEntryPoint entryPoint) {
        http.httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable()
                .requestCache().disable();

        return http
                .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                .addFilterAt(webFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange().pathMatchers(AUTH_WHITELIST).permitAll().and()
                .authorizeExchange().anyExchange().authenticated().and()
                .build();
    }

    @Bean
    public AuthManager authManager() {
        return new AuthManager();
    }

    @Bean
    public AuthenticationWebFilter webFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authManager());
        authenticationWebFilter.setServerAuthenticationConverter(new ServerHttpBasicAuthenticationConverter());
        authenticationWebFilter.setRequiresAuthenticationMatcher(new HeaderExchangeMatcher());
        authenticationWebFilter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        return authenticationWebFilter;
    }
}
