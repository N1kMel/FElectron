package com.example.felectron;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.felectron.Game.scoreI;
import static com.example.felectron.Game.scoreS;
import static com.example.felectron.Settings.sPref;

public class Lose extends AppCompatActivity {

    long BestScore;

   public void loadBestScore(){
       sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
       long Best = sPref.getLong("BestScore", 0);
       BestScore = Best;
   }


    public void saveScore(){
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putLong("BestScore", BestScore);
        ed.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);




        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView TextBestScore = findViewById(R.id.text_bestScore);

        TextView TextScore = findViewById(R.id.text_ViewScore);

         TextScore.setText("Your score:"+scoreS);
         loadBestScore();

         if( scoreI > BestScore){ TextBestScore.setText("       New record!" + "\n" + "Your best score:" + scoreS); BestScore = scoreI; saveScore(); }
         else TextBestScore.setText( "Your best score:"+(int) BestScore);








        Button buttonBack = findViewById(R.id.back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intentBack = new Intent(Lose.this, MainMenu.class);
                    startActivity(intentBack);finish();
                } catch (Exception e) {}

            }
        });

        Button buttonAgain = findViewById(R.id.again);
        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intentAgain = new Intent(Lose.this, Game.class);
                    startActivity(intentAgain);finish();
                } catch (Exception e) {}

            }
        });




    }
}
