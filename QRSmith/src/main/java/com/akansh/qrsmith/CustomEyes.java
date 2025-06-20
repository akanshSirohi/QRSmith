package com.akansh.qrsmith;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

class CustomEyes {

    private static void drawFinderPatternNormalStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, boolean rounded) {
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

    private static void drawHexagon(Canvas canvas, Paint paint, float centerX, float centerY, float size) {
        Path hexagonPath = new Path();
        for (int i = 0; i < 6; i++) {
            float angle = (float) (2.0 * Math.PI * i / 6);
            float x = centerX + size * (float) Math.cos(angle);
            float y = centerY + size * (float) Math.sin(angle);
            if (i == 0) {
                hexagonPath.moveTo(x, y);
            } else {
                hexagonPath.lineTo(x, y);
            }
        }
        hexagonPath.close();
        canvas.drawPath(hexagonPath, paint);
    }

    public static void drawFinderPatternRoundedStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color) {
        drawFinderPatternNormalStyle(canvas, paint, x, y, size, multiple, color, true);
    }

    public static void drawFinderPatternSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color) {
        drawFinderPatternNormalStyle(canvas, paint, x, y, size, multiple, color, false);
    }

    public static void drawFinderPatternHexStyle(Canvas canvas, Paint paint, int x, int y, int size, int color) {
        float centerX = x + size/2f;
        float centerY = y + size/2f;

        // Draw outer hexagon
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(size/8f);
        drawHexagon(canvas, paint, centerX, centerY, size/2f);

        // Draw inner hexagon
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        drawHexagon(canvas, paint, centerX, centerY, size/4.8f);
    }
}
