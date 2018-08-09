package test.bird.starrysky_sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by root on 18-7-26.
 */

public class GuideView extends View {

    private float width ;
    private float height ;
    private String a = "052006000160900004049803620400000800083201590001000002097305240200009056000100970";
    private int[] b = new int[81];
    public GuideView(Context context) {
        super(context);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public int[] inita(){
        for (int i=0;i<81;i++){
            b[i] = a.charAt(i)-'0';
        }
        return b;
    }

    public String getBst(int x,int y){
        if(inita()[x+9*y] == 0){
            return "" ;
        }else {
            return inita()[x+9*y]+"" ;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = (w-74)/9f;
        this.height = (w-74)/9f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_play_grid_bg);
        Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect dst = new Rect(0,0,(int)(getWidth()),(int)(getWidth()));
        canvas.drawBitmap(bitmap,src,dst,null);

        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                Bitmap bitmapPlaybg1 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_play_grid_none_off);
                Rect src1 = new Rect(0,0,bitmapPlaybg1.getWidth(),bitmapPlaybg1.getHeight());
                Rect dst1 = new Rect((int)(i*width+37),(int)(j*width+37),(int)((i+1)*width+37),(int)((j+1)*width)+37);
                canvas.drawBitmap(bitmapPlaybg1,src1,dst1,null);
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
                canvas.drawText(getBst(i,j), i*width+x+37, y+j*height+37, numberPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(widthMeasureSpec));
    }
}
