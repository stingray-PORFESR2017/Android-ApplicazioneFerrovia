package com.example.traininfo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.StationViewHolder> {

    private final ArrayList<Station> mStationList;
    private Context context;
    private int type;

    class StationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mStation;
        private ImageView mRank;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
            mStation = itemView.findViewById(R.id.station_text);
            mRank = itemView.findViewById(R.id.rank_image);
            itemView.setOnClickListener(this);
        }

        void bindTo(Station s) {
            mStation.setText(s.getStation());
            switch (s.getRank()) {
                case 1:
                    mRank.setImageResource(R.drawable.diamond);
                    break;
                case 2:
                    mRank.setImageResource(R.drawable.gold);
                    break;
                case 3:
                    mRank.setImageResource(R.drawable.silver);
                    break;
                case 4:
                    mRank.setImageResource(R.drawable.bronze);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            String station = mStationList.get(pos).getStation();
            new AsyncTaskTrain(context, station).execute(1, type);

        }
    }

    public StationListAdapter(Context context, ArrayList<Station> stationList, int type) {
        this.context = context;
        this.mStationList = stationList;
        this.type = type;
    }

    @NonNull
    @Override
    public StationListAdapter.StationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = LayoutInflater.from(context).inflate(R.layout.stationlist_item, viewGroup, false);
        return new StationViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StationListAdapter.StationViewHolder stationViewHolder, int i) {
        Station mCurrent = mStationList.get(i);
        stationViewHolder.bindTo(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mStationList.size();
    }
}
