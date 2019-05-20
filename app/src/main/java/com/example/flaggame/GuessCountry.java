package com.example.flaggame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.flaggame.Classes.Flag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static com.example.flaggame.MainActivity.EXTRA_MESSAGE;

public class GuessCountry extends AppCompatActivity {
    ImageView flag;//ImageView Declaration
    Spinner spinner;//Spinner Declaration
    TextView correct;//TextView Declaration
    TextView correct_answer;//TextView Declaration
    TextView timerText;//TextView Declaration
    Button submit;//Button Declaration
    ArrayList<Flag> list = new ArrayList<Flag>();//ArrayList Declaration

    //Random Number Generating
    Random random = new Random();
    int index = random.nextInt(253);

    String flagFileName = "";
    String name = "";
    Flag flagClass;

    //Timer Declarations
    private static long timeLeftInMilliseconds = 11000;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_country);

        //XML Components Declaration
        flag = (ImageView) findViewById(R.id.flag);
        spinner = (Spinner) findViewById(R.id.spinner);
        correct = (TextView) findViewById(R.id.correct);
        correct_answer = (TextView) findViewById(R.id.correct_answer);
        timerText = (TextView) findViewById(R.id.timerText);
        submit = (Button) findViewById(R.id.submit);

        //If the timer Switch is on Timer will start
        if (MainActivity.msg.equals("timerOn")) {
            countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
                @Override
                public void onTick(long l) {
                    //Timer Running Code
                    timeLeftInMilliseconds = l;
                    int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;
                    if (seconds > 9) {
                        String timeLeft = "Seconds Left: ";
                        timeLeft += seconds;
                        timerText.setText(timeLeft);
                    } else {
                        String timeLeft = "Seconds Left: 0";
                        timeLeft += seconds;
                        timerText.setText(timeLeft);
                    }
                }

                @Override
                public void onFinish() {
                    //If the countdown timer finished finish() method will execute
                    correct.setTextColor(getResources().getColor(R.color.RED));//Changing Text Colour
                    correct.setText(getResources().getText(R.string.wrong));//Changing Text

                    correct_answer.setTextColor(getResources().getColor(R.color.GREEN));//Changing Text Colour into Green
                    correct_answer.setText(list.get(index).getName());//Getting Correct answer and show it

                    submit.setText(getResources().getText(R.string.nextButton));//Changing Button Text into Next
                    //Button Click
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(getIntent());
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);//Transition for New Activity

                            countDownTimer.start();//Countdown Timer Started Again
                        }
                    });
                }
            }.start();


        }

        //Reading Flags Images filenames From Text File
        InputStream io = this.getResources().openRawResource(R.raw.flags);
        BufferedReader reader = new BufferedReader(new InputStreamReader(io));

        //Reading Flags Names From Text File
        InputStream io2 = this.getResources().openRawResource(R.raw.flagsnames);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(io2));


        try {

            while ((flagFileName = reader.readLine()) != null && (name = reader2.readLine())
                    != null) {
                flagClass = new Flag(flagFileName, name);
                list.add(flagClass);

            }

            //Setting Random Image id
            int imageId = this.getResources().getIdentifier(list.get(index).getFileName().
                    toLowerCase(), "drawable", getPackageName());
            flag.setBackgroundResource(imageId);//Flag Image Added to flag ImageView
            io.close();

            //Spinner Started
            ArrayAdapter<Flag> adapter = new ArrayAdapter<Flag>(this,
                    R.layout.support_simple_spinner_dropdown_item, list);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            //submit Button OnClick() Method
            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (MainActivity.msg.equals("timerOn")) {
                        countDownTimer.cancel();//If submit Button clicked then pause the countdown timer
                    }
                    if (String.valueOf(spinner.getSelectedItem()).equals(list.get(index).getName())) {
                        correct.setTextColor(getResources().getColor(R.color.GREEN));//Change text colour into green
                        correct.setText(getResources().getText(R.string.correct));//setting Text
                        submit.setText(getResources().getText(R.string.nextButton));//Changing Button Text
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        });
                    } else {
                        //Change// text colour into Red
                        correct.setTextColor(getResources().getColor(R.color.RED));
                        correct.setText(getResources().getText(R.string.wrong));
                        correct_answer.setTextColor(getResources().getColor(R.color.GREEN));
                        correct_answer.setText(list.get(index).getName());
                        submit.setText(getResources().getText(R.string.nextButton));


                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(getIntent());
                                if (MainActivity.msg.equals("timerOn")) {
                                    countDownTimer.start();
                                }
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goHome(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
