package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;

public class ParticipantAlreadyRegisteredException extends RuntimeException {

    ParticipantAlreadyRegisteredException(Participant participant,Conference conference) {
        super("Participant " + participant + " is already registered to conference " + conference);
    }
}
