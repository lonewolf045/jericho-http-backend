package com.techm.crypton.jericho.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techm.crypton.jericho.models.Users;
import com.techm.crypton.jericho.payload.request.LoginRequest;
import com.techm.crypton.jericho.payload.request.SignupRequest;
import com.techm.crypton.jericho.payload.response.JwtResponse;
import com.techm.crypton.jericho.payload.response.MessageResponse;
import com.techm.crypton.jericho.repository.UserRepository;
import com.techm.crypton.jericho.security.jwt.JwtUtils;
import com.techm.crypton.jericho.security.services.UserDetailsImpl;

@Service
public class AuthService {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);;
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername()
				));
	}
	
	public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
		if(userRepo.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		Users user = new Users(signUpRequest.getFirstName(),signUpRequest.getLastName(),signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));
		userRepo.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
