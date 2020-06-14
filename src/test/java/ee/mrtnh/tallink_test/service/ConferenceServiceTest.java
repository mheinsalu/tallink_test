package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.exception.*;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ConferenceRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ConferenceServiceTest {

    // TODO: set up test base with rooms and conferences?

    @Autowired
    ConferenceServiceImpl conferenceService;

    @Mock
    ConferenceRepository conferenceRepository;

    @Mock
    ConferenceRoomRepository conferenceRoomRepository;

/*    @InjectMocks
    ConferenceServiceImpl conferenceService;*/
//    ConferenceService conferenceService;

    Conference conference;
    ConferenceRoom conferenceRoom;
    final Integer MAX_CAPACITY = 5;

    @BeforeEach
    void setUp() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        conferenceRoom = new ConferenceRoom("testRoomName", "testRoomLocation", MAX_CAPACITY);
        conference.setConferenceRoom(conferenceRoom);
    }

    @Test
    void addConference_alreadyExists() {
        doReturn(conference).when(conferenceService).findConference(conference);

        assertThrows(ConferenceAlreadyExistsException.class, () -> conferenceService.addConference(conference));
    }

    @Test
    void addConference_roomNotAvailable() {
        doReturn(conference).when(conferenceService).findConference(conference);

        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 0);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 0);
        Conference conflictingConference = new Conference("conflictingConference", conferenceStartDateTime, conferenceEndDateTime);
        conference.getConferenceRoom().getConferences().add(conflictingConference);

        assertThrows(ConferenceRoomBookedException.class, () -> conferenceService.addConference(conference));
    }

    @Test
    void addConference_success() {
        doReturn(Optional.empty()).when(conferenceService).findConference(conference);

        assertDoesNotThrow(() -> conferenceService.addConference(conference));
    }

    @Test
    void cancelConference_conferenceDoesntExist() {
        doReturn(Optional.empty()).when(conferenceService).findConference(conference);

        assertThrows(ConferenceNotFoundException.class, () -> conferenceService.cancelConference(conference));
    }

    @Test
    void cancelConference_success() {
        doReturn(conference).when(conferenceService).findConference(conference);

        assertDoesNotThrow(() -> conferenceService.cancelConference(conference));
    }

    @Test
    void checkConferenceRoomAvailability_roomDoesntExist() {
        doReturn(conference).when(conferenceService).findConference(conference);
        doReturn(Optional.empty()).when(conferenceService).findConferenceRoom(conference.getConferenceRoom());

        assertThrows(ConferenceRoomNotFoundException.class, () -> conferenceService.checkConferenceRoomAvailability(conference));
    }

    @Test
    void checkConferenceRoomAvailability_success() {
        doReturn(conference).when(conferenceService).findConference(conference);
        doReturn(conference.getConferenceRoom()).when(conferenceService).findConferenceRoom(conference.getConferenceRoom());

        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        conference.getParticipants().add(new Participant("FirstName_1 LastName_1", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_2 LastName_2", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_3 LastName_3", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_4 LastName_4", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_5 LastName_5", dateOfBirth));

        assertDoesNotThrow(() -> conferenceService.checkConferenceRoomAvailability(conference));
    }


}