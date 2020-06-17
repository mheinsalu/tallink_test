package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConferenceService {

    public String addConference(Conference conference);

    public String cancelConference(Conference conference);

    public String checkConferenceSeatsAvailability(Conference conference);

    public Conference findConference(Conference conference);

    public ConferenceRoom findConferenceRoom(ConferenceRoom conferenceRoom);
}
