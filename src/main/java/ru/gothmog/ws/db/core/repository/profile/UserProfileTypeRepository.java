package ru.gothmog.ws.db.core.repository.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gothmog.ws.db.core.model.profile.UserProfileType;
import ru.gothmog.ws.db.core.model.profile.UserProfileTypeName;

import java.util.Optional;

@Repository
public interface UserProfileTypeRepository extends JpaRepository<UserProfileType, Long> {

    Optional<UserProfileType> findByUserProfileTypeName(UserProfileTypeName userProfileTypeName);
}
