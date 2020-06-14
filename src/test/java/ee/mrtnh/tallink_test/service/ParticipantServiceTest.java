package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomCapacityFilledException;
import ee.mrtnh.tallink_test.exception.ParticipantAlreadyRegisteredException;
import ee.mrtnh.tallink_test.exception.ParticipantNotRegisteredException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class ParticipantServiceTest {

    @Autowired
    ParticipantService participantService;

    @Autowired
    ConferenceService conferenceService;

    @Mock
    ParticipantRepository participantRepository;

    @Mock
    ConferenceRepository conferenceRepository;

    Conference conference;
    Participant participant;

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        participant = new Participant("FirstName LastName", dateOfBirth);
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
    }

   /* @Test
    void findConference_success() {
        LocalDateTime conferenceDateTime = LocalDateTime.of(2020, 06, 20, 10, 15);
        Conference conference = new Conference("conferenceName", conferenceDateTime);
    }

    @Test
    void findConference_notFound() {
        LocalDateTime conferenceDateTime = LocalDateTime.of(2020, 06, 20, 10, 15);
        Conference conference = new Conference("conferenceName", conferenceDateTime);
    }*/

    @Test
    void addParticipantToConference_conferenceDoesntExist() {
        doReturn(Optional.empty()).when(conferenceService).findConference(conference);

        assertThrows(ConferenceNotFoundException.class, () -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void addParticipantToConference_noAvailableSeats() {
        doReturn(conference).when(conferenceService).findConference(conference);
        ConferenceRoom conferenceRoom = new ConferenceRoom("testRoomName", "testRoomLocation", 0);
        conference.setConferenceRoom(conferenceRoom);

        assertThrows(ConferenceRoomCapacityFilledException.class, () -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void addParticipantToConference_participantAlreadyRegistered() {
        doReturn(conference).when(conferenceService).findConference(conference);
        conference.addParticipant(participant);

        assertThrows(ParticipantAlreadyRegisteredException.class, () -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void addParticipantToConference_success() {
        doReturn(conference).when(conferenceService).findConference(conference);

        assertDoesNotThrow(() -> participantService.addParticipantToConference(participant, conference));
    }

    @Test
    void removeParticipantFromConference_conferenceDoesntExist() {
        doReturn(Optional.empty()).when(conferenceService).findConference(conference);

        assertThrows(ConferenceNotFoundException.class, () -> participantService.removeParticipantFromConference(participant, conference));
    }

    @Test
    void removeParticipantFromConference_participantNotRegistered() {
        doReturn(conference).when(conferenceService).findConference(conference);

        assertThrows(ParticipantNotRegisteredException.class, () -> participantService.removeParticipantFromConference(participant, conference));
    }

    @Test
    void removeParticipantFromConference_success() {
        doReturn(conference).when(conferenceService).findConference(conference);
        conference.addParticipant(participant);

        assertDoesNotThrow(() -> participantService.removeParticipantFromConference(participant, conference));
    }
}