package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.ConferenceRoom;

public class ConferenceRoomBookedException extends RuntimeException {

    ConferenceRoomBookedException(ConferenceRoom conferenceRoom) {
        super("Conference room " + conferenceRoom + " is already booked");
    }
}
