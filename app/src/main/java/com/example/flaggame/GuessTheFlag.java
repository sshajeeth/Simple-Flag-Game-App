package com.example.flaggame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flaggame.Classes.Flag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.RED;

public class GuessTheFlag extends AppCompatActivity{
    ImageView img1;
    ImageView img2;
    ImageView img3;
    TextView countryName;
    TextView answer;
    TextView timerText;
    Button submit;
    int correctAns;
    ArrayList<Flag> list = new ArrayList<Flag>();
    Random random = new Random();

    String flagFileName = "";
    String name = "";
    Flag flagClass;
    private static long timeLeftInMilliseconds = 11000;
    CountDownTimer countDownTimer;

    ArrayList<ImageView> imageViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);

        img1 = (ImageView) findViewById(R.id.flag1);
        img2 = (ImageView) findViewById(R.id.flag2);
        img3 = (ImageView) findViewById(R.id.flag3);
        countryName = (TextView) findViewById(R.id.countryName);
        answer = (TextView) findViewById(R.id.answer);
        timerText=(TextView)findViewById(R.id.timerText);
        submit = (Button) findViewById(R.id.level3Submit);

        imageViewList.add(img1);
        imageViewList.add(img2);
        imageViewList.add(img3);

        //Random Number Generating
        int index1 = random.nextInt(253);
        int index2 = random.nextInt(253);
        int index3 = random.nextInt(253);
        int imgIndex1 = random.nextInt(3);
        int imgIndex2 = random.nextInt(3);
        int imgIndex3 = random.nextInt(3);

        while (index1==index2||index1==index3||index2==index3||imgIndex1==imgIndex2||imgIndex1
                ==imgIndex3||imgIndex2==imgIndex3){
            index1 = random.nextInt(253);
            index2 = random.nextInt(253);
            index3 = random.nextInt(253);
            imgIndex1 = random.nextInt(3);
            imgIndex2 = random.nextInt(3);
            imgIndex3 = random.nextInt(3);
        }

 correctAns=imgIndex1;


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

            int image1 = this.getResources().getIdentifier(list.get(index1).
                    getFileName().toLowerCase(), "drawable", getPackageName());
            int image2 = this.getResources().getIdentifier(list.get(index2).
                    getFileName().toLowerCase(), "drawable", getPackageName());
            int image3 = this.getResources().getIdentifier(list.get(index3).
                    getFileName().toLowerCase(), "drawable", getPackageName());

            imageViewList.get(imgIndex1).setBackgroundResource(image1);
            imageViewList.get(imgIndex2).setBackgroundResource(image2);
            imageViewList.get(imgIndex3).setBackgroundResource(image3);


            countryName.setText(list.get(index1).getName());
            io.close();

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
                       answer.setText("Correct Answer is Image "+correctAns);
                       answer.setTextColor(getResources().getColor(R.color.GREEN));
                       submit.setVisibility(View.VISIBLE);

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

            imageViewList.get(imgIndex1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answer.setTextColor(getResources().getColor(R.color.GREEN));
                    answer.setText(getResources().getText(R.string.correct));
                    submit.setVisibility(View.VISIBLE);
                }
            });
            imageViewList.get(imgIndex2).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    answer.setTextColor(getResources().getColor(R.color.RED));
                    answer.setText(getResources().getText(R.string.wrong));
                    submit.setVisibility(View.INVISIBLE);

                }
            });
            imageViewList.get(imgIndex3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answer.setTextColor(getResources().getColor(R.color.RED));
                    answer.setText(getResources().getText(R.string.wrong));
                    submit.setVisibility(View.INVISIBLE);

                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
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
