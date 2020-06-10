package ee.mrtnh.tallink_test.repo;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {

}