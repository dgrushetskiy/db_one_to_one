package ru.gothmog.ws.db.core.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import ru.gothmog.ws.db.core.model.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "roles", schema = "auth",
        indexes = {@Index(name = "unq_roles_role_name_key", columnList = "role_name", unique = true)})
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "role_name", length = 100)
    private RoleName roleName;
}
