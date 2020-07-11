package ee.mrtnh.tallink_test.exception;

public class ConferenceAlreadyExistsException extends RuntimeException {

    public ConferenceAlreadyExistsException() {
        super("Conference already exists");

    }
}
