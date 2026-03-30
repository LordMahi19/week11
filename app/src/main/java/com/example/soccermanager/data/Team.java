package com.example.soccermanager.data;

import java.util.UUID;

public class Team implements SoccerEntity {
    private String id;
    private String name;
    private String country;
    private String league;
    private String stadium;
    private int yearFounded;

    public Team(String name, String country, String league, String stadium, int yearFounded) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.country = country;
        this.league = league;
        this.stadium = stadium;
        this.yearFounded = yearFounded;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getLeague() {
        return league;
    }

    public String getStadium() {
        return stadium;
    }

    public int getYearFounded() {
        return yearFounded;
    }
}
