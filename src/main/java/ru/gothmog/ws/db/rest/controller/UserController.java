package ru.gothmog.ws.db.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gothmog.ws.db.core.service.UserSignUpService;
import ru.gothmog.ws.db.rest.payload.UserIdentityAvailability;
import ru.gothmog.ws.db.rest.payload.UserSummary;
import ru.gothmog.ws.db.security.CurrentUser;
import ru.gothmog.ws.db.security.UserPrincipal;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserSignUpService userSignUpService;

    @Autowired
    public UserController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return null;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userSignUpService.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userSignUpService.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }
}
