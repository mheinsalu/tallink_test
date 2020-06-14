package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.ConferenceRoom;

public class ConferenceRoomNotFoundException extends RuntimeException {

    ConferenceRoomNotFoundException(ConferenceRoom conferenceRoom) {
        super("Could not find conference room " + conferenceRoom);
    }
}
