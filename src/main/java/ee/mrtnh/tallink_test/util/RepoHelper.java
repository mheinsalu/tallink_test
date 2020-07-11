package ee.mrtnh.tallink_test.util;

import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomNotFoundException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ConferenceRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class RepoHelper {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    public Conference findConference(Conference conference) {
        log.debug("Finding conference {}", conference);
        return conferenceRepository.findConferenceByNameAndStartDateTimeAndEndDateTimeAndConferenceRoomId(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime(), conference.getConferenceRoomId());
    }

    public Conference findConferenceById(Long conferenceId) {
        Optional<Conference> savedConference = conferenceRepository.findById(conferenceId);
        if (savedConference.isEmpty()) {
            log.warn("Conference with ID {} doesn't exist", conferenceId);
            throw new ConferenceNotFoundException(conferenceId);
        }
        log.info("Found Conference {}", savedConference);
        return savedConference.get();
    }

    public ConferenceRoom findConferenceRoomById(Long conferenceRoomId) {
        Optional<ConferenceRoom> roomFromDb = conferenceRoomRepository.findById(conferenceRoomId);
        if (roomFromDb.isEmpty()) {
            log.warn("Conference Room with ID {} doesn't exist", conferenceRoomId);
            throw new ConferenceRoomNotFoundException(conferenceRoomId);
        }
        log.info("Found Conference Room {}", roomFromDb);
        return roomFromDb.get();
    }

    public Integer getAvailableRoomCapacity(Long conferenceId) {
        Conference conference = findConferenceById(conferenceId);
        ConferenceRoom conferenceRoom = findConferenceRoomById(conference.getConferenceRoomId());
        return conferenceRoom.getMaxSeats() - conference.getParticipants().size();
    }
}
