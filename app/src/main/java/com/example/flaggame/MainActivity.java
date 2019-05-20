package com.example.flaggame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Initializing XML Components
    Button btnGuessCountry;
    Button btnGuessHint;
    Button btnGuessTheFlag;
    Button btnAdvanceLevel;
    Switch timer;
    static String msg = "";//msg Variable for Extra Message
    public static final String EXTRA_MESSAGE =
            "com.example.android.twoactivities.extra.MESSAGE";//Extra Message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuessCountry = (Button) findViewById(R.id.guess_country);//Guess Country Button
        btnGuessHint = (Button) findViewById(R.id.guess_hints);//Guess Hints Button
        btnGuessTheFlag = (Button) findViewById(R.id.guess_the_flag);//Guess Flags Button
        btnAdvanceLevel = (Button) findViewById(R.id.advance_level);//Advance Level Button
        timer = (Switch) findViewById(R.id.timer);//Timer Switch

        //Guess Country Button OnClickListener
        btnGuessCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), GuessCountry.class);
                        intent.putExtra(EXTRA_MESSAGE, msg);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    }
                }, 100);

            }
        });

        //Guess Hints Button OnClickListener
        btnGuessHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), GuessHints.class);
                        intent.putExtra(EXTRA_MESSAGE, msg);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    }
                }, 100);

            }
        });
        //Guess the Flag Button OnClickListener
        btnGuessTheFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), GuessTheFlag.class);
                        intent.putExtra(EXTRA_MESSAGE, msg);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    }
                }, 100);

            }
        });

        //Advance Level Button OnClickListener
        btnAdvanceLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), AdvanceLevel.class);
                        intent.putExtra(EXTRA_MESSAGE, msg);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    }
                }, 100);

            }
        });
        //Timer Button
        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    msg = "timerOn";//If the button is on Pass "timerOn" Message to Another Activity
                }else {
                    msg = "timerOff";//If the button is on Pass "timerOff" Message to Another Activity
                }
            }} );
    }
}
