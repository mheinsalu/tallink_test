package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ParticipantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private ParticipantRepository participantRepository;
    private ConferenceRepository conferenceRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, ConferenceRepository conferenceRepository) {
        this.participantRepository = participantRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public String addParticipantToConference(Participant participant, Conference conference) {
        // TODO: update Conference in DB after success? Add participant to DB?
        throw new RuntimeException("Not yet implemented");
    }

    public String removeParticipantFromConference(Participant participant, Conference conference) {

        throw new RuntimeException("Not yet implemented");
    }
}
