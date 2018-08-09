package test.bird.starrysky_sudoku;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends BaseActivity {
    private ProgressBar pb;
    private TextView timecount;
    private ImageButton pausebt ;
    private SudokuView sudokuView ;
    private PauseDialog pauseDialog;
    private LostDialog lostDialog ;
    private WinDialog winDialog ;
    protected View keypad [] = new View[9];
    private Game mgame = new Game();
    private ImageButton mark ;
    private ImageButton revoke ;
    private MainActivity mainActivity = new MainActivity();
    private static CountDownTimerSupport mTimer;
    private int flag1 = 0;
    private static long finishTime;

    public CountDownTimerSupport getmTimer(){
        return mTimer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sudokuView = findViewById(R.id.sudokuview1) ;

        revoke = findViewById(R.id.revoke);
        mark = findViewById(R.id.mark);
        pb =  findViewById(R.id.bar1);
        timecount =  findViewById(R.id.timecount) ;
        pausebt =  findViewById(R.id.pause1);
        pauseDialog = new PauseDialog(this);
        lostDialog = new LostDialog(this);
        winDialog = new WinDialog(this);
        getbt();
        setlistener();
        setTime();
        setTitle();
        initButton();
        pausebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                dialogShow(pauseDialog);

                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(2, 1f, 1f, 1, 0, 0.8f);
                }
                mTimer.pause();
            }
        });
    }

    public void setTime(){
        mTimer = new CountDownTimerSupport();
        if (mainActivity.getGameLevel()==1){
            mTimer.setMillisInFuture( 1 * 60 * 1000);
        }else {
            mTimer.setMillisInFuture( 10 * 60 * 1000);
        }
        mTimer.setCountDownInterval(1000);
        mTimer.setOnCountDownTimerListener(new OnCountDownTimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                finishTime = millisUntilFinished ;
                String str2 = String.valueOf(((millisUntilFinished/1000) % 60));
                if (((millisUntilFinished/1000) % 60)<10){
                    str2 = "0"+(millisUntilFinished/1000) % 60;
                }
                timecount.setText((millisUntilFinished/60/1000) + ":" + str2);
                if (millisUntilFinished < 11*1000){
                    if (mainActivity.soundOn) {
                        mainActivity.mPool.play(7, 1f, 1f, 1, 0, 0.8f);
                    }   
                }
                if(millisUntilFinished < 1*1000){
                    if (mainActivity.mPool!=null) {
                        mainActivity.mPool.stop(7);
                    }
                }
                if (mainActivity.getGameLevel()==1){
                    pb.setProgress((int) millisUntilFinished/10/60);
                }else {
                    pb.setProgress((int) millisUntilFinished/100/60);
                }
            }

            @Override
            public void onFinish() {
                timecount.setText((0+":" + "00"));
                pb.setProgress(0);
                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(6, 1f, 1f, 1, 0, 0.8f);
                }
                try {
                    dialogShow(lostDialog);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (mainActivity.soundOn) {
                    mainActivity.mPool.play(2, 1f, 1f, 1, 0, 0.8f);
                }
            }

        });

        mTimer.start();
    }
    public void win(){
        if (mgame.youWin()){
            mTimer.pause();
            if (mainActivity.soundOn) {
                mainActivity.mPool.play(5, 1f, 1f, 1, 0, 0.8f);
            }
            delayChange();
            SudokuView.setChangeTime(8);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogShow(winDialog);
                    if (mainActivity.soundOn) {
                        mainActivity.mPool.play(2, 1f, 1f, 1, 0, 0.8f);
                    }
                }
            },2000);
        }
    }

    public void delayChange(){
        final Handler handler=new Handler();
        Runnable runnable=new Runnable(){
            @Override
            public void run() {
                sudokuView.invalidate();
                int a = SudokuView.getChangeTime();
                a--;
                SudokuView.setChangeTime(a);
                if (a > -1){
                    handler.postDelayed(this, 200);
                }
            }
        };
        handler.postDelayed(runnable, 200);

    }

    public void setTitle(){
        final TextView titleName = findViewById(R.id.titlename);
        titleName.post(new Runnable() {
            @Override
            public void run() {
                titleName.setText("STAR - "+mainActivity.getGameLevel()+"");
            }
        });
    }

    public void initButton() {
        for (int i = 0; i < 9; i++) {
            mark.setEnabled(false);
            mark.setAlpha(0.55f);
            keypad[i].setEnabled(false);
            keypad[i].setAlpha(0.55f);
            revoke.setEnabled(false);
            revoke.setAlpha(0.55f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setButton();
        return super.onTouchEvent(event);
    }

    public void setButton() {
        for (int i = 0; i < 9; i++) {
            if (sudokuView.getNumber()!=0&&!sudokuView.getOldNumber()){
                revoke.setEnabled(true);
                revoke.setAlpha(1.0f);
                keypad[i].setEnabled(true);
                keypad[i].setAlpha(1.0f);
                mark.setEnabled(false);
                mark.setAlpha(0.55f);
            }
            if (sudokuView.getNumber() == 0){
                mark.setEnabled(true);
                mark.setAlpha(1.0f);
                keypad[i].setEnabled(true);
                keypad[i].setAlpha(1.0f);
                revoke.setEnabled(true);
                revoke.setAlpha(1.0f);
            }
            if (sudokuView.numButtonFill()) {
                mark.setEnabled(false);
                mark.setAlpha(0.55f);
                keypad[i].setEnabled(false);
                keypad[i].setAlpha(0.55f);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(GameActivity.this,MainActivity.class);
            startActivity(intent);
            mTimer.stop();
            ActivityCollector.removeAvtivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void dialogShow(Dialog dialog){
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        dialog.onWindowAttributesChanged(wl);//设置点击外围解散
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void getbt(){
        keypad[0] = findViewById(R.id.bt1);
        keypad[1] = findViewById(R.id.bt2);
        keypad[2] = findViewById(R.id.bt3);
        keypad[3] = findViewById(R.id.bt4);
        keypad[4] = findViewById(R.id.bt5);
        keypad[5] = findViewById(R.id.bt6);
        keypad[6] = findViewById(R.id.bt7);
        keypad[7] = findViewById(R.id.bt8);
        keypad[8] = findViewById(R.id.bt9);
        mark = (ImageButton) findViewById(R.id.mark);
        revoke = (ImageButton)findViewById(R.id.revoke);
    }

    public void setlistener(){
        for(int i=0;i<9;i++){
            final int t = i+1 ;
            keypad[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.mPool.play(1,1f,1f,1,0,0.8f);
                    if (flag1%2 == 1){
                        sudokuView.setCellTitle(t);
                    }else if(t == sudokuView.getNumber()){
                        sudokuView.setTitle(0);
                    }else {
                        sudokuView.setTitle(t);
                        if (mgame.isFlag()){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mgame.setCount(mgame.getCount() - 1);
                                    sudokuView.invalidate();
                                    final Toast ts = Toast.makeText(getBaseContext(),"错误数字，已为您撤销！",Toast.LENGTH_SHORT);
                                    ts.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ts.cancel();
                                        }
                                    },1000);
                                    mgame.setFlag(false);
                                }
                            },300);
                        }
                    }
                    win();
                }
            });
            mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag1++;
                    if (flag1%2 == 1){
                        mark.setAlpha(0.55f);
                    }else {
                        mark.setAlpha(1.0f);
                    }
                }
            });
            revoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = mgame.getCount();
                    if (a>0) {
                        mgame.setCount(a - 1);
                    }
                    if (a == 1){
                        revoke.setEnabled(false);
                        revoke.setAlpha(0.55f);
                    }
                    sudokuView.invalidate();
                }
            });
        }
    }
}
