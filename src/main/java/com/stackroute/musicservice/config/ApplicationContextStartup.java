package com.stackroute.musicservice.config;

import com.stackroute.musicservice.domain.Track;
import com.stackroute.musicservice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import org.jboss.logging.Logger;

@Component
@PropertySource("classpath:application.properties")
class ApplicationContextStartup implements ApplicationListener<ContextRefreshedEvent> {
    TrackRepository trackRepository;

    @Autowired
    private Environment environment;

/*    @Value("${spring.Track.trackId1}")
    private int trackId;
    @Value("${spring.Track.trackName1}")
    private String trackName;
    @Value("${spring.Track.trackComments1}")
    private String trackComments;*/



    public ApplicationContextStartup(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Track track=new Track(Integer.parseInt(environment.getProperty("spring.Track.trackId1")),environment.getProperty("spring.Track.trackName1"),
                environment.getProperty("spring.Track.trackComments1"));
        trackRepository.save(track);

    }
}
