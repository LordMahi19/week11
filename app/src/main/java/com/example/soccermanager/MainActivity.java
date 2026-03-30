package com.example.soccermanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.example.soccermanager.data.*;
import com.example.soccermanager.ui.EntityFragment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private TeamRepository teamRepo;
    private PlayerRepository playerRepo;
    private MatchRepository matchRepo;

    private EntityFragment teamFragment;
    private EntityFragment playerFragment;
    private EntityFragment matchFragment;

    private SearchView searchView;
    private Spinner spinnerSort;
    private ViewPager2 viewPager;

    private String currentQuery = "";
    private int currentSortPos = 0; // 0: Name (A-Z), 1: Name (Z-A)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupInsets();

        // 1. Initialize Repositories and Data
        teamRepo = new TeamRepository();
        DataProvider<Team> teamProvider = new DataProvider<>();
        teamProvider.getSampleData(Team.class).forEach(teamRepo::add);

        playerRepo = new PlayerRepository();
        DataProvider<Player> playerProvider = new DataProvider<>();
        playerProvider.getSampleData(Player.class).forEach(playerRepo::add);

        matchRepo = new MatchRepository();
        DataProvider<Match> matchProvider = new DataProvider<>();
        matchProvider.getSampleData(Match.class).forEach(matchRepo::add);

        // Iterate through Teams using Custom Iterator to print/log them (demonstration criteria)
        TeamIterator iterator = new TeamIterator(teamRepo.getAll());
        while(iterator.hasNext()) {
            Team t = iterator.next();
            System.out.println("Loaded team: " + t.getName());
        }

        // 2. Initialize UI Components
        searchView = findViewById(R.id.searchView);
        spinnerSort = findViewById(R.id.spinnerSort);
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Setup fragments
        teamFragment = new EntityFragment();
        playerFragment = new EntityFragment();
        matchFragment = new EntityFragment();

        viewPager.setAdapter(new ViewPagerAdapter(this));

        // Connect TabLayout with ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Teams"); break;
                case 1: tab.setText("Players"); break;
                case 2: tab.setText("Matches"); break;
            }
        }).attach();

        // Setup SearchView with lambda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                refreshCurrentTab();
                return true;
            }
        });

        // Setup Spinner
        String[] sortOptions = {"Sort by Name (A-Z)", "Sort by Name (Z-A)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortOptions);
        spinnerSort.setAdapter(adapter);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSortPos = position;
                refreshCurrentTab();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // When swiping tabs, refresh the specific list
                refreshCurrentTab();
            }
        });

        // Initial Data Load
        viewPager.post(this::refreshCurrentTab);
    }

    private void refreshCurrentTab() {
        int position = viewPager.getCurrentItem();
        boolean sortAsc = (currentSortPos == 0);

        // Sorting Lambda (Comparator)
        Comparator<SoccerEntity> comparator = (e1, e2) -> {
            int res = e1.getName().compareToIgnoreCase(e2.getName());
            return sortAsc ? res : -res;
        };

        if (position == 0) { // Teams
            List<Team> filtered = teamRepo.filter(t -> TextUtils.isEmpty(currentQuery) || 
                                                       t.getName().toLowerCase().contains(currentQuery.toLowerCase()));
            // Streams API with Lambda transformation/sorting
            List<Team> sorted = filtered.stream().sorted(comparator).collect(Collectors.toList());
            teamFragment.updateList(sorted);
        } else if (position == 1) { // Players
            List<Player> filtered = playerRepo.filter(p -> TextUtils.isEmpty(currentQuery) || 
                                                           p.getName().toLowerCase().contains(currentQuery.toLowerCase()));
            List<Player> sorted = filtered.stream().sorted(comparator).collect(Collectors.toList());
            playerFragment.updateList(sorted);
        } else if (position == 2) { // Matches
            List<Match> filtered = matchRepo.filter(m -> TextUtils.isEmpty(currentQuery) || 
                                                         m.getName().toLowerCase().contains(currentQuery.toLowerCase()));
            List<Match> sorted = filtered.stream().sorted(comparator).collect(Collectors.toList());
            matchFragment.updateList(sorted);
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: return teamFragment;
                case 1: return playerFragment;
                case 2: return matchFragment;
                default: return teamFragment;
            }
        }
        @Override
        public int getItemCount() {
            return 3;
        }
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}