package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.exception.ConferenceCapacityFilledException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ParticipantAlreadyRegisteredException;
import ee.mrtnh.tallink_test.exception.ParticipantNotRegisteredException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ParticipantRepository;
import ee.mrtnh.tallink_test.util.RepoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    ParticipantRepository participantRepository;

    @Mock
    ConferenceRepository conferenceRepository;

    @Mock
    RepoHelper repoHelper;

    @InjectMocks
    ParticipantServiceImpl participantService;

    Conference conference;
    Participant participant;
    ConferenceRoom conferenceRoom;
    final Integer MAX_CAPACITY = 5;

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        participant = new Participant("FirstName LastName", dateOfBirth);

        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        conferenceRoom = new ConferenceRoom("testRoomName", "testRoomLocation", MAX_CAPACITY);
        conference.setConferenceRoom(conferenceRoom);
    }

    @Test
    void addParticipantToConference_conferenceDoesntExist() {
        doReturn(null).when(repoHelper).findConference(conference);

        assertThrows(ConferenceNotFoundException.class, () -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void addParticipantToConference_noAvailableSeats() {
        doReturn(conference).when(repoHelper).findConference(conference);
        ConferenceRoom room=new ConferenceRoom("testRoomName", "testRoomLocation", 1);
        conference.setConferenceRoom(room);
        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        Participant newParticipant=new Participant("First_Name Last_Name", dateOfBirth);
        conference.addParticipant(newParticipant);

        assertThrows(ConferenceCapacityFilledException.class, () -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void addParticipantToConference_participantAlreadyRegistered() {
        doReturn(conference).when(repoHelper).findConference(conference);

        conference.addParticipant(participant);

        assertThrows(ParticipantAlreadyRegisteredException.class, () -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void addParticipantToConference_success() {
        doReturn(conference).when(repoHelper).findConference(conference);

        assertDoesNotThrow(() -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void removeParticipantFromConference_conferenceDoesntExist() {
        doReturn(null).when(repoHelper).findConference(conference);

        assertThrows(ConferenceNotFoundException.class, () -> participantService.removeParticipantFromConference(participant, conference));
    }

    @Test
    void removeParticipantFromConference_participantNotRegistered() {
        doReturn(conference).when(repoHelper).findConference(conference);

        assertThrows(ParticipantNotRegisteredException.class, () -> participantService.removeParticipantFromConference(participant, conference));
    }

    @Test
    void removeParticipantFromConference_success() {
        doReturn(conference).when(repoHelper).findConference(conference);

        conference.addParticipant(participant);

        assertDoesNotThrow(() -> participantService.removeParticipantFromConference(participant, conference));
    }
}