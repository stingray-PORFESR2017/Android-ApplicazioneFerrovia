package com.example.traininfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

public class CmadExtendedInfoListAdapter extends RecyclerView.Adapter<CmadExtendedInfoListAdapter.CmadExtendedInfoViewHolder> {

    private final Cmad s;
    private final ArrayList<Madred> madredList;
    private final ArrayList<Madill> madillList;
    private final ArrayList<String[]> map=new ArrayList<>();
    private Context context;
    private OnCmadExtendedInfoListener mOnCmadExtendedInfoListener;


    class CmadExtendedInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnCmadExtendedInfoListener onCmadExtendedInfoListener;
        private TextView mMadredMacAdr;
        private TextView mMadredDate;
        private ImageView mMadredImg;


        public CmadExtendedInfoViewHolder(@NonNull View itemView, OnCmadExtendedInfoListener onCmadExtendedInfoListener) {
            super(itemView);
            this.onCmadExtendedInfoListener=onCmadExtendedInfoListener;
            itemView.setOnClickListener(this);

            mMadredMacAdr= itemView.findViewById(R.id.madredMacAdr);
            mMadredDate= itemView.findViewById(R.id.madredDate);
            mMadredImg=itemView.findViewById(R.id.madredImg);

        }

        void bindTo(String[] m) {

            mMadredMacAdr.setText(m[0]);
            mMadredDate.setText(m[1]);
            switch (m[2]) {
                case "madred":
                    mMadredImg.setImageResource(R.drawable.firemad);
                    break;
                case "madill":
                    mMadredImg.setImageResource(R.drawable.lightmad);
                    break;
            }
        }
        @Override
        public void onClick(View v) {
            onCmadExtendedInfoListener.OnMadClick(getAdapterPosition());
        }

    }

    public CmadExtendedInfoListAdapter(Context context, Cmad s, ArrayList<Madred> mr, ArrayList<Madill> ml, OnCmadExtendedInfoListener mOnCmadExtendedInfoListener) {
        this.context = context;
        this.s = s;
        madredList=mr;
        madillList=ml;
        int i=1;
        for(Madred m:mr){
            String[] ar=new String[3];
            ar[0]="MADRED "+i+" ADR: "+m.getMadredMacAdr();
            ar[1]="DATE: "+m.getMadredDate();
            ar[2]="madred";
            map.add(ar);
            i++;
        }
        i=1;
        for(Madill m:ml){
            String[] ar=new String[3];
            ar[0]="MADILL "+i+" ADR: "+m.getMadillMacAdr();
            ar[1]="DATE: "+m.getMadillDate();
            ar[2]="madill";
            map.add(ar);
            i++;
        }
        this.mOnCmadExtendedInfoListener=mOnCmadExtendedInfoListener;
    }

    public void update(ArrayList<Madred> mr, ArrayList<Madill> ml){
        madillList.clear();
        madillList.addAll(ml);
        madredList.clear();
        madredList.addAll(mr);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CmadExtendedInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = LayoutInflater.from(context).inflate(R.layout.cmadextendedinfolist_item, viewGroup, false);

        return new CmadExtendedInfoViewHolder(mItemView,mOnCmadExtendedInfoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final CmadExtendedInfoListAdapter.CmadExtendedInfoViewHolder statusViewHolder, int i) {
        String[] mCurrent = map.get(i);
        statusViewHolder.bindTo(mCurrent);

    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public interface OnCmadExtendedInfoListener{
        void OnMadClick(int position);
    }
}
