package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.model.Participant;
import ee.mrtnh.tallink_test.service.implementation.ParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ParticipantControllerTest {

    @MockBean
    private ParticipantServiceImpl participantService;

    @Autowired
    private MockMvc mockMvc;

    private Conference conference;
    private Participant participant;

    private final String validJson = "{\n" +
            "    \"participant\": {\n" +
            "        \"fullName\": \"FirstName LastName\",\n" +
            "        \"dateOfBirth\": \"10-06-2020\"\n" +
            "    },\n" +
            "    \"conferenceId\": 1 \n" +
            "}";

    @BeforeEach
    void setUp() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        conference.setId(1L);
        conference.setConferenceRoomId(1L);

        LocalDate dateOfBirth = LocalDate.of(2020, Month.JUNE, 20);
        participant = new Participant("FirstName LastName", dateOfBirth);
    }

    @Test
    void addParticipantToConference_validJson() throws Exception {
        doReturn("test message").when(participantService).addParticipantToConference(participant, conference.getId());
        mockMvc.perform(post("/addParticipantToConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson))
                .andExpect(status().isOk());
    }

    @Test
    void addParticipantToConference_invalidJson() throws Exception {
        doReturn("test message").when(participantService).addParticipantToConference(participant, conference.getId());
        mockMvc.perform(post("/addParticipantToConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeParticipantFromConference_validJson() throws Exception {
        doReturn("test message").when(participantService).addParticipantToConference(participant, conference.getId());
        mockMvc.perform(delete("/removeParticipantFromConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson))
                .andExpect(status().isOk());
    }

    @Test
    void removeParticipantFromConference_invalidJson() throws Exception {
        doReturn("test message").when(participantService).addParticipantToConference(participant, conference.getId());
        mockMvc.perform(delete("/removeParticipantFromConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
}