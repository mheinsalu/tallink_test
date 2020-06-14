package ee.mrtnh.tallink_test.model;

import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);

    @Test
    void createParticipant_valid() {
        assertDoesNotThrow(() -> new Participant("FirstName LastName", dateOfBirth));
    }

    @Test
    void createParticipant_nameFormat_Invalid() {
        assertThrows(ValidationException.class, () -> new Participant("invalidName", dateOfBirth));
    }

}