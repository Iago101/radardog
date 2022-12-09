package br.edu.ifsp.arq.ads.dmos5.radardog.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.util.UUID;

public class Dog {

    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String breed;
    private String description;
    private String lastplace;
    private String gender;

    public Dog(String name, String breed, String description, String lastplace,
                String gender) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.breed = breed;
        this.description = description;
        this.lastplace = lastplace;
        this.gender = gender;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getLastplace() {
        return lastplace;
    }

    public void setLastplace(String lastplace) {
        this.lastplace = lastplace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
