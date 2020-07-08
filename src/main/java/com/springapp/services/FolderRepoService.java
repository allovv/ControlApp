package com.springapp.services;

import com.springapp.entities.FolderEntity;
import com.springapp.entities.IssueEntity;
import com.springapp.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderRepoService {
    //-------------------------------------------------------------
    @Autowired
    FolderRepository folderRepository;

    @Autowired
    IssueRepoService issueRepoService;

    //-------------------------------------------------------------

    public boolean addFolder(FolderEntity folderEntity) {
        //проверка при добавлении по названию проекта
        FolderEntity folderEntityDB = folderRepository.findByName(folderEntity.getName());

        if (folderEntityDB != null) {
            return false;
        }
        if (folderEntity.getCreatorId() == null) {
            return false;
        }

        //сохранение
        folderRepository.save(folderEntity);
        return true;
    }

    public void editFolderById(String name, Long id) {
        FolderEntity folderToEdit = this.findById(id);
        folderToEdit.setName(name);
        folderRepository.save(folderToEdit);
    }

    //delete --------------------------------------------
    public void deleteById(Long id) {
        folderRepository.deleteById(id);
        List<IssueEntity> issueEntities = issueRepoService.findAllByFolderId(id);
        for (int i = 0; i < issueEntities.size(); i++) {
            issueRepoService.deleteById(issueEntities.get(i).getId());
        }
    }

    public void deleteAll() {
        folderRepository.deleteAll();
        issueRepoService.deleteAll();
    }

    public void deleteAllByCreatorId(Long creatorId) {
        List<FolderEntity> folderEntities = folderRepository.findAllByCreatorId(creatorId);
        for (int i = 0; i < folderEntities.size(); i++) {
            deleteById(folderEntities.get(i).getId());
        }
    }

    //save --------------------------------------------
    public void save(FolderEntity folderEntity) {
        folderRepository.save(folderEntity);
    }

    //find --------------------------------------------
    public List<FolderEntity> findAllByCreatorId(Long creatorId) {
        return folderRepository.findAllByCreatorId(creatorId);
    }

    public FolderEntity findById(Long id) {
        return folderRepository.findById(id).orElse(new FolderEntity(" ", null));
    }

    public FolderEntity findByName(String name) {
        return folderRepository.findByName(name);
    }

    public List<FolderEntity> findAll() {
        return folderRepository.findAll();
    }

}
