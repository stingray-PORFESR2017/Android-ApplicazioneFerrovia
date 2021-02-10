package cnr.isti.traininfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CmadListAdapter extends RecyclerView.Adapter<CmadListAdapter.StatusViewHolder> {

    private final ArrayList<Cmad> mCmadList;
    private Context context;
    private OnStatusListener mOnStatusListener;

    class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnStatusListener onStatusListener;
        private TextView mEntityView;
        private TextView mDateView;
        private TextView mCmadHeader;
        private TextView mCmadType;
        private TextView mCmadLongitude;
        private TextView mCmadLatitude;

        public StatusViewHolder(@NonNull View itemView, OnStatusListener onStatusListener) {
            super(itemView);
            this.onStatusListener=onStatusListener;
            itemView.setOnClickListener(this);
            mEntityView = itemView.findViewById(R.id.cmadMacadd);
            mDateView = itemView.findViewById(R.id.cmadDate);
            mCmadHeader=itemView.findViewById(R.id.cmadHeader);
            mCmadType=itemView.findViewById(R.id.cmadType);
            mCmadLongitude = itemView.findViewById(R.id.cmadLongitude);
            mCmadLatitude = itemView.findViewById(R.id.cmadLatitude);

            mEntityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    String s=(String)mEntityView.getText();
                    String h=s.replace("MAC_ADR : ","");
                    ClipData clip = ClipData.newPlainText("text", h);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context.getApplicationContext(), R.string.copied_to_clipboard,Toast.LENGTH_SHORT).show();


                }
            });
            mCmadLongitude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    String s=(String)mCmadLongitude.getText();
                    String h=s.replace("CMAD_LONGITUDE: ","");
                    ClipData clip = ClipData.newPlainText("text", h);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context.getApplicationContext(), R.string.copied_to_clipboard,Toast.LENGTH_SHORT).show();

                }
            });
            mCmadLatitude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    String s=(String)mCmadLatitude.getText();
                    String h=s.replace("CMAD_LATITUDE: ","");
                    ClipData clip = ClipData.newPlainText("text", h);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context.getApplicationContext(), R.string.copied_to_clipboard,Toast.LENGTH_SHORT).show();

                }
            });
        }

        void bindTo(Cmad s) {

            mEntityView.setText("MAC_ADR : "+s.getEntity());
            mDateView.setText("DATE: "+s.getDate());
            mCmadHeader.setText("CMAD_HEADER: "+s.getCmadHeader());
            mCmadType.setText("CMAD_TYPE: "+s.getCmadType());
            mCmadLongitude.setText("CMAD_LONGITUDE: "+s.getCmadLongitude());
            mCmadLatitude.setText("CMAD_LATITUDE: "+s.getCmadLatitude());
        }
        @Override
        public void onClick(View v) {
            onStatusListener.OnCmadClick(getAdapterPosition());
        }
    }

    public CmadListAdapter(Context context, ArrayList<Cmad> cmadList, OnStatusListener onStatusListener) {
        this.context = context;
        this.mCmadList = cmadList;
        this.mOnStatusListener=onStatusListener;
    }
    public void update(ArrayList<Cmad> cmadList){
        mCmadList.clear();
        mCmadList.addAll(cmadList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CmadListAdapter.StatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = LayoutInflater.from(context).inflate(R.layout.statuslist_item, viewGroup, false);

        return new StatusViewHolder(mItemView,mOnStatusListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final CmadListAdapter.StatusViewHolder statusViewHolder, int i) {
        Cmad mCurrent = mCmadList.get(i);
        statusViewHolder.bindTo(mCurrent);
      /*  statusViewHolder.mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    statusViewHolder.mStatus.setBackgroundColor(Color.parseColor(statusViewHolder.colorStatus));
                } else {
                    statusViewHolder.mStatus.setBackgroundColor(Color.parseColor("#F44336"));
                }
            }
        }); */
    }

    @Override
    public int getItemCount() {
        return mCmadList.size();
    }

    public interface OnStatusListener{
        void OnCmadClick(int position);
    }
}