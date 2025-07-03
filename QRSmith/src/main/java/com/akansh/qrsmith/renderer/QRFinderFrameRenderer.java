package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

class QRFinderFrameRenderer {

    public void drawRoundedSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, float strokeWidth, int color) {
        float radius = (multiple / 2f) * 5f;

        // start with all rounded
        float[] radii = {
                radius, radius,   // top-left
                radius, radius,   // top-right
                radius, radius,   // bottom-right
                radius, radius    // bottom-left
        };

        CommonShapeUtils.drawMultiRoundCornerStyleFrame(canvas, paint, x, y, size, strokeWidth, color, radii);
    }

    public void drawSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, float strokeWidth, int color) {

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        float inset = strokeWidth / 2f;
        canvas.drawRoundRect(new RectF(x + inset, y + inset, x + size - inset, y + size - inset), 0, 0, paint);
    }

    public void drawHexStyle(Canvas canvas, Paint paint, int x, int y, int size, int color) {
        float centerX = x + size/2f;
        float centerY = y + size/2f;

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(size/8f);
        CommonShapeUtils.drawHexagon(canvas, paint, centerX, centerY, size/2f);
    }

    public void drawCircleStyle(Canvas canvas, Paint paint, int x, int y, int circleDiameter, float strokeWidth, int foregroundColor) {
        float inset = strokeWidth / 2f;

        // Draw the outer circle
        if (paint.getShader() == null) paint.setColor(foregroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        canvas.drawOval(new RectF(x + inset, y + inset, x + circleDiameter - inset, y + circleDiameter - inset), paint);
    }



    public void drawOneSharpCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, float strokeWidth, int color, CommonShapeUtils.CornerPosition sharpCorner) {
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

        CommonShapeUtils.drawMultiRoundCornerStyleFrame(canvas, paint, x, y, size, strokeWidth, color, radii);
    }

    public void drawTechEyeStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, float strokeWidth, int color, CommonShapeUtils.CornerPosition sharpCorner) {
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

        CommonShapeUtils.drawMultiRoundCornerStyleFrame(canvas, paint, x, y, size, strokeWidth, color, radii);
    }

    public void drawSoftRoundedStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, float strokeWidth, int color, CommonShapeUtils.CornerPosition sharpCorner) {
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

        CommonShapeUtils.drawMultiRoundCornerStyleFrame(canvas, paint, x, y, size, strokeWidth, color, radii);
    }

    public void drawPinchedSquircleStyle(Canvas canvas, Paint paint, int x, int y, int size, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {-1, 1, 1, 1, -1, -1};
        CommonShapeUtils.drawCommonSVGStyleEyeFrame(canvas, paint, x, y, size, color, pos, orientation, EyesShapesSVGContants.PinchedSquircle_Frame_SVG);
    }

    public void drawBlobCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, -1, 1, 1, -1};
        CommonShapeUtils.drawCommonSVGStyleEyeFrame(canvas, paint, x, y, size, color, pos, orientation, EyesShapesSVGContants.BlobCorner_Frame_SVG);
    }

    public void drawCornerWarpStyle(Canvas canvas, Paint paint, int x, int y, int size, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, -1, 1, 1, -1};
        CommonShapeUtils.drawCommonSVGStyleEyeFrame(canvas, paint, x, y, size, color, pos, orientation, EyesShapesSVGContants.CornerWarp_Frame_SVG);
    }
}
