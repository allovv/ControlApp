package com.springapp.services;

import com.springapp.entities.IssueEntity;
import com.springapp.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        if (issueEntity.getCreatorId() == null) {
            return false;
        }

        //установка даты
        ZonedDateTime date = LocalDateTime.now().atZone(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        issueEntity.setCreateDate(date.format(dateTimeFormatter));

        //сохранение
        issueRepository.save(issueEntity);
        return true;
    }

    /**
     * редактируемые поля:
     * name
     * description
     * folderId
     * tags
     */
    public boolean editIssueById(IssueEntity editedIssue, Long id) {
        if (id == null) {
            return false;
        }
        IssueEntity issueToEdit = this.findById(id);
        issueToEdit.setName(editedIssue.getName());
        issueToEdit.setDescription(editedIssue.getDescription());
        issueToEdit.setFolderId(editedIssue.getFolderId());
        issueToEdit.setTagsNoParsing(editedIssue.getTagsNoParsing());

        //обновление информации в БД
        issueRepository.save(issueToEdit);
        return true;
    }

    public void setStatusById(Long id, IssueEntity.IssueStatus status) {
        //изменение состояния задачи на "удалена"
        IssueEntity issue = this.findById(id);
        issue.setStatus(status);
        issue.setFolderId(null); //TODO: пустой id категории

        //обновляем информацию в БД
        this.save(issue);
    }

    //delete --------------------------------------------
    public void deleteById(Long id) {
        issueRepository.deleteById(id);
    }

    public void deleteAll() {
        issueRepository.deleteAll();
    }

    public void deleteAllByCreatorId(Long creatorId) {
        issueRepository.deleteAllByCreatorId(creatorId);
    }

    //save --------------------------------------------
    public void save(IssueEntity issueEntity ) {
        issueRepository.save(issueEntity);
    }

    //find --------------------------------------------
    public IssueEntity findById(Long id) {
        return issueRepository.findById(id).get();
    }

    public List<IssueEntity> findAllByFolderId(Long folderId) {
        return issueRepository.findAllByFolderId(folderId);
    }

    public List<IssueEntity> findAll() {
        return issueRepository.findAll();
    }

    //special find --------------------------------------------
    public List<IssueEntity> findAllByFolderIdAndStatus(Long folderId, IssueEntity.IssueStatus status) {
        return issueRepository.findAllByFolderIdAndStatus(folderId, status);
    }

    public List<IssueEntity> findAllByFolderIdAndDone(Long folderId, Boolean done) {
        return issueRepository.findAllByFolderIdAndDone(folderId, done);
    }

    public List<IssueEntity> findAllByCreatorIdAndStatus(Long creatorId, IssueEntity.IssueStatus status) {
        return issueRepository.findAllByCreatorIdAndStatus(creatorId, status);
    }

}
