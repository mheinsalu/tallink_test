package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Conference;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConferenceService {

    public String addConference(Conference conference);

    public String cancelConference(Conference conference);

    public String checkConferenceSeatsAvailability(Conference conference);

}
