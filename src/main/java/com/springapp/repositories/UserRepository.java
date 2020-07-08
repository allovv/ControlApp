package com.springapp.repositories;

import com.springapp.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    List<UserEntity> findAll();

}
