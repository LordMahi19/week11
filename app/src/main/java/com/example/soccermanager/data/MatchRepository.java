package com.example.soccermanager.data;

import java.util.List;

public class MatchRepository extends Repository<Match> {
    // We can filter matches where the specified team is either home or away
    public List<Match> filterByTeam(String teamName) {
        return filter(match -> match.getHomeTeam().equalsIgnoreCase(teamName) || 
                               match.getAwayTeam().equalsIgnoreCase(teamName));
    }
}
