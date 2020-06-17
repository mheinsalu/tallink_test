package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Conference;

public class ConferenceAlreadyExistsException extends RuntimeException {
    // TODO: google if these are necessary. RESTful services
    public ConferenceAlreadyExistsException(Conference conference) {
        super(conference + " already exists");
    }
}
