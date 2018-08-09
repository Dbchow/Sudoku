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
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 18-7-16.
 */

public class StartDialog extends Dialog {
    private Context context;
    private ImageView starView;
    private MainActivity mainActivity = new MainActivity();

    public StartDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdialog);
        setCanceledOnTouchOutside(false);
        starView = (ImageView) findViewById(R.id.startpc);
        ImageButton ib = (ImageButton) findViewById(R.id.close);
        Button playbt = (Button) findViewById(R.id.play);

        if (mainActivity.getGameLevel() < MainActivity.gameLevelPass-1 || mainActivity.getGameLevel() ==  Game.starPassConut ){
            starView.setBackgroundResource(R.drawable.ic_pop_star);
            if (mainActivity.soundOn) {
                mainActivity.mPool.play(4, 1f, 1f, 1, 0, 0.8f);
            }
        }

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                dismiss();
            }
        });
        playbt.setOnClickListener(new View.OnClickListener() {
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
    }
    public void setTitleName(){
        final TextView gameLevelName = (TextView) findViewById(R.id.gamelevelname) ;
        try {
            gameLevelName.post(new Runnable() {
                @Override
                public void run() {
                    gameLevelName.setText("STAR - "+mainActivity.getGameLevel());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        starView.post(new Runnable() {
            @Override
            public void run() {
                if (mainActivity.getGameLevel() < MainActivity.gameLevelPass || mainActivity.getGameLevel() ==  Game.starPassConut ){
                    starView.setBackgroundResource(R.drawable.ic_pop_star);
                }else {
                    starView.setBackgroundResource(R.drawable.ic_pop_star_bg);
                }
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(4, 1f, 1f, 1, 0, 0.8f);
                }
            }
        });
    }
}
