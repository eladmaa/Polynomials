package com.example.polinomials;

import android.text.InputType;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PolynomialActivity extends AppCompatActivity {
    TableLayout table;
    TableRow title;
    TableRow labels;
    TableRow data;
    TextView[] row1Text;
    EditText[] coefs;
    FirebaseDatabase DB;
    int colNum;
    String[] colName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polynomial);
        Button btnSubmit = findViewById(R.id.btn_submit);
        Button btnReturn = findViewById(R.id.btn_return);

        btnSubmit.setOnClickListener(view -> {
            //                sendToDB;
            DB = FirebaseDatabase.getInstance();
            DatabaseReference DBRef = DB.getReference("Polynomials");

            ArrayList<String> coefsValues = new ArrayList<>();

// Add the EditText values to the ArrayList
            for (EditText coef : coefs) {
                String editTextValue = coef.getText().toString();
                coefsValues.add(editTextValue);
            }

            // Create a HashMap to hold the EditText values
            HashMap<String, Object> coefsMap = new HashMap<>();

// Add the EditText values to the HashMap
            for (int i = 1; i < coefsValues.size(); i++) {
                coefsMap.put("coefficient" + i, coefsValues.get(i));
            }

// Upload the HashMap to the EditTextArrays node
            DBRef.child(coefsValues.get(0)).setValue(coefsMap).
                    addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Data uploaded successfully",Toast.LENGTH_LONG).show()).
                    addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "uploading data Failed",Toast.LENGTH_LONG).show());


            Intent j = new Intent(PolynomialActivity.this, MainActivity.class);
            setResult(RESULT_OK, j);

            finish();
        });
        btnReturn.setOnClickListener(view -> {
            Intent j = new Intent(PolynomialActivity.this, MainActivity.class);
            setResult(RESULT_OK, j);
            finish();
        });

        Intent i = getIntent();
        colNum = i.getIntExtra("rank", 0);
        colName = new String[colNum + 2];
        colName[0] = "Name";
//         setting name for each column

        for (int j = 1; j < colNum + 2; j++) {
            colName[j] = "X^" + (j - 1);
        }
        //  https://code.tutsplus.com/tutorials/android-user-interface-design-table-layouts--mobile-4788
        // create and set Table for coefficients
        table = findViewById(R.id.PolynomialTable);
        labels = findViewById(R.id.coefsNameRow);
        data = findViewById(R.id.coefsDataRow);
        title = findViewById(R.id.titleRow);

        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
      // title column
        TextView titleText = new TextView(this);
        titleText.setText(R.string.TitleText);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titleText.setGravity(Gravity.CENTER);
        titleText.setTypeface(Typeface.SERIF, Typeface.BOLD);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = colNum + 2;  // setting number of columns
        title.addView(titleText, params);

        row1Text = new TextView[colNum + 2];
        for (int j = 0; j < colNum + 2; j++) {
            row1Text[j] = new TextView(this);
            row1Text[j].setText(colName[j]);
            row1Text[j].setTypeface(Typeface.SERIF, Typeface.BOLD);
            labels.addView(row1Text[j]);
        }
        coefs = new EditText[colNum + 2];
        for (int j = 0; j < colNum + 2; j++) {
            coefs[j] = new EditText(this);
            if (j == 0) {
                coefs[j].setHint("string");
                data.addView(coefs[j]);
            } else {
                coefs[j].setInputType(InputType.TYPE_CLASS_NUMBER);
                coefs[j].setTypeface(Typeface.SERIF, Typeface.BOLD);
                coefs[j].setHint("coef");
                data.addView(coefs[j]);
            }
        }

    }
}