package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Conference;

public class ConferenceAlreadyExistsException extends RuntimeException {

    ConferenceAlreadyExistsException(Conference conference) {
        super("Conference " + conference + " already exists");
    }
}
