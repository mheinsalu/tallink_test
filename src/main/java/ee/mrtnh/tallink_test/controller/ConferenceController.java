package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.service.ConferenceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class ConferenceController {

    @Autowired
    ConferenceServiceImpl conferenceService;

    @PostMapping("/addConference")
    public String addConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for TallinkController -> addConference. Received message is {}", conference);
        // TODO: bindingResult -> error Message ->(?) alert
        String resultMessage = conferenceService.addConference(conference);

        log.info("Sending response: " + resultMessage);
        return resultMessage;
    }

    @DeleteMapping("/cancelConference")
    public String cancelConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for TallinkController -> addConference. Received message is {}", conference);
        String resultMessage = conferenceService.cancelConference(conference);

        log.info("Sending response: " + resultMessage);
        return resultMessage;
    }

    @GetMapping("/checkConferenceSeatsAvailability")
    public String checkConferenceSeatsAvailability(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for TallinkController -> checkConferenceSeatsAvailability. Received message is {}", conference);
        String resultMessage = conferenceService.checkConferenceSeatsAvailability(conference);

        log.info("Sending response: " + resultMessage);
        return resultMessage;
    }
}
