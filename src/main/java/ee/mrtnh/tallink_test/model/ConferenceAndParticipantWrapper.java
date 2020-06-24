package ee.mrtnh.tallink_test.model;

import lombok.Getter;

import javax.validation.Valid;

@Getter
public class ConferenceAndParticipantWrapper {

    @Valid
    private Conference conference;

    @Valid
    private Participant participant;

}
