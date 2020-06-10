package ee.mrtnh.tallink_test.model;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class Participant {

    @NotNull(message = "Participant must have name")
    String fullName; // TODO: check regex firstName LastName

    @NotNull(message = "Participant must have date of birth")
    Instant dateOfBirth;

    @NotNull(message = "Participant must have email address")
    String email; // email as unique identifier
}
