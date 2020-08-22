package com.barmej.culturalwords;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerActivity extends AppCompatActivity {

    TextView QuestionAnswer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        QuestionAnswer = findViewById(R.id.text_view_answer);

        QuestionAnswer.setText(getIntent().getStringExtra("AnswerPackage"));
    }
    public void Back(View view) {
        finish();
    }


}
