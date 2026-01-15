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

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "series")
    private List<User> users;

    protected Series() {
    }

    public Series(String name, Integer noOfSeasons) {
        this.name = name;
        this.noOfSeasons = noOfSeasons;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}