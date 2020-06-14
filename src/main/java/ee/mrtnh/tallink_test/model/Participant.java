package ee.mrtnh.tallink_test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    public Participant(String fullName, LocalDate dateOfBirth) {
        if (!fullName.matches(".+ .+")) {
            throw new ValidationException("Participant's full name must be formatted as FirstName_LastName");
        }
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    @NotNull(message = "Participant must have name")
    @Column(nullable = false)
    String fullName;

    @NotNull(message = "Participant must have date of birth")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyy")
    LocalDate dateOfBirth;

    @ManyToMany(mappedBy = "participants")
    @ToString.Exclude
    Set<Conference> conferences = new HashSet<>();

}
