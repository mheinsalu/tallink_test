package ee.mrtnh.tallink_test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    @Pattern(regexp = ".+", message = "Conference room must have name with non-zero length")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Conference room must have location")
    @Pattern(regexp = ".+", message = "Conference room must have location with non-zero length")
    @Column(nullable = false)
    private String location;

    @Positive(message = "Conference room must have max seats amount (positive integer)")
    @Column(nullable = false)
    private Integer maxSeats;

    @OneToMany(
            mappedBy = "conferenceRoom",
            orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude // including this would cause infinite referential loop (conference<->conferenceRoom)
    @JsonIgnore // Without this an infinite loop occurs when Serializing
    private Set<Conference> conferences = new HashSet<>();

    public boolean isConferenceRoomBooked(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return conferences.stream().anyMatch(conference -> {
            if (startDateTime.isAfter(conference.getStartDateTime()) && startDateTime.isBefore(conference.getEndDateTime())) {
                return true;
            } else if (endDateTime.isAfter(conference.getStartDateTime()) && endDateTime.isBefore(conference.getEndDateTime())) {
                return true;
            } else
                return startDateTime.isEqual(conference.getStartDateTime()) || endDateTime.isEqual(conference.getEndDateTime());
        });
    }
}
