
package com.stackroute.musicservice.service;


import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.exception.GlobalException;
import com.stackroute.musicservice.exception.TrackAlreadyExistsException;
import com.stackroute.musicservice.exception.TrackNotFoundException;
import com.stackroute.musicservice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

@CacheConfig(cacheNames = "track")
public class TrackServiceImpl2 implements TrackService{

    TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl2(TrackRepository trackRepository)
    {
        this.trackRepository=trackRepository;
    }

    public void simulateDelay()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException{
        if(trackRepository.existsById(track.getTrackId()))
        {
            throw new TrackAlreadyExistsException("Track already exists");
        }
        Track savedUser=trackRepository.save(track);


        /*
       if(savedUser==null)
       {
           throw new TrackAlreadyExistsException("Track already exists");
       }*/

        return savedUser;
    }

    @Cacheable
    @Override
    public List<Track> getAllTracks() {

        simulateDelay();
        List<Track> list=(List<Track>)trackRepository.findAll();
        return list;
    }

    @Override
    public Track getTrackById(int id) throws TrackNotFoundException{
        Track track=null;
        if(trackRepository.existsById(id))
        {
            track=trackRepository.getOne(id);
        }
        else
        {
            throw new TrackNotFoundException("track not exists");
        }
        if(track==null){
            throw new TrackNotFoundException("track not found");
        }
        return track;
    }

    @Override
    public Track getByName(String trackName) throws TrackNotFoundException {
        Track track=null;
        track=trackRepository.getByName(trackName);
        if(track==null){
            throw new TrackNotFoundException("track name not found");
        }
        return track;
    }

    @Override
    public Boolean deleteTrack(int trackId) throws TrackNotFoundException {
        if(trackRepository.existsById(trackId))
        {
            trackRepository.deleteById(trackId);
        }
        else
        {
            return false;
        }

        //return track object
        return false;
    }

    @Override
//    @CachePut("track")
    public Track updateComments(int id,Track track) throws GlobalException {
        Track track1=null;
        if(trackRepository.existsById(id))
        {
            track.setTrackComments(track.getTrackComments());
            track1=trackRepository.save(track);
        }
        else
        {
            throw new GlobalException();
        }

        return track1;

    }
}