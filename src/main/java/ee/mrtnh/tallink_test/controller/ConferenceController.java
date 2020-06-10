package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.CancellationRequest;
import ee.mrtnh.tallink_test.model.CheckAvailabilityRequest;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.service.TallinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class ConferenceController {

    @Autowired
    TallinkService tallinkService;

    @PostMapping("/")
    public void addConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for TallinkController -> addConference");
        log.info("Received message is " + conference);


//        log.info("Sending response: " + response);
//        return response;
    }

    @DeleteMapping
    public void cancelConference(@RequestBody @Valid CancellationRequest cancellationRequest, BindingResult bindingResult) {

    }

    @GetMapping
    public void checkConferenceRoomAvailability(@RequestBody @Valid CheckAvailabilityRequest checkAvailabilityRequest, BindingResult bindingResult) {

    }
}
