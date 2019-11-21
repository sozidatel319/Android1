package com.example.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DaysOfWeekAdapter extends RecyclerView.Adapter<DaysOfWeekAdapter.ViewHolder> {

    private String[] daysofweek;

    public DaysOfWeekAdapter(String data[]) {
        daysofweek = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dayofweek_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setLabel(daysofweek[position]);
    }

    @Override
    public int getItemCount() {
        return daysofweek.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
        }

        void setLabel(String text) {
            label.setText(text);
        }
    }
}
