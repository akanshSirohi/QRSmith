package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.akansh.qrsmith.util.CommonShapeUtils;

public class QRFinderBallRenderer {

    public enum CornerPosition {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }

    private void drawNormalStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, boolean rounded) {
        int stroke = size / 7;
        int innerSize = size * 3 / 7;
        int innerOffset = size * 2 / 7;
        float radiusMultiple = rounded ? 1.0f : 0.0f;
        float radius = (multiple / 2f) * radiusMultiple;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(x + innerOffset, y + innerOffset, x + innerOffset + innerSize, y + innerOffset + innerSize), radius, radius, paint);
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

        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        CommonShapeUtils.drawHexagon(canvas, paint, centerX, centerY, size/4.0f);
    }

    public void drawCircleStyle(Canvas canvas, Paint paint, int x, int y, int circleDiameter, int foregroundColor) {
        int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
        int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;

        paint.setColor(foregroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(new RectF((x + MIDDLE_DOT_OFFSET), (y + MIDDLE_DOT_OFFSET), (x + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER), (y + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER)), paint);
    }

    private void drawMultiRoundCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, float[] radii) {
        float gapModules = 1.8f;
        float innerOffset = multiple * gapModules;
        float innerSize   = size - (innerOffset * 2f);

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);    // filled, not stroked

        RectF rect = new RectF(
                x + innerOffset,
                y + innerOffset,
                x + innerOffset + innerSize,
                y + innerOffset + innerSize
        );


        Path path = new Path();
        path.addRoundRect(rect, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    public void drawOneSharpCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CornerPosition sharpCorner) {
        float radius    = (multiple / 2f) * 2f; // Tweak as needed

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);    // filled, not stroked

        float[] radii = {
                radius, radius,   // TL
                radius, radius,   // TR
                radius, radius,   // BR
                radius, radius    // BL
        };
        switch (sharpCorner) {
            case TOP_LEFT:
                radii[4] = radii[5] = 0;
                break;
            case TOP_RIGHT:
                radii[6] = radii[7] = 0;
                break;
            case BOTTOM_RIGHT:
                radii[0] = radii[1] = 0;
                break;
            case BOTTOM_LEFT:
                radii[2] = radii[3] = 0;
                break;
        }

        drawMultiRoundCornerStyle(canvas, paint, x, y, size, multiple, color, radii);
    }

    public void drawTechEyeStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CornerPosition sharpCorner) {
        float radius = (multiple / 2f) * 2f; // Tweak as needed

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);    // filled, not stroked

        float[] radii = {
                radius, radius,   // TL
                radius, radius,   // TR
                radius, radius,   // BR
                radius, radius    // BL
        };
        switch (sharpCorner) {
            case TOP_LEFT:
                radii[0] = radii[1] = 0;
                radii[4] = radii[5] = 0;
                break;
            case TOP_RIGHT:
                radii[2] = radii[3] = 0;
                radii[6] = radii[7] = 0;
                break;
            case BOTTOM_RIGHT:
                radii[4] = radii[5] = 0;
                radii[0] = radii[1] = 0;
                break;
            case BOTTOM_LEFT:
                radii[6] = radii[7] = 0;
                radii[2] = radii[3] = 0;
                break;
        }

        drawMultiRoundCornerStyle(canvas, paint, x, y, size, multiple, color, radii);
    }

    public void drawSoftRoundedStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CornerPosition sharpCorner) {
        float radius = (multiple / 2f) * 2f; // Tweak as needed

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);    // filled, not stroked

        float[] radii = {
                radius, radius,   // TL
                radius, radius,   // TR
                radius, radius,   // BR
                radius, radius    // BL
        };

        switch (sharpCorner) {
            case TOP_LEFT:
                radii[2] = radii[3] = 0;
                radii[4] = radii[5] = 0;
                radii[6] = radii[7] = 0;
                break;
            case TOP_RIGHT:
                radii[0] = radii[1] = 0;
                radii[4] = radii[5] = 0;
                radii[6] = radii[7] = 0;
                break;
            case BOTTOM_RIGHT:
                radii[0] = radii[1] = 0;
                radii[2] = radii[3] = 0;
                radii[6] = radii[7] = 0;
                break;
            case BOTTOM_LEFT:
                radii[0] = radii[1] = 0;
                radii[2] = radii[3] = 0;
                radii[4] = radii[5] = 0;
                break;
        }

        drawMultiRoundCornerStyle(canvas, paint, x, y, size, multiple, color, radii);
    }
}
