package ee.mrtnh.tallink_test.repo;

import ee.mrtnh.tallink_test.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface  ConferenceRepository extends JpaRepository<Conference, Long> {

    public Conference findConferenceByNameAndStartDateAndTimeAndEndDateAndTime(String conferenceName, LocalDateTime startDateTime, LocalDateTime endDateTime);

}