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
import java.util.Arrays;
import java.util.Random;

public class GuessHints extends AppCompatActivity {
    ImageView img;
    EditText character;
    Button submit;
    TextView answerPopUp;
    TextView printName;
    TextView timerText;
    private static long timeLeftInMilliseconds = 11000;
    ArrayList<Flag> list = new ArrayList<Flag>();
    Random random = new Random();
    int index = random.nextInt(253);
    String flagFileName = "";
    String name = "";
    Flag flagClass;
    String y = "";
    String x = "";
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_hints);

        img = (ImageView) findViewById(R.id.img);
        character = (EditText) findViewById(R.id.character);
        submit = (Button) findViewById(R.id.submit);
        answerPopUp = (TextView) findViewById(R.id.answerPopUp);
        printName = (TextView) findViewById(R.id.printName);
        timerText = (TextView) findViewById(R.id.timerText);

        //Reading flag Images filenames from File
        InputStream io = this.getResources().openRawResource(R.raw.flags);
        BufferedReader reader = new BufferedReader(new InputStreamReader(io));

        //Reading flag names from File
        InputStream io2 = this.getResources().openRawResource(R.raw.flagsnames);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(io2));

        try {

            while ((flagFileName = reader.readLine()) != null && (name = reader2.readLine())
                    != null) {
                flagClass = new Flag(flagFileName, name);
                list.add(flagClass);

            }

            int imageId = this.getResources().getIdentifier(list.get(index).getFileName().
                    toLowerCase(), "drawable", getPackageName());
            img.setBackgroundResource(imageId);
            final String country = list.get(index).getName().toLowerCase();   //Getting country name

            //If  timer is on
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
                        printName.setTextColor(getResources().getColor(R.color.BLUE));
                        printName.setText(country);
                        submit.setText(getResources().getText(R.string.nextButton));
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startActivity(getIntent());
                                countDownTimer.start();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            }
                        });

                    }
                }.start();

            }

            //Putting underscore
            final ArrayList<Integer> index = new ArrayList<>();
            for (int i = 0; i < country.length(); i++) {
                if (Character.isWhitespace(country.charAt(i))) {
                    x = x.concat(" ");
                } else {
                    x = x.concat(" _");
                }
            }

            //putting country name into variable y
            for (int i = 0; i < country.length(); i++) {
                if (Character.isWhitespace(country.charAt(i))) {
                    y = y.concat(" ");
                } else {
                    y = y.concat(" " + country.charAt(i));
                }
            }

            //putting x and y to charArray
            final char[] countryCharArray = y.toCharArray();
            final char[] chararray = x.toCharArray();

            printName.setText(x);//setText to printName

            //Button onClick()
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.msg.equals("timerOn")) {
                        countDownTimer.start();
                    }

                    String letter = character.getText().toString();

                    if (y.contains(letter)) {
                        answerPopUp.setTextColor(getResources().getColor(R.color.GREEN));
                        answerPopUp.setText(getResources().getString(R.string.correct));
                    } else {
                        answerPopUp.setTextColor(getResources().getColor(R.color.RED));
                        answerPopUp.setText(getResources().getString(R.string.wrong));
                    }
                    for (int i = 0; i < countryCharArray.length; i++) {
                        if (countryCharArray[i] == letter.charAt(0)) {
                            index.add(i);
                        }
                    }

                    for (int i = 0; i < index.size(); i++) {
                        chararray[index.get(i)] = letter.charAt(0);
                        System.out.println(chararray);
                    }
                    int count = 0;

                    index.clear();
                    printName.setText("");

                    for (int i = 0; i < countryCharArray.length; i++) {
                        String y = String.valueOf(chararray[i]);
                        printName.append(y);
                        System.out.print(chararray[i]);
                        if (y.equals("_")) {
                            count++;
                        }
                    }

                    if (count != 0) {
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

                    character.setText("");//Empty the character
                }
            });

            io.close();
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
