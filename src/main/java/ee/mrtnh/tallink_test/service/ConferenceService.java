package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Conference;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConferenceService {

    String addConference(Conference conference);

    String cancelConference(Long conferenceId);

    String checkConferenceSeatsAvailability(Long conferenceId);

}
