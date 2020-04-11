package com.parsegram.boot.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.parsegram.boot.security.RoleType.ROLE_ADMIN;
import static com.parsegram.boot.security.RoleType.ROLE_USER;
import static com.parsegram.boot.utils.PasswordUtils.hashPassword;

@Slf4j
public class AuthManager implements ReactiveAuthenticationManager, InitializingBean {

    @Value("#{environment['BASIC_AUTH_USERNAME']}")
    private String userName;

    @Value("#{environment['BASIC_AUTH_PASSWORD_HASH']}")
    private String passwordHash;

    @Value("#{environment['BASIC_AUTH_ADMIN_USERNAME']}")
    private String adminUserName;

    @Value("#{environment['BASIC_AUTH_ADMIN_PASSWORD_HASH']}")
    private String adminPasswordHash;

    @Value("#{environment['BASIC_AUTH_PASSWORD_SALT']}")
    private String salt;

    private List<String> activeNames;

    private final List<GrantedAuthority> userRoles = Stream.of(ROLE_USER).map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    private final List<GrantedAuthority> adminRoles = Stream.of(ROLE_USER, ROLE_ADMIN).map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    @Override
    public void afterPropertiesSet() {
        activeNames = Stream.of(userName, adminUserName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return Mono.just(authentication);
        }

        return Mono.just(authentication)
                .switchIfEmpty(Mono.defer(this::raiseBadCredentials))
                .cast(UsernamePasswordAuthenticationToken.class)
                .filter(it -> checkName(it.getName()))
                .filter(it -> hashPassword(it.getCredentials().toString(), salt).equals(passwordByName(it.getName())))
                .switchIfEmpty(Mono.defer(this::raiseBadCredentials)).cast(Authentication.class)
                .map(it -> new UsernamePasswordAuthenticationToken(it.getPrincipal(), it.getCredentials(), getRolesToUser(it.getName())));
    }

    private boolean checkName(String name) {
        return activeNames.contains(name);
    }

    private String passwordByName(String name) {
        if (userName.equals(name)) {
            return passwordHash;
        } else if (Optional.ofNullable(adminUserName).filter(it -> it.equals(name)).isPresent()) {
            return adminPasswordHash;
        } else {
            throw new IllegalArgumentException("Can't find user with name: " + name);
        }
    }


    private List<GrantedAuthority> getRolesToUser(String name) {
        if (userName.equals(name)) {
            return adminUserName == null ? adminRoles : userRoles;
        } else if (Optional.ofNullable(adminUserName).filter(it -> it.equals(name)).isPresent()) {
            return adminRoles;
        } else {
            throw new IllegalArgumentException("Can't find user with name: " + name);
        }
    }

    private <T> Mono<T> raiseBadCredentials() {
        return Mono.error(new BadCredentialsException("Invalid Credentials"));
    }
}
