package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.akansh.qrsmith.util.CommonShapeUtils;

class QRFinderFrameRenderer {

    private void drawNormalStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, boolean rounded) {
        int stroke = size / 7;
        float radiusMultiple = rounded ? 1.0f : 0.0f;
        float radius = (multiple / 2f) * radiusMultiple;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);
        canvas.drawRoundRect(new RectF(x, y, x + size, y + size), radius, radius, paint);
    }

    public void drawRoundedSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color) {
        drawNormalStyle(canvas, paint, x, y, size, multiple, color, true);
    }

    public void drawSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color) {
        drawNormalStyle(canvas, paint, x, y, size, multiple, color, false);
    }

    public void drawHexStyle(Canvas canvas, Paint paint, int x, int y, int size, int color) {
        float centerX = x + size/2f;
        float centerY = y + size/2f;

        // Draw outer hexagon
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(size/8f);
        CommonShapeUtils.drawHexagon(canvas, paint, centerX, centerY, size/2f);
    }

    public void drawCircleStyle(Canvas canvas, Paint paint, int x, int y, int circleDiameter, int foregroundColor) {
        int WHITE_CIRCLE_OFFSET = circleDiameter / 7;

        // Draw the outer circle
        paint.setColor(foregroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(WHITE_CIRCLE_OFFSET);

        canvas.drawOval(new RectF(x, y, (x + circleDiameter), (y + circleDiameter)), paint);
    }
}
