package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Participant;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ParticipantService {

    String addParticipantToConference(Participant participant, Long conferenceId);

    String removeParticipantFromConference(Long participantId, Long conferenceId);
}
