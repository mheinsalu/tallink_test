package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Conference;

public class ConferenceCapacityFilledException extends RuntimeException {

    public ConferenceCapacityFilledException(Conference conference) {
        super("Conference's capacity is filled " + conference);
    }
}
