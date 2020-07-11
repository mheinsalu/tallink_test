package ee.mrtnh.tallink_test.controller;

import ee.mrtnh.tallink_test.model.Conference;
import ee.mrtnh.tallink_test.service.implementation.FrontEndServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FrontEndControllerTest {

    @MockBean
    private FrontEndServiceImpl frontEndService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllConferences_valid() throws Exception {
        doReturn(Collections.EMPTY_LIST).when(frontEndService).getAllConferences();
        mockMvc.perform(get("/getAllConferences").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getConferenceById_valid() throws Exception {
        doReturn(new Conference()).when(frontEndService).getConferenceById("1");
        mockMvc.perform(get("/viewConferences/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllConferenceRooms_valid() throws Exception {
        doReturn(Collections.EMPTY_LIST).when(frontEndService).getAllConferenceRooms();
        mockMvc.perform(get("/getAllConferenceRooms").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllParticipants_valid() throws Exception {
        doReturn(Collections.EMPTY_LIST).when(frontEndService).getAllParticipants();
        mockMvc.perform(get("/getAllParticipants").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}