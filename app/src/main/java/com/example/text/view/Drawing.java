package com.example.text.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Drawing extends View {
    private Paint mPaint, mBitmapPaint;
    private Bitmap mBitmap;
//    private Canvas mCanvas;
    private Path mPath;

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private int color, size, state;

    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private ArrayList<Integer> colors = new ArrayList<Integer>();
    private ArrayList<Integer> sizes = new ArrayList<Integer>();

    public Drawing(Context c) {
        super(c);
    }


    public Drawing(Context context, int screenwidth, int screenheight, Bitmap bitmap) {
        super(context);
        mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()).copy(Bitmap.Config.ARGB_8888, true);
//        mCanvas = new Canvas(mBitmap);
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        setColor(Color.BLACK);
        setSize(10);
//        setState(state);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        for (int i = 0; i < paths.size(); i++) {
            mPaint.setColor(colors.get(i));
            mPaint.setStrokeWidth(sizes.get(i));
            canvas.drawPath(paths.get(i), mPaint);
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setState(int state) {
        this.state = state;
        // if (state == 0)
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        // else
        // mPaint.setXfermode(null);
    }

    /**
     * 撤销
     */
    public void onClickUndo() {
        if (paths.size() > 0) {
//            paths.get(paths.size() - 1).reset();
            Path p = paths.remove(paths.size() - 1);
            p.reset();
            undonePaths.add(p);
            sizes.remove(sizes.size() - 1);
            colors.remove(colors.size() - 1);

            mBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

            invalidate();
        }
    }

    private void touch_start(float x, float y) {
        undonePaths.clear();
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);

        colors.add(color);
        sizes.add(size);
        paths.add(mPath);
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public Bitmap getpaintbitmap() {
        return mBitmap;
    }
}