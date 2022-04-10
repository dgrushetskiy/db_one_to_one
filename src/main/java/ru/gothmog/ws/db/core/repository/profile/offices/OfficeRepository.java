package ru.gothmog.ws.db.core.repository.profile.offices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gothmog.ws.db.core.model.profile.offices.Office;

import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    Optional<Office> findByOfficeName(String officeName);
}
