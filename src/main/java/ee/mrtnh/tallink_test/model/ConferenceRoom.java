package ee.mrtnh.tallink_test.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

// TODO: builder?
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

    @OneToMany(
            mappedBy = "conferenceRoom",
//            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude // including this would cause infinite referential loop (conference<->conferenceRoom)
    private Set<Conference> conferences = new HashSet<>();

    public boolean isConferenceRoomBooked(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return conferences.stream().anyMatch(conference -> {
            if (startDateTime.isAfter(conference.getStartDateTime()) && startDateTime.isBefore(conference.getEndDateTime())) {
                return true;
            } else if (endDateTime.isAfter(conference.getStartDateTime()) && endDateTime.isBefore(conference.getEndDateTime())) {
                return true;
            } else if (startDateTime.isEqual(conference.getStartDateTime()) || endDateTime.isEqual(conference.getEndDateTime())) {
                return true;
            }
            return false;
        });
    }


}
