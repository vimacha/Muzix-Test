package com.stackroute.musicservice.config;

import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.repository.TrackRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.slf4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class CommandLineRunnerStartup implements org.springframework.boot.CommandLineRunner{

    TrackRepository trackRepository;

    @Autowired
    private Environment environment;

    public CommandLineRunnerStartup(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Track track=new Track(Integer.parseInt(environment.getProperty("spring.Track.trackId2")),environment.getProperty("spring.Track.trackName2"),
                environment.getProperty("spring.Track.trackComments2"));
        trackRepository.save(track);

    }
}
