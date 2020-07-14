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

    public boolean addFolder(FolderEntity newFolder) {
        if (newFolder.getCreatorId() == null) {
            return false;
        }

        //проверка при добавлении по названию проекта (для проектов конкретного пользователя)
        List<FolderEntity> folderEntities = folderRepository.findAllByCreatorId(newFolder.getCreatorId());

        String initialName = newFolder.getName();
        boolean again = true;
        int digit = 1;
        while (again) { //TODO: оптимизировать
            again = false;
            for (FolderEntity folder : folderEntities) {
                if (folder.getName().equals(newFolder.getName())) {
                    again = true;
                    newFolder.setName(initialName + " " + digit);
                    digit++;
                }
            }
        }
        //сохранение
        folderRepository.save(newFolder);

        return true;
    }

    public void editFolderById(String name, Long id) {
        FolderEntity folderToEdit = this.findById(id);
        folderToEdit.setName(name);
        folderRepository.save(folderToEdit);
    }

    public boolean isExist(String name, Long creatorId) {
        List<FolderEntity> folderEntities = folderRepository.findAllByCreatorId(creatorId);
        for (FolderEntity folder : folderEntities) {
            if (folder.getName().equals(name)) {
                return true;
            }
        }
        return false;
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

    public List<FolderEntity> findAll() {
        return folderRepository.findAll();
    }

}
