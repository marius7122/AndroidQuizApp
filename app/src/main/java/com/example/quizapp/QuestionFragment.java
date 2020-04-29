package com.example.quizapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class QuestionFragment extends Fragment {

    private View view;

    private Question[] questions;
    private int questionIndex;
    private int score;

    private String category;
    private String difficulty;

    private TextView tvQuestion;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private Button btnAnswer4;

    public QuestionFragment() {
        // Required empty public constructor
    }

    public QuestionFragment(Question[] questions, String category, String difficulty) {
        this.questions = questions;
        this.category = category;
        this.difficulty = difficulty;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);

        tvQuestion = view.findViewById(R.id.tvQuestion);
        btnAnswer1 = view.findViewById(R.id.btnAnswer1);
        btnAnswer2 = view.findViewById(R.id.btnAnswer2);
        btnAnswer3 = view.findViewById(R.id.btnAnswer3);
        btnAnswer4 = view.findViewById(R.id.btnAnswer4);

        // set listeners
        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                checkAnswer(b.getText().toString());
            }
        });
        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                checkAnswer(b.getText().toString());
            }
        });
        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                checkAnswer(b.getText().toString());
            }
        });
        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                checkAnswer(b.getText().toString());
            }
        });

        updateQuestion();

        return view;
    }

    private void checkAnswer(String ans) {
        if (ans.compareTo(questions[questionIndex - 1].answer) == 0)
            score++;
        updateQuestion();
    }

    private void updateQuestion() {
        if (questionIndex == questions.length) {
            gameOver();
            return;
        }

        Question q = questions[questionIndex];

        // shuffle options
        List<String> options = Arrays.asList(q.options);
        Collections.shuffle(options);
        options.toArray(q.options);

        tvQuestion.setText(q.question);
        btnAnswer1.setText(q.options[0]);
        btnAnswer2.setText(q.options[1]);
        btnAnswer3.setText(q.options[2]);
        btnAnswer4.setText(q.options[3]);

        questionIndex++;
    }

    private void gameOver() {
        AlertDialog.Builder alertDialogBulider = new AlertDialog.Builder(getContext());
        alertDialogBulider
                .setMessage("Your score is " + score + " / " + questions.length)
                .setCancelable(false)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logToDB();
                        startActivity(new Intent(getContext(), QuizActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logToDB();
                        startActivity(new Intent(getContext(), MainActivity.class));
                    }
                });

        AlertDialog alertDialog = alertDialogBulider.create();
        alertDialog.show();
    }

    private void logToDB() {
        Score s = new Score();
        s.category = category;
        s.difficulty = difficulty;
        s.score = Integer.toString(score);

        ((QuizActivity)getActivity()).dbHelper.addData(s);
    }

}
