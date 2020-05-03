package com.parsegram.boot.services;

import com.parsegram.boot.exceptions.AuteficationException;
import com.parsegram.boot.exceptions.RegistrationException;
import com.parsegram.boot.model.Profile;
import com.parsegram.boot.model.Role;
import com.parsegram.boot.model.User;
import com.parsegram.boot.model.YandexClient;
import com.parsegram.boot.model.dto.AuthRequest;
import com.parsegram.boot.model.dto.AuthResponse;
import com.parsegram.boot.model.dto.RegistrationDto;
import com.parsegram.boot.repos.UserRepository;
import com.parsegram.boot.utils.JWTUtil;
import com.parsegram.boot.utils.PBKDF2Encoder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.parsegram.boot.exceptions.AuteficationException.ID.DATA_NOT_VALID;
import static com.parsegram.boot.exceptions.RegistrationException.ID.EMAIL_IS_USED;
import static com.parsegram.boot.exceptions.RegistrationException.ID.EMAIL_NOT_VALID;

@Service
@RequiredArgsConstructor
public class UserService {
	private final JWTUtil jwtUtil;
	private final PBKDF2Encoder passwordEncoder;
	private final UserRepository userRepository;
	
	public Mono<AuthResponse> login(AuthRequest authRequest) {
		return userRepository.findByUsername(authRequest.getUsername())
				.flatMap(user -> {
					if (passwordEncoder.encode(authRequest.getPassword()).equals(user.getPassword())) {
						return Mono.just(AuthResponse
								.builder()
								.token(jwtUtil.generateToken(user))
								.username(user.getUsername())
								.build()
						);
					} else {
						return Mono.error(new AuteficationException(DATA_NOT_VALID));
					}
				});
	}

	public Mono<User> registration(RegistrationDto registration) {
		validate(registration);

		return userRepository.findByEmail(registration.getEmail())
				.map(user -> {
					if (user != null )
						throw new RegistrationException(EMAIL_IS_USED);
					return Mono.empty();
				})
				.then(Mono.just(createUser(registration)))
				.flatMap(userRepository::save);
	}

	private void validate(RegistrationDto registrationDto) {
		boolean valid = EmailValidator.getInstance().isValid(registrationDto.getEmail());
		if (!valid)
			throw new RegistrationException(EMAIL_NOT_VALID);
	}

	private User createUser(RegistrationDto registration) {
		Profile profile = Profile.builder()
				.id(UUID.randomUUID())
				.email(registration.getEmail())
				.yandexClient(new YandexClient())
				.createAt(Date.from(Instant.now()))
				.phone(registration.getPhone())
				.subscribes(Collections.emptyList())
				.build();

		return User.builder()
				.username(registration.getUsername())
				.password(passwordEncoder.encode(registration.getPassword()))
				.enabled(true)
				.roles(List.of(Role.ROLE_USER))
				.profile(profile)
				.build();
	}

	public Flux<User> getAllUsers() {
		return userRepository.findAll();
	}

	/*@PostConstruct
	public void init() {
		User user = new User(UUID.randomUUID(), "user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER));
		User admin = new User(UUID.randomUUID(), "admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN));

		List<User> users = Stream.of(user, admin).collect(Collectors.toList());
		userRepository.saveAll(users).subscribe();
	}*/
}