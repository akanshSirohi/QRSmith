package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

class QRFinderFrameRenderer {

    private void drawMultiRoundCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int color, float[] radii) {
        // stroke and radius follow the library’s own math
        int stroke = size / 7;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);

        Path path = new Path();
        path.addRoundRect(new RectF(x, y, x + size, y + size), radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    public void drawRoundedSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color) {
        float radius = (multiple / 2f) * 5f;

        // start with all rounded
        float[] radii = {
                radius, radius,   // top-left
                radius, radius,   // top-right
                radius, radius,   // bottom-right
                radius, radius    // bottom-left
        };

        drawMultiRoundCornerStyle(canvas, paint, x, y, size, color, radii);
    }

    public void drawSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int color) {
        int stroke = size / 7;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);
        canvas.drawRoundRect(new RectF(x, y, x + size, y + size), 0, 0, paint);
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



    public void drawOneSharpCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition sharpCorner) {
        float radius = (multiple / 2f) * 5f;

        // start with all rounded
        float[] radii = {
                radius, radius,   // top-left
                radius, radius,   // top-right
                radius, radius,   // bottom-right
                radius, radius    // bottom-left
        };

        switch (sharpCorner) {
            case TOP_LEFT:
                radii[4] = radii[5] = 0;
                break;
            case TOP_RIGHT:
                radii[6] = radii[7] = 0;
                break;
            case BOTTOM_LEFT:
                radii[2] = radii[3] = 0;
                break;
        }

        drawMultiRoundCornerStyle(canvas, paint, x, y, size, color, radii);
    }

    public void drawTechEyeStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition sharpCorner) {
        float radius = (multiple / 2f) * 5f;

        // start with all rounded
        float[] radii = {
                radius, radius,   // top-left
                radius, radius,   // top-right
                radius, radius,   // bottom-right
                radius, radius    // bottom-left
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
            case BOTTOM_LEFT:
                radii[6] = radii[7] = 0;
                radii[2] = radii[3] = 0;
                break;
        }

        drawMultiRoundCornerStyle(canvas, paint, x, y, size, color, radii);
    }

    public void drawSoftRoundedStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition sharpCorner) {
        float radius = (multiple / 2f) * 5f;

        // start with all rounded
        float[] radii = {
                radius, radius,   // top-left
                radius, radius,   // top-right
                radius, radius,   // bottom-right
                radius, radius    // bottom-left
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
            case BOTTOM_LEFT:
                radii[0] = radii[1] = 0;
                radii[2] = radii[3] = 0;
                radii[4] = radii[5] = 0;
                break;
        }

        drawMultiRoundCornerStyle(canvas, paint, x, y, size, color, radii);
    }

    public void drawPinchedSquircleStyle(Canvas canvas, Paint paint, int x, int y, int size, int color, CommonShapeUtils.CornerPosition pos) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        int[] orientation = {-1, 1, 1, 1, -1, -1};

        Path finder = CommonShapeUtils.makeFinderFramePath(pos, size, x, y, CommonShapeUtils.PinchedSquircle_Frame_SVG, orientation);
        canvas.drawPath(finder, paint);
    }

    public void drawBlobCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int color, CommonShapeUtils.CornerPosition pos) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        int[] orientation = {1, 1, -1, 1, 1, -1};

        Path finder = CommonShapeUtils.makeFinderFramePath(pos, size, x, y, CommonShapeUtils.BlobCorner_Frame_SVG, orientation);
        canvas.drawPath(finder, paint);
    }
}
