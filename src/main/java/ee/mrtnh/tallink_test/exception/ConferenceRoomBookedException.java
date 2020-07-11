package ee.mrtnh.tallink_test.exception;

public class ConferenceRoomBookedException extends RuntimeException {

    public ConferenceRoomBookedException(Long conferenceRoomId) {
        super("Conference room with ID " + conferenceRoomId + " is already booked in selected time period");
    }
}
