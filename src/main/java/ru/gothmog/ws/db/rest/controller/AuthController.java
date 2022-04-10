package ru.gothmog.ws.db.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.gothmog.ws.db.core.model.auth.User;
import ru.gothmog.ws.db.core.service.UserSignUpService;
import ru.gothmog.ws.db.core.utils.GrantedOffice;
import ru.gothmog.ws.db.rest.payload.request.LoginRequest;
import ru.gothmog.ws.db.rest.payload.request.SignupRequest;
import ru.gothmog.ws.db.rest.payload.responce.JwtResponse;
import ru.gothmog.ws.db.rest.payload.responce.MessageResponse;
import ru.gothmog.ws.db.security.UserPrincipal;
import ru.gothmog.ws.db.security.jwt.JwtUtils;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserSignUpService userSignUpService;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserSignUpService userSignUpService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userSignUpService = userSignUpService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        List<String> offices = userDetails.getOffices().stream()
                .map(GrantedOffice::getOffice)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(userDetails.getId(),
                jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles, offices));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final SignupRequest signUpRequest) {
        if (userSignUpService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userSignUpService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Optional<User> user = userSignUpService.createUserForRegistration(signUpRequest);
        if (user.isEmpty()) {
            ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User failed to register"));
        }
       String userName = "";
        if (user.isPresent()){
            userName = user.get().getUsername();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(userName).toUri();

        return ResponseEntity.created(location).body(new MessageResponse("User registered successfully!"));
    }
}
