package test.bird.starrysky_sudoku;

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

public class LostDialog extends Dialog {
    private Context context;
    private Button button;
    private MainActivity mainActivity = new MainActivity();
    protected static boolean playagain = false ;
    public LostDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.lostdialog);
        ImageButton ib1 = (ImageButton) findViewById(R.id.close1);
        Button againbt = (Button) findViewById(R.id.again);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);

            }
        });
        againbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
                playagain = true ;
            }
        });
    }
}
