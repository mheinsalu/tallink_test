package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.exception.ConferenceCapacityFilledException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ParticipantAlreadyRegisteredException;
import ee.mrtnh.tallink_test.exception.ParticipantNotRegisteredException;
import ee.mrtnh.tallink_test.model.Conference;
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
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ConferenceRepository conferenceRepository;

    @Mock
    private RepoHelper repoHelper;

    @InjectMocks
    private ParticipantServiceImpl participantService;

    private Conference conference;
    private Participant participant;

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        participant = new Participant("FirstName LastName", dateOfBirth);
        participant.setId(1);

        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        conference.setId(1L);
        conference.setConferenceRoomId(1L);
    }

    @Test
    void addParticipantToConference_conferenceDoesntExist() {
        doThrow(ConferenceNotFoundException.class).when(repoHelper).findConferenceById(conference.getId());

        assertThrows(ConferenceNotFoundException.class, () -> participantService.addParticipantToConference(participant, conference.getId()));
    }

    @Test
    void addParticipantToConference_noAvailableSeats() {
        doReturn(conference).when(repoHelper).findConferenceById(conference.getId());
        doReturn(0).when(repoHelper).getAvailableRoomCapacity(conference.getId());

        assertThrows(ConferenceCapacityFilledException.class, () -> participantService.addParticipantToConference(participant, conference.getId()));
    }

    @Test
    void addParticipantToConference_participantAlreadyRegistered() {
        doReturn(conference).when(repoHelper).findConferenceById(conference.getId());
        doReturn(1).when(repoHelper).getAvailableRoomCapacity(conference.getId());
        conference.addParticipant(participant);

        assertThrows(ParticipantAlreadyRegisteredException.class, () -> participantService.addParticipantToConference(participant, conference.getId()));
    }

    @Test
    void addParticipantToConference_success() {
        doReturn(conference).when(repoHelper).findConferenceById(conference.getId());
        doReturn(1).when(repoHelper).getAvailableRoomCapacity(conference.getId());

        assertDoesNotThrow(() -> participantService.addParticipantToConference(participant, conference.getId()));
    }

    @Test
    void removeParticipantFromConference_conferenceDoesntExist() {
        doThrow(ConferenceNotFoundException.class).when(repoHelper).findConferenceById(conference.getId());

        assertThrows(ConferenceNotFoundException.class, () -> participantService.removeParticipantFromConference(participant.getId(), conference.getId()));
    }

    @Test
    void removeParticipantFromConference_participantNotRegistered() {
        doReturn(conference).when(repoHelper).findConferenceById(conference.getId());

        assertThrows(ParticipantNotRegisteredException.class, () -> participantService.removeParticipantFromConference(participant.getId(), conference.getId()));
    }

    @Test
    void removeParticipantFromConference_success() {
        doReturn(conference).when(repoHelper).findConferenceById(conference.getId());
        conference.addParticipant(participant);

        assertDoesNotThrow(() -> participantService.removeParticipantFromConference(participant.getId(), conference.getId()));
    }
}