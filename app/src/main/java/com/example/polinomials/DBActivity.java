package com.example.polinomials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DBActivity extends AppCompatActivity {
    private TableLayout table;
    FirebaseDatabase DB;
    Button btnReturn;
    String polynomialName;
    TextView[] coefs;
    TableRow title;
    TableRow labels;
    TextView[] row1Text;
    final int colNum=5;  // max degree of Polynomial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);
        btnReturn = findViewById(R.id.btn_Return);
        table = findViewById(R.id.PolynomialTable);
        title = findViewById(R.id.titleRow);
        labels = findViewById(R.id.coefsRow);

        // Initialize Firebase database
      //  table.setShrinkAllColumns(true);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView titleText = new TextView(this);
        titleText.setText(R.string.header2);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titleText.setGravity(Gravity.CENTER);
        titleText.setTypeface(Typeface.SERIF, Typeface.BOLD);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        title.addView(titleText, params);

        // Initialize Firebase database
        DB = FirebaseDatabase.getInstance();
        DatabaseReference DBRef = DB.getReference("Polynomials");


        String[] colName = new String[colNum + 2];
        colName[0] = "Polynomials Name";
//         setting name for each column

        for (int j = 1; j < colNum + 2; j++) {
            colName[j] = "X^" + (j - 1) +" ";
        }
        row1Text = new TextView[colNum + 2];
        for (int j = 0; j < colNum + 2; j++) {
            row1Text[j] = new TextView(this);
            row1Text[j].setText(colName[j]);
            row1Text[j].setTypeface(Typeface.SERIF, Typeface.BOLD);
            labels.addView(row1Text[j]);
        }
        // Set a listener for changes
        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop through each child of the EditTextArrays node
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    int size = (int) childSnapshot.getChildrenCount();
                    coefs = new TextView[size];
                    TextView[] text = new TextView[colNum+2];
                    int count=0;
                    polynomialName = childSnapshot.getKey();
                    TableRow tableRow = new TableRow(DBActivity.this);
                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.span = colNum + 2;  // setting number of columns
                    text[0] = new TextView(DBActivity.this);
                    text[0].setText(polynomialName);
                    tableRow.addView(text[0]);
                    for(DataSnapshot childs : childSnapshot.getChildren()) {
                        text[count] = new TextView(DBActivity.this);
                        String coef = Objects.requireNonNull(childs.getValue()).toString();
                        text[count].setText(coef);
                        tableRow.addView(text[count++]);
                    }
                    // Create a new row in the table layout
                    // Add the table row to the table layout
                    table.addView(tableRow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        btnReturn.setOnClickListener(view -> {
            Intent j = new Intent(DBActivity.this, MainActivity.class);
            setResult(RESULT_OK, j);
            finish();
        });

    }
}
