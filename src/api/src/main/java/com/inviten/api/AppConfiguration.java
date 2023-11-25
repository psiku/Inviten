package com.inviten.api;

import com.inviten.api.features.meetings.IMeetingRepository;
import com.inviten.api.features.meetings.MeetingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean(name = "userRepository")
    public IMeetingRepository getMeetingRepository() {
        return new MeetingRepository();
    }
}
