package ee.mrtnh.tallink_test.controller.advice;

import ee.mrtnh.tallink_test.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class CustomAdvice {

    @ResponseBody
    @ExceptionHandler(ConferenceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String conferenceNotFoundHandler(ConferenceNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConferenceCapacityFilledException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ConferenceRoomCapacityFilledHandler(ConferenceCapacityFilledException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ParticipantAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ParticipantAlreadyRegisteredHandler(ParticipantAlreadyRegisteredException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ParticipantNotRegisteredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ParticipantNotRegisteredHandler(ParticipantNotRegisteredException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConferenceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ConferenceAlreadyExistsHandler(ConferenceAlreadyExistsException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConferenceRoomBookedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ConferenceRoomBookedHandler(ConferenceRoomBookedException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConferenceRoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ConferenceRoomNotFoundHandler(ConferenceRoomNotFoundException e) {
        return e.getMessage();
    }

}
