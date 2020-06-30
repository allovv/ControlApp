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
    private Boolean done;
    private Long listID;
    //-------------------------------------------------------------

    protected IssueEntity() {

    }

    public IssueEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
