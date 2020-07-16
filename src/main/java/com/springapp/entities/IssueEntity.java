package com.springapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "issues_table")
public class IssueEntity {
    //-------------------------------------------------------------
    public enum IssueStatus {
        DELETE, COMMON, IMPORTANT;
    }

    //-------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Название задачи не должно быть пустым.")
    private String name;

    @Size(max=255, message = "Максимальная длина описания 255 символов.")
    private String description = "";

    private Long folderId;

    @NotNull(message = "Идентификатор создателя при создании задачи не должен быть пустым.")
    private Long creatorId;

    @NotNull(message = "Статус задачи не должен быть пустым.")
    private IssueStatus status = IssueStatus.COMMON;

    @NotNull(message = "Статус задачи не должен быть пустым.")
    private Boolean done = false;

    private String createDate = "";

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "tag_id"))
    private Set<String> tagsContainer = new TreeSet<>(); //для удобства вывода в шаблоне используется контейнейр с тегами

    @Size(max=50, message = "Вы превысили максимальное количество тегов.")
    private String tagsNoParsing = ""; //теги одной строкой, разделенные ","

    //-------------------------------------------------------------
    protected IssueEntity() {}

    public IssueEntity(String name, Long creatorId) {
        this.name = name;
        this.description = "";
        this.creatorId = creatorId;
        this.done = false;

        //установка даты
        ZonedDateTime date = LocalDateTime.now().atZone(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.createDate = date.format(dateTimeFormatter);

        status = IssueStatus.COMMON;
    }

    public IssueEntity(String name, String description, Long creatorId, Long folderId) {
        this.name = name;
        this.description = description;
        this.creatorId = creatorId;
        this.folderId = folderId;
        this.done = false;

        //установка даты
        ZonedDateTime date = LocalDateTime.now().atZone(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.createDate = date.format(dateTimeFormatter);

        status = IssueStatus.COMMON;
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

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    private void setTagsContainer(Set<String> tagsContainer) {
        //не требуется этот метод
        this.tagsContainer = tagsContainer;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
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
    public void changeDone() {
        done = !done;
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
