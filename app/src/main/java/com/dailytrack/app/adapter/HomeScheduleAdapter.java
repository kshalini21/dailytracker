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
import com.dailytrack.app.data.db.entity.ClassScheduleEntity;

public class HomeScheduleAdapter extends ListAdapter<ClassScheduleEntity, HomeScheduleAdapter.VH> {

    public interface OnScheduleClick { void onClick(ClassScheduleEntity s); }
    private final OnScheduleClick listener;

    public HomeScheduleAdapter(OnScheduleClick l) {
        super(new DiffUtil.ItemCallback<ClassScheduleEntity>() {
            @Override public boolean areItemsTheSame(@NonNull ClassScheduleEntity a, @NonNull ClassScheduleEntity b) { return a.id == b.id; }
            @Override public boolean areContentsTheSame(@NonNull ClassScheduleEntity a, @NonNull ClassScheduleEntity b) { return a.subject.equals(b.subject) && a.startTime.equals(b.startTime); }
        });
        this.listener = l;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int vt) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_home_schedule, p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        ClassScheduleEntity s = getItem(pos);
        h.tvTime.setText(s.startTime);
        h.tvSubject.setText(s.subject);
        h.tvRoom.setText(s.room.isEmpty() ? s.teacher : s.room);
        h.itemView.setOnClickListener(v -> listener.onClick(s));
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTime, tvSubject, tvRoom;
        VH(View v) {
            super(v);
            tvTime = v.findViewById(R.id.tv_time);
            tvSubject = v.findViewById(R.id.tv_subject);
            tvRoom = v.findViewById(R.id.tv_room);
        }
    }
}
