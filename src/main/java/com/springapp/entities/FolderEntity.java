package com.springapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "folders_table")
public class FolderEntity {
    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Название не может быть пустым.")
    @Size(max=30, message = "Название обалсти не должно превышать 30 символов.")
    private String name;

    @NotNull(message = "Идентификатор создателя не может быть пустым.")
    private Long creatorId;

    //-------------------------------------------------------------
    protected FolderEntity() {}

    public FolderEntity(String name, Long creatorId) {
        this.name = name;
        this.creatorId = creatorId;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    //-------------------------------------------------------------
}
