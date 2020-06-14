package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
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
    public void addConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        log.info("Call for TallinkController -> addConference");
        log.info("Received message is " + conference);
        // TODO: bindingResult -> error Message ->(?) alert
        String resultMessage = conferenceService.addConference(conference);

//        log.info("Sending response: " + response);
//        return response;
    }

    @DeleteMapping("/cancelConference")
    public void cancelConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        String resultMessage = conferenceService.cancelConference(conference);
    }

    @GetMapping("/checkConferenceRoomAvailability")
    public void checkConferenceRoomAvailability(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
        String resultMessage = conferenceService.checkConferenceRoomAvailability(conference);
    }
}
