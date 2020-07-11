package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.exception.ConferenceAlreadyExistsException;
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
            throw new ConferenceAlreadyExistsException();
        }
        ConferenceRoom roomFromDb = repoHelper.findConferenceRoomById(conference.getConferenceRoomId());
        if (roomFromDb.isConferenceRoomBooked(conference.getStartDateTime(), conference.getEndDateTime())) {
            log.warn("{} is already booked in time period {} - {}",
                    roomFromDb, conference.getStartDateTime(), conference.getEndDateTime());
            throw new ConferenceRoomBookedException(roomFromDb.getId());
        }

        Conference savedConference = conferenceRepository.save(conference);
        log.info("Added/saved to db conference {}", savedConference);

        return String.format("Added/saved conference with ID %s to db", savedConference.getId());
    }

    public String cancelConference(Long conferenceId) {
        log.info("Cancelling conference with ID {}", conferenceId);
        Conference savedConference = repoHelper.findConferenceById(conferenceId); // TODO: is this check needed? UI displays Conferences fetched from DB
        conferenceRepository.deleteById(savedConference.getId());

        log.info("Cancelled conference {}", savedConference);
        return String.format("Cancelled conference with ID %s", conferenceId);
    }

    public String checkConferenceSeatsAvailability(Long conferenceId) {
        log.info("Checking whether there are available seats in conference with ID {}", conferenceId);
        Integer availableSeats = repoHelper.getAvailableRoomCapacity(conferenceId);

        String returnMessage = String.format("There are %d available seats in conference with ID %s", availableSeats, conferenceId);
        log.info(returnMessage);
        return returnMessage;
    }
}
