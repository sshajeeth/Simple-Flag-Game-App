package com.example.flaggame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flaggame.Classes.Flag;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static com.example.flaggame.MainActivity.EXTRA_MESSAGE;

public class AdvanceLevel extends AppCompatActivity {
    ImageView flag1;
    ImageView flag2;
    ImageView flag3;
    EditText ans1;
    EditText ans2;
    EditText ans3;
    Button submit;
    TextView scoreXML;
    TextView timerText;


    int score = 0;

    ArrayList<Flag> list = new ArrayList<Flag>();
    Random random = new Random();
    String flagFileName = "";
    String name = "";
    Flag flagClass;
    private static long timeLeftInMilliseconds = 31000;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_level);
        flag1 = (ImageView) findViewById(R.id.flag1);
        flag2 = (ImageView) findViewById(R.id.flag2);
        flag3 = (ImageView) findViewById(R.id.flag3);
        ans1 = (EditText) findViewById(R.id.ans1);
        ans2 = (EditText) findViewById(R.id.ans2);
        ans3 = (EditText) findViewById(R.id.ans3);
        submit = (Button) findViewById(R.id.submit);
        scoreXML = (TextView) findViewById(R.id.score);
        timerText = (TextView) findViewById(R.id.timerText);


        Intent intent = getIntent();


        //timerText.setText(timer.getTimeLeft());
        //Random Number Generating
        int index1 = random.nextInt(253);
        int index2 = random.nextInt(253);
        int index3 = random.nextInt(253);


        while (index1 == index2 || index1 == index3 || index2 == index3) {
            index1 = random.nextInt(253);
            index2 = random.nextInt(253);
            index3 = random.nextInt(253);


        }
        InputStream io = this.getResources().openRawResource(R.raw.flags);
        BufferedReader reader = new BufferedReader(new InputStreamReader(io));

        InputStream io2 = this.getResources().openRawResource(R.raw.flagsnames);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(io2));

        try {

            while ((flagFileName = reader.readLine()) != null && (name = reader2.readLine())
                    != null) {
                flagClass = new Flag(flagFileName, name);
                list.add(flagClass);

            }

            int image1 = this.getResources().getIdentifier(list.get(index1).getFileName().
                    toLowerCase(), "drawable", getPackageName());


            int image2 = this.getResources().getIdentifier(list.get(index2).getFileName().
                    toLowerCase(), "drawable", getPackageName());
            int image3 = this.getResources().getIdentifier(list.get(index3).getFileName().
                    toLowerCase(), "drawable", getPackageName());

            flag1.setBackgroundResource(image1);
            flag2.setBackgroundResource(image2);
            flag3.setBackgroundResource(image3);

            final String answer1 = list.get(index1).getName().toLowerCase();
            final String answer2 = list.get(index2).getName().toLowerCase();
            final String answer3 = list.get(index3).getName().toLowerCase();

            if (MainActivity.msg.equals("timerOn")) {
                countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
                    @Override
                    public void onTick(long l) {

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
                        ans1.setTextColor(getResources().getColor(R.color.BLUE));
                        ans1.setText(answer1);
                        ans2.setTextColor(getResources().getColor(R.color.BLUE));
                        ans2.setText(answer2);
                        ans3.setTextColor(getResources().getColor(R.color.BLUE));
                        ans3.setText(answer3);
                        scoreXML.setText("Score: " + 0);
                        submit.setText(getResources().getText(R.string.nextButton));
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startActivity(getIntent());
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                countDownTimer.start();
                            }
                        });
                    }
                }.start();
                boolean timeRunning = true;


            }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.msg.equals("timerOn")) {
                        countDownTimer.cancel();
                    }
                    if (answer1.contentEquals(ans1.getText())) {
                        ans1.setTextColor(getResources().getColor(R.color.GREEN));
                        score += 1;

                    } else if (answer2.contentEquals(ans2.getText())) {
                        ans2.setTextColor(getResources().getColor(R.color.GREEN));
                        score += 1;

                    } else if (answer3.contentEquals(ans3.getText())) {
                        ans3.setTextColor(getResources().getColor(R.color.GREEN));
                        score += 1;

                    } else {
                        ans1.setTextColor(getResources().getColor(R.color.RED));
                        ans2.setTextColor(getResources().getColor(R.color.RED));
                        ans3.setTextColor(getResources().getColor(R.color.RED));
                    }


                    scoreXML.setText("Score: " + String.valueOf(score));
                    submit.setText(getResources().getText(R.string.nextButton));

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(getIntent());
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            if (MainActivity.msg.equals("timerOn")) {
                                countDownTimer.start();
                            }
                            finish();

                        }
                    });
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

