package me.theofrancisco.worldtour;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> altAnswers = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();

    class Question {
        int[] images = new int[3];
        String answer;

        Question(String answer, int image1, int image2, int image3) {
            this.answer = answer;
            this.images[0] = image1;
            this.images[1] = image2;
            this.images[2] = image3;
            altAnswers.add(answer);
        }
    }

    private int right = 0;
    private int wrong = 0;
    private TextView lblRight;
    private TextView lblWrong;
    private ImageView imgCity;
    private int questionIndex = 0;
    private int currentImage = 0;
    private Question currentQuestion;
    private int posCorrectAnswer = 0;
    private Button[] answerButtons = new Button[5];
    private boolean questionFinished = false;
    private boolean quizzEnd = false;

    private void initApp() {

        questions.add(new Question("Florence", R.drawable.florence1, R.drawable.florence2, R.drawable.florence3));
        questions.add(new Question("Havana", R.drawable.havana1, R.drawable.havana2, R.drawable.havana3));
        questions.add(new Question("Hoi An", R.drawable.hoian1, R.drawable.hoian2, R.drawable.hoian3));
        questions.add(new Question("Hong Kong", R.drawable.hongkong1, R.drawable.hongkong2, R.drawable.hongkong3));
        questions.add(new Question("Miami", R.drawable.miami1, R.drawable.miami2, R.drawable.miami3));
        questions.add(new Question("Mumbai", R.drawable.mumbai1, R.drawable.mumbai2, R.drawable.mumbai3));
        questions.add(new Question("Prague", R.drawable.prague1, R.drawable.prague2, R.drawable.prague3));
        questions.add(new Question("Stockholm", R.drawable.stockholm1, R.drawable.stockholm2, R.drawable.stockholm3));

        lblRight = findViewById(R.id.lblRigth);
        lblWrong = findViewById(R.id.lblWrong);
        imgCity = findViewById(R.id.imgCity);

        answerButtons[0] = findViewById(R.id.btnOption1);
        answerButtons[1] = findViewById(R.id.btnOption2);
        answerButtons[2] = findViewById(R.id.btnOption3);
        answerButtons[3] = findViewById(R.id.btnOption4);

        updatePoints();
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
        showQuestion();
    }

    public void btnOptionClicked(View view) {
        //if all the questions were answered, then exit.
        if (quizzEnd) return;

        String b = (String) view.getTag();
        int op = Integer.parseInt(b);
        Button btn = findViewById(view.getId());
        if (op == posCorrectAnswer) {
            btn.setBackgroundColor(Color.parseColor("#66BB6A"));
            right++;
            questionFinished = true;
        } else {
            wrong++;
            btn.setBackgroundColor(Color.parseColor("#E57373"));
        }
        updatePoints();
    }

    private void updatePoints() {
        lblRight.setText(getResources().getText(R.string.right) + ":" + right + "");
        lblWrong.setText(getResources().getText(R.string.wrong) + ":" + wrong);
    }

    public void btnNextClicked(View view) {
        currentImage++;
        Log.e("Main Activity", "Current Image: " + currentImage);
        if (currentImage == 3) currentImage = 0;
        imgCity.setImageResource(currentQuestion.images[currentImage]);
    }

    public void btnNxtQuestionClicked(View view) {
        if (questionFinished) {
            questionFinished = false;
            //restore background color for buttons to default
            for (int i = 0; i < 4; i++)
                answerButtons[i].setBackgroundResource(android.R.drawable.btn_default);

            if (++questionIndex < questions.size()) {
                currentQuestion = questions.get(questionIndex);
                showQuestion();
            } else {
                quizzEnd = true;
                Button b = findViewById(R.id.btnNxtQuestion);
                b.setText(R.string.quizzend);
            }
        }
    }

    public void showQuestion() {
        imgCity.setImageResource(currentQuestion.images[currentImage]);
        //Generate a Random Button Position for the correct answer
        Random random = new Random();

        posCorrectAnswer = random.nextInt(4);
        String answer = currentQuestion.answer;
        answerButtons[posCorrectAnswer].setText(answer);

        //settings the wrongs answers
        //1-shuffle the list with the all the answers, so isnt going to be
        //the same every time
        Collections.shuffle(altAnswers);
        int btnIndex = 0;
        int optIndex = 0;
        //Set the rest 3 buttons
        for (int i = 0; i < 3; i++) {
            //jump the correct answer button
            if (btnIndex == posCorrectAnswer) btnIndex++;
            //get an alternative answer
            String altAnswer = altAnswers.get(optIndex++);
            //if that answer is the same as the correct answer, then get the next
            if (altAnswer.equals(currentQuestion.answer)) {
                altAnswer = altAnswers.get(optIndex++);
            }
            answerButtons[btnIndex++].setText(altAnswer);
        }
    }
}

