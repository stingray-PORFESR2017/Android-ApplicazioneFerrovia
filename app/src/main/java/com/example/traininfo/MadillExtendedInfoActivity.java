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
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MadillExtendedInfoActivity extends AppCompatActivity {
    private TextView mMadillMacAdr;
    private TextView mMadillDate;
    private TextView mMadillHeader;
    private TextView mMadillType;
    private TextView mMadillRevision;
    private TextView mMadillPosition;
    private TextView mMadillDescription;
    private TextView mMadillLongitude;
    private TextView mMadillLatitude;
    private TextView mMadillDigitalInfo;
    private TextView mMadillPotenzaLampada;
    private TextView mMadillComandoLampada;
    private TextView mMadillVitaLampada;
    private TextView mMadillTensioneLampada;
    private TextView mMadillCorrenteLampada;
    private TextView mMadillScorta;
    private TextView mRawBase64;
    private TextView mCrc;
    private ArrayList<String> s=new ArrayList<>();
    private ArrayList<TextView> t=new ArrayList<>();
    private String macEnte;
    private String macCMAD;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madill_extended_info);

        Intent intent = getIntent();
        Madill ml=intent.getParcelableExtra("madill");

        //per la gestione dei comandi
        macEnte=ml.getMadillMacAdr();
        macCMAD=intent.getStringExtra("cmad_addr");

        s=new ArrayList<>();
        s.add(ml.getMadillMacAdr());
        s.add(ml.getMadillDate());
        s.add(ml.getMadillHeader());
        s.add(ml.getMadillType());
        s.add(ml.getMadillRevision());
        s.add(ml.getMadillPosition());
        s.add(ml.getMadillDescription());
        s.add(ml.getMadillLongitude());
        s.add(ml.getMadillLatitude());
        s.add(ml.getMadillDigitalInfo());
        s.add(ml.getMadillPotenzaLampada());
        s.add(ml.getMadillComandoLampada());
        s.add(ml.getMadillTensioneLampada());
        s.add(ml.getMadillVitaLampada());
        s.add(ml.getMadillCorrenteLampada());
        s.add(ml.getMadillScorta());


        mMadillMacAdr=findViewById(R.id.madillMacAdr);
        mMadillDate=findViewById(R.id.madillDate);
        mMadillHeader=findViewById(R.id.madillHeader);
        mMadillType=findViewById(R.id.madillType);
        mMadillRevision=findViewById(R.id.madillRevision);
        mMadillPosition=findViewById(R.id.madillPosition);
        mMadillDescription=findViewById(R.id.madillDescription);
        mMadillLongitude=findViewById(R.id.madillLongitude);
        mMadillLatitude=findViewById(R.id.madillLatitude);
        mMadillDigitalInfo=findViewById(R.id.madillDigitalInfo);
        mMadillPotenzaLampada=findViewById(R.id.madillPotenzaLampada);
        mMadillComandoLampada=findViewById(R.id.madillComandoLampada);
        mMadillVitaLampada=findViewById(R.id.madillVitaLampada);
        mMadillTensioneLampada=findViewById(R.id.madillTensioneLampada);
        mMadillCorrenteLampada=findViewById(R.id.madillCorrenteLampada);
        mMadillScorta=findViewById(R.id.madillScorta);
 /*       mRawBase64=findViewById(R.id.madillRaw);
        mCrc=findViewById(R.id.madillCrc);  */

        mMadillMacAdr.setText("Madill MAC_ADR\n "+ml.getMadillMacAdr());
        mMadillDate.setText("Madill DATE\n "+ml.getMadillDate());
        mMadillHeader.setText("Madill HEADER\n "+ml.getMadillHeader());
        mMadillType.setText("Madill TYPE\n "+ml.getMadillType());
        mMadillRevision.setText("Madill REVISION\n "+ml.getMadillRevision());
        mMadillPosition.setText("Madill POSITION\n "+ml.getMadillPosition());
        mMadillDescription.setText("Madill DESCRPTION\n "+ml.getMadillDescription());
        mMadillLongitude.setText("Madill LONGITUDE\n "+ml.getMadillLongitude());
        mMadillLatitude.setText("Madill LATITUDE\n "+ml.getMadillLatitude());
        mMadillDigitalInfo.setText("Madill DIGITAL_INFO\n "+ml.getMadillDigitalInfo());
        mMadillPotenzaLampada.setText("PotenzaLampada\n "+ml.getMadillPotenzaLampada());
        mMadillComandoLampada.setText("ComandoLampada\n "+ml.getMadillComandoLampada());
        mMadillTensioneLampada.setText("TensioneLampada\n "+ml.getMadillTensioneLampada());
        mMadillVitaLampada.setText("VitaLampada\n "+ml.getMadillVitaLampada());
        mMadillCorrenteLampada.setText("CorrenteLampada\n "+ml.getMadillCorrenteLampada());
        mMadillScorta.setText("Madill Scorta\n "+ml.getMadillScorta());
   /*     mRawBase64.setText("Madill RAWBASE64: "+ml.getMadillRawBase());
        mCrc.setText("Madill CRC: "+ml.getMadillCrc()); */

        t.add(mMadillMacAdr);
        t.add(mMadillDate);
        t.add(mMadillHeader);
        t.add(mMadillType);
        t.add(mMadillRevision);
        t.add(mMadillPosition);
        t.add(mMadillDescription);
        t.add(mMadillLongitude);
        t.add(mMadillLatitude);
        t.add(mMadillDigitalInfo);
        t.add(mMadillPotenzaLampada);
        t.add(mMadillComandoLampada);
        t.add(mMadillTensioneLampada);
        t.add(mMadillVitaLampada);
        t.add(mMadillCorrenteLampada);
        t.add(mMadillScorta);

        String i=intent.getStringExtra("number");
        setTitle("Info Madill "+i);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        final float pixelToDp = getResources().getDisplayMetrics().density;
        int screenHeightDp =  (int) (screenHeight * pixelToDp + 0.5f)/40;
        int screenWidthDp =  (int) (screenWidth * pixelToDp + 0.5f)/10;

        for(int j=0;j<t.size();j++){
            setViewSize(t.get(j),screenHeightDp,screenWidthDp);
            t.get(j).setOnClickListener(new DoubleClickListener(s.get(j)) {
                @Override
                public void onSingleClick(View v) {

                }
                @Override
                public void onDoubleClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", this.textToCopy);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Copiato negli appunti",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void setViewSize(View v, int screenHeightDp, int screenWidthDp){

        ViewGroup.LayoutParams params = v.getLayoutParams();
        //    params.height = screenHeightDp;
        params.width = screenWidthDp;
        v.setLayoutParams(params);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option_ill_red, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            case R.id.btn_on:
                new AsyncTaskCommander(this, Command.ON, entityType.LUCE, macCMAD, macEnte).execute();
                return true;

            case R.id.btn_off:
                new AsyncTaskCommander(this, Command.OFF, entityType.LUCE, macCMAD, macEnte).execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}