package me.theofrancisco.worldtour;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

//!--(c)teopinillo jun/2018--

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> pos_0_2 = new ArrayList<>();
    ArrayList<Integer> pos_0_3 = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();
    private ImageView imgCity;
    private int questionIndex;
    private int currentImage;
    private Question currentQuestion;
    private RadioButton[] cityBtn = new RadioButton[3];
    private RadioGroup radioGroup;
    private CheckBox[] factBtn = new CheckBox[4];
    private Button btnOtherTip;
    private Button btnNextQuestion;
    private int maxPointPossibles;
    private int questionPoints;
    private int score;
    private EditText editCountry;
    private Button btnSubmit;
    private TextView txtTrueFacts;

    private void initApp() {

        btnOtherTip = findViewById(R.id.btnOtherTip);
        btnNextQuestion = findViewById(R.id.btnNxtQuestion);
        editCountry = findViewById(R.id.editCountry);
        radioGroup = findViewById(R.id.radioGroup);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtTrueFacts = findViewById(R.id.txtTrueFacts);

        Question question = new Question("Itaky", "Florence", R.drawable.florence1, R.drawable.florence2, R.drawable.florence3);
        question.setFact(getResources().getString(R.string.florence_fact_1));
        question.setFact(getResources().getString(R.string.florence_fact_2));
        question.setFact(getResources().getString(R.string.florence_fact_3));
        question.setFact(getResources().getString(R.string.florence_fact_4));
        questions.add(question);

        question = new Question("Cuba", "Havana", R.drawable.havana1, R.drawable.havana2, R.drawable.havana3);
        question.setFact(getResources().getString(R.string.havana_fact_1));
        question.setFact(getResources().getString(R.string.havana_fact_2));
        question.setFact(getResources().getString(R.string.havana_fact_3));
        question.setFact(getResources().getString(R.string.havana_fact_4));
        questions.add(question);

        question = new Question("Viet Nam", "Hoi An", R.drawable.hoian1, R.drawable.hoian2, R.drawable.hoian3);
        question.setFact(getResources().getString(R.string.hoian_fact_1));
        question.setFact(getResources().getString(R.string.hoian_fact_2));
        question.setFact(getResources().getString(R.string.hoian_fact_3));
        question.setFact(getResources().getString(R.string.hoian_fact_4));
        questions.add(question);

        question = new Question("China", "Hong Kong", R.drawable.hongkong1, R.drawable.hongkong2, R.drawable.hongkong3);
        question.setFact(getResources().getString(R.string.hongkong_fact_1));
        question.setFact(getResources().getString(R.string.hongkong_fact_2));
        question.setFact(getResources().getString(R.string.hongkong_fact_3));
        question.setFact(getResources().getString(R.string.hongkong_fact_4));
        questions.add(question);

        question = new Question("USA", "Miami", R.drawable.miami1, R.drawable.miami2, R.drawable.miami3);
        question.setFact(getResources().getString(R.string.miami_fact_1));
        question.setFact(getResources().getString(R.string.miami_fact_2));
        question.setFact(getResources().getString(R.string.miami_fact_3));
        question.setFact(getResources().getString(R.string.miami_fact_4));
        questions.add(question);

        question = new Question("India", "Mumbai", R.drawable.mumbai1, R.drawable.mumbai2, R.drawable.mumbai3);
        question.setFact(getResources().getString(R.string.mumbai_fact_1));
        question.setFact(getResources().getString(R.string.mumbai_fact_2));
        question.setFact(getResources().getString(R.string.mumbai_fact_3));
        question.setFact(getResources().getString(R.string.mumbai_fact_4));
        questions.add(question);

        question = new Question("Czech Republic", "Prague", R.drawable.prague1, R.drawable.prague2, R.drawable.prague3);
        question.setFact(getResources().getString(R.string.prague_fact_1));
        question.setFact(getResources().getString(R.string.prague_fact_2));
        question.setFact(getResources().getString(R.string.prague_fact_3));
        question.setFact(getResources().getString(R.string.prague_fact_4));
        questions.add(question);

        question = new Question("Sweden", "Stockholm", R.drawable.stockholm1, R.drawable.stockholm2, R.drawable.stockholm3);
        question.setFact(getResources().getString(R.string.stockholm_fact_1));
        question.setFact(getResources().getString(R.string.stockholm_fact_2));
        question.setFact(getResources().getString(R.string.stockholm_fact_3));
        question.setFact(getResources().getString(R.string.stockholm_fact_4));
        questions.add(question);

        maxPointPossibles = questions.size() * 4;
        score = 0;
        //shufle the order of the questions, so they will appear on
        //different order every time the app start
        Collections.shuffle(questions);

        for (int i = 0; i < 3; i++) {
            pos_0_2.add(i);
            pos_0_3.add(i);
        }
        pos_0_3.add(3);

        imgCity = findViewById(R.id.imgCity);

        cityBtn[0] = findViewById(R.id.btnOption1);
        cityBtn[1] = findViewById(R.id.btnOption2);
        cityBtn[2] = findViewById(R.id.btnOption3);

        factBtn[0] = findViewById(R.id.checkBox1);
        factBtn[1] = findViewById(R.id.checkBox2);
        factBtn[2] = findViewById(R.id.checkBox3);
        factBtn[3] = findViewById(R.id.checkBox4);
        radioGroup.requestFocus();
    }

    void createQuizz(Question question) {
        //clear the country edit text
        editCountry.setText("");
        //clear all the radio buttons and the facts

        radioGroup.clearCheck();
        for (int i = 0; i < 4; i++) {
            factBtn[i].setChecked(false);
        }
        LinkedList<Question> otherOptions = new LinkedList<>(questions);
        otherOptions.remove(question);
        Collections.shuffle(otherOptions);

        //setting the city
        Collections.shuffle(pos_0_2);
        //set the right answer
        cityBtn[pos_0_2.get(0)].setText(question.getCity());
        cityBtn[pos_0_2.get(0)].setTag(1);
        //set other cities

        for (int i = 1; i < 3; i++) {
            cityBtn[pos_0_2.get(i)].setText(otherOptions.get(i).getCity());
            cityBtn[pos_0_2.get(i)].setTag(0); //if user select this, get 0 point
        }

        //setting the facts
        Collections.shuffle(pos_0_3);
        factBtn[pos_0_3.get(0)].setText(question.getFact(pos_0_2.get(0)));
        factBtn[pos_0_3.get(0)].setTag(1);
        factBtn[pos_0_3.get(1)].setText(question.getFact(pos_0_2.get(1)));
        factBtn[pos_0_3.get(1)].setTag(1);
        factBtn[pos_0_3.get(2)].setText(otherOptions.get(2).getFact(0));
        factBtn[pos_0_3.get(2)].setTag(-1);
        factBtn[pos_0_3.get(3)].setText(otherOptions.get(3).getFact(1));
        factBtn[pos_0_3.get(3)].setTag(-1);

        //set the image
        imgCity.setImageResource(question.images[0]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialization the Questions
        initApp();
        //shuffle the questions
        Collections.shuffle(questions);
        questionIndex = 0;
        currentImage = 0;
        //Show the first question
        currentQuestion = questions.get(0);
        createQuizz(currentQuestion);
    }

    public void btnOtherTipClicked(View view) {
        currentImage++;
        Log.e("Main Activity", "Current Image: " + currentImage);
        if (currentImage == 3) currentImage = 0;
        imgCity.setImageResource(currentQuestion.images[currentImage]);
    }

    public void showToast(String _text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, _text, duration);
        toast.show();
    }

    public void btnNxtQuestionClicked(View view) {
        addPoints();
        showToast("points: " + questionPoints);
        if (++questionIndex < questions.size()) {
            currentQuestion = questions.get(questionIndex);
            createQuizz(currentQuestion);
        } else {
            finalScore();
        }
    }

    public void btnSubmitClicked(View view) {
        finalScore();
    }

    //add all the points the user get
    //for its answers
    public void addPoints() {
        int p = 0;
        //first: the ponts for the city
        RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
        if (rb != null) p = (Integer) rb.getTag();

        //second: point for the facts
        for (int i = 0; i < 4; i++) {
            if (factBtn[i].isChecked()) {
                p += (Integer) factBtn[i].getTag();
            }
        }
        //third: point for the country
        if (currentQuestion.country.toLowerCase().equals(editCountry.getText().toString().trim().toLowerCase()))
            p++;

        questionPoints = p;
        score += p;

    }

    public void finalScore() {

        btnOtherTip.setEnabled(false);
        btnNextQuestion.setEnabled(false);
        btnSubmit.setEnabled(false);
        int per = (score * 100) / maxPointPossibles;
        String result = "Final Score: " + score + " for " + per + " %";
        txtTrueFacts.setText(result);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }

    class Question {
        int[] images = new int[3];
        String city;
        String country;
        ArrayList<String> facts; //the facts about the city or country


        Question(String country, String city, int image1, int image2, int image3) {
            this.city = city;
            this.country = country;
            this.images[0] = image1;
            this.images[1] = image2;
            this.images[2] = image3;
            facts = new ArrayList<>();
        }

        void setFact(String fact) {
            facts.add(fact);
            Collections.shuffle(facts);
        }

        String getCity() {
            return city;
        }

        String getFact(int p) {
            return facts.get(p);
        }
    }


}

