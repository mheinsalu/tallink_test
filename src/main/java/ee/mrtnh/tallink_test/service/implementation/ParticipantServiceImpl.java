package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.exception.ConferenceCapacityFilledException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ParticipantAlreadyRegisteredException;
import ee.mrtnh.tallink_test.exception.ParticipantNotRegisteredException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ParticipantRepository;
import ee.mrtnh.tallink_test.service.ParticipantService;
import ee.mrtnh.tallink_test.util.RepoHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    RepoHelper repoHelper;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ParticipantRepository participantRepository;

    public String addParticipantToConference(Participant participant, Conference conference) {

        Conference conferenceFromDb = findExistingConference(conference);
        if (conferenceFromDb.getAvailableRoomCapacity() == 0) {
            log.warn("{} has no available seats", conferenceFromDb);
            throw new ConferenceCapacityFilledException(conferenceFromDb);
        }
        if (conferenceFromDb.addParticipant(participant)) {
            participantRepository.save(participant);
            conferenceRepository.save(conferenceFromDb);
            String message = String.format("%s added to %s", participant, conferenceFromDb);
            log.info(message);
            return message;
        }
        log.warn("{} is already registered to {}", participant, conferenceFromDb);
        throw new ParticipantAlreadyRegisteredException(participant, conferenceFromDb);
    }

    public String removeParticipantFromConference(Participant participant, Conference conference) {
        Conference conferenceFromDb = findExistingConference(conference);
        if (conferenceFromDb.removeParticipant(participant)) {
            String message = String.format("Removed %s from %s", participant, conferenceFromDb);
            log.info(message);
            return message;
        }
        log.warn("Unable to remove {} from {}. Participant is not registered", participant, conferenceFromDb);
        throw new ParticipantNotRegisteredException(participant, conferenceFromDb);
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
