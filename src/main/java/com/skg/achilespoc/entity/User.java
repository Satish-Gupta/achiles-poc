package com.skg.achilespoc.entity;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.Frozen;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;

import java.util.List;
import java.util.Map;

@Table(table="users")
public class User
{
    @PartitionKey
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private Integer age;

    @Column @Frozen
    private Biography bio;

    @Column
    private List<String> favoriteTags;

    @Column
    private Map<Integer,String> preferences;

    // Getters and setters ...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Biography getBio() {
        return bio;
    }

    public void setBio(Biography bio) {
        this.bio = bio;
    }

    public List<String> getFavoriteTags() {
        return favoriteTags;
    }

    public void setFavoriteTags(List<String> favoriteTags) {
        this.favoriteTags = favoriteTags;
    }

    public Map<Integer, String> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<Integer, String> preferences) {
        this.preferences = preferences;
    }

    @Override
    public String toString() {
        return  "User Details---\nId: " + id +
                "\nFirstName: " + firstname +
                "\nLastName: " + lastname +
                "\nage: " + age +
                "\npreferences: " + preferences +
                "\nfavoriteTags: " + favoriteTags +
                "\nBio: " + bio;
    }
}