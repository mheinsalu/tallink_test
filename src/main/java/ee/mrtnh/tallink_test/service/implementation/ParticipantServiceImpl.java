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
            log.info("{} added to {}", participant, conferenceFromDb);
            return String.format("Participant with ID %s added to conference with ID %s", participant.getId(), conferenceFromDb.getId());
        }
        log.warn("Participant with ID {} is already registered to Conference with ID {}", participant.getId(), conferenceFromDb.getId());
        throw new ParticipantAlreadyRegisteredException(participant, conferenceId);
    }

    public String removeParticipantFromConference(Long participantId, Long conferenceId) {
        Conference conferenceFromDb = repoHelper.findConferenceById(conferenceId);

        if (conferenceFromDb.removeParticipantById(participantId)) {
            String message = String.format("Removed Participant with ID %s from conference with ID %s", participantId, conferenceFromDb.getId());
            log.info(message);
            return message;
        }
        log.warn("Unable to remove Participant with ID {} from conference with ID {}. Participant is not registered", participantId, conferenceFromDb.getId());
        throw new ParticipantNotRegisteredException(participantId, conferenceId);
    }
}
