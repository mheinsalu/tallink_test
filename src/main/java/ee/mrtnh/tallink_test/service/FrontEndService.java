package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.model.Participant;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FrontEndService {

    List<Conference> getAllConferences();

    List<ConferenceRoom> getAllConferenceRooms();

    List<Participant> getAllParticipants();

    Conference getConferenceById(String conferenceId);
}
