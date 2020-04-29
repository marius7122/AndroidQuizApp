package com.example.quizapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;


public class QuizConfigFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner categorySpinner, difficultySpinner;

    public QuizConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz_config, container, false);

        // set spinners values
        categorySpinner = view.findViewById(R.id.categorySpinner);
        difficultySpinner = view.findViewById(R.id.difficultySpinner);

        ArrayAdapter<CharSequence> categoryAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.question_types, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> difficultyAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.difficulties, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        difficultySpinner.setAdapter(difficultyAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        // set button callback
        Button btnGenerateQuiz = view.findViewById(R.id.btnGenerateQuiz);
        btnGenerateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQuiz();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void generateQuiz()
    {
        String category = categorySpinner.getSelectedItem().toString();
        String difficulty = difficultySpinner.getSelectedItem().toString();

        HashMap<String, String> categoryToId = new HashMap<>();
        categoryToId.put("General Knowledge", "9");
        categoryToId.put("Video Games", "15");
        categoryToId.put("Computers", "18");
        categoryToId.put("Math", "19");

        ((QuizActivity)getActivity()).generateQuiz(categoryToId.get(category), difficulty);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
