package ee.mrtnh.tallink_test.service;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ConferenceRoomRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;
    private ConferenceRoomRepository conferenceRoomRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ConferenceRoomRepository conferenceRoomRepository) {
        this.conferenceRepository = conferenceRepository;
        this.conferenceRoomRepository = conferenceRoomRepository;
    }

    public String addConference(Conference conference) {

        throw new RuntimeException("Not yet implemented");
    }

    public String cancelConference(Conference conference) {

        throw new RuntimeException("Not yet implemented");
    }

    public String checkConferenceRoomAvailability(Conference conference) {

        throw new RuntimeException("Not yet implemented");
    }

    public Conference findConference(Conference conference) {
        return conferenceRepository.findConferenceByNameAndStartDateAndTimeAndEndDateAndTime(
                conference.getName(), conference.getStartDateAndTime(), conference.getEndDateAndTime());
    }

    public ConferenceRoom findConferenceRoom(ConferenceRoom conferenceRoom) {
        return conferenceRoomRepository.findConferenceRoomByNameAndAndLocation(
                conferenceRoom.getName(), conferenceRoom.getLocation());
    }

}
