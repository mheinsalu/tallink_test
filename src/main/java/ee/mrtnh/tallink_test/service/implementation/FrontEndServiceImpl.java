package ee.mrtnh.tallink_test.service.implementation;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.repo.ConferenceRepository;
import ee.mrtnh.tallink_test.repo.ConferenceRoomRepository;
import ee.mrtnh.tallink_test.repo.ParticipantRepository;
import ee.mrtnh.tallink_test.service.FrontEndService;
import ee.mrtnh.tallink_test.util.RepoHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FrontEndServiceImpl implements FrontEndService {

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    RepoHelper repoHelper;

    @Override
    public List<Conference> getAllConferences() {
        log.info("Fetching Conferences from DB");
        List<Conference> conferences = conferenceRepository.findAll();
        log.info("Fetched {} Conferences from DB", conferences.size());
        return conferences;
    }

    @Override
    public Conference getConferenceById(String conferenceId) {
        log.info("Fetching Conference with ID {} from DB", conferenceId);
        Long id = parseConferenceIdStringToLong(conferenceId);
        log.info("Fetched Conference with ID {} from DB", conferenceId);
        return repoHelper.findConferenceById(id);
    }

    private Long parseConferenceIdStringToLong(String conferenceId) {
        try {
            return Long.valueOf(conferenceId);
        } catch (NumberFormatException e) {
            String message = String.format("Could not parse conferenceId %s to Long", conferenceId);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public List<ConferenceRoom> getAllConferenceRooms() {
        log.info("Fetching ConferenceRooms from DB");
        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findAll();
        log.info("Fetched {} ConferenceRooms from DB", conferenceRooms.size());
        return conferenceRooms;
    }

    @Override
    public List<Participant> getAllParticipants() {
        log.info("Fetching Participants from DB");
        List<Participant> participants = participantRepository.findAll();
        log.info("Fetched {} Participants from DB", participants.size());
        return participants;
    }
}
