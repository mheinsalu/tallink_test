package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.exception.ConferenceAlreadyExistsException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomBookedException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.service.ConferenceService;
import ee.mrtnh.tallink_test.util.RepoHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    RepoHelper repoHelper;

    public String addConference(Conference conference) {
        log.info("Adding conference {}", conference);
        if (repoHelper.findConference(conference) != null) {
            log.warn("{} already exists", conference);
            throw new ConferenceAlreadyExistsException(conference);
        }
        ConferenceRoom roomFromDb = repoHelper.findConferenceRoom(conference.getConferenceRoom());
        if (roomFromDb.isConferenceRoomBooked(conference.getStartDateTime(), conference.getEndDateTime())) {
            log.warn("{} is already booked in time period {} - {}",
                    roomFromDb, conference.getStartDateTime(), conference.getEndDateTime());
            throw new ConferenceRoomBookedException(roomFromDb);
        }
        conference.setConferenceRoom(roomFromDb);

        Conference savedConference = conferenceRepository.save(conference);
        String returnMessage = String.format("Added/saved to db conference %s", savedConference);
        log.info(returnMessage);

        return returnMessage;
    }

    public String cancelConference(Conference conference) {
        log.info("Cancelling conference {}", conference);
        Conference savedConference = findExistingConference(conference);
        conferenceRepository.deleteById(savedConference.getId());
        String returnMessage = String.format("Cancelled conference %s", conference);
        log.info(returnMessage);
        return returnMessage;
    }

    public String checkConferenceSeatsAvailability(Conference conference) {
        log.info("Checking whether there are available seats in conference {}", conference);
        Conference savedConference = findExistingConference(conference);
        Integer availableSeats = savedConference.getAvailableRoomCapacity();

        String returnMessage = String.format("There are %d available seats in conference %s", availableSeats, conference);
        log.info(returnMessage);
        return returnMessage;
    }

    private Conference findExistingConference(Conference conference) {
        Conference savedConference = repoHelper.findConference(conference);
        if (savedConference == null) {
            log.warn("{} doesn't exist", conference);
            throw new ConferenceNotFoundException(conference);
        }
        return savedConference;
    }

}
