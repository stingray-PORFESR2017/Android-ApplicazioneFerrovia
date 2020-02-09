package com.example.traininfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import java.util.ArrayList;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MadredExtendedInfoActivity extends AppCompatActivity {
    private TextView mMadredMacAdr;
    private TextView mMadredDate;
    private TextView mMadredHeader;
    private TextView mMadredType;
    private TextView mMadredRevision;
    private TextView mMadredPosition;
    private TextView mMadredDescription;
    private TextView mMadredLongitude;
    private TextView mMadredLatitude;
    private TextView mMadredDigitalInfo;
    private TextView mMadredWireDigitalInfo;
    private TextView mRawBase64;
    private TextView mCrc;
    private TextView[] mMadredTemperatura=new TextView[2];
    private TextView[] mMadredValoreCorrenteCavo=new TextView[12];
    private ArrayList<String> s=new ArrayList<>();
    private ArrayList<TextView> t=new ArrayList<>();
    private String macEnte;
    private String macCMAD;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madred_extended_info);

        Intent intent = getIntent();
        Madred m=intent.getParcelableExtra("madred");

    //per la gestione dei comandi
        macEnte=m.getMadredMacAdr();
        macCMAD=intent.getStringExtra("cmad_addr");

        s=new ArrayList<>();
        s.add(m.getMadredDate());
        s.add(m.getMadredMacAdr());
        s.add(m.getMadredHeader());
        s.add(m.getMadredType());
        s.add(m.getMadredRevision());
        s.add(m.getMadredPosition());
        s.add(m.getMadredDescription());
        s.add(m.getMadredLongitude());
        s.add(m.getMadredLatitude());
        s.add(m.getMadredDigitalInfo());
        s.add(m.getMadredWireDigitalInfo());
        s.add(m.getMadredWireDigitalInfo());
        ArrayList<String> temperatura=m.getMadredTemperatura();
        for(int i=1;i<3;i++){
            s.add(temperatura.get(i-1));
        }
        ArrayList<String> corrente=m.getMadredValoreCorrenteCavo();
        for(int i=1;i<13;i++){
            s.add(corrente.get(i-1));
        }

        mMadredMacAdr= findViewById(R.id.madredMacAdr);
        mMadredDate= findViewById(R.id.madredDate);
        mMadredHeader= findViewById(R.id.madredHeader);
        mMadredType= findViewById(R.id.madredType);
        mMadredRevision=findViewById(R.id.madredRevision);
        mMadredPosition=findViewById(R.id.madredPosition);
        mMadredDescription=findViewById(R.id.madredDescription);
        mMadredLongitude=findViewById(R.id.madredLongitude);
        mMadredLatitude=findViewById(R.id.madredLatitude);
        mMadredDigitalInfo=findViewById(R.id.madredDigitalInfo);
        mMadredWireDigitalInfo=findViewById(R.id.madredWireDigitalInfo);
        mMadredTemperatura[0]=findViewById(R.id.madredTemperatura1);
        mMadredTemperatura[1]=findViewById(R.id.madredTemperatura2);
        mMadredValoreCorrenteCavo[0]=findViewById(R.id.madredValoreCorrenteCavo1);
        mMadredValoreCorrenteCavo[1]=findViewById(R.id.madredValoreCorrenteCavo2);
        mMadredValoreCorrenteCavo[2]=findViewById(R.id.madredValoreCorrenteCavo3);
        mMadredValoreCorrenteCavo[3]=findViewById(R.id.madredValoreCorrenteCavo4);
        mMadredValoreCorrenteCavo[4]=findViewById(R.id.madredValoreCorrenteCavo5);
        mMadredValoreCorrenteCavo[5]=findViewById(R.id.madredValoreCorrenteCavo6);
        mMadredValoreCorrenteCavo[6]=findViewById(R.id.madredValoreCorrenteCavo7);
        mMadredValoreCorrenteCavo[7]=findViewById(R.id.madredValoreCorrenteCavo8);
        mMadredValoreCorrenteCavo[8]=findViewById(R.id.madredValoreCorrenteCavo9);
        mMadredValoreCorrenteCavo[9]=findViewById(R.id.madredValoreCorrenteCavo10);
        mMadredValoreCorrenteCavo[10]=findViewById(R.id.madredValoreCorrenteCavo11);
        mMadredValoreCorrenteCavo[11]=findViewById(R.id.madredValoreCorrenteCavo12);
    /*    mRawBase64=findViewById(R.id.madredRaw);
        mCrc=findViewById(R.id.madredCrc);*/



        mMadredMacAdr.setText("Madred MAC_ADR\n  "+m.getMadredMacAdr());
        mMadredDate.setText("Madred DATE\n  "+m.getMadredDate());
        mMadredHeader.setText("Madred HEADER\n  "+m.getMadredHeader());
        mMadredType.setText("Madred TYPE\n  "+m.getMadredType());
        mMadredRevision.setText("Madred REVISION\n  "+m.getMadredRevision());
        mMadredPosition.setText("Madred POSITION\n  "+m.getMadredPosition());
        mMadredDescription.setText("Madred DESCRIPTION\n  "+m.getMadredDescription());
        mMadredLongitude.setText("Madred LONGITUDE\n  "+m.getMadredLongitude());
        mMadredLatitude.setText("Madred LATITUDE\n  "+m.getMadredLatitude());
        mMadredDigitalInfo.setText("DIGITAL_INFO\n  "+m.getMadredDigitalInfo());
        mMadredWireDigitalInfo.setText("WIRE_DIGITAL_INFO\n  "+m.getMadredWireDigitalInfo());
    /*    mRawBase64.setText("Madred RAWBASE64: "+m.getMadredRawBase());
        mCrc.setText("Madred CRC: "+m.getMadredCrc());*/

        temperatura=m.getMadredTemperatura();
        for(int i=1;i<3;i++){
            mMadredTemperatura[i-1].setText("Temperatura"+i+"\n  "+temperatura.get(i-1));
        }
        corrente=m.getMadredValoreCorrenteCavo();
        for(int i=1;i<13;i++){
            mMadredValoreCorrenteCavo[i-1].setText("Corrente Cavo"+i+"\n  "+corrente.get(i-1));
        }
        t.add(mMadredMacAdr);
        t.add(mMadredDate);
        t.add(mMadredHeader);
        t.add(mMadredType);
        t.add(mMadredRevision);
        t.add(mMadredPosition);
        t.add(mMadredDescription);
        t.add(mMadredLongitude);
        t.add(mMadredLatitude);
        t.add(mMadredDigitalInfo);
        t.add(mMadredWireDigitalInfo);
        t.add(mMadredTemperatura[0]);
        t.add(mMadredTemperatura[1]);
        t.add(mMadredValoreCorrenteCavo[0]);
        t.add(mMadredValoreCorrenteCavo[1]);
        t.add(mMadredValoreCorrenteCavo[2]);
        t.add(mMadredValoreCorrenteCavo[3]);
        t.add(mMadredValoreCorrenteCavo[4]);
        t.add(mMadredValoreCorrenteCavo[5]);
        t.add(mMadredValoreCorrenteCavo[6]);
        t.add(mMadredValoreCorrenteCavo[7]);
        t.add(mMadredValoreCorrenteCavo[8]);
        t.add(mMadredValoreCorrenteCavo[9]);
        t.add(mMadredValoreCorrenteCavo[10]);
        t.add(mMadredValoreCorrenteCavo[11]);
        String i=intent.getStringExtra("number");
        setTitle("Info Madred "+i);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        final float pixelToDp = getResources().getDisplayMetrics().density;
        int screenHeightDp =  (int) (screenHeight * pixelToDp + 0.5f)/40;
        int screenWidthDp =  (int) (screenWidth * pixelToDp + 0.5f)/10;
        int j;
        for(j=0;j<t.size();j++) {
            setViewSize(t.get(j), screenHeightDp, screenWidthDp);
            t.get(j).setOnClickListener(new DoubleClickListener(s.get(j)) {
                @Override
                public void onSingleClick(View v) {

                }

                @Override
                public void onDoubleClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", this.textToCopy);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), R.string.copied_to_clipboard,Toast.LENGTH_SHORT).show();
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
                new AsyncTaskCommander(this, Command.ON, entityType.SCALDINA, macCMAD, macEnte).execute();
                return true;

            case R.id.btn_off:
                new AsyncTaskCommander(this, Command.OFF, entityType.SCALDINA, macCMAD, macEnte).execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}