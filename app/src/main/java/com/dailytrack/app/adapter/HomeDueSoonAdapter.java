package com.dailytrack.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.dailytrack.app.R;
import com.dailytrack.app.data.db.entity.TaskEntity;
import com.dailytrack.app.util.DateUtils;

public class HomeDueSoonAdapter extends ListAdapter<TaskEntity, HomeDueSoonAdapter.VH> {

    public interface OnTaskClick { void onClick(TaskEntity t); }
    private final OnTaskClick listener;

    public HomeDueSoonAdapter(OnTaskClick l) {
        super(new DiffUtil.ItemCallback<TaskEntity>() {
            @Override public boolean areItemsTheSame(@NonNull TaskEntity a, @NonNull TaskEntity b) { return a.id == b.id; }
            @Override public boolean areContentsTheSame(@NonNull TaskEntity a, @NonNull TaskEntity b) { return a.title.equals(b.title) && a.dueDate == b.dueDate; }
        });
        this.listener = l;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int vt) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_due_soon, p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        TaskEntity t = getItem(pos);
        h.tvTitle.setText(t.title);
        h.tvCat.setText(t.category);
        h.tvDue.setText("Due " + DateUtils.displayDate(t.dueDate));
        // Priority dot color
        int col;
        switch (t.priority) {
            case "High":   col = h.itemView.getContext().getColor(R.color.priority_high);   break;
            case "Low":    col = h.itemView.getContext().getColor(R.color.priority_low);    break;
            default:       col = h.itemView.getContext().getColor(R.color.priority_medium); break;
        }
        h.vPriority.setBackgroundColor(col);
        h.itemView.setOnClickListener(v -> listener.onClick(t));
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCat, tvDue; View vPriority;
        VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_task_title);
            tvCat = v.findViewById(R.id.tv_category);
            tvDue = v.findViewById(R.id.tv_due);
            vPriority = v.findViewById(R.id.v_priority);
        }
    }
}
