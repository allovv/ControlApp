package com.springapp.services;

import com.springapp.entities.CollectionEntity;
import com.springapp.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionRepoService {
    //-------------------------------------------------------------
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    IssueRepoService issueRepoService;

    //-------------------------------------------------------------

    //delete --------------------------------------------
    public void deleteById(Long id) {
        collectionRepository.deleteById(id);
        issueRepoService.deleteById(id);
    }

    public void deleteAll() {
        collectionRepository.deleteAll();
        issueRepoService.deleteAll();
    }

    /*
    public void deleteAllByFolderId(Long folderId) {
        List<CollectionEntity> collectionEntities = collectionRepository.findAllByFolderId(folderId);
        for (int i = 0; i < collectionEntities.size(); i++) {
            deleteById(collectionEntities.get(i).getId());
        }
    }

     */

    //save --------------------------------------------
    public void save(CollectionEntity collectionEntity) {
        collectionRepository.save(collectionEntity);
    }

    //find --------------------------------------------
    public CollectionEntity findById(Long id) {
        return collectionRepository.findById(id).orElse(new CollectionEntity(" ", " ", " ", null));
    }

    public CollectionEntity findByName(String name) {
        return collectionRepository.findByName(name);
    }

    /*
    public List<CollectionEntity> findAllByFolderId(Long folderId) {
        return collectionRepository.findAllByFolderId(folderId);
    }

     */

    public List<CollectionEntity> findAll() {
        return collectionRepository.findAll();
    }
}
