package com.example.nvtrong.chartview.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyView extends View {
    private static Paint paint;
    private int screenW, screenH;
    private float X, Y;
    private Path path;
    private float initialScreenW;
    private float initialX, plusX;
    private float TX;
    private boolean translate;
    private int flash;
    private float sizeX = 10f;
    private float sizeY = 6f;
    private Queue<Location> stack = new LinkedList<>();

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(7, 0, 0, Color.RED);


        path = new Path();
        TX = 0;
        translate = false;

        flash = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        sizeX = getMeasuredWidth();
        sizeY = getMeasuredHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawLine(canvas);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
        X = 0;
        Y = (screenH / 2);

        initialScreenW = screenW;
        initialX = 100;
        plusX = (screenW / 50);

        path.moveTo(X, Y);
        Log.d("DDDDDDDDDD", X + ";" + Y + "-" + initialScreenW + "--" + initialX);

    }

    private void drawBorder(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        RectF rectF = new RectF();
        rectF.set(0, 0, sizeX, sizeY);
        canvas.drawRect(rectF, paint);
    }

    private void drawLine(Canvas canvas) {
        flash += 1;
//        if (flash < 10 || (flash > 20 && flash < 30)) {
//            paint.setStrokeWidth(16);
        paint.setColor(Color.RED);
//            paint.setShadowLayer(12, 0, 0, Color.RED);
//        } else {
//            paint.setStrokeWidth(10);
//            paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
//            paint.setShadowLayer(7, 0, 0, Color.RED);
//        }

        if (flash == 100) {
            flash = 0;
        }
        Log.d("FFFFFFFFFFFFFFF", flash + ";");

        path.lineTo(X, Y);
        canvas.translate(-TX, 0);
        if (translate == true) {
            TX += 4;
        }
//        addPoint(X, Y);
//        if (X >= sizeX) {
//            path.reset();
//            path.moveTo(0, screenH / 2);
//            while (stack.size()>3) {
//                Location location = stack.poll();
//                path.lineTo(location.getX(), location.getY());
//            }
//            stack = new LinkedList<>();
//            canvas.drawPath(path, paint);
//            Location location = stack.poll();
//            X = location.getX();
//            Y = location.getY();
//
//        }
        if (X < initialX) {
            X += 8;
        } else {
            if (X < initialX + plusX) {
                X += 2;
                Y -= 8;
            } else {
                if (X < initialX + (plusX * 2)) {
                    X += 2;
                    Y += 14;
                } else {
                    if (X < initialX + (plusX * 3)) {
                        X += 2;
                        Y -= 12;
                    } else {
                        if (X < initialX + (plusX * 4)) {
                            X += 2;
                            Y += 6;
                        } else {
                            if (X < initialScreenW) {
                                X += 8;
                            } else {
                                translate = true;
                                initialX = initialX + initialScreenW;
                            }
                        }
                    }
                }
            }

        }

        canvas.drawPath(path, paint);


        //canvas.restore();

        invalidate();
    }

    private void addPoint(float x, float y) {
        Location location = new Location(x, y);
        stack.add(location);
    }
}
