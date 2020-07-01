package com.springapp.repositories;

import com.springapp.entities.ListEntity;
import org.springframework.data.repository.CrudRepository;

public interface ListRepository extends CrudRepository<ListEntity, Long> {
}
