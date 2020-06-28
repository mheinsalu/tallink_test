package ee.mrtnh.tallink_test.repo;

import ee.mrtnh.tallink_test.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface  ConferenceRepository extends JpaRepository<Conference, Long> {

    Conference findConferenceByNameAndStartDateTimeAndEndDateTime(String conferenceName, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Conference> findAllByConferenceRoom_Id(long id);
}