package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ParticipantService {

    String addParticipantToConference(Participant participant, Conference conference);

    String removeParticipantFromConference(Participant participant, Conference conference);
}
