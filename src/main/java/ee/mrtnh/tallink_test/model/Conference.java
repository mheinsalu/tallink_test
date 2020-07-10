package ee.mrtnh.tallink_test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.ValidationException;
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
public class Conference {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    public Conference(String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (name.length() > 150) {
            throw new ValidationException("Conference's name must be <=150 characters");
        }
        if (name.isEmpty()) {
            throw new ValidationException("Conference's name must not be empty");
        }
        if (startDateTime.isAfter(endDateTime)) {
            throw new ValidationException("Conference's start time must be before its end time");
        }
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @NotNull(message = "Conference must have name")
    @Pattern(regexp = ".{1,150}", message = "Conference must have name with length [1, 150]")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Conference must have starting date and time")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startDateTime;

    @NotNull(message = "Conference must have ending date and time")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm") // TODO: how to check that start<end in JSON des?
    private LocalDateTime endDateTime;

    @NotNull(message = "Conference must have Conference Room ID")
    @Positive(message = "Conference's Conference Room ID must be positive")
    @Column(nullable = false)
    private Long conferenceRoomId;

    // TODO: needed here? replace with helper method?
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "CONFERENCE_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PARTICIPANT_ID"))
    @ToString.Exclude
    private Set<Participant> participants = new HashSet<>();

    public boolean addParticipant(Participant participant) {
        return participants.add(participant);
    }

    public boolean removeParticipantById(long participantId) {
        return participants.removeIf(participant -> participant.getId()==(participantId));
    }

}
