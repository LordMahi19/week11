package com.example.soccermanager.data;

import java.util.UUID;

public class Player implements SoccerEntity {
    private String id;
    private String name;
    private int age;
    private String country;
    private String position;
    private String team;
    private int number;

    public Player(String name, int age, String country, String position, String team, int number) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.country = country;
        this.position = position;
        this.team = team;
        this.number = number;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }

    public int getNumber() {
        return number;
    }
}
