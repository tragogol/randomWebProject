package com.zato.randomWebProject.config;

import com.zato.randomWebProject.data.UserStats;
import com.zato.randomWebProject.repository.UserStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initUserStats(UserStatsRepository repository) {
    return args -> {
      log.info("Preloading " + repository.save(new UserStats( 100, 50)));
      log.info("Preloading " + repository.save(new UserStats( 100, 45)));
    };
  }


}
