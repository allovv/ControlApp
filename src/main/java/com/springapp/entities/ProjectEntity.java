package com.springapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "projects_table")
public class ProjectEntity {
    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private String description;

    private String creationDate;
    private String updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity creator;
    //-------------------------------------------------------------

    ProjectEntity() {}

    ProjectEntity(UserEntity creator, String description, String name) {
        this.name = name;
        this.description = description;
        this.creationDate = "";
        this.updateDate = creationDate;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }
}
