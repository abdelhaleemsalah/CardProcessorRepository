package com.cardprocessor.cardprocessorbatchproject.repositories;

import com.cardprocessor.cardprocessorbatchproject.entities.PortalUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<PortalUser , Long> {

    PortalUser findPortalUserByUsername(String username);
}
