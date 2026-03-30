package com.example.soccermanager.data;

import java.util.List;

public class PlayerRepository extends Repository<Player> {
    public List<Player> filterByTeam(String teamName) {
        return filter(player -> player.getTeam().equalsIgnoreCase(teamName));
    }
}
