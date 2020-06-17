package ee.mrtnh.tallink_test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Conference(String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (name.length() > 150) {
            throw new ValidationException("Conference's name must be <=150 characters");
        }
        if (startDateTime.isAfter(endDateTime)) {
            throw new ValidationException("Conference's start time must be before its end time");
        }
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @NotNull(message = "Conference must have name")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Conference must have starting date and time")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startDateTime;

    @NotNull(message = "Conference must have ending date and time")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime endDateTime;

    /*@NotNull(message = "Conference must have conference room id") // add test
    @Column(name = "CONFERENCE_ROOM_ID")
    private Long conferenceRoomId;*/

    @ManyToOne
    @JoinColumn(name = "CONFERENCE_ROOM_ID")
    ConferenceRoom conferenceRoom; // TODO: just room id better so JSON would not have to send entire room?

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "CONFERENCE_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PARTICIPANT_ID"))
//    @ToString.Exclude
    private Set<Participant> participants = new HashSet<>();

    public boolean addParticipant(Participant participant) {
        return participants.add(participant);
    }

    public boolean removeParticipant(Participant participant) {
        return participants.remove(participant);
    }

    public Integer getAvailableRoomCapacity() {
        return conferenceRoom.getMaxSeats() - participants.size();
    }
}
