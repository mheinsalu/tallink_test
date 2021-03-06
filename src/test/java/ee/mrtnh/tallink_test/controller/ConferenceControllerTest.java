package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.service.implementation.ConferenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConferenceControllerTest {

    @MockBean
    private ConferenceServiceImpl conferenceService;

    @Autowired
    private MockMvc mockMvc;

    private Conference conference;

    private final String validJson = "{\n" +
            "    \"name\": \"testConferenceName\",\n" +
            "    \"startDateTime\": \"10-06-2020 10:10\",\n" +
            "    \"endDateTime\": \"10-06-2020 11:15\",\n" +
            "    \"conferenceRoomId\": 1 \n" +
            "}";

    @BeforeEach
    void setUp() {
        LocalDateTime conferenceStartDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 10, 15);
        LocalDateTime conferenceEndDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 11, 15);
        conference = new Conference("conferenceName", conferenceStartDateTime, conferenceEndDateTime);
        conference.setId(1L);
        conference.setConferenceRoomId(1L);
    }

    @Test
    void addConference_validJson() throws Exception {
        doReturn("test message").when(conferenceService).addConference(conference);
        mockMvc.perform(post("/addConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson))
                .andExpect(status().isOk());
    }

    @Test
    void addConference_invalidJson() throws Exception {
        doReturn("test message").when(conferenceService).addConference(conference);
        mockMvc.perform(post("/addConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelConference_validJson() throws Exception {
        doReturn("test message").when(conferenceService).cancelConference(conference.getId());
        mockMvc.perform(delete("/cancelConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson))
                .andExpect(status().isOk());
    }

    @Test
    void cancelConference_invalidJson() throws Exception {
        doReturn("test message").when(conferenceService).cancelConference(conference.getId());
        mockMvc.perform(delete("/cancelConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkConferenceSeatsAvailability_validJSon() throws Exception {
        doReturn("test message").when(conferenceService).checkConferenceSeatsAvailability(conference.getId());
        mockMvc.perform(post("/checkConferenceSeatsAvailability")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson))
                .andExpect(status().isOk());
    }

    @Test
    void checkConferenceSeatsAvailability_invalidJSon() throws Exception {
        doReturn("test message").when(conferenceService).checkConferenceSeatsAvailability(conference.getId());
        mockMvc.perform(post("/checkConferenceSeatsAvailability")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
}