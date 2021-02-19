package cnr.isti.traininfo;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MeteoActivity extends AppCompatActivity implements AsyncResponse {

    public static final String STATION = "STATION";

    AsyncTaskTrain aTask = new AsyncTaskTrain(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datimeteo);

        aTask.delegate = this;

        Intent intent = getIntent();
        String station = intent.getStringExtra("STATION");
        Station s = (Station) intent.getSerializableExtra("STATIONDATA");

        TextView tempooggi =  findViewById(R.id.textViewTempeOggi);
        tempooggi.setText("Temperatura Oggi: "+ s.getDatiMeteo().getOggiTemperatura() +" °C");
         tempooggi =  findViewById(R.id.textViewoggiTempMattino);
        tempooggi.setText("Temperatura Oggi Mattino: "+ s.getDatiMeteo().getOggiTemperaturaMattino()+" °C");
         tempooggi =  findViewById(R.id.textViewTempeoggiTempPomeriggio);
        tempooggi.setText("Temperatura Oggi Pomeriggio: "+ s.getDatiMeteo().getOggiTemperaturaPomeriggio()+" °C");
         tempooggi =  findViewById(R.id.textViewTempeoggiTempSera);
        tempooggi.setText("Temperatura Oggi Sera: "+ s.getDatiMeteo().getOggiTemperaturaSera()+" °C");


        tempooggi =  findViewById(R.id.textViewTempedomaniTemp);
        tempooggi.setText("Temperatura Domani: "+ s.getDatiMeteo().getDomaniTemperatura()+" °C");

        tempooggi =  findViewById(R.id.textViewTempedomanimattinaTemp);
        tempooggi.setText("Temperatura Domani Mattino: "+ s.getDatiMeteo().getDomaniTemperaturaMattino()+" °C");
        tempooggi =  findViewById(R.id.textViewTempedomanipomeriggioTemp);
        tempooggi.setText("Temperatura Domani Pomeriggio: "+ s.getDatiMeteo().getDomaniTemperaturaPomeriggio()+" °C");
        tempooggi =  findViewById(R.id.textViewTempedomaniseraTemp);
        tempooggi.setText("Temperatura Domani Sera: "+ s.getDatiMeteo().getDomaniTemperaturaSera()+" °C");
        setTitle("Meteo "+ station);
        Log.d("OnCreateMeteo", "process Fin");

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
    public void processFinish(ArrayList<DATrain> output) {

    }

    @Override
    public void processFinish(LinkedList<String> output, int t) {

    }

    @Override
    public void processFinish(ArrayList<Cmad> output, int t) {

    }

    @Override
    public void processFinish(Station output, int t) {

        Log.d("processF", "process Fin");

    }


}



