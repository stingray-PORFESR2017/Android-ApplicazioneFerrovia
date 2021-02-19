package cnr.isti.traininfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MeteoListAdapter  extends RecyclerView.Adapter<MeteoListAdapter.MeteoViewHolder> {

    private final ArrayList<Station> mStationList;
    private Context context;

    public MeteoListAdapter(Context context, ArrayList<Station> stationList) {
        this.context = context;
        this.mStationList = stationList;

    }

    class MeteoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mStation;
        private ImageView mRank;
        private Station s;

        void bindTo(Station s) {
            this.s = s;
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

        public MeteoViewHolder(@NonNull View itemView) {
            super(itemView);
            mStation = itemView.findViewById(R.id.station_text);
            mRank = itemView.findViewById(R.id.rank_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();
            Station station = mStationList.get(pos);
            new AsyncTaskTrain(context, station).execute(100, 100);
        }
    }

    @NonNull
    @Override
    public MeteoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(context).inflate(R.layout.stationlist_item, parent, false);
        return new MeteoListAdapter.MeteoViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeteoViewHolder holder, int position) {
        Station mCurrent = mStationList.get(position);
        holder.bindTo(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mStationList.size();
    }


}
