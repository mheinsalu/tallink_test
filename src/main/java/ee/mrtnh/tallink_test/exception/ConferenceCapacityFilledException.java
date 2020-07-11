package ee.mrtnh.tallink_test.exception;

public class ConferenceCapacityFilledException extends RuntimeException {

    public ConferenceCapacityFilledException(Long conferenceId) {
        super("There are no more available seats in conference with ID " + conferenceId);
    }
}
