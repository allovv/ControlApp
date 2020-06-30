package com.springapp.repositories;

import com.springapp.entities.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProjectsRepository extends CrudRepository<ProjectEntity, Long> {

}
