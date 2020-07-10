package ee.mrtnh.tallink_test.exception;

public class ParticipantNotRegisteredException extends RuntimeException {

    public ParticipantNotRegisteredException(Long participantId, Long conferenceId) {
        super("Participant with ID " + participantId + " is not registered to conference with ID " + conferenceId);
    }
}
