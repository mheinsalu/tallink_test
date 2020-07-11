package ee.mrtnh.tallink_test.exception;

import ee.mrtnh.tallink_test.model.Participant;

public class ParticipantAlreadyRegisteredException extends RuntimeException {

    public ParticipantAlreadyRegisteredException(Participant participant, Long conferenceId) {
        super("Participant " + participant + " is already registered to conference with ID " + conferenceId);
    }
}
