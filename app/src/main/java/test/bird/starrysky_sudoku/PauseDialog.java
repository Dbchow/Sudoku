package test.bird.starrysky_sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by root on 18-7-16.
 */

public class PauseDialog extends Dialog {
    private GameActivity gameActivity = new GameActivity();
    private MainActivity mainActivity = new MainActivity();
    private Context context;
    protected ImageButton bgm;
    protected ImageButton audio;

    public PauseDialog(@NonNull Context context) {
        super(context);
         this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pausedialog);
        setCanceledOnTouchOutside(false);
        ImageButton ib = (ImageButton) findViewById(R.id.close);
        Button bt1 = (Button) findViewById(R.id.restart);
        Button bt2 = (Button) findViewById(R.id.returnmain);
        audio = (ImageButton) findViewById(R.id.backgroundaudio);
        bgm = (ImageButton) findViewById(R.id.backgroundmusic);

        setPic();
        bgm();
        audio();

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                gameActivity.getmTimer().resume();
                dismiss();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                Intent intent = new Intent(context,GameActivity.class);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }
    private void bgm(){

        bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                if (mainActivity.musicOn){
                    bgm.setBackgroundResource(R.drawable.ic_pop_music_off);
                    if (SplashActivity.getmPlayer() != null) {
                        SplashActivity.getmPlayer().pause();
                        mainActivity.musicOn = false ;
                    }
                }else {
                    bgm.setBackgroundResource(R.drawable.ic_pop_music_on);
                    if (SplashActivity.getmPlayer() != null) {
                        SplashActivity.getmPlayer().start();
                        mainActivity.musicOn = true ;
                    }
                }
            }
        });
    }

    private void audio(){

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                if (mainActivity.soundOn){
                    audio.setBackgroundResource(R.drawable.ic_pop_audio_off);
                    mainActivity.soundOn = false ;
                }else {
                    audio.setBackgroundResource(R.drawable.ic_pop_audio_on);
                    mainActivity.soundOn = true ;
                }
            }
        });
    }

    public void setPic(){
        if (mainActivity.musicOn){
            bgm.setBackgroundResource(R.drawable.ic_pop_music_on);
        }else{
            bgm.setBackgroundResource(R.drawable.ic_pop_music_off);
        }
        if (mainActivity.soundOn){
            audio.setBackgroundResource(R.drawable.ic_pop_audio_on);
        }else{
            audio.setBackgroundResource(R.drawable.ic_pop_audio_off);
        }

    }

}
