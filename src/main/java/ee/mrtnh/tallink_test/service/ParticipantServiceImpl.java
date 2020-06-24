package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.exception.ConferenceCapacityFilledException;
import ee.mrtnh.tallink_test.exception.ConferenceNotFoundException;
import ee.mrtnh.tallink_test.exception.ParticipantAlreadyRegisteredException;
import ee.mrtnh.tallink_test.exception.ParticipantNotRegisteredException;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ParticipantRepository;
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

        Conference conferenceFromDb = repoHelper.findConference(conference);// TODO: separate to private method getExistingConference? maybe in repoUtil
        if (conferenceFromDb == null) {
            log.warn("{} not found", conference);
            throw new ConferenceNotFoundException(conference);
        }
        if (conferenceFromDb.getAvailableRoomCapacity() == 0) { // TODO: separate to private method checkCapacity? maybe in repoUtil
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
        Conference conferenceFromDb = repoHelper.findConference(conference);
        if (conferenceFromDb == null) {
            log.warn("{} not found", conference);
            throw new ConferenceNotFoundException(conference);
        }
        if (conferenceFromDb.removeParticipant(participant)) {
            // TODO: /note: should Participant be removed from Db? In that case should check If registered to other confs
            String message = String.format("Removed %s from %s", participant, conferenceFromDb);
            log.info(message);
            return message;
        }
        log.warn("Unable to remove {} from {}. Participant is not registered", participant, conferenceFromDb);
        throw new ParticipantNotRegisteredException(participant, conferenceFromDb);
    }
}
