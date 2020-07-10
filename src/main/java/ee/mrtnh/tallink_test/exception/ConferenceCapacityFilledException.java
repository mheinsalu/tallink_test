package ee.mrtnh.tallink_test.exception;

public class ConferenceCapacityFilledException extends RuntimeException {

    public ConferenceCapacityFilledException(Long conferenceId) {
        super("Capacity is filled of conference with ID " + conferenceId);
    }
}
