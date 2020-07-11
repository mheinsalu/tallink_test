package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.exception.ConferenceAlreadyExistsException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomBookedException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.util.RepoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ConferenceServiceImplTest {

    @Mock
    private ConferenceRepository conferenceRepository;

    @Mock
    private RepoHelper repoHelper;

    @InjectMocks
    private ConferenceServiceImpl conferenceService;

    private Conference conference;
    private ConferenceRoom conferenceRoom;

    @BeforeEach
    void setUp() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        conference.setConferenceRoomId(1L);

        Integer maxCapacity = 5;
        conferenceRoom = new ConferenceRoom("testRoomName", "testRoomLocation", maxCapacity);
        conferenceRoom.setId(1L);
    }

    @Test
    void addConference_alreadyExists() {
        doReturn(conference).when(repoHelper).findConference(conference);

        assertThrows(ConferenceAlreadyExistsException.class, () -> conferenceService.addConference(conference));
    }

    @Test
    void addConference_roomNotAvailable() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 0);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 0);
        Conference conflictingConference = new Conference("conflictingConference", conferenceStartDateTime, conferenceEndDateTime);
        conflictingConference.setConferenceRoomId(conference.getConferenceRoomId());
        conferenceRoom.getConferences().add(conflictingConference);

        doReturn(null).when(repoHelper).findConference(conference);
        doReturn(conferenceRoom).when(repoHelper).findConferenceRoomById(conference.getConferenceRoomId());

        assertThrows(ConferenceRoomBookedException.class, () -> conferenceService.addConference(conference));
    }

    @Test
    void addConference_success() {
        doReturn(null).when(repoHelper).findConference(conference);
        doReturn(conferenceRoom).when(repoHelper).findConferenceRoomById(conference.getConferenceRoomId());
        doReturn(conference).when(conferenceRepository).save(conference);

        assertDoesNotThrow(() -> conferenceService.addConference(conference));
    }

    @Test
    void cancelConference_conferenceDoesntExist() {
        doThrow(ConferenceNotFoundException.class).when(repoHelper).findConferenceById(conference.getId());

        assertThrows(ConferenceNotFoundException.class, () -> conferenceService.cancelConference(conference.getId()));
    }

    @Test
    void cancelConference_success() {
        doReturn(conference).when(repoHelper).findConferenceById(conference.getId());

        assertDoesNotThrow(() -> conferenceService.cancelConference(conference.getId()));
    }

    @Test
    void checkConferenceRoomAvailability_success() {
        doReturn(1).when(repoHelper).getAvailableRoomCapacity(conference.getId());

        assertDoesNotThrow(() -> conferenceService.checkConferenceSeatsAvailability(conference.getId()));
    }
}