package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SampleView extends View {
    private Paint paint = new Paint();

    public SampleView(Context context) {
        super(context);
        paint.setTextSize(50);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("Hello Android", 10, 100, paint);
    }
}
