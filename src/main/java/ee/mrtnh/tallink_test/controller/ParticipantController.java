package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.service.ParticipantServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class ParticipantController {

    @Autowired
    ParticipantServiceImpl participantService;

    @PostMapping("/addParticipantToConference")
    public void addParticipantToConference(@RequestBody @Valid Participant participant, @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for TallinkController -> addParticipant");
        log.info("Received message is " + participant);

        String resultMessage = participantService.addParticipantToConference(participant, conference);
//        log.info("Sending response: " + response);
//        return response;
    }

    @DeleteMapping("/removeParticipantFromConference")
    public void removeParticipantFromConference(@RequestBody @Valid Participant participant, @Valid Conference conference, BindingResult bindingResult) {

        String resultMessage = participantService.removeParticipantFromConference(participant, conference);

    }

}
