package com.springapp.repositories;

import com.springapp.entities.IssueEntity;
import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<IssueEntity, Long> {
}
