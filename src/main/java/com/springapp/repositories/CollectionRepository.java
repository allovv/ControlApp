package com.springapp.repositories;

import com.springapp.entities.CollectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends CrudRepository<CollectionEntity, Long> {

    CollectionEntity findByName(String name);
    List<CollectionEntity> findAll();

}
