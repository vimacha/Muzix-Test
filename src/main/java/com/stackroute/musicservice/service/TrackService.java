package com.stackroute.musicservice.service;
import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.exception.GlobalException;
import com.stackroute.musicservice.exception.TrackAlreadyExistsException;
import com.stackroute.musicservice.exception.TrackNotFoundException;

import java.util.List;
public interface TrackService {

    public Track saveTrack(Track track) throws TrackAlreadyExistsException;

    public List<Track> getAllTracks();

    public Track getTrackById(int id) throws TrackNotFoundException;

    public Track getByName(String trackName) throws TrackNotFoundException;

    public Boolean deleteTrack(int trackId) throws TrackNotFoundException;

    public Track updateComments(int id, Track track) throws GlobalException;
}