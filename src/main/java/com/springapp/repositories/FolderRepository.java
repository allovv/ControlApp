package com.springapp.repositories;

import com.springapp.entities.FolderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends CrudRepository<FolderEntity, Long> {

    FolderEntity findByName(String name);
    List<FolderEntity> findAllByCreatorId(Long creatorId);
    List<FolderEntity> findAll();

}
