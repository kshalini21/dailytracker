package com.dailytrack.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.dailytrack.app.R;
import com.dailytrack.app.data.db.entity.TaskEntity;
import com.dailytrack.app.util.DateUtils;

public class HomeTodayTaskAdapter extends ListAdapter<TaskEntity, HomeTodayTaskAdapter.VH> {

    public interface OnTaskToggle { void onToggle(TaskEntity task); }
    private final OnTaskToggle listener;

    public HomeTodayTaskAdapter(OnTaskToggle l) {
        super(new DiffUtil.ItemCallback<TaskEntity>() {
            @Override public boolean areItemsTheSame(@NonNull TaskEntity a, @NonNull TaskEntity b) { return a.id == b.id; }
            @Override public boolean areContentsTheSame(@NonNull TaskEntity a, @NonNull TaskEntity b) { return a.isCompleted == b.isCompleted && a.title.equals(b.title); }
        });
        this.listener = l;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int vt) {
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_home_task, p, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        TaskEntity t = getItem(pos);
        h.cb.setChecked(t.isCompleted);
        h.tvTitle.setText(t.title);
        h.tvTitle.setAlpha(t.isCompleted ? 0.5f : 1f);
        h.tvDue.setText(t.dueDate > 0 ? DateUtils.displayDate(t.dueDate) : "");
        h.tvCat.setText(t.category);
        h.cb.setOnClickListener(v -> listener.onToggle(t));
        h.itemView.setOnClickListener(v -> listener.onToggle(t));
    }

    static class VH extends RecyclerView.ViewHolder {
        CheckBox cb; TextView tvTitle, tvDue, tvCat;
        VH(View v) {
            super(v);
            cb = v.findViewById(R.id.cb_task);
            tvTitle = v.findViewById(R.id.tv_task_title);
            tvDue = v.findViewById(R.id.tv_task_due);
            tvCat = v.findViewById(R.id.tv_task_cat);
        }
    }
}
