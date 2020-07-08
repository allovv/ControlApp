package com.springapp.repositories;

import com.springapp.entities.IssueEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<IssueEntity, Long> {

    IssueEntity findByName(String name);
    List<IssueEntity> findAllByFolderId(Long id);
    List<IssueEntity> findAll();

}
