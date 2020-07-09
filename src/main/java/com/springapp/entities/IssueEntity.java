package com.springapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "issues_table")
public class IssueEntity {
    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Название задачи не должно быть пустым.")
    private String name;

    @Size(max=255, message = "Максимальная длина описания 255 символов.")
    private String description;

    @NotNull(message = "Идентификатор области при создании задачи не должен быть пустым.")
    private Long folderId;

    private Boolean done = false;

    //-------------------------------------------------------------
    protected IssueEntity() {}

    public IssueEntity(String name, Long folderId) {
        this.name = name;
        this.description = "";
        this.folderId = folderId;
        done = false;
    }

    public IssueEntity(String name, String description, Long folderId) {
        this.name = name;
        this.description = description;
        this.folderId = folderId;
        done = false;
    }

    //-------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    //-------------------------------------------------------------
    public boolean isDone() {
        return done;
    }
}
