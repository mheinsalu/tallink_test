package ee.mrtnh.tallink_test.util;

import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomNotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RepoHelperTest {

    @Mock
    private ConferenceRepository conferenceRepository;

    @Mock
    private ConferenceRoomRepository conferenceRoomRepository;

    @InjectMocks
    private RepoHelper repoHelper;

    private Conference conference;
    private ConferenceRoom conferenceRoom;

    @BeforeEach
    void setUp() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        conference.setId(1L);
        conference.setConferenceRoomId(1L);

        Integer maxCapacity = 5;
        conferenceRoom = new ConferenceRoom("testRoomName", "testRoomLocation", maxCapacity);
        conferenceRoom.setId(1L);
    }

    @Test
    void findConference_notFound() {
        doReturn(null).when(conferenceRepository)
                .findConferenceByNameAndStartDateTimeAndEndDateTimeAndConferenceRoomId(
                        conference.getName()
                        , conference.getStartDateTime()
                        , conference.getEndDateTime()
                        , conference.getConferenceRoomId()
                );

        assertDoesNotThrow(() -> repoHelper.findConference(conference));
    }

    @Test
    void findConference_success() {
        doReturn(conference).when(conferenceRepository)
                .findConferenceByNameAndStartDateTimeAndEndDateTimeAndConferenceRoomId(
                        conference.getName()
                        , conference.getStartDateTime()
                        , conference.getEndDateTime()
                        , conference.getConferenceRoomId()
                );

        assertDoesNotThrow(() -> repoHelper.findConference(conference));
    }

    @Test
    void findConferenceById_notFound() {
        doReturn(Optional.empty()).when(conferenceRepository).findById(conference.getId());

        assertThrows(ConferenceNotFoundException.class, () -> repoHelper.findConferenceById(conference.getId()));
    }

    @Test
    void findConferenceById_success() {
        doReturn(Optional.of(conference)).when(conferenceRepository).findById(conference.getId());

        assertDoesNotThrow(() -> repoHelper.findConferenceById(conference.getId()));
    }

    @Test
    void findConferenceRoomById_notFound() {
        doReturn(Optional.empty()).when(conferenceRoomRepository).findById(conference.getConferenceRoomId());

        assertThrows(ConferenceRoomNotFoundException.class, () -> repoHelper.findConferenceRoomById(conference.getId()));
    }

    @Test
    void findConferenceRoomById_success() {
        doReturn(Optional.of(conferenceRoom)).when(conferenceRoomRepository).findById(conference.getConferenceRoomId());

        assertDoesNotThrow(() -> repoHelper.findConferenceRoomById(conference.getConferenceRoomId()));
    }

    @Test
    void getAvailableRoomCapacity_zero() {
        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        Participant participant = new Participant("FirstName LastName", dateOfBirth);
        participant.setId(1);

        conferenceRoom.setMaxSeats(1);
        conference.getParticipants().add(participant);

        doReturn(Optional.of(conference)).when(conferenceRepository).findById(conference.getId());
        doReturn(Optional.of(conferenceRoom)).when(conferenceRoomRepository).findById(conference.getConferenceRoomId());

        assertEquals(0, (int) repoHelper.getAvailableRoomCapacity(conference.getId()));
    }

    @Test
    void getAvailableRoomCapacity_available() {
        doReturn(Optional.of(conference)).when(conferenceRepository).findById(conference.getId());
        doReturn(Optional.of(conferenceRoom)).when(conferenceRoomRepository).findById(conference.getConferenceRoomId());

        assertTrue(repoHelper.getAvailableRoomCapacity(conference.getId()) > 0);
    }
}