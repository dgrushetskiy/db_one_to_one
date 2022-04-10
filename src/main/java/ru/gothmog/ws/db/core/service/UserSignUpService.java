package ru.gothmog.ws.db.core.service;


import ru.gothmog.ws.db.core.model.auth.User;
import ru.gothmog.ws.db.rest.payload.request.SignupRequest;

import java.util.Optional;

public interface UserSignUpService {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> createUserForRegistration(SignupRequest signUpRequest);
}
