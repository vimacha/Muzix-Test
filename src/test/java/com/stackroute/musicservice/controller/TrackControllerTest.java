package com.stackroute.musicservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.exception.GlobalException;
import com.stackroute.musicservice.exception.TrackAlreadyExistsException;
import com.stackroute.musicservice.exception.TrackNotFoundException;
import com.stackroute.musicservice.service.TrackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Track track;

    @MockBean
    private TrackService trackService;
    @InjectMocks
    private TrackController trackController;

    private List<Track> list =null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
        track = new Track();
        track.setTrackId(2);
        track.setTrackName("premam");
        track.setTrackComments("good  movie album");
        list = new ArrayList<>();
        list.add(track);
    }

    @Test
    public void saveTrack() throws Exception {

        when(trackService.saveTrack((Track)ArgumentMatchers.any())).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void saveTrackFailure() throws Exception {
        when(trackService.saveTrack((Track)ArgumentMatchers.any())).thenThrow(TrackAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }


    //update test
    @Test
    public void getUpdateTrack() throws Exception, GlobalException {
        when(trackService.updateComments(track.getTrackId(),track)).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/track/update/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void getUpdateTrackFailure() throws GlobalException,Exception {
        when(trackService.updateComments(track.getTrackId(),track)).thenThrow(GlobalException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/track/update/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    //delete test
    @Test
    public void getDeleteTrack() throws Exception {
        when(trackService.deleteTrack(track.getTrackId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/track/delete/2")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void getDeleteTrackFailure() throws Exception {
        when(trackService.deleteTrack(track.getTrackId())).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/track/delete/2")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }
    //All tracks test
    @Test
    public void getAllTracks() throws Exception {
        when(trackService.getAllTracks()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }



    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}