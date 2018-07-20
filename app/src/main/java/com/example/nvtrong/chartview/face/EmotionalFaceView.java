package com.example.nvtrong.chartview.face;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.nvtrong.chartview.R;

public class EmotionalFaceView extends View implements View.OnClickListener {
    //  Color
    private static final int DEFAULT_FACE_CORLOR = Color.YELLOW;
    private static final int DEFAULT_EYES_CORLOR = Color.BLACK;
    private static final int DEFAULT_MOUTH_CORLOR = Color.BLACK;
    private static final int DEFAULT_BORDER_CORLOR = Color.BLACK;

    // Size
    public static final float DEFAULT_BORDER_WIDTH = 4.0f;

    // State
    public static final int HAPPY = 0;
    public static final int SAD = 1;

    //Paint object for color inf and styling
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //Some colors for the face background, eyes and mouth
    private int faceColor = DEFAULT_FACE_CORLOR;
    private int eyesColor = DEFAULT_EYES_CORLOR;
    private int mouthColor = DEFAULT_MOUTH_CORLOR;
    private int borderColor = DEFAULT_BORDER_CORLOR;
    //Face border width in pixels
    private float borderWidth = DEFAULT_BORDER_WIDTH;
    //State default
    private int happinessState = HAPPY;
    //View size in pixels
    private int size = 0;
    //Drawer curve mouth
    private Path mouthPath = new Path();


    public EmotionalFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFaceBackground(canvas);
        drawEyes(canvas);
        drawMouth(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        size = Math.min(getMeasuredWidth(), getMeasuredHeight());

        setMeasuredDimension(size, size);
    }

    private void drawFaceBackground(Canvas canvas) {
        paint.setColor(faceColor);
        //Draw fill
        paint.setStyle(Paint.Style.FILL);

        float radius = size / 2f;

        canvas.drawCircle(size / 2f, size / 2f, radius, paint);

        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);


        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint);

    }

    private void drawEyes(Canvas canvas) {
        paint.setColor(eyesColor);
        paint.setStyle(Paint.Style.FILL);

        RectF leftEyeRect = new RectF();
        leftEyeRect.set(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f);
        canvas.drawOval(leftEyeRect, paint);
        RectF RectFrightEyeRect = new RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f);

        canvas.drawOval(RectFrightEyeRect, paint);


    }

    private void drawMouth(Canvas canvas) {
        //Clear
        mouthPath.reset();
        //move to x0,y0
        mouthPath.moveTo(size * 0.22f, size * 0.7f);
        if (happinessState == HAPPY) {
            //draw curve start from (x0,y0) through (x1,y1) to (x2,y2)
            mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f);
            // 3 vẽ 1 đường cong từ điểm (x2, y2) tới điẻm (x0, y0) đi qua điểm (x3, y3)
            mouthPath.quadTo(size * 0.50f, size * 0.90f, size * 0.22f, size * 0.70f);
        } else {             //Sad mouth path
            //draw curve start from (x0,y0) through (x1,y1) to (x2,y2)
            mouthPath.quadTo(size * 0.50f, size * 0.50f, size * 0.78f, size * 0.70f);
            // 3 vẽ 1 đường cong từ điểm (x2, y2) tới điẻm (x0, y0) đi qua điểm (x3, y3)
            mouthPath.quadTo(size * 0.50f, size * 0.60f, size * 0.22f, size * 0.70f);
        }
        mouthPath.close();
        paint.setColor(mouthColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(mouthPath, paint);
    }

    TypedArray typedArray;

    private void setupAttributes(AttributeSet attributeSet) {
        //Obtain a type arrays of attributes
        typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.EmotionalFaceView, 0, 0);

        //Extract custom attributes into member variables
        happinessState = typedArray.getInt(R.styleable.EmotionalFaceView_state, HAPPY);
        faceColor = typedArray.getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_CORLOR);
        eyesColor = typedArray.getColor(R.styleable.EmotionalFaceView_eyesColor, DEFAULT_EYES_CORLOR);
        mouthColor = typedArray.getColor(R.styleable.EmotionalFaceView_mouthColor, DEFAULT_MOUTH_CORLOR);
        borderColor = typedArray.getColor(R.styleable.EmotionalFaceView_borderColor, DEFAULT_BORDER_CORLOR);

        borderWidth = typedArray.getDimension(R.styleable.EmotionalFaceView_borderWidth, DEFAULT_BORDER_WIDTH);
        Log.d("Click", this.toString());
        invalidate();
    }

    // click from activity parent
    OnClickListener onClickListener;

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(this);
        onClickListener = l;
    }

    @Override
    public void onClick(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                faceColor = typedArray.getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_CORLOR);
                invalidate();
            }
        }, 100);
        faceColor = Color.WHITE;
        invalidate();
        //action of activity parent
        onClickListener.onClick(this);
    }

    public void setHappinessState(int happinessState) {
        this.happinessState = happinessState;
        invalidate();
    }
}
