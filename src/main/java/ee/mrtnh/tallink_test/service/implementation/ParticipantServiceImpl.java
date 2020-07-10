package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.exception.ConferenceCapacityFilledException;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    RepoHelper repoHelper;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ParticipantRepository participantRepository;

    public String addParticipantToConference(Participant participant, Long conferenceId) {
        Conference conferenceFromDb = repoHelper.findConferenceById(conferenceId);
        if (repoHelper.getAvailableRoomCapacity(conferenceFromDb.getId()) == 0) {
            log.warn("{} has no available seats", conferenceFromDb);
            throw new ConferenceCapacityFilledException(conferenceId);
        }
        if (conferenceFromDb.addParticipant(participant)) {
            participantRepository.save(participant);
            conferenceRepository.save(conferenceFromDb);
            String message = String.format("%s added to %s", participant, conferenceFromDb);
            log.info(message);
            return message;
        }
        log.warn("{} is already registered to {}", participant, conferenceFromDb);
        throw new ParticipantAlreadyRegisteredException(participant, conferenceId);
    }

    public String removeParticipantFromConference(Long participantId, Long conferenceId) {
        Conference conferenceFromDb = repoHelper.findConferenceById(conferenceId);

        if (conferenceFromDb.removeParticipantById(participantId)) {
            String message = String.format("Removed Participant with ID %s from %s", participantId, conferenceFromDb);
            log.info(message);
            return message;
        }
        log.warn("Unable to remove Participant with ID {} from {}. Participant is not registered", participantId, conferenceFromDb);
        throw new ParticipantNotRegisteredException(participantId, conferenceId);
    }
}
