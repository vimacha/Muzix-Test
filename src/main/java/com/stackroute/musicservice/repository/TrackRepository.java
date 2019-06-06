package com.stackroute.musicservice.repository;
import java.util.List;
import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.exception.TrackNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TrackRepository extends JpaRepository<Track,Integer> {

    @Query("select t from Track t where t.trackName=:trackName")
    public Track getByName(@Param("trackName") String trackName) throws TrackNotFoundException;

}

