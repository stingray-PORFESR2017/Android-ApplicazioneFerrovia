package cnr.isti.traininfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Madred implements Parcelable {
    public String madred_macAdr;
    public String madred_date;
    public String madred_header;
    public String madred_type;
    public String madred_revision;
    public String madred_position;
    public String madred_description;
    public String madred_longitude;
    public String madred_latitude;
    public String madred_digital_info;
    public String madred_wire_digital_info;
    public String madred_RawBase64;
    public String madred_Crc;
    public ArrayList<String> madred_temperatura;
    public ArrayList<String> madred_valoreCorrenteCavo;

    public Madred(String m,String d,String h,String t,String r,String p,String des,String lon,String lat,String di,String wdi,ArrayList<String> temperatura,ArrayList<String> corrente,String rb,String crc){
        madred_macAdr=m;
        madred_date=d;
        madred_header=h;
        madred_type=t;
        madred_revision=r;
        madred_position=p;
        madred_description=des;
        madred_longitude=lon;
        madred_latitude=lat;
        madred_digital_info=di;
        madred_wire_digital_info=wdi;
        madred_temperatura=temperatura;
        madred_valoreCorrenteCavo=corrente;
        madred_RawBase64=rb;
        madred_Crc=crc;
    }


    protected Madred(Parcel in) {
        madred_macAdr = in.readString();
        madred_date = in.readString();
        madred_header = in.readString();
        madred_type = in.readString();
        madred_revision = in.readString();
        madred_position = in.readString();
        madred_description = in.readString();
        madred_longitude = in.readString();
        madred_latitude = in.readString();
        madred_digital_info = in.readString();
        madred_wire_digital_info = in.readString();
        madred_RawBase64 = in.readString();
        madred_Crc = in.readString();
        madred_temperatura = in.createStringArrayList();
        madred_valoreCorrenteCavo = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(madred_macAdr);
        dest.writeString(madred_date);
        dest.writeString(madred_header);
        dest.writeString(madred_type);
        dest.writeString(madred_revision);
        dest.writeString(madred_position);
        dest.writeString(madred_description);
        dest.writeString(madred_longitude);
        dest.writeString(madred_latitude);
        dest.writeString(madred_digital_info);
        dest.writeString(madred_wire_digital_info);
        dest.writeString(madred_RawBase64);
        dest.writeString(madred_Crc);
        dest.writeStringList(madred_temperatura);
        dest.writeStringList(madred_valoreCorrenteCavo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Madred> CREATOR = new Creator<Madred>() {
        @Override
        public Madred createFromParcel(Parcel in) {
            return new Madred(in);
        }

        @Override
        public Madred[] newArray(int size) {
            return new Madred[size];
        }
    };

    public String getMadredMacAdr(){ return madred_macAdr; }

    public String getMadredDate(){ return madred_date; }

    public String getMadredHeader(){ return madred_header; }

    public String getMadredType(){ return madred_type; }

    public String getMadredRevision(){ return madred_revision; }

    public String getMadredPosition(){ return madred_position; }

    public String getMadredDescription(){ return madred_description; }

    public String getMadredLongitude(){ return madred_longitude; }

    public String getMadredLatitude(){ return madred_latitude; }

    public String getMadredDigitalInfo(){ return madred_digital_info; }

    public String getMadredWireDigitalInfo(){ return madred_wire_digital_info; }

    public String getMadredRawBase(){ return madred_RawBase64; }

    public String getMadredCrc(){ return madred_Crc; }

    public ArrayList<String> getMadredTemperatura(){ return madred_temperatura; }

    public ArrayList<String> getMadredValoreCorrenteCavo(){ return madred_valoreCorrenteCavo; }


}
