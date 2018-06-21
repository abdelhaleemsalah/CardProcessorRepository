package com.cardprocessor.cardprocessorbatchproject.configuration;

import com.cardprocessor.cardprocessorbatchproject.repositories.UserRepository;
import com.cardprocessor.cardprocessorbatchproject.entities.PortalUser;
import com.cardprocessor.cardprocessorbatchproject.entities.UserRole;
import com.cardprocessor.cardprocessorbatchproject.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@Configuration
public class InitialDataSetup {

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, UserRoleRepository userRoleRepository) {

        return (args) -> {

            PortalUser user = new PortalUser();
            PortalUser user2 = new PortalUser();
            PortalUser user3 = new PortalUser();
            UserRole userRole2 = new UserRole();
            UserRole userRole3 = new UserRole();
            UserRole userRole = new UserRole();
            userRole.setPortalUser(user);
            userRole.setRole("Super");
            userRole2.setPortalUser(user2);
            userRole2.setRole("Super");
            userRole3.setPortalUser(user3);
            userRole3.setRole("Super");


            HashSet<UserRole> userRoles = new HashSet<>();
            userRoles.add(userRole);
            HashSet<UserRole> userRoles2 = new HashSet<>();
            userRoles2.add(userRole2);
            HashSet<UserRole> userRoles3 = new HashSet<>();
            userRoles3.add(userRole3);


            user.setUsername("superuser");
            user.setPassword(new BCryptPasswordEncoder().encode("password"));
            user.setEnabled(true);
            user.setUserRole(userRoles);

            user2.setUsername("merchant1");
            user2.setPassword(new BCryptPasswordEncoder().encode("password"));
            user2.setEnabled(true);
            user2.setUserRole(userRoles2);

            user3.setUsername("merchant2");
            user3.setPassword(new BCryptPasswordEncoder().encode("password"));
            user3.setEnabled(true);
            user3.setUserRole(userRoles3);



            userRepository.save(user);
            userRepository.save(user2);
            userRepository.save(user3);

            userRoleRepository.save(userRole);
            userRoleRepository.save(userRole2);
            userRoleRepository.save(userRole3);
            System.out.println(userRepository.findAll());
        };
    }

    public void superuser(){

    }
}
