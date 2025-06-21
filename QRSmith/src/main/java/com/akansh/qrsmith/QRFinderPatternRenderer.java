package com.akansh.qrsmith;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class QRFinderPatternRenderer {

    private void drawNormalStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, boolean rounded) {
        int stroke = size / 7;
        int innerSize = size * 3 / 7;
        int innerOffset = size * 2 / 7;
        float radiusMultiple = rounded ? 1.0f : 0.0f;
        float radius = (multiple / 2f) * radiusMultiple;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);
        canvas.drawRoundRect(new RectF(x, y, x + size, y + size), radius, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(x + innerOffset, y + innerOffset,
                x + innerOffset + innerSize, y + innerOffset + innerSize), radius, radius, paint);
    }



    public void drawRoundedStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color) {
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

        // Draw inner hexagon
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        CommonShapeUtils.drawHexagon(canvas, paint, centerX, centerY, size/4.8f);
    }

    public void drawCircleStyle(Canvas canvas, Paint paint, int x, int y, int circleDiameter, int foregroundColor) {
        int WHITE_CIRCLE_OFFSET = circleDiameter / 7;
        int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
        int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;

        // Draw the outer circle
        paint.setColor(foregroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(WHITE_CIRCLE_OFFSET);

        canvas.drawOval(
                new RectF(
                        (float) x,
                        (float) y,
                        (float) (x + circleDiameter),
                        (float) (y + circleDiameter)
                ),
                paint
        );

        // Draw the middle dot
        paint.setColor(foregroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(
                new RectF(
                        (float) (x + MIDDLE_DOT_OFFSET),
                        (float) (y + MIDDLE_DOT_OFFSET),
                        (float) (x + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER),
                        (float) (y + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER)
                ),
                paint
        );
    }
}
