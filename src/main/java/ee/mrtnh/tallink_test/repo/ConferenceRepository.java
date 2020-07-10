package ee.mrtnh.tallink_test.repo;

import ee.mrtnh.tallink_test.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface  ConferenceRepository extends JpaRepository<Conference, Long> {

    // TODO: method name quite ugly. Is there a better way to do this?
    Conference findConferenceByNameAndStartDateTimeAndEndDateTimeAndConferenceRoomId(String conferenceName, LocalDateTime startDateTime, LocalDateTime endDateTime, Long conferenceRoomId);
}