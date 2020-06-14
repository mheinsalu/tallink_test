package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;

public class ConferenceRoomCapacityFilledException extends RuntimeException {

    ConferenceRoomCapacityFilledException(ConferenceRoom conferenceRoom) {
        super("Conference room's capacity is filled " + conferenceRoom);
    }
}
