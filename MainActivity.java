package com.example.appnumbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    class Number {
        int number;

        public Number() {}
        public Number(int n) {
            number = n;
        }
        public boolean isSquare() {
            double squareRoot = Math.sqrt(number);
            if(squareRoot == Math.floor(squareRoot)) {
                return true;
            }
            return false;
        }
        public boolean isTriangular() {
            int x=1;
            int triangularNum=1;

            while(triangularNum<number) {
                x++;
                triangularNum += x;
            }
            if(triangularNum == number ) {
                return true;
            }
            return false;
        }


    }
    public void testNumber(View view){
        Log.i("Info","Button Pressed.");
        EditText editText = (EditText) findViewById(R.id.editTextNumber);
        String message;
        if (editText.getText().toString().isEmpty()) {
            message = "Please enter a number.";
        } else {
            Number myNumber = new Number();
            myNumber.number = Integer.parseInt(editText.getText().toString());
            message = editText.getText().toString();
            if (myNumber.isSquare() && myNumber.isTriangular()) {
                message += " is both square and triangular.";
            } else if (myNumber.isSquare()) {
                message += " is square but not triangular.";
            } else if (myNumber.isTriangular()) {
                message += " is triangular but not square.";
            } else {
                message += " is neither square nor triangular.";
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}