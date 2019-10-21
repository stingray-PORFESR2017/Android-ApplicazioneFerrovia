package com.example.traininfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Cmad implements Parcelable {

    private String entity;
    private String date;
    private String cmad_header;
    private String cmad_type;
    private String cmad_revision;
    private String cmad_position;
    private String cmad_description;
    private String cmad_longitude;
    private String cmad_latitude;
    private String cmad_digital_info;
    private String Temp_Est;
    private String Lux;
    private String Temp_Suolo;
    private String tensioneL1;
    private String tensioneL2;
    private String tensioneL3;

    private String correnteL1;
    private String correnteL2;
    private String correnteL3;
    private String potenzaAttivaL1;
    private String potenzaAttivaL2;
    private String potenzaAttivaL3;
    private String potenzaReattivaL1;
    private String potenzaReattivaL2;
    private String potenzaReattivaL3;
    private String fattorePotenzaL1;
    private String fattorePotenzaL2;
    private String fattorePotenzaL3;
    private String EnergiaAttiva;
    private String EnergiaReattiva;
    private String rawbase64;
    private String crc;
    private ArrayList<Madred> madredList;
    private ArrayList<Madill> madillList;



    public Cmad(String MAC_ADR, String DATE, String CMAD_HEADER, String CMAD_TYPE, String CMAD_REVISION, String CMAD_POSITION, String CMAD_DESCRIPTION, String CMAD_LONGITUDE, String CMAD_LATITUDE, String CMAD_DIGITAL_INFO, String TempEst, String Lux, String tensioneL1, String tensioneL2, String tensioneL3, String correnteL1, String correnteL2, String correnteL3, String potenzaAttivaL1, String potenzaAttivaL2, String potenzaAttivaL3, String potenzaReattivaL1, String potenzaReattivaL2, String potenzaReattivaL3, String fattorePotenzaL1, String fattorePotenzaL2, String fattorePotenzaL3, String TempSuolo, String EnergiaAttiva, String EnergiaReattiva, ArrayList<Madred> mr, ArrayList<Madill> ml, String rawbase64, String crc){
        entity=MAC_ADR;
        date = DATE;
        cmad_header = CMAD_HEADER;
        cmad_type=CMAD_TYPE;
        cmad_revision = CMAD_REVISION;
        cmad_position = CMAD_POSITION;
        cmad_description=CMAD_DESCRIPTION;
        cmad_longitude = CMAD_LONGITUDE;
        cmad_latitude=CMAD_LATITUDE;
        cmad_digital_info=CMAD_DIGITAL_INFO;
        Temp_Est=TempEst;
        this.Lux=Lux;
        this.tensioneL1=tensioneL1;
        this.tensioneL2=tensioneL2;
        this.tensioneL3=tensioneL3;

        this.correnteL1=correnteL1;
        this.correnteL2=correnteL2;
        this.correnteL3=correnteL3;

        this.potenzaAttivaL1=potenzaAttivaL1;
        this.potenzaAttivaL2=potenzaAttivaL2;
        this.potenzaAttivaL3=potenzaAttivaL3;

        this.potenzaReattivaL1=potenzaReattivaL1;
        this.potenzaReattivaL2=potenzaReattivaL2;
        this.potenzaReattivaL3=potenzaReattivaL3;

        this.fattorePotenzaL1=fattorePotenzaL1;
        this.fattorePotenzaL2=fattorePotenzaL2;
        this.fattorePotenzaL3=fattorePotenzaL3;
        Temp_Suolo=TempSuolo;
        this.EnergiaAttiva=EnergiaAttiva;
        this.EnergiaReattiva=EnergiaReattiva;
        madredList=mr;
        madillList=ml;
        this.rawbase64=rawbase64;
        this.crc=crc;
    }


    protected Cmad(Parcel in) {
        entity = in.readString();
        date = in.readString();
        cmad_header = in.readString();
        cmad_type = in.readString();
        cmad_revision = in.readString();
        cmad_position = in.readString();
        cmad_description = in.readString();
        cmad_longitude = in.readString();
        cmad_latitude = in.readString();
        cmad_digital_info = in.readString();
        Temp_Est = in.readString();
        Lux = in.readString();
        Temp_Suolo = in.readString();
        tensioneL1 = in.readString();
        tensioneL2 = in.readString();
        tensioneL3 = in.readString();
        correnteL1 = in.readString();
        correnteL2 = in.readString();
        correnteL3 = in.readString();
        potenzaAttivaL1 = in.readString();
        potenzaAttivaL2 = in.readString();
        potenzaAttivaL3 = in.readString();
        potenzaReattivaL1 = in.readString();
        potenzaReattivaL2 = in.readString();
        potenzaReattivaL3 = in.readString();
        fattorePotenzaL1 = in.readString();
        fattorePotenzaL2 = in.readString();
        fattorePotenzaL3 = in.readString();
        EnergiaAttiva = in.readString();
        EnergiaReattiva = in.readString();
        rawbase64 = in.readString();
        crc = in.readString();
        madredList = in.createTypedArrayList(Madred.CREATOR);
        madillList = in.createTypedArrayList(Madill.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(entity);
        dest.writeString(date);
        dest.writeString(cmad_header);
        dest.writeString(cmad_type);
        dest.writeString(cmad_revision);
        dest.writeString(cmad_position);
        dest.writeString(cmad_description);
        dest.writeString(cmad_longitude);
        dest.writeString(cmad_latitude);
        dest.writeString(cmad_digital_info);
        dest.writeString(Temp_Est);
        dest.writeString(Lux);
        dest.writeString(Temp_Suolo);
        dest.writeString(tensioneL1);
        dest.writeString(tensioneL2);
        dest.writeString(tensioneL3);
        dest.writeString(correnteL1);
        dest.writeString(correnteL2);
        dest.writeString(correnteL3);
        dest.writeString(potenzaAttivaL1);
        dest.writeString(potenzaAttivaL2);
        dest.writeString(potenzaAttivaL3);
        dest.writeString(potenzaReattivaL1);
        dest.writeString(potenzaReattivaL2);
        dest.writeString(potenzaReattivaL3);
        dest.writeString(fattorePotenzaL1);
        dest.writeString(fattorePotenzaL2);
        dest.writeString(fattorePotenzaL3);
        dest.writeString(EnergiaAttiva);
        dest.writeString(EnergiaReattiva);
        dest.writeString(rawbase64);
        dest.writeString(crc);
        dest.writeTypedList(madredList);
        dest.writeTypedList(madillList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cmad> CREATOR = new Creator<Cmad>() {
        @Override
        public Cmad createFromParcel(Parcel in) {
            return new Cmad(in);
        }

        @Override
        public Cmad[] newArray(int size) {
            return new Cmad[size];
        }
    };

    public String getEntity() {
        return entity;
    }

    public String getDate() {return date; }

    public String getCmadHeader() {
        return cmad_header;
    }

    public String getCmadType() {
        return cmad_type;
    }

    public String getCmadRevision() {
        return cmad_revision;
    }

    public String getCmadPosition() {return cmad_position; }

    public String getCmadDescription() {return cmad_description; }

    public String getCmadLongitude() {
        return cmad_longitude;
    }

    public String getCmadLatitude() {
        return cmad_latitude;
    }

    public String getCmadDigitalInfo() { return cmad_digital_info; }

    public String getTempEst() {
        return Temp_Est;
    }

    public String getLux() {
        return Lux;
    }

    public String getTempSuolo() {return Temp_Suolo; }

    public String getTensioneL1() {return tensioneL1; }

    public String getTensioneL2() {return tensioneL2; }

    public String getTensioneL3() {return tensioneL3; }

    public String getCorrenteL1() {return correnteL1; }

    public String getCorrenteL2() {return correnteL2; }

    public String getCorrenteL3() {return correnteL3; }

    public String getPotenzaAttivaL1() {return potenzaAttivaL1; }

    public String getPotenzaAttivaL2() {return potenzaAttivaL2; }

    public String getPotenzaAttivaL3() {return potenzaAttivaL3; }

    public String getPotenzaReattivaL1() {return potenzaReattivaL1; }

    public String getPotenzaReattivaL2() {return potenzaReattivaL2; }

    public String getPotenzaReattivaL3() {return potenzaReattivaL3; }

    public String getFattorePotenzaL1() {return fattorePotenzaL1; }

    public String getFattorePotenzaL2() {return fattorePotenzaL2; }

    public String getFattorePotenzaL3() {return fattorePotenzaL3; }

    public String getEnergiaAttiva() {
        return EnergiaAttiva;
    }

    public String getEnergiaReattiva() {
        return EnergiaReattiva;
    }

    public ArrayList<Madred> getMadred() { return madredList; }

    public ArrayList<Madill> getMadill() { return madillList; }

    public String getRawBase() { return rawbase64; }

    public String getCrc() { return crc; }

}