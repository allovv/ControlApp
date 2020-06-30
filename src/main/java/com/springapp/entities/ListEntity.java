package com.springapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "lists_table")
public class ListEntity {
    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProjectEntity fromProject;
    //-------------------------------------------------------------

    ListEntity() {}

    ListEntity(String name, ProjectEntity fromProject) {
        this.name = name;
        this.fromProject = fromProject;
    }

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

    public ProjectEntity getFromProject() {
        return fromProject;
    }

    public void setFromProject(ProjectEntity fromProject) {
        this.fromProject = fromProject;
    }
}
