package com.springapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.TreeSet;

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

    @NotNull(message = "Статус задачи не должен быть пустым.")
    private Boolean done = false;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "tag_id"))
    private Set<String> tagsContainer = new TreeSet<>(); //для удобства вывода в шаблоне используется контейнейр с тегами

    @Size(max=50, message = "Вы превысили максимальное количество тегов.")
    private String tagsNoParsing = ""; //теги одной строкой, разделенные ","

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

    public Set<String> getTagsContainer() {
        return tagsContainer;
    }

    public String getTagsNoParsing() {
        return tagsNoParsing;
    }

    /**
     * Теги устанавливаются в поле tagsNoParsing
     * строкой без пробелов, разделенные запятыми
     */
    public void setTagsNoParsing(String tagsNoParsing) {
        //убрать все пробелы из строки
        tagsNoParsing = tagsNoParsing.replace(" ", "");

        //установка тегов и обновление контейнера с тегами
        this.tagsNoParsing = tagsNoParsing;
        this.updateTagsContainer(tagsNoParsing);
    }

    //-------------------------------------------------------------
    public boolean isDone() {
        return done;
    }

    private void updateTagsContainer(String tagsNoPars) {
        if (tagsNoPars == null) {
            return;
        }
        //установка тегов
        String[] tagsArray = tagsNoPars.split(",");
        TreeSet<String> tagsSet = new TreeSet<>();
        for (String tag: tagsArray) {
            if (!tag.isEmpty()) {
                tagsSet.add(tag);
            }
        }

        this.tagsContainer = tagsSet;
    }
}
