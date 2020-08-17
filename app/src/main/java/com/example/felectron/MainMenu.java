package com.example.felectron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity implements View.OnTouchListener {
    ImageView setting;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting = findViewById(R.id.settings);
        setting.setOnTouchListener((View.OnTouchListener) MainMenu.this);

        Button buttonSt = (Button) findViewById(R.id.btn_start);
        buttonSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainMenu.this, Game.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }

            }
        });

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:

                try {
                    Intent settings = new Intent(MainMenu.this, Settings.class);
                    startActivity(settings);
                    finish();
                } catch ( Exception e){}
                break;
        }
        return true;
    }
}
