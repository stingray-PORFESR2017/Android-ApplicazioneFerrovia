package com.example.traininfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import java.util.ArrayList;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CmadExtendedInfoActivity extends AppCompatActivity implements CmadExtendedInfoListAdapter.OnCmadExtendedInfoListener{
    RecyclerView mRecyclerView;
    private Cmad ml;
    private CmadExtendedInfoListAdapter sAdapter;
    private ArrayList<Madred> madredList;
    private ArrayList<Madill> madillList;
    private TextView mCmadRevision;
    private TextView mCmadPosition;
    private TextView mCmadDescription;
    private TextView mCmadDigitalInfo;
    private TextView mTempEst;
    private TextView mLux;
    private TextView mTensioneL1;
    private TextView mTensioneL2;
    private TextView mTensioneL3;
    private TextView mCorrenteL1;
    private TextView mCorrenteL2;
    private TextView mCorrenteL3;
    private TextView mPotenzaAttivaL1;
    private TextView mPotenzaAttivaL2;
    private TextView mPotenzaAttivaL3;
    private TextView mPotenzaReattivaL1;
    private TextView mPotenzaReattivaL2;
    private TextView mPotenzaReattivaL3;
    private TextView mFattorePotenzaL1;
    private TextView mFattorePotenzaL2;
    private TextView mFattorePotenzaL3;
    private TextView mTempSuolo;
    private TextView mEnergiaAttiva;
    private TextView mEnergiaReattiva;
    private TextView mRawBase64;
    private TextView mCrc;
    private ArrayList<String> s=new ArrayList<>();
    private ArrayList<TextView> t=new ArrayList<>();

    private Button buttonLuciOn;
    private Button buttonLuciOff;
    private Button buttonRedOn;
    private Button buttonRedOff;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmad_extended_info);

        Intent intent = getIntent();
        ml = intent.getParcelableExtra("cmad");

        s = new ArrayList<>();

        s.add(ml.getCmadRevision());
        s.add(ml.getCmadPosition());
        s.add(ml.getCmadDescription());
        s.add(ml.getCmadDigitalInfo());
        s.add(ml.getTempEst());
        s.add(ml.getLux());
        s.add(ml.getTensioneL1());
        s.add(ml.getTensioneL2());
        s.add(ml.getTensioneL3());
        s.add(ml.getCorrenteL1());
        s.add(ml.getCorrenteL2());
        s.add(ml.getCorrenteL3());
        s.add(ml.getPotenzaAttivaL1());
        s.add(ml.getPotenzaAttivaL2());
        s.add(ml.getPotenzaAttivaL3());
        s.add(ml.getPotenzaReattivaL1());
        s.add(ml.getPotenzaReattivaL2());
        s.add(ml.getPotenzaReattivaL3());
        s.add(ml.getFattorePotenzaL1());
        s.add(ml.getFattorePotenzaL2());
        s.add(ml.getFattorePotenzaL3());
        s.add(ml.getTempSuolo());
        s.add(ml.getEnergiaAttiva());
        s.add(ml.getEnergiaReattiva());

        mCmadRevision = findViewById(R.id.cmadRevision);
        mCmadPosition = findViewById(R.id.cmadPosition);
        mCmadDescription = findViewById(R.id.cmadDescription);
        mCmadDigitalInfo = findViewById(R.id.cmadDigitalInfo);
        mTempEst = findViewById(R.id.tempEst);
        mLux = findViewById(R.id.lux);
        mTensioneL1 = findViewById(R.id.tensioneL1);
        mTensioneL2 = findViewById(R.id.tensioneL2);
        mTensioneL3 = findViewById(R.id.tensioneL3);
        mCorrenteL1 = findViewById(R.id.correnteL1);
        mCorrenteL2 = findViewById(R.id.correnteL2);
        mCorrenteL3 = findViewById(R.id.correnteL3);
        mPotenzaAttivaL1 = findViewById(R.id.potenzaAttivaL1);
        mPotenzaAttivaL2 = findViewById(R.id.potenzaAttivaL2);
        mPotenzaAttivaL3 = findViewById(R.id.potenzaAttivaL3);
        mPotenzaReattivaL1 = findViewById(R.id.potenzaReattivaL1);
        mPotenzaReattivaL2 = findViewById(R.id.potenzaReattivaL2);
        mPotenzaReattivaL3 = findViewById(R.id.potenzaReattivaL3);
        mFattorePotenzaL1 = findViewById(R.id.fattorePotenzaL1);
        mFattorePotenzaL2 = findViewById(R.id.fattorePotenzaL2);
        mFattorePotenzaL3 = findViewById(R.id.fattorePotenzaL3);
        mTempSuolo = findViewById(R.id.tempSuolo);
        mEnergiaAttiva = findViewById(R.id.energiaAttiva);
        mEnergiaReattiva = findViewById(R.id.energiaReattiva);
        //         mRawBase64 =findViewById(R.id.cmadRaw);
        //         mCrc =findViewById(R.id.cmadCrc);


        //gestione bottoni comandi
        buttonRedOn= findViewById(R.id.buttonRedOn);
        buttonRedOff = findViewById(R.id.buttonRedOff);
        buttonLuciOn= findViewById(R.id.buttonLuciOn);
        buttonLuciOff= findViewById(R.id.buttonLuciOff);

        buttonLuciOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskCommander(getApplicationContext(), Command.ON, entityType.LUCE, ml.getEntity(), null).execute();            }
        });

        buttonLuciOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskCommander(getApplicationContext(), Command.OFF, entityType.LUCE, ml.getEntity(), null).execute();            }
        });

        buttonRedOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskCommander(getApplicationContext(), Command.ON, entityType.SCALDINA, ml.getEntity(), null).execute();
            }
        });

        buttonRedOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskCommander(getApplicationContext(), Command.OFF, entityType.SCALDINA, ml.getEntity(), null).execute();
            }
        });



        mCmadRevision.setText("CMAD_REVISION\n   " + ml.getCmadRevision());
        mCmadPosition.setText("CMAD_POSITION\n " + ml.getCmadPosition());
        mCmadDescription.setText("CMAD_DESCRIPTION\n " + ml.getCmadDescription());
        mCmadDigitalInfo.setText("CMAD_DIGITAL_INFO\n " + ml.getCmadDigitalInfo());
        mTempEst.setText("TempEst\n " + ml.getTempEst());
        mLux.setText("Lux\n " + ml.getLux());
        mTempSuolo.setText("TempSuolo\n " + ml.getTempSuolo());
        mTensioneL1.setText("Tensione L1\n " + ml.getTensioneL1());
        mTensioneL2.setText("Tensione L2\n " + ml.getTensioneL2());
        mTensioneL3.setText("Tensione L3\n " + ml.getTensioneL3());
        mCorrenteL1.setText("Corrente L1\n " + ml.getCorrenteL1());
        mCorrenteL2.setText("Corrente L2\n " + ml.getCorrenteL2());
        mCorrenteL3.setText("Corrente L3\n " + ml.getCorrenteL3());
        mPotenzaAttivaL1.setText("Potenza Attiva L1\n " + ml.getPotenzaAttivaL1());
        mPotenzaAttivaL2.setText("Potenza Attiva L2\n " + ml.getPotenzaAttivaL2());
        mPotenzaAttivaL3.setText("Potenza Attiva L3\n " + ml.getPotenzaAttivaL3());
        mPotenzaReattivaL1.setText("Potenza Reattiva L1\n " + ml.getPotenzaReattivaL1());
        mPotenzaReattivaL2.setText("Potenza Reattiva L2\n " + ml.getPotenzaReattivaL2());
        mPotenzaReattivaL3.setText("Potenza Reattiva L3\n " + ml.getPotenzaReattivaL3());
        mFattorePotenzaL1.setText("Fattore Potenza L1\n " + ml.getFattorePotenzaL1());
        mFattorePotenzaL2.setText("Fattore Potenza L2\n " + ml.getFattorePotenzaL2());
        mFattorePotenzaL3.setText("Fattore Potenza L3\n " + ml.getFattorePotenzaL3());
        mEnergiaAttiva.setText("Energia Attiva\n " + ml.getEnergiaAttiva());
        mEnergiaReattiva.setText("Energia Reattiva\n " + ml.getEnergiaReattiva());
        //      mRawBase64.setText("CMAD_RAW_BASE64: "+s.getRawBase());
        //      mCrc.setText("CMAD_CRC: "+s.getCrc());

        madredList = intent.getParcelableArrayListExtra("madred");
        madillList = intent.getParcelableArrayListExtra("madill");

        mRecyclerView = findViewById(R.id.recycler_view_cmadExtendedInfo);
        sAdapter = new CmadExtendedInfoListAdapter(this, ml, madredList, madillList, this);
        mRecyclerView.setAdapter(sAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setTitle("CMAD "+ml.getEntity());

        t.add(mCmadRevision);
        t.add(mCmadPosition);
        t.add(mCmadDescription);
        t.add(mCmadDigitalInfo);
        t.add(mTempEst);
        t.add(mLux);
        t.add(mTensioneL1);
        t.add(mTensioneL2);
        t.add(mTensioneL3);
        t.add(mCorrenteL1);
        t.add(mCorrenteL2);
        t.add(mCorrenteL3);
        t.add(mPotenzaAttivaL1);
        t.add(mPotenzaAttivaL2);
        t.add(mPotenzaAttivaL3);
        t.add(mPotenzaReattivaL1);
        t.add(mPotenzaReattivaL2);
        t.add(mPotenzaReattivaL3);
        t.add(mFattorePotenzaL1);
        t.add(mFattorePotenzaL2);
        t.add(mFattorePotenzaL3);
        t.add(mTempSuolo);
        t.add(mEnergiaAttiva);
        t.add(mEnergiaReattiva);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        final float pixelToDp = getResources().getDisplayMetrics().density;
        int screenHeightDp = (int) (screenHeight * pixelToDp + 0.5f) / 40;
        int screenWidthDp = (int) (screenWidth * pixelToDp + 0.5f) / 10;

        for (int j = 0; j < t.size(); j++) {
            setViewSize(t.get(j), screenHeightDp, screenWidthDp);
            t.get(j).setOnClickListener(new DoubleClickListener(s.get(j)) {
                @Override
                public void onSingleClick(View v) {

                }

                @Override
                public void onDoubleClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", this.textToCopy);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Copiato negli appunti",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void setViewSize(View v,int screenHeightDp,int screenWidthDp){
        ViewGroup.LayoutParams params = v.getLayoutParams();
        //    params.height = screenHeightDp;
        params.width = screenWidthDp;
        v.setLayoutParams(params);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
    @Override
    public void OnMadClick(int position){
        Intent intent;
        if(position<madredList.size()){
            intent = new Intent(this, MadredExtendedInfoActivity.class);
            intent.putExtra("madred",madredList.get(position));
            intent.putExtra("number",Integer.toString(position+1));
            intent.putExtra("cmad_addr", ml.getEntity());
            startActivity(intent);
        }
        else{
            intent = new Intent(this, MadillExtendedInfoActivity.class);
            intent.putExtra("madill",madillList.get(position-madredList.size()));
            intent.putExtra("number",Integer.toString(1+position-madredList.size()));
            intent.putExtra("cmad_addr", ml.getEntity());
            startActivity(intent);
        }

    }

}