package kaveri.android.drawingboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K K Ram on 9/13/2017.
 */

public class DrawingBoard extends View {

    private int paintColor;
    private int   brushSize;
    private Path  mPath= new Path();
    List<Path> path;

    private Paint mPaint,canvasPaint;

    private Canvas canvas;
    private Bitmap bitmap;
    Context c;


    public DrawingBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        c=context;
        path= new ArrayList<>();

        TypedArray a= context.obtainStyledAttributes(attrs,R.styleable.DrawingBoard,0,0);

        try{
                paintColor=a.getColor(R.styleable.DrawingBoard_brushColor,Color.BLACK);
                brushSize=a.getDimensionPixelOffset(R.styleable.DrawingBoard_brushSize,2);

        }finally {
            a.recycle();
        }

        mPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(paintColor);
        mPaint.setStrokeWidth(brushSize);
        mPaint.setStyle(Paint.Style.STROKE);

        canvasPaint= new Paint(Paint.DITHER_FLAG);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap= Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        canvas= new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       // super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,canvasPaint);
      // canvas.drawPath(mPath,mPaint);
      for(int i=0;i<path.size();i++){
           canvas.drawPath(path.get(i),mPaint);
      }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX =event.getX();
        float touchY=event.getY();
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(touchX,touchY);

                break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(touchX,touchY);

                    break;
            case MotionEvent.ACTION_UP:

                mPath.lineTo(touchX,touchY);
                path.add(mPath);
                canvas.drawPath(mPath,mPaint);
                mPath.reset();
                break;
                default: return false;

        }
        invalidate();
        return true;
    }


}
