package com.springapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "issues_table")
public class IssueEntity {
    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    private Long folderId;

    private Boolean done;

    //-------------------------------------------------------------
    protected IssueEntity() {

    }

    public IssueEntity(String name, String description, Long creatorId) {
        this.name = name;
        this.description = description;
        this.folderId = creatorId;
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

}
