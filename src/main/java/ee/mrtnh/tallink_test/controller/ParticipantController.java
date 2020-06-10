package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.CancellationRequest;
import ee.mrtnh.tallink_test.model.CheckAvailabilityRequest;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.service.TallinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class ParticipantController {

    @PostMapping("/")
    public void addParticipant(@RequestBody @Valid Participant participant, BindingResult bindingResult) {
        log.info("Call for TallinkController -> addParticipant");
        log.info("Received message is " + participant);


//        log.info("Sending response: " + response);
//        return response;
    }

    @DeleteMapping
    public void removeParticipant(@RequestBody @Valid Participant participant, BindingResult bindingResult) {

    }

}
