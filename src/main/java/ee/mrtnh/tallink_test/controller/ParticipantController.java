package ee.mrtnh.tallink_test.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ee.mrtnh.tallink_test.model.ConferenceAndParticipantWrapper;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.service.implementation.ParticipantServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class ParticipantController {

    @Autowired
    ParticipantServiceImpl participantService;

    @PostMapping(value = "/addParticipantToConference", consumes = "application/json")
    public ResponseEntity<String> addParticipantToConference(@RequestBody @Valid ConferenceAndParticipantWrapper request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Input constraint violation {}", bindingResult.getAllErrors());
            String errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Long conferenceId = request.getConferenceId();
        Participant participant = request.getParticipant();
        log.info("Call for ParticipantController -> addParticipantToConference. Message is {}, {}", participant, conferenceId);

        String resultMessage = participantService.addParticipantToConference(participant, conferenceId);
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeParticipantFromConference", consumes = "application/json")
    public ResponseEntity<String> removeParticipantFromConference(@RequestBody @Valid ConferenceAndParticipantWrapper request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Input constraint violation {}", bindingResult.getAllErrors());
            String errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Long conferenceId = request.getConferenceId();
        Participant participant = request.getParticipant();
        log.info("Call for ParticipantController -> removeParticipantFromConference. Message is {}, {}", participant, conferenceId);

        String resultMessage = participantService.removeParticipantFromConference(participant.getId(), conferenceId);
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }

}
