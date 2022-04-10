package ru.gothmog.ws.db.core.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gothmog.ws.db.core.model.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_profile_types", schema = "auth", indexes = @Index(name = "unq_user_profile_types_name_key", columnList = "type_name", unique = true))
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProfileType extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "type_name", nullable = false)
    private UserProfileTypeName userProfileTypeName;

    @OneToMany(mappedBy = "userProfileType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserProfile> userProfiles = new ArrayList<>();
}
