package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.service.implementation.FrontEndServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class FrontEndController {

    // TODO: tests

    @Autowired
    FrontEndServiceImpl frontEndService;

    @GetMapping(value = "/getAllConferences")
    public ResponseEntity<List<Conference>> getAllConferences() {
        log.info("Call for FrontEndController -> getAllConferences");

        List<Conference> listOfConferences = frontEndService.getAllConferences();

        return new ResponseEntity<>(listOfConferences, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllConferenceRooms")
    public ResponseEntity<List<ConferenceRoom>> getAllConferenceRooms() {
        log.info("Call for FrontEndController -> getAllConferenceRooms");

        List<ConferenceRoom> listOfConferenceRooms = frontEndService.getAllConferenceRooms();

        return new ResponseEntity<>(listOfConferenceRooms, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllParticipants")
    public ResponseEntity<List<Participant>> getAllParticipants() {
        log.info("Call for FrontEndController -> getAllParticipants");

        List<Participant> listOfParticipants = frontEndService.getAllParticipants();

        return new ResponseEntity<>(listOfParticipants, HttpStatus.OK);
    }
}
