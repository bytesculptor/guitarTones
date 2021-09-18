package dev.bytesculptor.guitartones.ui;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.bytesculptor.guitartones.R;
import dev.bytesculptor.guitartones.dialogs.DialogGameCompleted;
import dev.bytesculptor.guitartones.utilities.Tuning;


public class PlayActivity extends AppCompatActivity
        implements DialogGameCompleted.GameCompletedListener {

    private int tuningChoice = 0, toneChoice = 0;

    private int goodCnt = 0, badCnt = 0;
    private int[][] allNotesTable = new int[6][12];
    private TextView tvGoodCnt, tvBadCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tuningChoice = extras.getInt("tuning");
            toneChoice = extras.getInt("tone");
        } else {
            Toast.makeText(getBaseContext(), "main bundle failed", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView tvFind = findViewById(R.id.tvFind);
        tvFind.setText(Tuning.SCALE[toneChoice]);

        setTuning();

        setTonesPerString();
        clearTonesOnStrings();

        tvGoodCnt = findViewById(R.id.tvGoodCnt);
        tvBadCnt = findViewById(R.id.tvBadCnt);

    }


    @SuppressLint("SetTextI18n")
    public void setTuning() {
        for (int i = 1; i < 7; i++) {
            Button bt = (Button) findViewById(getResources().getIdentifier("string" + i, "id", getPackageName()));

            switch (tuningChoice) {
                case 0:
                    bt.setText(" " + Tuning.SCALE[Tuning.OFFSET_STD[i - 1]] + " ");
                    break;

                case 1:
                    bt.setText(" " + Tuning.SCALE[Tuning.OFFSET_Eb[i - 1]] + " ");
                    break;

                case 2:
                    bt.setText(" " + Tuning.SCALE[Tuning.OFFSET_D[i - 1]] + " ");
                    break;

                case 3:
                    bt.setText(" " + Tuning.SCALE[Tuning.OFFSET_DROP_D[i - 1]] + " ");
                    break;

                case 4:
                    bt.setText(" " + Tuning.SCALE[Tuning.OFFSET_DROP_C[i - 1]] + " ");
                    break;

                case 5:
                    bt.setText(" " + Tuning.SCALE[Tuning.OFFSET_DROP_CIS[i - 1]] + " ");
                    break;

                default:
                    bt.setText(" " + Tuning.SCALE[Tuning.OFFSET_STD[i - 1]] + " ");
                    break;
            }
        }
    }


    public void setTonesPerString() {
        for (int k = 0; k < 6; k++) {
            for (int i = 1; i < 13; i++) {
                String baseString = "bt" + Tuning.STRING_BUTTONS[k] + i;
                Button bt = (Button) findViewById(getResources().getIdentifier(baseString, "id", getPackageName()));
                //bt.setText(Tuning.scale[(Tuning.stringOffset2[k] + i) % 12]);
                int pos1 = 0;

                switch (tuningChoice) {
                    case 0:
                        pos1 = (Tuning.OFFSET_STD[k] + i) % 12;
                        bt.setText(Tuning.SCALE[pos1]);
                        allNotesTable[k][i - 1] = pos1;
                        break;

                    case 1:
                        pos1 = (Tuning.OFFSET_Eb[k] + i) % 12;
                        bt.setText(Tuning.SCALE[pos1]);
                        allNotesTable[k][i - 1] = pos1;
                        break;

                    case 2:
                        pos1 = (Tuning.OFFSET_D[k] + i) % 12;
                        bt.setText(Tuning.SCALE[pos1]);
                        allNotesTable[k][i - 1] = pos1;
                        break;

                    case 3:
                        pos1 = (Tuning.OFFSET_DROP_D[k] + i) % 12;
                        bt.setText(Tuning.SCALE[pos1]);
                        allNotesTable[k][i - 1] = pos1;
                        break;

                    case 4:
                        pos1 = (Tuning.OFFSET_DROP_C[k] + i) % 12;
                        bt.setText(Tuning.SCALE[pos1]);
                        allNotesTable[k][i - 1] = pos1;
                        break;

                    case 5:
                        pos1 = (Tuning.OFFSET_DROP_CIS[k] + i) % 12;
                        bt.setText(Tuning.SCALE[pos1]);
                        allNotesTable[k][i - 1] = pos1;
                        break;

                    default:
                        Toast.makeText(getBaseContext(), "tuning failed, err. 12", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    public void clearTonesOnStrings() {
        for (int k = 0; k < 6; k++) {
            for (int i = 1; i < 13; i++) {
                String baseString = "bt" + Tuning.STRING_BUTTONS[k] + i;
                Button bt = findViewById(getResources().getIdentifier(baseString, "id", getPackageName()));
                bt.setText("   ");
            }
        }
    }


    private void correctButton(Button bt) {
        bt.setBackgroundColor(Color.GREEN);
        goodCnt++;
        tvGoodCnt.setText("" + goodCnt);

        if (goodCnt >= 6) {
            DialogFragment dialogClearMemo = new DialogGameCompleted();
            Bundle bundle = new Bundle();
            bundle.putFloat("success", ((float) goodCnt / ((float) goodCnt + (float) badCnt) * 100));
            dialogClearMemo.setArguments(bundle);
            dialogClearMemo.show(getFragmentManager(), "cm");
        }
    }


    private void wrongButton(Button bt, int note) {
        bt.setBackgroundColor(Color.RED);
        badCnt++;
        tvBadCnt.setText("" + badCnt);
        bt.setText(Tuning.SCALE[note]);

    }

    private void checkTone(Button bt, int string, int fret) {
        if (allNotesTable[string][fret] == toneChoice) {
            correctButton(bt);
        } else {
            wrongButton(bt, allNotesTable[string][fret]);
        }
    }

    public void onButtonClick(View view) {
        Button bt;
        switch (view.getId()) {
            case R.id.bte1:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 0);
                break;

            case R.id.bte2:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 1);
                break;

            case R.id.bte3:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 2);
                break;

            case R.id.bte4:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 3);
                break;

            case R.id.bte5:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 4);
                break;

            case R.id.bte6:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 5);
                break;

            case R.id.bte7:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 6);
                break;

            case R.id.bte8:
                bt = findViewById(view.getId());
                checkTone(bt, 0, 7);
                break;

            case R.id.bte9:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 0, 8);
                break;

            case R.id.bte10:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 0, 9);
                break;

            case R.id.bte11:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 0, 10);
                break;

            case R.id.bte12:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 0, 11);
                break;


            //------

            case R.id.btB1:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 0);
                break;

            case R.id.btB2:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 1);
                break;

            case R.id.btB3:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 2);
                break;

            case R.id.btB4:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 3);
                break;

            case R.id.btB5:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 4);
                break;

            case R.id.btB6:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 5);
                break;

            case R.id.btB7:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 6);
                break;

            case R.id.btB8:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 7);
                break;

            case R.id.btB9:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 8);
                break;

            case R.id.btB10:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 9);
                break;

            case R.id.btB11:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 10);
                break;

            case R.id.btB12:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 1, 11);
                break;


            //----

            case R.id.btG1:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 0);
                break;

            case R.id.btG2:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 1);
                break;

            case R.id.btG3:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 2);
                break;

            case R.id.btG4:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 3);
                break;

            case R.id.btG5:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 4);
                break;

            case R.id.btG6:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 5);
                break;

            case R.id.btG7:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 6);
                break;

            case R.id.btG8:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 7);
                break;

            case R.id.btG9:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 8);
                break;

            case R.id.btG10:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 9);
                break;

            case R.id.btG11:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 10);
                break;

            case R.id.btG12:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 2, 11);
                break;


            //------


            case R.id.btD1:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 0);
                break;

            case R.id.btD2:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 1);
                break;

            case R.id.btD3:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 2);
                break;

            case R.id.btD4:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 3);
                break;

            case R.id.btD5:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 4);
                break;

            case R.id.btD6:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 5);
                break;

            case R.id.btD7:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 6);
                break;

            case R.id.btD8:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 7);
                break;

            case R.id.btD9:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 8);
                break;

            case R.id.btD10:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 9);
                break;

            case R.id.btD11:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 10);
                break;

            case R.id.btD12:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 3, 11);
                break;


            //------

            case R.id.btA1:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 0);
                break;

            case R.id.btA2:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 1);
                break;

            case R.id.btA3:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 2);
                break;

            case R.id.btA4:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 3);
                break;

            case R.id.btA5:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 4);
                break;

            case R.id.btA6:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 5);
                break;

            case R.id.btA7:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 6);
                break;

            case R.id.btA8:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 7);
                break;

            case R.id.btA9:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 8);
                break;

            case R.id.btA10:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 9);
                break;

            case R.id.btA11:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 10);
                break;

            case R.id.btA12:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 4, 11);
                break;


            //----
            case R.id.btE1:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 0);
                break;

            case R.id.btE2:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 1);
                break;

            case R.id.btE3:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 2);
                break;

            case R.id.btE4:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 3);
                break;

            case R.id.btE5:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 4);
                break;

            case R.id.btE6:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 5);
                break;

            case R.id.btE7:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 6);
                break;

            case R.id.btE8:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 7);
                break;

            case R.id.btE9:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 8);
                break;

            case R.id.btE10:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 9);
                break;

            case R.id.btE11:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 10);
                break;

            case R.id.btE12:
                bt = (Button) findViewById(view.getId());
                checkTone(bt, 5, 11);
                break;
        }
    }

    @Override
    public void onSuccessCopied() {
        finish();
    }
}




      /*
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(2);
        grid.setColumnCount(2);
        grid.setBackgroundColor(Color.WHITE);

       // GridLayout params = new GridLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
       // grid.setLayoutParams(params);

        GridLayout.Spec rowSpan = GridLayout.spec(0, 1);
        GridLayout.Spec colSpan = GridLayout.spec(0, 1);
        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(rowSpan, colSpan);
        Button bt1 = new Button(this);
        bt1.setText("Abc");
        bt1.setId(0);
        grid.addView(bt1, gridParams);
        */


        /*
        RelativeLayout[] childLayout= new RelativeLayout[listLength] ;
        TextView[] tvName  = new TextView[listLength];

        for(int i =0;i<listLength;i++){
            try{

                childLayout[i] = new RelativeLayout(this);
                childLayout[i].setPadding(5, 5, 5, 5);
                params = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, 75);
                if(i==0){params.addRule(RelativeLayout.BELOW);}
                else{params.addRule(RelativeLayout.BELOW,i);}
                childLayout[i].setId(i+1);
                childLayout[i].setClickable(true);
                childLayout[i].setLayoutParams(params);
                childLayout[i].setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {


                        //Create the intent
                        Intent i = new Intent("ACTIIVTY");
                        startActivity(i);
                    }
                });

                tvName[i] = new TextView(this);
                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                tvName[i].setLayoutParams(params);
                childLayout[i].addView(tvName[i]);
                if(comList[i*3].length()>24){
                    String name = comList[i*3].substring(0,24)+"...";
                    tvName[i].setText(name);
                }else{
                    tvName[i].setText(comList[i*3]);
                }
                tvName[i].setId(listLength+1+i);
                tvName[i].setTextSize(12);
                tvName[i].setTextColor(Color.BLACK);

                parentLayout.addView(childLayout[i]);

            }catch(Exception e){System.out.println("Errrorrrrr");}
        }

    StartApp();
    }


    public void StartApp(){
        if (0 == 1)return;
    }*/