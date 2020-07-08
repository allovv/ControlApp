package com.springapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "collections_table")
public class CollectionEntity {
    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    private String creationDate;
    private String updateDate;

    private Long folderId;

    //-------------------------------------------------------------
    protected CollectionEntity() {}

    public CollectionEntity(String name, String description, String creationDate, Long folderId) {
        this.name = name;
        this.folderId = folderId;
        this.description = description;
        this.creationDate = creationDate;
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

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
