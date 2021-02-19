package cnr.isti.traininfo;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class Station implements Serializable {
    private String station;
    private String id_staz;
    private int id_reg;
    private int rank;
    private DatiMeteo datiMeteo;

    public Station(String s, int r){
        station = s;
        rank = r;
    }

    public Station(String s, int r, String staz, int reg){
        station = s;
        rank = r;
        id_staz = staz;
        id_reg = reg;
    }

    public String getStation() {
        return station;
    }

    public int getRank() {
        return rank;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getId_staz() {
        return id_staz;
    }

    public void setId_staz(String id_staz) {
        this.id_staz = id_staz;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId_reg() {
        return id_reg;
    }

    public void setId_reg(int id_reg) {
        this.id_reg = id_reg;
    }

    public void put(JSONObject meteo) {
            Log.d("meteo",meteo.toString());
          datiMeteo  = new DatiMeteo(meteo);
    }

    public DatiMeteo getDatiMeteo() {
        return datiMeteo;
    }

    public void setDatiMeteo(DatiMeteo datiMeteo) {
        this.datiMeteo = datiMeteo;
    }
}
