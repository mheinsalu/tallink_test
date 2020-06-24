package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.exception.ConferenceAlreadyExistsException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ConferenceRoomBookedException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.util.RepoHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
        // TODO: I don't like this. Since UI has selectable table of rooms that are fetched from db,
        //  I want to be able to send room ID, save Conference to db nad have Conference.conferenceRoom populated on repo.find

        Conference savedConference = conferenceRepository.save(conference);
        String returnMessage = String.format("Added/saved to db conference %s", savedConference);
        log.info(returnMessage);
        return returnMessage;
    }

    public String cancelConference(Conference conference) {
        log.info("Cancelling conference {}", conference);
        Conference savedConference = repoHelper.findConference(conference);
        if (savedConference == null) {
            log.warn("{} doesn't exist", conference);
            throw new ConferenceNotFoundException(conference);
        }
        conferenceRepository.deleteById(savedConference.getId());
        String returnMessage = String.format("Cancelled conference %s", conference);
        log.info(returnMessage);
        return returnMessage;
    }

    public String checkConferenceSeatsAvailability(Conference conference) {
        log.info("Checking whether there are available seats in conference {}", conference);
        Conference savedConference = repoHelper.findConference(conference); // TODO: duplicate code? refactor
        if (savedConference == null) {
            log.warn("{} doesn't exist", conference);
            throw new ConferenceNotFoundException(conference);
        }
        Integer availableSeats = savedConference.getAvailableRoomCapacity();

        String returnMessage = String.format("There are %d available seats in conference %s", availableSeats, conference);
        log.info(returnMessage);
        return returnMessage;
    }

}
