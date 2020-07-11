package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ConferenceRoomRepository;
import ee.mrtnh.tallink_test.repo.ParticipantRepository;
import ee.mrtnh.tallink_test.util.RepoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class FrontEndServiceImplTest {

    @Mock
    ConferenceRepository conferenceRepository;

    @Mock
    ConferenceRoomRepository conferenceRoomRepository;

    @Mock
    ParticipantRepository participantRepository;

    @Mock
    RepoHelper repoHelper;

    @InjectMocks
    FrontEndServiceImpl frontEndService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllConferences_success() {
        doReturn(Collections.EMPTY_LIST).when(conferenceRepository).findAll();

        assertDoesNotThrow(() -> frontEndService.getAllConferences());
    }

    @Test
    void getConferenceById_invalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> frontEndService.getConferenceById("A"));
    }

    @Test
    void getConferenceById_success() {
        assertDoesNotThrow(() -> frontEndService.getConferenceById("1"));
    }

    @Test
    void getAllConferenceRooms_success() {
        doReturn(Collections.EMPTY_LIST).when(conferenceRoomRepository).findAll();

        assertDoesNotThrow(() -> frontEndService.getAllConferenceRooms());
    }

    @Test
    void getAllParticipants_success() {
        doReturn(Collections.EMPTY_LIST).when(participantRepository).findAll();

        assertDoesNotThrow(() -> frontEndService.getAllParticipants());
    }
}