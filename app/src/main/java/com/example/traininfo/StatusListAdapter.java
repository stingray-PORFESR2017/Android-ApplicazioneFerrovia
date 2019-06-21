package com.example.traininfo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class StatusListAdapter extends RecyclerView.Adapter<StatusListAdapter.StatusViewHolder> {

    private final ArrayList<Status> mStatusList;
    private Context context;

    class StatusViewHolder extends RecyclerView.ViewHolder {

        private TextView mEntityView;
        private TextView mPositionView;
        private TextView mCurrentValueView;
        public ImageView mStatus;
        public ToggleButton mToggle;
        public String colorStatus;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            mEntityView = itemView.findViewById(R.id.entity);
            mPositionView = itemView.findViewById(R.id.position);
            mCurrentValueView = itemView.findViewById(R.id.current_value);
            mStatus = itemView.findViewById(R.id.status_image);
            mToggle = itemView.findViewById(R.id.toggle);
        }

        void bindTo(Status s) {
            mPositionView.setText(s.getPosition());
            mEntityView.setText(s.getEntity());
            mCurrentValueView.setText(s.getCurrentValue());
            switch (s.getStatus()) {
                case "alert":
                    mStatus.setBackgroundColor(Color.parseColor("#FFC107"));
                    mToggle.setChecked(true);
                    colorStatus = "#FFC107";
                    break;
                case "ok":
                    mStatus.setBackgroundColor(Color.parseColor("#4CAF50"));
                    mToggle.setChecked(true);
                    colorStatus = "#4CAF50";
                    break;
                case "off":
                    mStatus.setBackgroundColor(Color.parseColor("#F44336"));
                    mToggle.setChecked(false);
                    colorStatus = "#F44336";
                    break;
            }
        }

        public String getColorStatus(){
            return colorStatus;
        }
    }

    public StatusListAdapter(Context context, ArrayList<Status> statusList) {
        this.context = context;
        this.mStatusList = statusList;
    }

    @NonNull
    @Override
    public StatusListAdapter.StatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = LayoutInflater.from(context).inflate(R.layout.statuslist_item, viewGroup, false);
        return new StatusViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusListAdapter.StatusViewHolder statusViewHolder, int i) {
        Status mCurrent = mStatusList.get(i);
        statusViewHolder.bindTo(mCurrent);
        statusViewHolder.mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    statusViewHolder.mStatus.setBackgroundColor(Color.parseColor(statusViewHolder.colorStatus));
                } else {
                    statusViewHolder.mStatus.setBackgroundColor(Color.parseColor("#F44336"));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStatusList.size();
    }
}
