package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    public DatabaseHelper dbHelper;

    private FrameLayout flQuiz;
    private ProgressDialog pd;
    private ArrayList<Question> questions;

    private String category;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        dbHelper = new DatabaseHelper(this);
        // dbHelper.deleteDB();

        Fragment quizConfigFragment = new QuizConfigFragment();

        flQuiz = (FrameLayout)findViewById(R.id.flQuiz);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.flQuiz, quizConfigFragment);
        transaction.commit();

    }


    public void generateQuiz(String category, String difficulty, String categoryName)
    {
        String url = "https://opentdb.com/api.php?amount=10&category=" + category +
                "&difficulty=" + difficulty + "&type=multiple&encode=base64";

        Log.i("foo", "generating quiz: " + url);
        this.category = categoryName;
        this.difficulty = difficulty;

        new JsonTask().execute(url);
    }

    private void loadQuizFragment()
    {
        QuestionFragment questionFragment =
                new QuestionFragment(questions.toArray(new Question[0]), category, difficulty);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.flQuiz, questionFragment);
        transaction.commit();
    }

    private String base64ToString(String b64)
    {
        return new String(Base64.decode(b64, Base64.DEFAULT));
    }

    class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(QuizActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                Log.i("foo", params[0]);
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            Log.i("foo", result);

            // parse JSON
            Gson gson = new Gson();
            QuestionWrapper qr = gson.fromJson(result, QuestionWrapper.class);

            questions = new ArrayList<>();
            for(int i = 0; i < qr.results.length; i++)
            {
                DetaliedQuestion dq = qr.results[i];
                Question q = new Question();

                q.answer = base64ToString(dq.correct_answer);
                q.question = base64ToString(dq.question);
                q.options = new String[dq.incorrect_answers.length + 1];
                q.options[0] = q.answer;
                for(int j = 0; j < dq.incorrect_answers.length; j++)
                    q.options[j + 1] = base64ToString(dq.incorrect_answers[j]);

                questions.add(q);

                Log.i("foo", q.question + " " + q.options[1]);
            }

            loadQuizFragment();
        }
    }

}
