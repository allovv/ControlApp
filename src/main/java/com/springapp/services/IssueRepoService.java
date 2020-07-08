package com.springapp.services;

import com.springapp.entities.IssueEntity;
import com.springapp.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueRepoService {
    //-------------------------------------------------------------
    @Autowired
    IssueRepository issueRepository;

    //-------------------------------------------------------------

    //delete --------------------------------------------
    public void deleteById(Long id) {
        issueRepository.deleteById(id);
    }

    public void deleteAll() {
        issueRepository.deleteAll();
    }

    /*
    public void deleteAllByCollectionId(Long creatorId) {
        List<IssueEntity> issueEntities = issueRepository.findAllByCollectionId(creatorId);
        for (int i = 0; i < issueEntities.size(); i++) {
            deleteById(issueEntities.get(i).getId());
        }
    }

     */

    //save --------------------------------------------
    public void save(IssueEntity issueEntity ) {
        issueRepository.save(issueEntity);
    }

    //find --------------------------------------------
    public IssueEntity findById(Long id) {
        return issueRepository.findById(id).orElse(new IssueEntity(" ", " ", null));
    }

    public IssueEntity findByName(String name) {
        return issueRepository.findByName(name);
    }

    /*
    public List<IssueEntity> findAllByCollectionId(Long collectionId) {
        return issueRepository.findAllByCollectionId(collectionId);
    }
     */

    public List<IssueEntity> findAll() {
        return issueRepository.findAll();
    }
}
