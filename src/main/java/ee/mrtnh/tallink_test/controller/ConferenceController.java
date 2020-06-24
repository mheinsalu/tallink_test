package ee.mrtnh.tallink_test.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.service.ConferenceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class ConferenceController {

    // TODO: controller tests

    @Autowired
    ConferenceServiceImpl conferenceService;

    @PostMapping(value = "/addConference", consumes = "application/json")
    public String addConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for ConferenceController -> addConference. Received message is {}", conference);
        // TODO: bindingResult -> error Message ->(?) alert
        String resultMessage = conferenceService.addConference(conference);

        log.info("Sending response: " + resultMessage);
        return resultMessage;
    }

    @DeleteMapping(value = "/cancelConference", consumes = "application/json")
    public String cancelConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for ConferenceController -> addConference. Received message is {}", conference);
        String resultMessage = conferenceService.cancelConference(conference);

        log.info("Sending response: " + resultMessage);
        return resultMessage;
    }

    @GetMapping(value = "/checkConferenceSeatsAvailability", consumes = "application/json")
    public String checkConferenceSeatsAvailability(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for ConferenceController -> checkConferenceSeatsAvailability. Received message is {}", conference);
        String resultMessage = conferenceService.checkConferenceSeatsAvailability(conference);

        log.info("Sending response: " + resultMessage);
        return resultMessage;
    }
}
