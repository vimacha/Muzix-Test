
package com.stackroute.musicservice.service;

import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.exception.GlobalException;
import com.stackroute.musicservice.exception.TrackAlreadyExistsException;
import com.stackroute.musicservice.exception.TrackNotFoundException;
import com.stackroute.musicservice.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
public class TrackServiceTest {
    private Track track;
    private Optional optional;
    @Mock
    private TrackRepository trackRepository;
    @InjectMocks
    private TrackServiceImpl trackService;
    List<Track> list= null;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setTrackId(2);
        track.setTrackName("premam");
        track.setTrackComments("good movie album");
        list = new ArrayList<>();
        list.add(track);
    }
    //save test
    @Test
    public void saveTrackTestSuccess() throws TrackAlreadyExistsException{
        when(trackRepository.save((Track)any())).thenReturn(track);
        Track savedUser = trackService.saveTrack(track);
        assertEquals(track,savedUser);
        verify(trackRepository,times(1)).save(track);
    }


   //update test
   @Test
   public void testUpdateTrackComments() throws GlobalException {

       when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
       when(trackRepository.save((Track)any())).thenReturn(track);
       track.setTrackComments("morning");
       Track track1=trackService.updateComments(track.getTrackId(),track);
       Assert.assertEquals("morning",track1.getTrackComments());
   }

    @Test
    public void testUpdateTrackCommentsFailure() throws TrackNotFoundException{

        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        when(trackRepository.save((Track)any())).thenReturn(track);
        track.setTrackComments("afternoon");
        Track track1=trackService.updateComments(track.getTrackId(),track);
        Assert.assertNotSame("afternoons",track1.getTrackComments());
    }


    //delete test
    @Test
    public void deleteTrackTest() throws TrackNotFoundException {
        trackRepository.save(track);
        when(trackRepository.findAll()).thenReturn(list);
        trackService.deleteTrack(track.getTrackId());
        boolean delete=trackService.trackRepository.existsById(9);
        assertEquals(false,delete);
    }

    @Test
    public void testDeleteTrackFailure(){

        Track track=new Track(21,"panja","pawan");
        when(trackRepository.findAll()).thenReturn(list);
        trackRepository.delete(track);
        boolean deletedTrack=trackRepository.existsById(21);
        Assert.assertNotSame(true,deletedTrack);
    }

    //trackbyname test
    @Test
    public void testTrackByName() throws TrackNotFoundException{
        when(trackRepository.getByName(track.getTrackName())).thenReturn(track);
        Track track1=trackService.getByName(track.getTrackName());
        Assert.assertEquals("premam",track1.getTrackName());
    }

    @Test(expected = TrackNotFoundException.class)
    public void testTrackByNameFailure() throws TrackNotFoundException{
        when(trackRepository.getByName(track.getTrackName())).thenReturn(null);
        Track track1=trackService.getByName(track.getTrackName());
    }


    //All tracks test
    @Test
    public void getAllTracks(){
        trackRepository.save(track);
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> userlist = trackService.getAllTracks();
        assertEquals(list,userlist);
    }
}
