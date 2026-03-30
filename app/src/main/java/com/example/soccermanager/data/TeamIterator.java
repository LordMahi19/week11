package com.example.soccermanager.data;

import java.util.List;

public class TeamIterator implements CustomIterator<Team> {
    private List<Team> teams;
    private int currentIndex = 0;

    public TeamIterator(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < teams.size();
    }

    @Override
    public Team next() {
        if (!hasNext()) {
            return null;
        }
        return teams.get(currentIndex++);
    }
}
