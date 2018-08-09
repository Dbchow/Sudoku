package test.bird.starrysky_sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by root on 18-7-9.
 */

public class SudokuView extends View {

   private float width ;
   private float height ;
   private int selectX;
   private int selectY;
   private static int changeTime = 8 ;
   private Game mgame = new Game();

    public SudokuView(Context context) {
        super(context);
    }
    public SudokuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = (w-74)/9f;
        this.height = (w-74)/9f;
        selectX = -1 ;
        selectY = -1 ;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //set view background
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_play_grid_bg);
        Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect dst = new Rect(0,0,(int)(getWidth()),(int)(getWidth()));
        canvas.drawBitmap(bitmap,src,dst,null);
        //background picture
        Bitmap bitmapPlaybg1 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_play_grid_none_off);
        Bitmap bitmapPlaybg2 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_play_grid_filled_off);
        Bitmap bitmapPlaybg3 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_grid_none_on);
        Bitmap bitmapPlaybg4 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_grid_filled_on);
        Bitmap bitmapPlaybg5 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_grid_focus_off);
        Bitmap bitmapPlaybg6 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_grid_focus_clash);
        Bitmap bitmapPlaybg7 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_grid_filled_clash);
        Rect src1 = new Rect(0,0,bitmapPlaybg1.getWidth(),bitmapPlaybg1.getHeight());
        //set cell background
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if (mgame.getNumber(i,j)==""){
                    Rect dst1 = new Rect((int)(i*width+37),(int)(j*width+37),(int)((i+1)*width+37),(int)((j+1)*width)+37);
                    canvas.drawBitmap(bitmapPlaybg1,src1,dst1,null);
                }else if (mgame.usedNumber(i,j)!=0){
                    Rect dst1 = new Rect((int)(i*width+37),(int)(j*width+37),(int)((i+1)*width+37),(int)((j+1)*width)+37);
                    canvas.drawBitmap(bitmapPlaybg1, src1, dst1, null);
                }else {
                    Rect dst1 = new Rect((int)(i*width+37),(int)(j*width+37),(int)((i+1)*width+37),(int)((j+1)*width)+37);
                    canvas.drawBitmap(bitmapPlaybg2,src1,dst1,null);
                }
            }
        }
        //draw tip background  draw tip same number
        if (selectX+selectY>=0) {
            if (mgame.getNumber(selectX, selectY) == "") {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        //draw row
                        if (mgame.getNumber(selectX, j) == "") {
                            Rect dst1 = new Rect((int) (selectX * width + 37), (int) (j * width + 37), (int) ((selectX + 1) * width + 37), (int) ((j + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg3, src1, dst1, null);
                        } else {
                            Rect dst1 = new Rect((int) (selectX * width + 37), (int) (j * width + 37), (int) ((selectX + 1) * width + 37), (int) ((j + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg4, src1, dst1, null);
                            if (mgame.usedNumber(selectX, j) != 0) {
                                Rect dst2 = new Rect((int) (selectX * width + 37), (int) (j * width + 37), (int) ((selectX + 1) * width + 37), (int) ((j + 1) * width) + 37);
                                canvas.drawBitmap(bitmapPlaybg3, src1, dst2, null);
                            }
                        }
                        //draw column
                        if (mgame.getNumber(i, selectY) == "") {
                            Rect dst1 = new Rect((int) (i * width + 37), (int) (selectY * width + 37), (int) ((i + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg3, src1, dst1, null);
                        } else {
                            Rect dst1 = new Rect((int) (i * width + 37), (int) (selectY * width + 37), (int) ((i + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg4, src1, dst1, null);
                            if (mgame.usedNumber(i, selectY) != 0) {
                                Rect dst2 = new Rect((int) (i * width + 37), (int) (selectY * width + 37), (int) ((i + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                                canvas.drawBitmap(bitmapPlaybg3, src1, dst2, null);
                            }
                        }
                        //draw small cell
                        int x = selectX / 3 * 3;
                        int y = selectY / 3 * 3;
                        if (mgame.getNumber(x + i % 3, y + i / 3) == "") {
                            Rect dst1 = new Rect((int) ((x + i % 3) * width + 37), (int) ((y + i / 3) * width + 37), (int) ((x + i % 3 + 1) * width + 37), (int) ((y + i / 3 + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg3, src1, dst1, null);
                        } else {
                            Rect dst1 = new Rect((int) ((x + i % 3) * width + 37), (int) ((y + i / 3) * width + 37), (int) ((x + i % 3 + 1) * width + 37), (int) ((y + i / 3 + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg4, src1, dst1, null);
                            if (mgame.usedNumber(x + i % 3, y + i / 3) != 0) {
                                Rect dst2 = new Rect((int) ((x + i % 3) * width + 37), (int) ((y + i / 3) * width + 37), (int) ((x + i % 3 + 1) * width + 37), (int) ((y + i / 3 + 1) * width) + 37);
                                canvas.drawBitmap(bitmapPlaybg3, src1, dst2, null);
                            }
                        }
                        Rect dst1 = new Rect((int) (selectX * width + 37), (int) (selectY * width + 37), (int) ((selectX + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                        canvas.drawBitmap(bitmapPlaybg5, src1, dst1, null);
                    }
                }
            } else {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (mgame.getNumber(selectX, selectY).equals(mgame.getNumber(i, j))) {
                            Rect dst1 = new Rect((int) (i * width + 37), (int) (j * width + 37), (int) ((i + 1) * width + 37), (int) ((j + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg4, src1, dst1, null);
                            if (mgame.usedNumber(i, j) != 0) {
                                Rect dst2 = new Rect((int) (i * width + 37), (int) (j * width + 37), (int) ((i + 1) * width + 37), (int) ((j + 1) * width) + 37);
                                canvas.drawBitmap(bitmapPlaybg3, src1, dst2, null);
                            }
                        }

                    }
                }
            }
            //draw repeat number tip
            if (mgame.getNumber(selectX, selectY) != "") {
                int x = selectX / 3 * 3;
                int y = selectY / 3 * 3;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (mgame.getNumber(selectX, selectY).equals(mgame.getNumber(selectX, j)) && (selectY != j)) {
                            Rect dst1 = new Rect((int) (selectX * width + 37), (int) (j * width + 37), (int) ((selectX + 1) * width + 37), (int) ((j + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg7, src1, dst1, null);
                            Rect dst2 = new Rect((int) (selectX * width + 37), (int) (selectY * width + 37), (int) ((selectX + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg6, src1, dst2, null);
                        } else if (mgame.getNumber(selectX, selectY).equals(mgame.getNumber(i, selectY)) && (selectX != i)) {
                            Rect dst1 = new Rect((int) (i * width + 37), (int) (selectY * width + 37), (int) ((i + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg7, src1, dst1, null);
                            Rect dst2 = new Rect((int) (selectX * width + 37), (int) (selectY * width + 37), (int) ((selectX + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                            canvas.drawBitmap(bitmapPlaybg6, src1, dst2, null);
                        } else if (mgame.getNumber(selectX, selectY).equals(mgame.getNumber(x + j % 3, y + j / 3))) {
                            if (((x + j % 3) != selectX) && ((y + j / 3) != selectY)) {
                                Rect dst1 = new Rect((int) ((x + j % 3) * width + 37), (int) ((y + j / 3) * width + 37), (int) ((x + j % 3 + 1) * width + 37), (int) ((y + j / 3 + 1) * width) + 37);
                                canvas.drawBitmap(bitmapPlaybg7, src1, dst1, null);
                                Rect dst2 = new Rect((int) (selectX * width + 37), (int) (selectY * width + 37), (int) ((selectX + 1) * width + 37), (int) ((selectY + 1) * width) + 37);
                                canvas.drawBitmap(bitmapPlaybg6, src1, dst2, null);
                            }
                        }
                    }
                }
            }
        }
        Paint darkPaint = new Paint();
        darkPaint.setColor(getResources().getColor(R.color.sudolu_dark));
        darkPaint.setStrokeWidth(6);
        Paint hilitePaint =new Paint();
        hilitePaint.setColor(getResources().getColor(R.color.sudoku_hilite));
        Paint lightPaint =new Paint();
        lightPaint.setColor(getResources().getColor(R.color.sudoku_light));
        //draw line
        for(int i=0;i<9;i++){
            canvas.drawLine(37, (i)*height+37,getWidth()-37,(i)*height+37,lightPaint);
            canvas.drawLine(37, i*height+38,getWidth()-37,i*height+38,lightPaint);
            canvas.drawLine(i*width+37, 37,i*width+37,getWidth()-37,lightPaint);
            canvas.drawLine(i*width+38,38,i*width+38,getWidth()-38,lightPaint);
            if(i%3==0){
                canvas.drawLine(37, (i)*height+37,getWidth()-37,(i)*height+37,darkPaint);
                canvas.drawLine(i*width+37, 37,i*width+37,getWidth()-37,darkPaint);
            }
        }
        canvas.drawLine(37, 9*height+37,getWidth()-37,9*height+37,darkPaint);
        canvas.drawLine(9*width+37, 37,9*width+37,getWidth()-37,darkPaint);
        //draw cell number
        Paint numberPaint =new Paint();
        numberPaint.setColor(0xff0d151f);
        numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextSize(height*0.75f);
        numberPaint.setFakeBoldText(true);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        //number center
        Paint.FontMetrics fMetrics=numberPaint.getFontMetrics();
        float x=width/2;
        float y=height/2-(fMetrics.ascent+fMetrics.descent)/2;

        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if (mgame.getUndoNumber(i,j) == 0){
                    canvas.drawText("", i*width+x+37, y+j*height+37, numberPaint);
                }else {
                    canvas.drawText(mgame.getUndoNumber(i,j)+"", i*width+x+37, y+j*height+37, numberPaint);
                }
            }
        }

        if (mgame.youWin()) {
            if (changeTime > -1) {
                for(int i=0;i<9;i++)
                {
                    for(int j=0;j<9;j++)
                    {
                        Rect dst1 = new Rect((int)(i*width+27),(int)(changeTime*width+27),(int)((i+1)*width+37+10),(int)(((changeTime+1)*width)+37+10));
                        canvas.drawBitmap(bitmapPlaybg1,src1,dst1,null);
                        canvas.drawText(mgame.getUndoNumber(i, j)+"", i*width+x+37, y+changeTime*height+37, numberPaint);
                        canvas.drawLine(i*width+37, 37,i*width+37,getWidth()-37,lightPaint);
                        canvas.drawLine(i*width+38,38,i*width+38,getWidth()-38,lightPaint);
                        if(i%3==0){
                            canvas.drawLine(i*width+37, 37,i*width+37,getWidth()-37,darkPaint);
                        }
                        if (j != changeTime){
                            Rect dst2 = new Rect((int)(i*width+37),(int)(j*width+37),(int)((i+1)*width+37),(int)(((j+1)*width)+37));
                            canvas.drawBitmap(bitmapPlaybg1,src1,dst2,null);
                            canvas.drawText(mgame.getUndoNumber(i, j)+"", i*width+x+37, y+j*height+37, numberPaint);
                        }

                    }
                }
            }
        }
        //draw small cell number
        if (selectX+selectY>=0) {
            if (mgame.getNumber(selectX, selectY) == "") {
                Paint samllPaint = new Paint();
                samllPaint.setColor(0xff0d151f);
                samllPaint.setStyle(Paint.Style.STROKE);
                samllPaint.setFakeBoldText(true);
                samllPaint.setTextSize(height/3 * 0.75f);
                samllPaint.setTextAlign(Paint.Align.CENTER);
                //number center
                Paint.FontMetrics fMetrics1 = samllPaint.getFontMetrics();
                float x1 = width / 2 / 3;
                float y1 = height / 2 / 3 - (fMetrics1.ascent + fMetrics1.descent) / 2;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (!mgame.getSmallCell(selectX*9+selectY,i+j*3).equals("0")) {
                            canvas.drawText(mgame.getSmallCell(selectX*9+selectY,i+j*3),
                                    width / 3 * i + x1 + 37 + selectX * width,
                                    y1 + width / 3 * j + 37 + selectY * width, samllPaint);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()!=MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }
        int x = (int) ((event.getX() - 37) / width);
        int y = (int) ((event.getY() - 37) / height);
        if ( x <= 8 && y <= 8) {  //判断点击的是否是游戏界面
            selectX = x;
            selectY = y;
            invalidate();
        }

        return true;
    }

    public void setTitle(int i){
        if(selectX+selectY>=0) {
            mgame.setTitle(i, selectX, selectY);
            invalidate();  //每次填写一个数 都要重新进行绘制
        }
    }

    public void setCellTitle(int i){
        if (selectX+selectY>=0){
            mgame.setCellTitle(i,selectX*9+selectY,i-1);
            invalidate();
        }
    }

    public static int getChangeTime() {
        return changeTime;
    }

    public static void setChangeTime(int changeTime) {
        SudokuView.changeTime = changeTime;
    }

    public int getNumber(){
        if (selectY+selectX>=0){
        return mgame.getIntNumber(selectX,selectY);
        }
        return 0;
    }

    public boolean getOldNumber(){
        return mgame.getOldNumber(selectX,selectY);
    }

    public boolean numButtonFill() {
        boolean fill = false;
        int timeCount = 0;
        if (selectY+selectX>=0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (mgame.getUndoNumber(i, j)!=0) {
                        if (mgame.getUndoNumber(i, j) == mgame.getUndoNumber(selectX, selectY)) {
                            timeCount++;
                        }
                    }
                }
            }
        }
        if (timeCount == 9){
            fill = true ;
        }
        return fill;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(widthMeasureSpec));
    }
}
