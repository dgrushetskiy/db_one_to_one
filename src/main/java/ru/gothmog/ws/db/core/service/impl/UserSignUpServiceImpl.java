package ru.gothmog.ws.db.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gothmog.ws.db.core.model.auth.Role;
import ru.gothmog.ws.db.core.model.auth.RoleName;
import ru.gothmog.ws.db.core.model.auth.User;
import ru.gothmog.ws.db.core.model.profile.UserProfile;
import ru.gothmog.ws.db.core.model.profile.UserProfileType;
import ru.gothmog.ws.db.core.model.profile.UserProfileTypeName;
import ru.gothmog.ws.db.core.model.profile.offices.Office;
import ru.gothmog.ws.db.core.repository.auth.RoleRepository;
import ru.gothmog.ws.db.core.repository.auth.UserRepository;
import ru.gothmog.ws.db.core.repository.profile.UserProfileTypeRepository;
import ru.gothmog.ws.db.core.repository.profile.offices.OfficeRepository;
import ru.gothmog.ws.db.core.service.UserSignUpService;
import ru.gothmog.ws.db.rest.payload.request.SignupRequest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserSignUpServiceImpl implements UserSignUpService {

    private static final String AGRONOMIST = "Агроном";
    private static final String INVESTOR = "Инвестор";
    private static final String FARMER = "Фермер";
    private static final String SPK_REPRESENTATIVE = "Представитель СПК";
    private static final String TK_REPRESENTATIVE = "Представитель ТК";
    private static final String NO_FIRST_NAME = "введите имя";
    private static final String NO_LAST_NAME = "введите фамилию";
    private static final String NO_PATRONYMIC = "введите отчество";
    private static final String UNDEFINED = "Не определено";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserProfileTypeRepository userProfileTypeRepository;
    private final OfficeRepository officeRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserSignUpServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserProfileTypeRepository userProfileTypeRepository, OfficeRepository officeRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userProfileTypeRepository = userProfileTypeRepository;

        this.officeRepository = officeRepository;
        this.encoder = encoder;
    }


    @Override
    public Boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> createUserForRegistration(final SignupRequest signUpRequest) {
        if (signUpRequest != null) {
            final User user = createUser(signUpRequest);
            final UserProfile userProfile = createUserProfile();
            final String userRoleInCabinet = signUpRequest.getRoleInCabinet();
            Set<Role> roles = new HashSet<>();
            Set<Office> offices = new HashSet<>();
            Role userRole;
            Office userOffice;
            if (StringUtils.isBlank(userRoleInCabinet)) {
                userRole = roleRepository.findByRoleName(RoleName.ROLE_ANONYMOUS)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                userOffice = officeRepository.findByOfficeName(FARMER)
                        .orElseThrow(() -> new RuntimeException("Error: Office is not found."));
                offices.add(userOffice);
            } else {
                userOffice = officeRepository.findByOfficeName(userRoleInCabinet)
                        .orElseThrow(() -> new RuntimeException("Error: Office is not found."));
                offices.add(userOffice);
                RoleName roleName = getRoleName(userRoleInCabinet);
                userRole = roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
            userProfile.setOffices(offices);
            userProfile.setUser(user);
            user.setRoles(roles);
            user.setUserProfile(userProfile);
            userRepository.save(user);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    private RoleName getRoleName(String userRoleInCabinet) {
        switch (userRoleInCabinet) {
            case AGRONOMIST:
                return RoleName.ROLE_AGRONOMIST;
            case INVESTOR:
                return RoleName.ROLE_INVESTOR;
            case FARMER:
                return RoleName.ROLE_FARMER;
            case SPK_REPRESENTATIVE:
                return RoleName.ROLE_SPK_REPRESENTATIVE;
            case TK_REPRESENTATIVE:
                return RoleName.ROLE_TK_REPRESENTATIVE;
            default:
                return RoleName.ROLE_ANONYMOUS;
        }
    }

    private UserProfile createUserProfile() {
        final UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(NO_FIRST_NAME);
        userProfile.setLastName(NO_LAST_NAME);
        userProfile.setPatronymic(NO_PATRONYMIC);
        userProfile.setDateBirth(LocalDate.now());
        userProfile.setPlaceBirth(UNDEFINED);
        userProfile.setFileAvatarPath(UNDEFINED);
        userProfile.setIsB2B(false);
        final Optional<UserProfileType> userProfileType = userProfileTypeRepository.findByUserProfileTypeName(UserProfileTypeName.INDIVIDUAL);
        userProfile.setUserProfileType(userProfileType.orElse(null));
        return userProfile;
    }

    private User createUser(final SignupRequest signUpRequest) {
        final User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setStatus(true);
        return user;
    }
}
