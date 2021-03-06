package ca.acsea.funstop.sponsorquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ca.acsea.funstop.Globals;
import ca.acsea.funstop.R;
import ca.acsea.funstop.User;

import static android.content.Context.MODE_PRIVATE;


public class Quiz extends Fragment {


    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Question currentQuestion;
    private List<Question> qList;
    private View view;
    private TextView questionBox;
    private Button buttonA, buttonB, buttonC, buttonD;
    private int index;
    private DatabaseReference ref;
    private FirebaseUser currentUser;
    private int points;
    private User mUser;
    SharedPreferences prefs;

    public Quiz() {
        // Required empty public constructor
    }

    public Quiz(FragmentManager fm, User user, DatabaseReference ref){
        this.fragmentManager = fm;
        this.ref = ref;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        prefs = getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        //Changes the actionbar's Title
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#e6b773'>Daily quiz </font>"));
        view = inflater.inflate(R.layout.fragment_quiz, container, false);
//        getPoint();

        Gson gson = new Gson();
        String json = prefs.getString("userObject", "");
        mUser = gson.fromJson(json, User.class);

        points =(int)mUser.getPoint();

        transaction = fragmentManager.beginTransaction();

        //TODO: proceeds to the next question after clicking on button
        questionBox = (TextView) view.findViewById(R.id.questionBox);
        buttonA = (Button) view.findViewById(R.id.buttonA);
        buttonB = (Button) view.findViewById(R.id.buttonB);
        buttonC = (Button) view.findViewById(R.id.buttonC);
        buttonD = (Button) view.findViewById(R.id.buttonD);

        init();


        // Inflate the layout for this fragment
        return view;
    }

