package ee.mrtnh.tallink_test.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.service.implementation.ConferenceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class ConferenceController {

    @Autowired
    ConferenceServiceImpl conferenceService;

    @PostMapping(value = "/addConference", consumes = "application/json")
    public ResponseEntity<String> addConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Input constraint violation {}", bindingResult.getAllErrors());
            String errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        log.info("Call for ConferenceController -> addConference. Received message is {}", conference);
        String resultMessage = conferenceService.addConference(conference);

        log.info("Sending response: " + resultMessage);
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cancelConference", consumes = "application/json")
    public ResponseEntity<String> cancelConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Input constraint violation {}", bindingResult.getAllErrors());
            String errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        log.info("Call for ConferenceController -> addConference. Received message is {}", conference);
        String resultMessage = conferenceService.cancelConference(conference);

        log.info("Sending response: " + resultMessage);
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }

    @GetMapping(value = "/checkConferenceSeatsAvailability", consumes = "application/json")
    public ResponseEntity<String> checkConferenceSeatsAvailability(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Input constraint violation {}", bindingResult.getAllErrors());
            String errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        log.info("Call for ConferenceController -> checkConferenceSeatsAvailability. Received message is {}", conference);
        String resultMessage = conferenceService.checkConferenceSeatsAvailability(conference);

        log.info("Sending response: " + resultMessage);
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }
}
