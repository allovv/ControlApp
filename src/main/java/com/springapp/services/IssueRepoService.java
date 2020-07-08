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

    public boolean addIssue(IssueEntity issueEntity) {
        if (issueEntity.getFolderId() == null) {
            return false;
        }

        //сохранение
        issueRepository.save(issueEntity);
        return true;
    }

    public void editIssueById(Boolean done, String description, String name, Long id) {
        IssueEntity issueToEdit = this.findById(id);
        issueToEdit.setName(name);
        issueToEdit.setDescription(description);
        issueToEdit.setDone(done);
        issueRepository.save(issueToEdit);
    }

    //delete --------------------------------------------
    public void deleteById(Long id) {
        issueRepository.deleteById(id);
    }

    public void deleteAll() {
        issueRepository.deleteAll();
    }

    //save --------------------------------------------
    public void save(IssueEntity issueEntity ) {
        issueRepository.save(issueEntity);
    }

    //find --------------------------------------------
    public IssueEntity findById(Long id) {
        return issueRepository.findById(id).orElse(new IssueEntity(" ", null));
    }

    public List<IssueEntity> findAllByFolderId(Long collectionId) {
        return issueRepository.findAllByFolderId(collectionId);
    }

    public List<IssueEntity> findAll() {
        return issueRepository.findAll();
    }
}
