package ee.mrtnh.tallink_test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotNull(message = "Conference room must have name")
    String name;

    @NotNull(message = "Conference room must have location")
    String location;

    @Positive(message = "Conference room must have max seats amount (positive integer)")
    Integer maxSeats;
}
