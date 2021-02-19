package cnr.isti.traininfo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DatiMeteo implements Serializable {

    int oggiTemperatura = 0;
    int oggiTemperaturaMattino= 0;
    int oggiTemperaturaPomeriggio= 0;
    int oggiTemperaturaSera= 0;
    int domaniTemperatura= 0;
    int domaniTemperaturaMattino= 0;
    int domaniTemperaturaPomeriggio= 0;
    int domaniTemperaturaSera= 0;

    int oggiTempo = 0;
    int oggiTempoMattino = 0;
    int oggiTempoPomeriggio = 0;
    int oggiTempoSera = 0;

    int domaniTempo = 0;
    int domaniTempoMattino = 0;
    int domaniTempoPomeriggio = 0;
    int domaniTempoSera = 0;

    public DatiMeteo(){

    }

    public  DatiMeteo(JSONObject meteo)  {
        try {

            oggiTempo = meteo.getInt("oggiTempo");
            oggiTempoMattino = meteo.getInt("oggiTempoMattino");
            oggiTempoPomeriggio = meteo.getInt("oggiTempoPomeriggio");
            oggiTempoSera = meteo.getInt("oggiTempoSera");

            domaniTempo = meteo.getInt("domaniTempo");
            domaniTempoMattino = meteo.getInt("domaniTempoMattino");
            domaniTempoPomeriggio = meteo.getInt("domaniTempoPomeriggio");
            domaniTempoSera = meteo.getInt("domaniTempoSera");

            oggiTemperatura = meteo.getInt("oggiTemperatura");
            oggiTemperaturaMattino = meteo.getInt("oggiTemperaturaMattino");
            oggiTemperaturaPomeriggio = meteo.getInt("oggiTemperaturaPomeriggio");
            oggiTemperaturaSera = meteo.getInt("oggiTemperaturaSera");

            domaniTemperatura = meteo.getInt("domaniTemperatura");
            domaniTemperaturaMattino = meteo.getInt("domaniTemperaturaMattino");
            domaniTemperaturaPomeriggio = meteo.getInt("domaniTemperaturaPomeriggio");
            domaniTemperaturaSera = meteo.getInt("domaniTemperaturaSera");
        }catch (JSONException e){
            Log.d("Err", e.getLocalizedMessage());
        }

    }

    public int getOggiTempo() {
        return oggiTempo;
    }

    public void setOggiTempo(int oggiTempo) {
        this.oggiTempo = oggiTempo;
    }

    public int getOggiTempoMattino() {
        return oggiTempoMattino;
    }

    public void setOggiTempoMattino(int oggiTempoMattino) {
        this.oggiTempoMattino = oggiTempoMattino;
    }

    public int getOggiTempoPomeriggio() {
        return oggiTempoPomeriggio;
    }

    public void setOggiTempoPomeriggio(int oggiTempoPomeriggio) {
        this.oggiTempoPomeriggio = oggiTempoPomeriggio;
    }

    public int getOggiTempoSera() {
        return oggiTempoSera;
    }

    public void setOggiTempoSera(int oggiTempoSera) {
        this.oggiTempoSera = oggiTempoSera;
    }

    public int getDomaniTempo() {
        return domaniTempo;
    }

    public void setDomaniTempo(int domaniTempo) {
        this.domaniTempo = domaniTempo;
    }

    public int getDomaniTempoMattino() {
        return domaniTempoMattino;
    }

    public void setDomaniTempoMattino(int domaniTempoMattino) {
        this.domaniTempoMattino = domaniTempoMattino;
    }

    public int getDomaniTempoPomeriggio() {
        return domaniTempoPomeriggio;
    }

    public void setDomaniTempoPomeriggio(int domaniTempoPomeriggio) {
        this.domaniTempoPomeriggio = domaniTempoPomeriggio;
    }

    public int getDomaniTempoSera() {
        return domaniTempoSera;
    }

    public void setDomaniTempoSera(int domaniTempoSera) {
        this.domaniTempoSera = domaniTempoSera;
    }

    public int getOggiTemperatura() {
        return oggiTemperatura;
    }

    public void setOggiTemperatura(int oggiTemperatura) {
        this.oggiTemperatura = oggiTemperatura;
    }

    public int getOggiTemperaturaMattino() {
        return oggiTemperaturaMattino;
    }

    public void setOggiTemperaturaMattino(int oggiTemperaturaMattino) {
        this.oggiTemperaturaMattino = oggiTemperaturaMattino;
    }

    public int getOggiTemperaturaPomeriggio() {
        return oggiTemperaturaPomeriggio;
    }

    public void setOggiTemperaturaPomeriggio(int oggiTemperaturaPomeriggio) {
        this.oggiTemperaturaPomeriggio = oggiTemperaturaPomeriggio;
    }

    public int getOggiTemperaturaSera() {
        return oggiTemperaturaSera;
    }

    public void setOggiTemperaturaSera(int oggiTemperaturaSera) {
        this.oggiTemperaturaSera = oggiTemperaturaSera;
    }

    public int getDomaniTemperatura() {
        return domaniTemperatura;
    }

    public void setDomaniTemperatura(int domaniTemperatura) {
        this.domaniTemperatura = domaniTemperatura;
    }

    public int getDomaniTemperaturaMattino() {
        return domaniTemperaturaMattino;
    }

    public void setDomaniTemperaturaMattino(int domaniTemperaturaMattino) {
        this.domaniTemperaturaMattino = domaniTemperaturaMattino;
    }

    public int getDomaniTemperaturaPomeriggio() {
        return domaniTemperaturaPomeriggio;
    }

    public void setDomaniTemperaturaPomeriggio(int domaniTemperaturaPomeriggio) {
        this.domaniTemperaturaPomeriggio = domaniTemperaturaPomeriggio;
    }

    public int getDomaniTemperaturaSera() {
        return domaniTemperaturaSera;
    }

    public void setDomaniTemperaturaSera(int domaniTemperaturaSera) {
        this.domaniTemperaturaSera = domaniTemperaturaSera;
    }


}
