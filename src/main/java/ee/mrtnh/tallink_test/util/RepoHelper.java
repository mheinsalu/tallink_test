package ee.mrtnh.tallink_test.util;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ConferenceRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RepoHelper {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    public Conference findConference(Conference conference) {
        log.debug("Finding conference {}", conference);
        return conferenceRepository.findConferenceByNameAndStartDateTimeAndEndDateTime(
                conference.getName(), conference.getStartDateTime(), conference.getEndDateTime());
    }

    public ConferenceRoom findConferenceRoom(ConferenceRoom conferenceRoom) {
        log.debug("Finding conference room {}", conferenceRoom);
        return conferenceRoomRepository.findConferenceRoomByNameAndAndLocation(
                conferenceRoom.getName(), conferenceRoom.getLocation());
    }

/*    public boolean isConferenceRoomBooked(ConferenceRoom conferenceRoom, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Conference> conferences = conferenceRepository.findAllByConferenceRoom_Id(conferenceRoom.getId());
        return conferences.stream().anyMatch(conference -> {
            if (startDateTime.isAfter(conference.getStartDateTime()) && startDateTime.isBefore(conference.getEndDateTime())) {
                return true;
            } else if (endDateTime.isAfter(conference.getStartDateTime()) && endDateTime.isBefore(conference.getEndDateTime())) {
                return true;
            } else
                return startDateTime.isEqual(conference.getStartDateTime()) || endDateTime.isEqual(conference.getEndDateTime());
        });
    }*/
}
