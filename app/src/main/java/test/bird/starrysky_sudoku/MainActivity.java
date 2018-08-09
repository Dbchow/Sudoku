package test.bird.starrysky_sudoku;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity {
    private StartDialog startDialog;
    private LostDialog lostDialog;
    private WinDialog winDialog;
    private SettingDialog settingDialog;
    private ImageButton key[] = new ImageButton[10];
    private ImageButton flash[] = new ImageButton[10];
    private ImageView line[] =new ImageView[10];
    private Timer timer = new Timer();
    private FrameLayout frameLayout;
    private static int gameLevel ;
    private ReboundScrollView rsv ;
    protected static SoundPool mPool;
    protected static int gameLevelPass = 0 ;
    protected static boolean musicOn = true ;
    protected static boolean soundOn = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        startDialog = new StartDialog(this);
        lostDialog = new LostDialog(this);
        winDialog = new WinDialog(this);
        settingDialog = new SettingDialog(this);
        mPool = new SoundPool(8, AudioManager.STREAM_MUSIC,0);
        rsv = findViewById(R.id.scrollView);

        initPool();
        getBotton();
        setStarImageBotton();
        showAnimotion();
        showView();
        delayTime();
        setStarOn();
        setOnClickListener();
        frameLayout = findViewById(R.id.starsort);
        ImageButton setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundOn) {
                    mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                }
                dialogShow(settingDialog);
                if (soundOn) {
                    mPool.play(2, 1f, 1f, 1, 0, 0.8f);
                } 
            }
        });
    }

    @Override
    protected void onStart() {
        if (winDialog.next) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogShow(startDialog);
                    if (soundOn) {
                        mPool.play(2, 1f, 1f, 1, 0, 0.8f);
                    }
                    gameLevel++;
                    startDialog.setTitleName();
                }
            }, 1000);
            winDialog.next = false ;
        }
        if (lostDialog.playagain) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogShow(startDialog);
                    if (soundOn) {
                        mPool.play(2, 1f, 1f, 1, 0, 0.8f);
                    }
                    startDialog.setTitleName();
                }
            }, 1000);
        }
        super.onStart();
    }
    protected void showView(){
        rsv.post(new Runnable() {
            public void run() {
                rsv.scrollTo(0,frameLayout.getMeasuredHeight()-rsv.getMeasuredHeight());
            }
        });

    }

    protected void delayTime(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 0;
                mHandler.sendMessage(message);
            }
        }, 6000, 6000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }, 8000, 6000);
    }

    protected void setOnClickListener(){
        for (int i=0;i<10;i++){
            final int t = i + 1;
            key[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (soundOn) {
                        mPool.play(1, 1f, 1f, 1, 0, 0.8f);
                    }
                    if (t == 1){
                        SharedPreferences mShared = getSharedPreferences("is",0);
                        boolean isFir = mShared.getBoolean("isFir",true);
                        SharedPreferences.Editor mEditor = mShared.edit();
                        if (isFir){
                            Intent intent = new Intent(MainActivity.this,GuideActivity.class);
                            startActivity(intent);
                            finish();
                            mEditor.putBoolean("isFir",false);
                            mEditor.commit();
                        }else{
                            dialogShow(startDialog);
                        }
                    }else {
                        dialogShow(startDialog);
                    }
                    if (soundOn) {
                        mPool.play(2, 1f, 1f, 1, 0, 0.8f);
                    }
                    startDialog.setTitleName();
                    gameLevel = t ;
                }
            });
        }
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setStarOn(){
        if (gameLevelPass >0){
            key[0].setBackgroundResource(R.drawable.ic_map_star_small_on);
        }
        if (gameLevelPass>1){
            for (int i=1;i<gameLevelPass;i++){
                key[i].setBackgroundResource(R.drawable.ic_map_star_nomal_on);
                if (i%4 == 0 || i%4 == 1){
                    line[i-1].setImageResource(R.drawable.ic_map_line_left_on);
                }else {
                    line[i-1].setImageResource(R.drawable.ic_map_line_right_on);
                }
            }
        }
        if (WinDialog.winFlag&&gameLevel == Game.starPassConut){
            ScaleAnimation animation =new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(0);
            animation.setFillBefore(true);
            animation.setStartOffset(500);
            key[gameLevel-1].startAnimation(animation);
//            AlphaAnimation animator1 = new AlphaAnimation(0.0f, 1.0f);
//            animator1.setDuration(500);
//            animator1.setFillAfter(true);
//            animator1.setRepeatCount(0);
//            key[gameLevel-1].startAnimation(animator1);
//            animation.setStartOffset(500);
//            WinDialog.winFlag = false ;
        }

        rsv.post(new Runnable() {
            @Override
            public void run() {
                rsv.scrollTo(0,frameLayout.getMeasuredHeight()-rsv.getMeasuredHeight()-getGameLevel()*300);
            }
        });
    }

    public void setStarImageBotton(){
        for (int i=gameLevelPass+1;i<10;i++){
            key[i].setEnabled(false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            savePassLevel();
            ActivityCollector.finishAll();
            android.os.Process.killProcess(android.os.Process.myPid());
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

    public void savePassLevel(){
        SharedPreferences mSharedPreferences = getSharedPreferences("gameData",0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("level",gameLevelPass);
        mEditor.putBoolean("music",musicOn);
        mEditor.putBoolean("sound",soundOn);
        mEditor.commit();
    }

    public void initPool(){
        mPool.load(this,R.raw.button,1);//1
        mPool.load(this,R.raw.map_star_on,1);//2
        mPool.load(this,R.raw.popup_appear,1);//3
        mPool.load(this,R.raw.popup_star,1);//4
        mPool.load(this,R.raw.puzzle_complete,1);//5
        mPool.load(this,R.raw.puzzle_fail,1);//6
        mPool.load(this,R.raw.time,1);//7
        mPool.load(this,R.raw.wrong_placement,1);//8
    }

    protected void getBotton(){
        key[0] = findViewById(R.id.startbt1);
        key[1] = findViewById(R.id.startbt2);
        key[2] = findViewById(R.id.startbt3);
        key[3] = findViewById(R.id.startbt4);
        key[4] = findViewById(R.id.startbt5);
        key[5] = findViewById(R.id.startbt6);
        key[6] = findViewById(R.id.startbt7);
        key[7] = findViewById(R.id.startbt8);
        key[8] = findViewById(R.id.startbt9);
        key[9] = findViewById(R.id.startbt10);
        flash[0] = findViewById(R.id.flashbt1);
        flash[1] = findViewById(R.id.flashbt2);
        flash[2] = findViewById(R.id.flashbt3);
        flash[3] = findViewById(R.id.flashbt4);
        flash[4] = findViewById(R.id.flashbt5);
        flash[5] = findViewById(R.id.flashbt6);
        flash[6] = findViewById(R.id.flashbt7);
        flash[7] = findViewById(R.id.flashbt8);
        flash[8] = findViewById(R.id.flashbt9);
        flash[9] = findViewById(R.id.flashbt10);
        line[0] = findViewById(R.id.starline1);
        line[1] = findViewById(R.id.starline2);
        line[2] = findViewById(R.id.starline3);
        line[3] = findViewById(R.id.starline4);
        line[4] = findViewById(R.id.starline5);
        line[5] = findViewById(R.id.starline6);
        line[6] = findViewById(R.id.starline7);
        line[7] = findViewById(R.id.starline8);
        line[8] = findViewById(R.id.starline9);
    }

    protected  void showAnimotion(){
        ImageView bgstar = findViewById(R.id.bgstar);
        AlphaAnimation animator = new AlphaAnimation(0.5f, 1.0f);
        animator.setDuration(1500);
        animator.setFillBefore(true);
        animator.setRepeatMode(AlphaAnimation.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        bgstar.startAnimation(animator);

        AlphaAnimation animator1 = new AlphaAnimation(0.1f, 1.0f);
        animator1.setDuration(1500);
        animator1.setFillBefore(true);
        animator1.setInterpolator(new DecelerateInterpolator(2f));
        animator1.setRepeatMode(AlphaAnimation.REVERSE);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        if (gameLevelPass == 0){
            flash[0].startAnimation(animator1);
        }else
        flash[gameLevelPass].startAnimation(animator1);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            if (msg.what == 0) {
                ImageView flash_big = findViewById(R.id.flash_big);
                TranslateAnimation translationAnimator = new TranslateAnimation(600, -1000, -200, 360);
                translationAnimator.setDuration(1200);
                translationAnimator.setRepeatCount(0);
                flash_big.setAnimation(translationAnimator);
                translationAnimator.start();

                ObjectAnimator alpha = ObjectAnimator.ofFloat(flash_big, "alpha", 0f, 1f, 0f);
                alpha.setDuration(1200);
                alpha.setRepeatCount(0);
                alpha.start();
            }
            if (msg.what == 1) {
                ImageView flash_small = findViewById(R.id.flash_small);
                TranslateAnimation translationAnimator2 = new TranslateAnimation(600, -900, -100, 360);
                translationAnimator2.setDuration(1200);
                translationAnimator2.setRepeatCount(0);
                flash_small.setAnimation(translationAnimator2);
                translationAnimator2.start();

                ObjectAnimator alpha2 = ObjectAnimator.ofFloat(flash_small, "alpha", 0f, 1f, 0f);
                alpha2.setDuration(1200);
                alpha2.setRepeatCount(0);
                alpha2.start();

                ImageView flash_small1 = findViewById(R.id.flash_small1);
                TranslateAnimation translationAnimator3 = new TranslateAnimation(400, -900, -300, 360);
                translationAnimator3.setDuration(1200);
                translationAnimator3.setRepeatCount(0);
                flash_small1.setAnimation(translationAnimator3);
                translationAnimator3.start();

                ObjectAnimator alpha3 = ObjectAnimator.ofFloat(flash_small, "alpha", 0f, 1f, 0f);
                alpha3.setDuration(1200);
                alpha3.setRepeatCount(0);
                alpha3.start();
            }
        }
    };


}