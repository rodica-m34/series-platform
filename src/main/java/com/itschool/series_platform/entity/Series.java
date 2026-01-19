package com.itschool.series_platform.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer noOfSeasons;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type")
    private CategoryType categoryType;

    @ManyToMany(mappedBy = "series", fetch = FetchType.EAGER)
    private List<User> users;

    protected Series() {
    }

    public Series(String name, Integer noOfSeasons,  CategoryType categoryType) {
        this.name = name;
        this.noOfSeasons = noOfSeasons;
        this.categoryType = categoryType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoOfSeasons() {
        return noOfSeasons;
    }

    public void setNoOfSeasons(Integer noOfSeasons) {
        this.noOfSeasons = noOfSeasons;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}