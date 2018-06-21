package com.cardprocessor.cardprocessorbatchproject.repositories;

import com.cardprocessor.cardprocessorbatchproject.entities.PortalUser;
import com.cardprocessor.cardprocessorbatchproject.entities.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository   extends CrudRepository<UserRole, Long> {

    //UserRole findPortalUserByUsername(String username);
}