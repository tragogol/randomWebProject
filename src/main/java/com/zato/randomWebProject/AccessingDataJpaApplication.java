package com.zato.randomWebProject;

import com.zato.randomWebProject.data.TmpUser;
import com.zato.randomWebProject.data.UserStats;
import com.zato.randomWebProject.repository.ITmpUserRepository;
import com.zato.randomWebProject.repository.IUserStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

//@SpringBootApplication
public class AccessingDataJpaApplication {
    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class);
    }

    @Bean
    public CommandLineRunner demo(ITmpUserRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new TmpUser("1", "1"));
            repository.save(new TmpUser("2", "2"));
            repository.save(new TmpUser("3", "3"));
            repository.save(new TmpUser("4", "4"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (TmpUser userStats : repository.findAll()) {
                log.info(userStats.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            TmpUser customer = (TmpUser) repository.findTmpUserByLoginValue("3");
            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findById('2'):");
            log.info("--------------------------------------------");
            log.info(repository.findTmpUserById(2).toString());
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info(DataSource.class.getName());
        };
    }
}
