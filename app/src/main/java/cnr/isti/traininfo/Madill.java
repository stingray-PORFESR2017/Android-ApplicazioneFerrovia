package cnr.isti.traininfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Madill implements Parcelable {

    public String madill_macAdr;
    public String madill_date;
    public String madill_header;
    public String madill_type;
    public String madill_revision;
    public String madill_position;
    public String madill_description;
    public String madill_longitude;
    public String madill_latitude;
    public String madill_digital_info;
    public String madill_ComandoLampada;
    public String madill_PotenzaLampada;
    public String madill_VitaLampada;
    public String madill_TensioneLampada;
    public String madill_CorrenteLampada;
    public String madill_Scorta;
    public String madill_Rawbase64;
    public String madill_Crc;

    public Madill(String m,String d,String h,String t,String r,String p,String des,String lon,String lat,String di,String cl,String pl,String vl,String tl,String crl,String s,String rb,String crc){
        madill_macAdr=m;
        madill_date=d;
        madill_header=h;
        madill_type=t;
        madill_revision=r;
        madill_position=p;
        madill_description=des;
        madill_longitude=lon;
        madill_latitude=lat;
        madill_digital_info=di;
        madill_ComandoLampada=cl;
        madill_PotenzaLampada=pl;
        madill_VitaLampada=vl;
        madill_TensioneLampada=tl;
        madill_CorrenteLampada=crl;
        madill_Scorta=s;
        madill_Rawbase64=rb;
        madill_Crc=crc;
    }


    protected Madill(Parcel in) {
        madill_macAdr = in.readString();
        madill_date = in.readString();
        madill_header = in.readString();
        madill_type = in.readString();
        madill_revision = in.readString();
        madill_position = in.readString();
        madill_description = in.readString();
        madill_longitude = in.readString();
        madill_latitude = in.readString();
        madill_digital_info = in.readString();
        madill_ComandoLampada = in.readString();
        madill_PotenzaLampada = in.readString();
        madill_VitaLampada = in.readString();
        madill_TensioneLampada = in.readString();
        madill_CorrenteLampada = in.readString();
        madill_Scorta = in.readString();
        madill_Rawbase64 = in.readString();
        madill_Crc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(madill_macAdr);
        dest.writeString(madill_date);
        dest.writeString(madill_header);
        dest.writeString(madill_type);
        dest.writeString(madill_revision);
        dest.writeString(madill_position);
        dest.writeString(madill_description);
        dest.writeString(madill_longitude);
        dest.writeString(madill_latitude);
        dest.writeString(madill_digital_info);
        dest.writeString(madill_ComandoLampada);
        dest.writeString(madill_PotenzaLampada);
        dest.writeString(madill_VitaLampada);
        dest.writeString(madill_TensioneLampada);
        dest.writeString(madill_CorrenteLampada);
        dest.writeString(madill_Scorta);
        dest.writeString(madill_Rawbase64);
        dest.writeString(madill_Crc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Madill> CREATOR = new Creator<Madill>() {
        @Override
        public Madill createFromParcel(Parcel in) {
            return new Madill(in);
        }

        @Override
        public Madill[] newArray(int size) {
            return new Madill[size];
        }
    };

    public String getMadillMacAdr(){ return madill_macAdr; }

    public String getMadillDate(){ return madill_date; }

    public String getMadillHeader(){ return madill_header; }

    public String getMadillType(){ return madill_type; }

    public String getMadillRevision(){ return madill_revision; }

    public String getMadillPosition(){ return madill_position; }

    public String getMadillDescription(){ return madill_description; }

    public String getMadillLongitude(){ return madill_longitude; }

    public String getMadillLatitude(){ return madill_latitude; }

    public String getMadillDigitalInfo(){ return madill_digital_info; }

    public String getMadillComandoLampada(){ return madill_ComandoLampada; }

    public String getMadillPotenzaLampada(){ return madill_PotenzaLampada; }

    public String getMadillVitaLampada(){ return madill_VitaLampada; }

    public String getMadillTensioneLampada(){ return madill_TensioneLampada; }

    public String getMadillCorrenteLampada(){ return madill_CorrenteLampada; }

    public String getMadillScorta(){ return madill_Scorta; }

    public String getMadillRawBase(){ return madill_Rawbase64; }

    public String getMadillCrc(){ return madill_Crc; }

}