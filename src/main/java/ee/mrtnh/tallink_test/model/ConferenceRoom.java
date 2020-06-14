package ee.mrtnh.tallink_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    public ConferenceRoom(String name, String location, Integer maxSeats) {
        this.name = name;
        this.location = location;
        this.maxSeats = maxSeats;
    }

    @NotNull(message = "Conference room must have name")
    @Column(nullable = false)
    String name;

    @NotNull(message = "Conference room must have location")
    @Column(nullable = false)
    String location;

    @Positive(message = "Conference room must have max seats amount (positive integer)")
    @Column(nullable = false)
    Integer maxSeats;

    @OneToMany(mappedBy = "conferenceRoom")
    @ToString.Exclude
    private Set<Conference> conferences = new HashSet<>();

    public boolean isConferenceRoomBooked(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return conferences.stream().anyMatch(conference -> {
            if (startDateTime.isAfter(conference.getStartDateAndTime()) && startDateTime.isBefore(conference.getEndDateAndTime())) {
                return true;
            } else if (endDateTime.isAfter(conference.getStartDateAndTime()) && endDateTime.isBefore(conference.getEndDateAndTime())) {
                return true;
            }
            return false;
        });

    }
}
