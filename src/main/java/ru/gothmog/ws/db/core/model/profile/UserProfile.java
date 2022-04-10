package ru.gothmog.ws.db.core.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gothmog.ws.db.core.model.AbstractEntity;
import ru.gothmog.ws.db.core.model.auth.User;
import ru.gothmog.ws.db.core.model.profile.offices.Office;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_profile", schema = "auth",indexes = @Index(name = "unq_user_profile_user_id", columnList = "user_id", unique = true))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProfile extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 200)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 200)
    private String lastName;

    @Column(name = "patronymic", nullable = false, length = 200)
    private String patronymic;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "place_birth")
    private String placeBirth;

    @Column(name = "file_avatar_path", length = 4096)
    private String fileAvatarPath;

    @Column(name = "is_b2b", nullable = false, columnDefinition = "boolean default false")
    private Boolean isB2B;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_users_user_profile"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_profile_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_profile_user_profile_types"))
    @JsonIgnore
    private UserProfileType userProfileType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_profile_offices", schema = "auth",
            joinColumns = @JoinColumn(name = "user_profile_id", foreignKey = @ForeignKey(name = "fk_user_profile_offices_user_profile")),
            inverseJoinColumns = @JoinColumn(name = "offices_id", foreignKey = @ForeignKey(name = "fk_user_profile_offices_offices")))
    private Set<Office> offices = new HashSet<>();
}
