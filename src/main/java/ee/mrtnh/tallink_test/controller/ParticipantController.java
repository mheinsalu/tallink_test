package ee.mrtnh.tallink_test.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceAndParticipantWrapper;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.service.ParticipantServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class ParticipantController {

    @Autowired
    ParticipantServiceImpl participantService;

    // TODO: error alert/page: for unexpected such as     "status": 400,"error": "Bad Request",

    @PostMapping(value = "/addParticipantToConference", consumes = "application/json")
    public String addParticipantToConference(@RequestBody @Valid ConferenceAndParticipantWrapper request, BindingResult bindingResult) {
        Conference conference = request.getConference();
        Participant participant = request.getParticipant();
        log.info("Call for ParticipantController -> addParticipantToConference. Message is {}, {}", participant, conference);

        String resultMessage = participantService.addParticipantToConference(participant, conference);
        return resultMessage;
    }

    @DeleteMapping(value = "/removeParticipantFromConference", consumes = "application/json")
    public String removeParticipantFromConference(@RequestBody @Valid ConferenceAndParticipantWrapper request, BindingResult bindingResult) {
        // TODO: @Valid doesn't seem to work
        Conference conference = request.getConference();
        Participant participant = request.getParticipant();
        log.info("Call for ParticipantController -> removeParticipantFromConference. Message is {}, {}", participant, conference);

        if (bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors()");
            return "bindingResult.hasErrors()";
        }

        String resultMessage = participantService.removeParticipantFromConference(participant, conference);
        return resultMessage;
    }

}
