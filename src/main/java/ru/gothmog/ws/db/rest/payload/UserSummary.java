package ru.gothmog.ws.db.rest.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String patronymic;
    private String lastName;
    private LocalDate dateBirth;

}
