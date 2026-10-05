package com.dailytrack.app.ui.onboarding;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dailytrack.app.R;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.VH> {

    private final Context context;
    private EditText nameEt;

    private final String[] emojis = {"📋","📚","🚀","👋"};
    private final String[] titles = {
        "Track what matters,\nnot just what's urgent.",
        "Create habits,\nmanage your classes\nand stay consistent.",
        "Make progress\nevery single day.",
        "What should we call you?"
    };
    private final String[] subs = {
        "Your daily goals, tasks and schedule — all in one place.",
        "Build routines that stick and never miss a class again.",
        "Small actions compound into big results over time.",
        "We'll personalize your DailyTrack experience."
    };

    public OnboardingAdapter(Context ctx) { this.context = ctx; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int vt) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(vt == 3 ? R.layout.page_onboarding_name : R.layout.page_onboarding, parent, false);
        return new VH(v);
    }

    @Override public int getItemViewType(int pos) { return pos; }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        if (h.tvEmoji != null) h.tvEmoji.setText(emojis[pos]);
        if (h.tvTitle != null) h.tvTitle.setText(titles[pos]);
        if (h.tvSub != null) h.tvSub.setText(subs[pos]);
        if (h.etName != null) nameEt = h.etName;
    }

    @Override public int getItemCount() { return 4; }

    public String getEnteredName() {
        if (nameEt != null && !TextUtils.isEmpty(nameEt.getText()))
            return nameEt.getText().toString().trim();
        return "";
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvTitle, tvSub; EditText etName;
        VH(View v) {
            super(v);
            tvEmoji = v.findViewById(R.id.tv_emoji);
            tvTitle = v.findViewById(R.id.tv_title);
            tvSub   = v.findViewById(R.id.tv_subtitle);
            etName  = v.findViewById(R.id.et_name);
        }
    }
}
