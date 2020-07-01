package com.springapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "lists_table")
public class ListEntity {
    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    //@ManyToOne(fetch = FetchType.EAGER)
    private Long projectID; //TODO: id or Entity?
    //-------------------------------------------------------------

    protected ListEntity() {}

    public ListEntity(String name, Long projectID) {
        this.name = name;
        this.projectID = projectID;
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

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }
}
