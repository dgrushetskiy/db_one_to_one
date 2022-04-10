package ru.gothmog.ws.db.core.model.profile.offices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gothmog.ws.db.core.model.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "offices", schema = "auth",indexes = @Index(name = "unq_offices_name_key", columnList = "office_name", unique = true))
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Office extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "office_name", length = 100)
    private String officeName;
}
