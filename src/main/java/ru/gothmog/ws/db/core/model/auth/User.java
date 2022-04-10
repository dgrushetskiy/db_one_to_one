package ru.gothmog.ws.db.core.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import ru.gothmog.ws.db.core.model.BaseEntity;
import ru.gothmog.ws.db.core.model.profile.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "auth",
        indexes = {
                @Index(name = "unq_users_email_key", columnList = "email", unique = true),
                @Index(name = "unq_users_username_key", columnList = "username", unique = true)
        },
        uniqueConstraints = {@UniqueConstraint(name = "unq_users_email_username_key", columnNames = {"email", "username"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {

    @NaturalId
    @NotBlank
    @Size(max = 150)
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 150)
    @Email
    @Column(name = "email", length = 150)
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "pswd")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", schema = "auth",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_roles_users")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_roles_roles")))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private UserProfile userProfile;
}
