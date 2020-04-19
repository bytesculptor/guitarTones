package com.bytesculptor.guitartones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> TuningList = new java.util.ArrayList<String>();
    private ArrayList<String> NoteList = new java.util.ArrayList<String>();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            NoteList.add(tun.scale[i]);
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