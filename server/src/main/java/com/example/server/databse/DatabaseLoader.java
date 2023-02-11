package com.example.server.databse;


import com.example.server.models.user.BlogEntry;
import com.example.server.models.user.Role;
import com.example.server.models.user.User;
import com.example.server.repositories.BlogEntryRepository;
import com.example.server.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {
        private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

        @Bean
        CommandLineRunner initDatabase(UserRepository userRepository, BlogEntryRepository blogEntryRepository) {

            return args -> {
                log.info("Preloading " + userRepository.save(new User("Lisa Regular", "password",  Role.REGULAR_USER)));
                log.info("Preloading " + userRepository.save(new User("Pavel Admin","password", Role.ADMIN)));
                log.info("Preloading " + blogEntryRepository.save(new BlogEntry("title", "source", "content")));

            };
        }
}

