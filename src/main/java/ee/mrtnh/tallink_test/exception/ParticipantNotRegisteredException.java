package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;

public class ParticipantNotRegisteredException extends RuntimeException {

    ParticipantNotRegisteredException(Participant participant, Conference conference) {
        super("Participant " + participant + " is not registered to conference " + conference);
    }
}
