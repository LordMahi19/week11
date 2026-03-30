package com.example.soccermanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccermanager.R;
import com.example.soccermanager.data.SoccerEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityFragment extends Fragment {

    private EntityAdapter<SoccerEntity> adapter;
    private List<SoccerEntity> currentList = new ArrayList<>();

    public EntityFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entity_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Setup adapter with lambda for click handler
        adapter = new EntityAdapter<>(currentList, item -> {
            Toast.makeText(getContext(), "Selected: " + item.getName(), Toast.LENGTH_SHORT).show();
        });
        
        recyclerView.setAdapter(adapter);
        return view;
    }

    @SuppressWarnings("unchecked")
    public void updateList(List<? extends SoccerEntity> items) {
        currentList = (List<SoccerEntity>) items;
        if (adapter != null) {
            adapter.updateData(currentList);
        }
    }
}
