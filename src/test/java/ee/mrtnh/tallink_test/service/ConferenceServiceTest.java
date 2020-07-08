package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.exception.ConferenceAlreadyExistsException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomBookedException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.service.implementation.ConferenceServiceImpl;
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
class ConferenceServiceTest {

    @Mock
    private ConferenceRepository conferenceRepository;

    @Mock
    private RepoHelper repoHelper;

    @InjectMocks
    private ConferenceServiceImpl conferenceService;

    private Conference conference;

    @BeforeEach
    void setUp() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        Integer maxCapacity = 5;
        ConferenceRoom conferenceRoom = new ConferenceRoom("testRoomName", "testRoomLocation", maxCapacity);
        conference.setConferenceRoom(conferenceRoom);
    }

    @Test
    void addConference_alreadyExists() {
        doReturn(conference).when(repoHelper).findConference(conference);
        doReturn(conference.getConferenceRoom()).when(repoHelper).findConferenceRoom(conference.getConferenceRoom());

        assertThrows(ConferenceAlreadyExistsException.class, () -> conferenceService.addConference(conference));
    }

    @Test
    void addConference_roomNotAvailable() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 0);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 0);
        Conference conflictingConference = new Conference("conflictingConference", conferenceStartDateTime, conferenceEndDateTime);
        conflictingConference.setConferenceRoom(conference.getConferenceRoom());
        doReturn(null).when(repoHelper).findConference(conflictingConference);

        conference.getConferenceRoom().getConferences().add(conference);
        doReturn(conference.getConferenceRoom()).when(repoHelper).findConferenceRoom(conflictingConference.getConferenceRoom());

        assertThrows(ConferenceRoomBookedException.class, () -> conferenceService.addConference(conflictingConference));
    }

    @Test
    void addConference_success() {
        doReturn(null).when(repoHelper).findConference(conference);
        doReturn(conference.getConferenceRoom()).when(repoHelper).findConferenceRoom(conference.getConferenceRoom());

        assertDoesNotThrow(() -> conferenceService.addConference(conference));
    }

    @Test
    void cancelConference_conferenceDoesntExist() {
        doReturn(null).when(repoHelper).findConference(conference);

        assertThrows(ConferenceNotFoundException.class, () -> conferenceService.cancelConference(conference));
    }

    @Test
    void cancelConference_success() {
        doReturn(conference).when(repoHelper).findConference(conference);

        assertDoesNotThrow(() -> conferenceService.cancelConference(conference));
    }

    @Test
    void checkConferenceRoomAvailability_conferenceDoesntExist() {
        doReturn(null).when(repoHelper).findConference(conference);

        assertThrows(ConferenceNotFoundException.class, () -> conferenceService.checkConferenceSeatsAvailability(conference));
    }

    @Test
    void checkConferenceRoomAvailability_success() {
        doReturn(conference).when(repoHelper).findConference(conference);

        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        conference.getParticipants().add(new Participant("FirstName_1 LastName_1", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_2 LastName_2", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_3 LastName_3", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_4 LastName_4", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_5 LastName_5", dateOfBirth));

        assertDoesNotThrow(() -> conferenceService.checkConferenceSeatsAvailability(conference));
    }


}