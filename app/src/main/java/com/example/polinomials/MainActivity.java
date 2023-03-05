package com.example.polinomials;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {
    private NumberPicker rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rank =  findViewById(R.id.editRankNumber);
        rank.setMaxValue(5);
        rank.setMinValue(0);
        Button btnSubmit = findViewById(R.id.btn_continue);
        Button btnDB = findViewById(R.id.viewDatabase);
        btnSubmit.setOnClickListener(view -> startViewActivity());
        btnDB.setOnClickListener(view -> startBDViewActivity());
    }

    private void startBDViewActivity() {
        Intent i = new Intent(MainActivity.this, DBActivity.class);
        startActivity(i);
    }


    private void startViewActivity() {
        Intent i = new Intent(MainActivity.this, PolynomialActivity.class);
        int x = rank.getValue();
        i.putExtra("rank", x);
        startActivity(i);
    }
}