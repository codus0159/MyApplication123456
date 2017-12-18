package cookmap.cookandroid.com.myapplication123456;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    protected Button smsButton, recogButton, servicebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsButton = (Button) findViewById(R.id.smsbutton);
        recogButton = (Button) findViewById(R.id.recogbutton);
        servicebutton = (Button) findViewById(R.id.servicebutton);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SMS.class);
                startActivity(intent);
            }
        });
        recogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Recog.class);
                startActivity(intent);
            }
        });
        servicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Location.class);
                startActivity(intent);
            }
        });


    }



}