package com.example.soccermanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.soccermanager.R;
import com.example.soccermanager.data.Match;
import com.example.soccermanager.data.Player;
import com.example.soccermanager.data.SoccerEntity;
import com.example.soccermanager.data.Team;
import java.util.List;
import java.util.function.Consumer;

public class EntityAdapter<T extends SoccerEntity> extends RecyclerView.Adapter<EntityAdapter.EntityViewHolder> {

    private List<T> items;
    private final Consumer<T> onItemClick;

    public EntityAdapter(List<T> items, Consumer<T> onItemClick) {
        this.items = items;
        this.onItemClick = onItemClick;
    }

    public void updateData(List<T> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EntityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entity, parent, false);
        return new EntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityViewHolder holder, int position) {
        T item = items.get(position);
        holder.tvName.setText(item.getName());
        
        // Show some custom text based on type, optional but nice
        if(item instanceof Team) {
            Team t = (Team) item;
            holder.tvDetails.setText("League: " + t.getLeague() + " | Country: " + t.getCountry());
        } else if(item instanceof Player) {
            Player p = (Player) item;
            holder.tvDetails.setText("Pos: " + p.getPosition() + " | Team: " + p.getTeam());
        } else if(item instanceof Match) {
            Match m = (Match) item;
            holder.tvDetails.setText("Date: " + m.getDate() + " | " + m.getLeague());
        } else {
            holder.tvDetails.setText(item.getId());
        }

        // Use lambda for click callback as required
        holder.itemView.setOnClickListener(v -> onItemClick.accept(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class EntityViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDetails;

        public EntityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvEntityName);
            tvDetails = itemView.findViewById(R.id.tvEntityDetails);
        }
    }
}
