package ee.mrtnh.tallink_test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConferenceTest {

    private Conference conference;
    private final LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
    private final LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 0);
    private final Participant participant = new Participant("FirstName LastName", dateOfBirth);

    @BeforeEach
    void setUp() {
        conference = new Conference("test", conferenceStartDateTime, conferenceStartDateTime.plusHours(1));
        Set<Participant> participants = new HashSet<>();
        participants.add(participant);
        conference.setParticipants(participants);
    }

    @Test
    void createConference_valid() {
        assertDoesNotThrow(() -> new Conference("test", conferenceStartDateTime, conferenceStartDateTime.plusHours(1)));
    }

    @Test
    void createConference_nameTooLong() {
        String nameTooLong = "i".repeat(151);
        assertThrows(ValidationException.class, () -> new Conference(nameTooLong, conferenceStartDateTime, conferenceStartDateTime.plusHours(1)));
    }

    @Test
    void createConference_endTimeBeforeStartTime() {
        assertThrows(ValidationException.class, () -> new Conference("test", conferenceStartDateTime, conferenceStartDateTime.minusHours(1)));
    }

    @Test
    void addParticipant_alreadyExists() {
        assertFalse(conference.addParticipant(participant));
    }

    @Test
    void addParticipant_doesntExists() {
        assertTrue(conference.addParticipant(new Participant("new Participant", dateOfBirth)));
    }

    @Test
    void removeParticipant_exists() {
        assertTrue(conference.removeParticipant(participant));
    }

    @Test
    void removeParticipant_doesntExist() {
        assertFalse(conference.removeParticipant(new Participant("new NewParticipant", dateOfBirth)));
    }
}