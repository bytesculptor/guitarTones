package dev.bytesculptor.guitartones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import dev.bytesculptor.guitartones.ui.PlayActivity;
import dev.bytesculptor.guitartones.utilities.Tuning;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> TuningList = new ArrayList<String>();
    private ArrayList<String> NoteList = new ArrayList<String>();
    private ArrayAdapter<String> dataAdapterTuning, dataAdapterTone;
    private Spinner spTuning, spTone;
    Tuning tun = new Tuning();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        spTuning = (Spinner) findViewById(R.id.spTuning);
        initSpinnerTuning();

        spTone = (Spinner) findViewById(R.id.spToneToFind);
        initSpinnerToneToFind();

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void initSpinnerTuning() {
        TuningList.clear();
        TuningList.add("Standard");
        TuningList.add("Eb");
        TuningList.add("D");
        TuningList.add("Drop D");
        TuningList.add("Drop C");
        TuningList.add("Drop C#");

        dataAdapterTuning = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TuningList);
        dataAdapterTuning.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTuning.setAdapter(dataAdapterTuning);
    }

    public void initSpinnerToneToFind() {
        NoteList.clear();
        for (int i = 0; i < 12; i++) {
            NoteList.add(tun.SCALE[i]);
        }

        dataAdapterTone = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NoteList);
        dataAdapterTone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTone.setAdapter(dataAdapterTone);
    }


    public void StartPlay(View v) {
        Intent intentPlay = new Intent(this, PlayActivity.class);
        intentPlay.putExtra("tuning", spTuning.getSelectedItemPosition());
        intentPlay.putExtra("tone", spTone.getSelectedItemPosition());
        startActivity(intentPlay);
    }
}