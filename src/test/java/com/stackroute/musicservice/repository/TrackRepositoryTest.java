package com.stackroute.musicservice.repository;

import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.exception.TrackNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackRepositoryTest {
    @Autowired
    private TrackRepository trackRepository;
    private Track track;

    @Before
    public void setup()
    {
        track=new Track();
        track.setTrackId(1);
        track.setTrackName("premam");
        track.setTrackComments("good album");
    }

    @After
    public void tearDown(){
        trackRepository.deleteAll();
    }
//save test
    @Test
    public void testSaveTrack()
    {
        trackRepository.save(track);
        Track fetchTrack=trackRepository.findById(track.getTrackId()).get();
        assertEquals(1,fetchTrack.getTrackId());

    }

    @Test
    public void testSaveTrackFailure(){
        Track testUser = new Track(2,"f2","fun movie");
        trackRepository.save(track);
        Track fetchUser = trackRepository.findById(track.getTrackId()).get();
        Assert.assertNotSame(testUser,track);
    }


//update test
    @Test
    public void testUpdateTrackSuccess() {
        trackRepository.save(track);
        trackRepository.findById(track.getTrackId()).get().setTrackComments("movie");
        Track fetchTrack = trackRepository.findById(track.getTrackId()).get();

        assertEquals("movie",fetchTrack.getTrackComments());

    }

    @Test
    public void testUpdateTrackFailure() {
        Track testUser = new Track(1, "chitram", "fun movie");
        trackRepository.save(testUser);
        Track fetchTrack = trackRepository.findById(testUser.getTrackId()).get();
        Assert.assertNotSame(testUser, fetchTrack);
    }


    //delete test
    @Test
    public void testDeleteTrack(){
        Track track=new Track(14,"dfghj","oiuyt");
        trackRepository.delete(track);
        boolean deletedTrack=trackRepository.existsById(14);
        assertEquals(false,deletedTrack);
    }


    @Test
    public void testDeleteTrackFailure(){
        Track track=new Track(2,"super","movie");
        trackRepository.delete(track);
        boolean deletedTrack=trackRepository.existsById(2);
        Assert.assertNotSame(true,deletedTrack);
    }

    //test all tracks
    @Test
    public void testGetAllTracks() {
        Track t = new Track(3,"eenadu","good movie");
        Track t1 = new Track(4,"RRR","ram ntr movie");
        trackRepository.save(t);
        trackRepository.save(t1);

        List<Track> list = trackRepository.findAll();
        assertEquals(3, list.get(0).getTrackId());
    }

    //Byname test

    @Test
    public void trackByName() {
        Track track=new Track(5,"adhi","hero");
        trackRepository.save(track);
        try {
            Assert.assertEquals(track,trackRepository.getByName("adhi"));
        } catch (TrackNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void trackByNameFailure() {
        Track track=new Track(5,"ram","hero");
        trackRepository.save(track);
        try {
            Assert.assertNotSame(track,trackRepository.getByName("ram"));
        } catch (TrackNotFoundException e) {
            e.printStackTrace();
        }
    }




}