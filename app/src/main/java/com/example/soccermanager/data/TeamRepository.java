package com.example.soccermanager.data;

import java.util.List;

public class TeamRepository extends Repository<Team> {
    public List<Team> filterByLeague(String league) {
        return filter(team -> team.getLeague().equalsIgnoreCase(league));
    }
}