    public void init(){
        qList = createQuestions();

        //Shuffle the question list only in the beginning.
        if(mUser.getIndex() == 0) {
            Collections.shuffle(qList);
        }
        currentQuestion = qList.get(mUser.getIndex());
        questionBox.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOptionList().get(0));
        buttonB.setText(currentQuestion.getOptionList().get(1));
        buttonC.setText(currentQuestion.getOptionList().get(2));
        buttonD.setText(currentQuestion.getOptionList().get(3));
        handleClick();
    }

    public void handleClick(){
        for(final Button btn: Arrays.asList(buttonA, buttonB, buttonC, buttonD)) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enableButton(false);
                    if (btn.getText().equals(currentQuestion.getAnswer())) {
                        Toast.makeText(getContext(), "correct", Toast.LENGTH_SHORT).show();
                        colorButton(btn, Color.rgb(162,196,255));
                        addPoints(10);
                    } else {
                        Toast.makeText(getContext(), "WRONG!!!!", Toast.LENGTH_SHORT).show();
                        colorButton(btn, Color.RED);
                        addPoints(5);
                    }
                }
            });
        }
    }

    public void addPoints(int value){
        mUser.setPoint(mUser.getPoint() + value);
        //Question number decision
        int i = mUser.getIndex();
        if(mUser.getIndex() < qList.size() - 1) {
            mUser.setIndex(++i);
        }else {
            mUser.setIndex(0);
        }
        save();
    }


    /**
     * Enabling or disabling all the buttons depends the value
     * @param value true will enable, false will disable
     */
    public void enableButton(boolean value){
        for(Button btn: Arrays.asList(buttonA, buttonB, buttonC, buttonD)){
            btn.setEnabled(value);
        }
    }

    /**
     * Changes the color of the button with correct result
     * @param btn
     */
    public void colorButton(final Button btn, int color){
        btn.setBackgroundColor(color);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setBackgroundColor(Color.WHITE);
                enableButton(true);
//                nextQuestion();
                transaction.replace(R.id.frameLayout, new QuizEnd()).commit();
            }
        }, 2000);
    }


    public ArrayList<Question> createQuestions(){
        String question1 = "Which day is the Lunar New Year day in 2020?";
        List<String> answer1 = Arrays.asList("January 25 th", "January 24 th", "January 27 th", "January 1 st");

        String question2 = "Which animal is not used as a zodiac animal for Lunar New Year?";
        List<String> answer2 = Arrays.asList("Rat", "Rabbit", "Dragon", "Lion");

        String question3 = "How many years are in one cycle of the Lunar New Year?";
        List<String> answer3 = Arrays.asList("10", "50", "12", "100");

        String question4 = "What determines the start of the Lunar New Year?";
        List<String> answer4 = Arrays.asList("The new moon on the first day of the new year", "The first day of the year in the Gregorian Calendar", "Exactly 20 days after the first day of the year in the Gregorian Calendar", "It is determined randomly");

        String question5 = "What was the zodiac animal sign for most of 2019?";
        List<String> answer5 = Arrays.asList("Rat", "Dog", "Pig", "Monkey");

        String question6 = "What is the zodiac animal sign for most of 2020??";
        List<String> answer6 = Arrays.asList("Dog", "Pig", "Monkey", "Rat");

        String question7 = "When was Lunar New Year in 2019?";
        List<String> answer7 = Arrays.asList("February 6th", "January 1st", "January 25th", "February 5th");

        String question8 = "How many zodiac animals does the Lunar New Year use?";
        List<String> answer8 = Arrays.asList("12", "11", "6", "13");

        String question9 = "What is Lunar New Year called in Korea?";
        List<String> answer9 = Arrays.asList("Tết", "Seollal", "Tsagaan Sar", "Spring Festival");

        String question10 = "What is Lunar New Year called in Vietnam?";
        List<String> answer10 = Arrays.asList("Tết", "Seollal", "Tsagaan Sar", "Spring Festival");

        String question11 = "What is Lunar New Year called in Mongolia?";
        List<String> answer11 = Arrays.asList("Tết", "Seollal", "Tsagaan Sar", "Spring Festival");

        String question12 = "Which calendar does the Lunar New Year follow?";
        List<String> answer12 = Arrays.asList("The Lunar Calendar", "The Gregorian Calendar", "The Vancouver Calendar", "The Toronto Calendar");

        String question13 = "How many months are in the Lunar Calendar?";
        List<String> answer13 = Arrays.asList("12 or 13", "10", "11", "6");

        String question14 = "Which day of the Lunar Calendar is Lunar New Year Celebrated?";
        List<String> answer14 = Arrays.asList("Last day of the year", "First day of the year", "Second day of the year", "15 th day of the year");

        String question15 = "What is the one event you should attend to celebrate Lunar New Year in Vancouver?";
        List<String> answer15 = Arrays.asList("LunarFest", "I am not sure", "I don’t know", "None");

        String question16 = "Which western holiday did Lunar New Year land on in the year 2010?";
        List<String> answer16 = Arrays.asList("Valentine’s Day", "New Year’s Day", "Christmas Day", "Family Day");

        String question17 = "What are the dates of the Lunar New Year celebration at Vancouver Art Gallery Plaza?";
        List<String> answer17 = Arrays.asList("January 18th ~ January 19th", "January 25th ~ January 26th", "January 16th ~ February 10th", "February 17th ~ February 18th");

        String question18 = "Which company does this logo belong to?";
        List<String> answer18 = Arrays.asList("option1", "answer", "option3", "option4");



        Question mQuestion1 = new Question(question1, answer1.get(0), answer1);
        Question mQuestion2 = new Question(question2, answer2.get(3), answer2);
        Question mQuestion3 = new Question(question3, answer3.get(2), answer3);
        Question mQuestion4 = new Question(question4, answer4.get(0), answer4);
        Question mQuestion5 = new Question(question5, answer5.get(2), answer5);
        Question mQuestion6 = new Question(question6, answer6.get(3), answer6);
        Question mQuestion7 = new Question(question7, answer7.get(3), answer7);
        Question mQuestion8 = new Question(question8, answer8.get(0), answer8);
        Question mQuestion9 = new Question(question9, answer9.get(1), answer9);
        Question mQuestion10 = new Question(question10, answer10.get(0), answer10);
        Question mQuestion11 = new Question(question11, answer11.get(2), answer11);
        Question mQuestion12 = new Question(question12, answer12.get(0), answer12);
        Question mQuestion13 = new Question(question13, answer13.get(0), answer13);
        Question mQuestion14 = new Question(question14, answer14.get(1), answer14);
        Question mQuestion15 = new Question(question15, answer15.get(0), answer15);
        Question mQuestion16 = new Question(question16, answer16.get(0), answer16);
        Question mQuestion17 = new Question(question17, answer17.get(1), answer17);
        Question mQuestion18 = new Question(question18, answer18.get(1), answer18);

        ArrayList<Question> list = new ArrayList<>();
        list.add(mQuestion1);
        list.add(mQuestion2);
        list.add(mQuestion3);
        list.add(mQuestion4);
        list.add(mQuestion5);
        list.add(mQuestion6);
        list.add(mQuestion7);
        list.add(mQuestion8);
        list.add(mQuestion9);
        list.add(mQuestion10);
        list.add(mQuestion11);
        list.add(mQuestion12);
        list.add(mQuestion13);
        list.add(mQuestion14);
        list.add(mQuestion15);
        list.add(mQuestion16);
        list.add(mQuestion17);
        list.add(mQuestion18);

        return list;
    }



    public void nextQuestion(){
        try{
            if(index < qList.size()-1){
                index++;
                currentQuestion = qList.get(index);
                questionBox.setText(currentQuestion.getQuestion());
                buttonA.setText(currentQuestion.getOptionList().get(0));
                buttonB.setText(currentQuestion.getOptionList().get(1));
                buttonC.setText(currentQuestion.getOptionList().get(2));
                buttonD.setText(currentQuestion.getOptionList().get(3));
            }else{
                transaction.replace(R.id.frameLayout, new QuizEnd()).commit();
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
    }
    public void onPause(){
        super.onPause();
    }

    public void save() {
        SharedPreferences.Editor prefsEditor = prefs.edit();
//        prefsEditor.putInt("points", points);
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("userObject", json);
        prefsEditor.commit();
//        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("point").setValue(points);
    }


}