package com.skg.achilespoc.entity;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.UDT;

import java.util.List;

@UDT(keyspace="testkeyspace", name = "bio_udt")
public class Biography
{
    @Column
    private String birthPlace;

    @Column
    private List<String> diplomas;

    @Column
    private String description;

    //Getters and Setters

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public List<String> getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(List<String> diplomas) {
        this.diplomas = diplomas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  "Bio Details---\ndescription: " + description +
                "\nbirthPlace: " + birthPlace +
                "\ndiplomas: " + diplomas;

    }
}