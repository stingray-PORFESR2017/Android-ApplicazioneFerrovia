package cnr.isti.traininfo;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.TrainViewHolder> {

    private final ArrayList<DATrain> mTrainList;
    private Context context;

    class TrainViewHolder extends RecyclerView.ViewHolder{

        private TextView mTrainView;
        private TextView mTimeView;
        private TextView mPlatformView;
        private TextView mContentView;

        public TrainViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrainView = itemView.findViewById(R.id.train_number);
            mTimeView = itemView.findViewById(R.id.train_time);
            mPlatformView = itemView.findViewById(R.id.platform);
            mContentView = itemView.findViewById(R.id.stoppes);
        }

        void bindTo(DATrain t) {
            mTimeView.setText(t.getTime());
            mTrainView.setText(t.getIdTrain());
            mPlatformView.setText(t.getPlatform());
            mContentView.setText(t.getContent());
        }
    }

    public TrainListAdapter(Context context, ArrayList<DATrain> trainList) {
        this.context = context;
        this.mTrainList = trainList;
    }

    @NonNull
    @Override
    public TrainListAdapter.TrainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = LayoutInflater.from(context).inflate(R.layout.trainlist_item, viewGroup, false);
        return new TrainViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainListAdapter.TrainViewHolder holder, int i) {
        DATrain mCurrent = mTrainList.get(i);
        holder.bindTo(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mTrainList.size();
    }
}
