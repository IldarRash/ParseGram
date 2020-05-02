package com.parsegram.boot.handlers;

import com.parsegram.boot.model.User;
import com.parsegram.boot.model.dto.AuthRequest;
import com.parsegram.boot.model.dto.AuthResponse;
import com.parsegram.boot.model.dto.RegistrationDto;
import com.parsegram.boot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> registration(@RequestBody RegistrationDto registration) {
        return userService.registration(registration)
                .log()
                .map(user -> ResponseEntity.ok().body(user.getId()));
    }


    @GetMapping("/users")
    public Flux<User> getUsers() {
        return userService.getAllUsers();
    }
}
