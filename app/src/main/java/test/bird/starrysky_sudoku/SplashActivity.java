package test.bird.starrysky_sudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {
    private MainActivity mainActivity = new MainActivity();
    private static MediaPlayer mPlayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        SharedPreferences sp = getSharedPreferences("gameData",0);
        mPlayer = MediaPlayer.create(this,R.raw.bgm);
        mainActivity.gameLevelPass = sp.getInt("level",0);;
        Game.starPassConut = sp.getInt("level",0);;
        MainActivity.musicOn = sp.getBoolean("music",true);
        MainActivity.soundOn = sp.getBoolean("sound",true);
        if (MainActivity.musicOn){
            mPlayer.start();
            mPlayer.setLooping(true);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    public static MediaPlayer getmPlayer() {
        return mPlayer;
    }
}
