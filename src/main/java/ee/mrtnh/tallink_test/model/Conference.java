package ee.mrtnh.tallink_test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class Conference {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    public Conference(String name, LocalDateTime startDateAndTime, LocalDateTime endDateAndTime) {
        if (name.length() > 150) {
            throw new ValidationException("Conference's name must be <=150 characters");
        }
        if (startDateAndTime.isAfter(endDateAndTime)) {
            throw new ValidationException("Conference's start time must be before its end time");
        }
        this.name = name;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
    }

    @NotNull(message = "Conference must have name")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Conference must have starting date and time")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyy HH:mm")
    private LocalDateTime startDateAndTime;

    @NotNull(message = "Conference must have ending date and time")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyy HH:mm")
    private LocalDateTime endDateAndTime;

    @NotNull(message = "Conference must have room")
    @ManyToOne
    @JoinColumn(name = "CONFERENCE_ROOM_ID")
    ConferenceRoom conferenceRoom;

    @ManyToMany
    @JoinTable(name = "CONFERENCE_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PARTICIPANT_ID"))
    @ToString.Exclude
    private Set<Participant> participants = new HashSet<>();

    public boolean addParticipant(Participant participant) {
        return participants.add(participant);
    }

    public boolean removeParticipant(Participant participant) {
        return participants.remove(participant);
    }

    public boolean isRoomCapacityFilled() {
        return participants.size() >= conferenceRoom.getMaxSeats();
    }
}
