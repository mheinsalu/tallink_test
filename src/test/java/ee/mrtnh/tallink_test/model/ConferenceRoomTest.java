package ee.mrtnh.tallink_test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConferenceRoomTest {

    ConferenceRoom conferenceRoom = new ConferenceRoom("testRoomName", "testRoomLocation", 5);

    @BeforeEach
    void setUp() {
        Set<Conference> conferences = new HashSet<>();

        LocalDateTime startTime1 = LocalDateTime.of(2020, Month.JUNE, 10, 10, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2020, Month.JUNE, 10, 11, 0);
        Conference conference10_11 = new Conference("test1", startTime1
                , endTime1);

        LocalDateTime startTime2 = LocalDateTime.of(2020, Month.JUNE, 10, 11, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2020, Month.JUNE, 10, 12, 0);
        Conference conference11_12 = new Conference("test1", startTime2
                , endTime2);

        conferences.add(conference10_11);
        conferences.add(conference11_12);

        conferenceRoom.setConferences(conferences);
    }

    @Test
    void isConferenceRoomBooked_true_startsLater() {
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JUNE, 10, 10, 30);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JUNE, 10, 11, 0);
        assertTrue(conferenceRoom.isConferenceRoomBooked(startTime, endTime));
    }

    @Test
    void isConferenceRoomBooked_true_startsEarlier() {
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JUNE, 10, 9, 30);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JUNE, 10, 10, 30);
        assertTrue(conferenceRoom.isConferenceRoomBooked(startTime, endTime));
    }

    @Test
    void isConferenceRoomBooked_false() {
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JUNE, 10, 13, 30);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JUNE, 10, 14, 30);
        assertFalse(conferenceRoom.isConferenceRoomBooked(startTime, endTime));
    }
}