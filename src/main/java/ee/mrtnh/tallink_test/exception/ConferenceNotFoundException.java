package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Conference;

public class ConferenceNotFoundException extends RuntimeException {

    ConferenceNotFoundException(Conference conference) {
        super("Could not find conference " + conference);
    }
}
