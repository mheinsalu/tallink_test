package ee.mrtnh.tallink_test.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Conference {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotNull(message = "Conference must have name")
    private String name; // TODO: max length 150

    @NotNull(message = "Conference must have date and time")
    private Instant dateTime; // TODO: google how to use

    private List<Participant> participants;

    ConferenceRoom conferenceRoom;

    public void removeParticipant() {

    }

    public void addParticipant(Participant participant) {

    }
}
