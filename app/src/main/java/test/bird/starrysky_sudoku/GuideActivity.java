package test.bird.starrysky_sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class GuideActivity extends BaseActivity {

    int i[] = new int[]{
            R.layout.activity_guide1,
            R.layout.activity_guide2,
            R.layout.activity_guide3,
            R.layout.activity_guide4,
            R.layout.activity_guide5,
            R.layout.activity_guide6,
    };
    int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i[0]);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            j++;
            if(j < 6){
                setContentView(i[j]);
            }else{
                Intent intent = new Intent(GuideActivity.this,GameActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onTouchEvent(event);
    }

}
