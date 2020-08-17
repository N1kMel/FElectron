package com.example.felectron;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.felectron.Settings.sPref;
import static java.lang.Math.min;


public class Game extends AppCompatActivity implements View.OnTouchListener {
    ImageView obstaclePlus;
    ImageView electron;
    ImageView obstacleMinus;
    ImageView obstacleProton;
    MediaPlayer mPlayer;
    boolean isTouch = false;
    TextView TextScore;

    SharedPreferences SpreF;

    int dy = 10;
    int ddx = 7;
    public static double score = 0;
    static long scoreI;
    static String scoreS;
    static float ds;
    int H, W;
    int electronH;


    public static float faster;

    int Rsize;
    int X;
    int Y;
    private ViewGroup mMoveLayout;
    public float obstaclePlusX, obstaclePlusY, electronX, electronY, obstacleMinusX, obstacleMinusY, obstacleProtonX, obstacleProtonY;
    int NumberOfNextObstacle;


    public boolean onTouch(View v, MotionEvent event) {
        X = (int) event.getX();  Y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                // нажатие
                break;

            case MotionEvent.ACTION_UP:
                isTouch = false;
                // отпускание
                break;
        }

        return true;
    }
    private boolean IsNear(float x1, float y1, float x2, float y2){
        if(Math.abs(x1+H/20-x2)*Math.abs(x1+H/20-x2) + Math.abs(y1-y2+H/20)*Math.abs(y1-y2+H/20) < W*W/4){
            return true;
        }
        else return false;
    }


    private boolean IsTntersection(float x1, float y1, float x2, float y2){
        if(Math.abs(x1 - x2 + electronH/3 ) < 150 && Math.abs(y1-y2 + electronH/3) < 150 ){
            return true;
        }
        else return false;
    }

    public boolean IsBeyondEdge(float x, float y){
        if( x + H/20 > W  || x + H/20 < 0 || y + H/20 > H ){
            return true;
        }
        else return false;
    }

    private float AttractionOrRepellingX (float eX,  float obX, int signFlag)
    {
        if( eX > obX) {eX += -1*signFlag * 10 * (1-(Math.abs(obX-eX)/W));  }
        if( eX  < obX) {eX += signFlag * 10 * (1-(Math.abs(obX-eX)/W));}
        return eX;

    }
    private float AttractionOrRepellingY (float eY,  float obY, int signFlag)
    {
        if( eY  > obY) {eY += -1*signFlag * 10 * (1-(Math.abs(obY-eY)/W));}
        if( eY  < obY)  {eY += signFlag * 10 * (1-(Math.abs(obY-eY)/W));}
        return eY;

    }

    public void loadChoose() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        float fast = sPref.getFloat("Speed", 0);
        float Dss = sPref.getFloat("ScoreSpeed", 0.1f);
        ds = Dss;
        faster = fast;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        scoreS = "";
        score = 0;

        mPlayer=MediaPlayer.create(this, R.raw.m_game_over );

        mMoveLayout = (ViewGroup) findViewById(R.id.move);
        mMoveLayout.setOnTouchListener((View.OnTouchListener) Game.this);
        TextScore = findViewById(R.id.text_score);

        NumberOfNextObstacle = (int) Math.round((Math.random()));


        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        H = displaymetrics.heightPixels;
        W = displaymetrics.widthPixels;
        electronH = H/8;

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        obstacleProton = findViewById(R.id.ObstacleProton);
        obstaclePlus =  findViewById(R.id.ObstaclePlus);
        obstacleMinus =  findViewById(R.id.ObstacleMinus);
        electron =  findViewById(R.id.electon);

        electron.setLayoutParams(new RelativeLayout.LayoutParams(H/8, H/8));

        obstacleMinus.setY(-H/6);
        obstacleMinus.setX((int)( Math.random()*7*W/10 + 100));

        obstacleProton.setX((int)( Math.random()*7*W/10 + 100));
        obstacleProton.setY(-H/6);

        obstaclePlus.setY(-H/6);

        obstaclePlus.setX((int)( Math.random()*7*W/10 + 100));
        electron.setX((int)( Math.random()*6*W/10 + 100));
        electron.setY(displaymetrics.heightPixels*8/10);

        loadChoose();

        Rsize = min(H, W) / 7;
        obstaclePlus.setLayoutParams(new RelativeLayout.LayoutParams(Rsize, Rsize));
        obstacleMinus.setLayoutParams(new RelativeLayout.LayoutParams(Rsize, Rsize));
        obstacleProton.setLayoutParams(new RelativeLayout.LayoutParams(Rsize, Rsize));


                final Timer Maintimer = new Timer();
                Maintimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        obstacleProtonX = obstacleProton.getX();
                        obstacleProtonY = obstacleProton.getY();

                        obstacleMinusX = obstacleMinus.getX();
                        obstacleMinusY = obstacleMinus.getY();

                        obstaclePlusY = obstaclePlus.getY();
                        obstaclePlusX = obstaclePlus.getX();

                        electronX = electron.getX();
                        electronY = electron.getY();


                        switch (NumberOfNextObstacle){
                            case 0:
                                //minus
                                obstacleMinusY += dy + faster;

                                if (obstacleMinusY > H + Rsize) {

                                      obstacleMinusX = ((int)( Math.random()*7*W/10 + 100));
                                      obstacleMinusY = -150;
                                      faster += 0.07;
                                      NumberOfNextObstacle = (int) Math.round((Math.random()*2));
                                }

                                if(IsNear(electronX, electronY, obstacleMinusX, obstacleMinusY)){

                                  electronX = AttractionOrRepellingX(electronX, obstacleMinusX, -1);
                                  electronY = AttractionOrRepellingY(electronY, obstacleMinusY, -1);

                                }

                                if(IsTntersection(electronX, electronY, obstacleMinusX, obstacleMinusY) || IsBeyondEdge(electronX, electronY) ){
                                    mPlayer.start();

                                    Maintimer.cancel();
                                    try {
                                        Intent intentLose = new Intent(Game.this, Lose.class);
                                        startActivity(intentLose); finish();
                                    } catch (Exception e) {}
                                }



                                break;
                            case 1:
                                //plus
                                obstaclePlusY += dy+faster;
                                if (obstaclePlusY > H + Rsize) {

                                    obstaclePlusX = ((int)( Math.random()*7*W/10 + 100));
                                    obstaclePlusY = -150;
                                    faster += 0.07;
                                    NumberOfNextObstacle = (int) Math.round((Math.random()*2));
                                }

                                if(IsNear(electronX, electronY, obstaclePlusX, obstaclePlusY)) {
                                    electronX = AttractionOrRepellingX(electronX, obstaclePlusX, 1);
                                    electronY = AttractionOrRepellingY(electronY, obstaclePlusY, 1);

                                }

                                if(IsTntersection(electronX, electronY, obstaclePlusX, obstaclePlusY) || IsBeyondEdge(electronX, electronY)){
                                    mPlayer.start();
                                    Maintimer.cancel();

                                    try {
                                        Intent intentLose = new Intent(Game.this, Lose.class);
                                        startActivity(intentLose);finish();
                                    } catch (Exception e) {}
                                }

                                break;
                            case 2:


                                obstacleProtonY += 20 + faster;

                                if (obstacleProtonY > H + Rsize) {

                                    obstacleProtonX = ((int)( Math.random()*7*W/10 + 100));
                                    obstacleProtonY = -150;
                                    faster += 0.07;
                                    NumberOfNextObstacle = (int) Math.round((Math.random()*2));
                                }


                                if(IsTntersection(electronX, electronY, obstacleProtonX, obstacleProtonY) || IsBeyondEdge(electronX, electronY) ){
                                    mPlayer.start();

                                    Maintimer.cancel();
                                    try {
                                        Intent intentLose = new Intent(Game.this, Lose.class);
                                        startActivity(intentLose);finish();
                                    } catch (Exception e) {}
                                }

                                break;



                        }




                        if (isTouch) {
                            if (Math.abs(electronX + H/16) > Math.abs(X)) { electronX -= ddx;}
                            if (Math.abs(electronY + H/16) > Math.abs(Y)) { electronY -= ddx;}
                            if (Math.abs(electronX + H/16) < Math.abs(X)) { electronX += ddx;}
                            if (Math.abs(electronY + H/16) < Math.abs(Y)) { electronY += ddx;}


                        }

                        score+=ds;
                        scoreI = (int) Math.round(score);
                        scoreS = Long.toString(scoreI);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextScore.setText(scoreS);

                                obstacleProton.setX(obstacleProtonX);
                                obstacleProton.setY(obstacleProtonY);

                                obstacleMinus.setX(obstacleMinusX);
                                obstacleMinus.setY(obstacleMinusY);

                                electron.setX(electronX);
                                electron.setY(electronY);

                                obstaclePlus.setX(obstaclePlusX);
                                obstaclePlus.setY(obstaclePlusY);

                            }
                        });
                    }
                }, 1000, 5); }}


