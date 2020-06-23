package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mt;
    Button mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mt= (TextView) findViewById(R.id.textView);
        mb= (Button) findViewById(R.id.button2);

        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mt.setText("Hello Atishay");
            }
        });
    }
}
