package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.exception.ConferenceAlreadyExistsException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomBookedException;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ConferenceServiceTest {

    // TODO: set up test base with rooms and conferences?

    @Mock
    ConferenceRepository conferenceRepository;

    @Mock
    ConferenceRoomRepository conferenceRoomRepository;

    @InjectMocks
    ConferenceServiceImpl conferenceService;

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
        doReturn(conference).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());
        ;

        assertThrows(ConferenceAlreadyExistsException.class, () -> conferenceService.addConference(conference));
    }

    @Test
    void addConference_roomNotAvailable() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 0);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 0);
        Conference conflictingConference = new Conference("conflictingConference", conferenceStartDateTime, conferenceEndDateTime);
        conflictingConference.setConferenceRoom(conference.getConferenceRoom());
        doReturn(null).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conflictingConference.getName(), conflictingConference.getStartDateTime(), conflictingConference.getEndDateTime());
        ;

        conference.getConferenceRoom().getConferences().add(conference);
        doReturn(conference.getConferenceRoom())
                .when(conferenceRoomRepository).findConferenceRoomByNameAndAndLocation(
                conflictingConference.getConferenceRoom().getName()
                , conflictingConference.getConferenceRoom().getLocation()
        );

        assertThrows(ConferenceRoomBookedException.class, () -> conferenceService.addConference(conflictingConference));
    }

    @Test
    void addConference_success() {
        // TODO: Optional.empty() instead of null?
        doReturn(null).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());
        doReturn(conference.getConferenceRoom())
                .when(conferenceRoomRepository).findConferenceRoomByNameAndAndLocation(
                conference.getConferenceRoom().getName()
                , conference.getConferenceRoom().getLocation()
        );

        assertDoesNotThrow(() -> conferenceService.addConference(conference));
    }

    @Test
    void cancelConference_conferenceDoesntExist() {
        doReturn(null).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());

        assertThrows(ConferenceNotFoundException.class, () -> conferenceService.cancelConference(conference));
    }

    @Test
    void cancelConference_success() {
        doReturn(conference).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());

        assertDoesNotThrow(() -> conferenceService.cancelConference(conference));
    }

    @Test
    void checkConferenceRoomAvailability_conferenceDoesntExist() {
        doReturn(null).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());

        assertThrows(ConferenceNotFoundException.class, () -> conferenceService.checkConferenceSeatsAvailability(conference));
    }

/*    @Test // TODO: this not necessary if UI has rooms for selection based on data fetched from DB
    void checkConferenceRoomAvailability_roomDoesntExist() {
        doReturn(conference).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());

        doReturn(null).when(conferenceRoomRepository).findConferenceRoomByNameAndAndLocation(
                conferenceRoom.getName(), conferenceRoom.getLocation());

        assertThrows(ConferenceRoomNotFoundException.class, () -> conferenceService.checkConferenceSeatsAvailability(conference));
    }*/

    @Test
    void checkConferenceRoomAvailability_success() {
        doReturn(conference).when(conferenceRepository).findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());

        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        conference.getParticipants().add(new Participant("FirstName_1 LastName_1", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_2 LastName_2", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_3 LastName_3", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_4 LastName_4", dateOfBirth));
        conference.getParticipants().add(new Participant("FirstName_5 LastName_5", dateOfBirth));

        assertDoesNotThrow(() -> conferenceService.checkConferenceSeatsAvailability(conference));
    }


}