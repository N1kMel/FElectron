package com.example.felectron;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.felectron.Game.ds;
import static com.example.felectron.Game.faster;


public class Settings extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;

    public static SharedPreferences sPref;
    int chooseSettingsFlag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_difficult);

        radioGroup = findViewById(R.id.radioGr);

        Button btn_back = (Button) findViewById(R.id.btn_backD);

        loadChoose();




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Settings.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }

            }
        });



    }

    public void saveChoose() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("Choose", radioGroup.getCheckedRadioButtonId());
        ed.putFloat("Speed", faster);
        ed.putFloat("ScoreSpeed", ds);
        ed.commit();

    }


    public void loadChoose() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        int chooseR = sPref.getInt("Choose", R.id.radio_easy);
        float fast = sPref.getFloat("Speed", 0);

        faster = fast;
        radioButton = findViewById(chooseR);
        radioButton.setChecked(true);


    }



    public void CheckButton(View v){

        int radioId = radioGroup.getCheckedRadioButtonId();
        /*


        radioButton = findViewById(radioId);
        System.out.println(radioId);

 */



         switch (radioId){
             case R.id.radio_easy:
                 faster=1;
                 ds = 0.05f;
                 saveChoose();
                 break;
             case R.id.radio_medium:
                 faster = 5;
                 ds = 0.08f;
                 saveChoose();
                 break;
             case R.id.radio_hard:
                  ds = 0.1f;
                  faster = 7;
                 saveChoose();
              break;





        }
    }
}