package ee.mrtnh.tallink_test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;

    public Participant(String fullName, LocalDate dateOfBirth) {
        if (!fullName.matches(".+ .+")) {
            throw new ValidationException("Participant's full name must be formatted as FirstName LastName");
        }
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    @NotNull(message = "Participant must have name")
    @Pattern(regexp = ".+ .+", message = "Participant's name must match regex \".+ .+\" e.g. Firstname Lastname")
    @Column(nullable = false)
    private String fullName;

    @NotNull(message = "Participant must have date of birth")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @ManyToMany(mappedBy = "participants")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Conference> conferences = new HashSet<>();

}
