package test.bird.starrysky_sudoku;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by root on 18-7-16.
 */

public class WinDialog extends Dialog {
    private Context context;
    private MainActivity mainActivity = new MainActivity();
    protected static boolean next = false ;
    protected static boolean winFlag = false ;
    public WinDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.windialog);
        setCanceledOnTouchOutside(false);
        ImageButton ib3 = (ImageButton) findViewById(R.id.close2);
        ImageView startpc = (ImageView) findViewById(R.id.startpc);
        Button nextbt = (Button) findViewById(R.id.next);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
                winFlag = true ;
            }
        });
        nextbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
                next = true;
            }
        });
        ScaleAnimation animation =new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(0);
        animation.setFillBefore(true);
        animation.setStartOffset(500);
        startpc.startAnimation(animation);
    }

}
