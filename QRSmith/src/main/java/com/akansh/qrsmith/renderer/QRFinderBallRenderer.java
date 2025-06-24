package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Collections;

public class QRFinderBallRenderer {

    public void drawRoundedSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color) {
        float radius = (multiple / 2f) * 2f; // Tweak as needed

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);    // filled, not stroked

        float[] radii = {
                radius, radius,   // TL
                radius, radius,   // TR
                radius, radius,   // BR
                radius, radius    // BL
        };

        CommonShapeUtils.drawMultiRoundCornerStyleBall(canvas, paint, x, y, size, multiple, color, radii);
    }

    public void drawSquaredStyle(Canvas canvas, Paint paint, int x, int y, int size, int color) {
        int stroke = size / 7;
        int innerSize = size * 3 / 7;
        int innerOffset = size * 2 / 7;

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setAntiAlias(true);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(x + innerOffset, y + innerOffset, x + innerOffset + innerSize, y + innerOffset + innerSize), 0, 0, paint);
    }

    public void drawHexStyle(Canvas canvas, Paint paint, int x, int y, int size, int color) {
        float centerX = x + size/2f;
        float centerY = y + size/2f;

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setStyle(Paint.Style.FILL);
        CommonShapeUtils.drawHexagon(canvas, paint, centerX, centerY, size/4.0f);
    }

    public void drawCircleStyle(Canvas canvas, Paint paint, int x, int y, int circleDiameter, int multiple, int foregroundColor) {

        float gapModules = 1.6f;
        float MIDDLE_DOT_OFFSET = multiple * gapModules;
        float MIDDLE_DOT_DIAMETER = circleDiameter - (MIDDLE_DOT_OFFSET * 2f);

        if (paint.getShader() == null) paint.setColor(foregroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(new RectF((x + MIDDLE_DOT_OFFSET), (y + MIDDLE_DOT_OFFSET), (x + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER), (y + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER)), paint);
    }

    public void drawOneSharpCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition sharpCorner) {

        float radius = (multiple / 2f) * 2f; // Tweak as needed

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
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
            case BOTTOM_LEFT:
                radii[2] = radii[3] = 0;
                break;
        }

        CommonShapeUtils.drawMultiRoundCornerStyleBall(canvas, paint, x, y, size, multiple, color, radii);
    }

    public void drawTechEyeStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition sharpCorner) {
        float radius = (multiple / 2f) * 2f; // Tweak as needed

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
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
            case BOTTOM_LEFT:
                radii[6] = radii[7] = 0;
                radii[2] = radii[3] = 0;
                break;
        }

        CommonShapeUtils.drawMultiRoundCornerStyleBall(canvas, paint, x, y, size, multiple, color, radii);
    }

    public void drawSoftRoundedStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition sharpCorner) {
        float radius = (multiple / 2f) * 2f; // Tweak as needed

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
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
            case BOTTOM_LEFT:
                radii[0] = radii[1] = 0;
                radii[2] = radii[3] = 0;
                radii[4] = radii[5] = 0;
                break;
        }

        CommonShapeUtils.drawMultiRoundCornerStyleBall(canvas, paint, x, y, size, multiple, color, radii);
    }

    public void drawPinchedSquircleStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = { -1, 1, 1, 1, -1, -1 };
        CommonShapeUtils.drawCommonSVGStyleEyeBall(canvas, paint, x, y, size, multiple, color, orientation, pos, Collections.singleton(EyesShapesSVGContants.PinchedSquircle_Ball_SVG), 0f);
    }

    public void drawBlobCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, -1, 1, 1, -1};
        CommonShapeUtils.drawCommonSVGStyleEyeBall(canvas, paint, x, y, size, multiple, color, orientation, pos, Collections.singleton(EyesShapesSVGContants.BlobCorner_Ball_SVG), 0f);
    }

    public void drawCornerWarpStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, -1, 1, 1, -1};
        CommonShapeUtils.drawCommonSVGStyleEyeBall(canvas, paint, x, y, size, multiple, color, orientation, pos, Collections.singleton(EyesShapesSVGContants.CornerWarp_Ball_SVG), 0f);
    }

    public void drawPillStackHStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, 1, 1, 1, 1};
        CommonShapeUtils.drawCommonSVGStyleEyeBall(canvas, paint, x, y, size, multiple, color, orientation, pos, EyesShapesSVGContants.PillStack_Ball_SVG, 90f);
    }

    public void drawPillStackVStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, 1, 1, 1, 1};
        CommonShapeUtils.drawCommonSVGStyleEyeBall(canvas, paint, x, y, size, multiple, color, orientation, pos, EyesShapesSVGContants.PillStack_Ball_SVG, 0f);
    }

    public void drawIncurveStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, 1, 1, 1, 1};
        CommonShapeUtils.drawCommonSVGStyleEyeBall(canvas, paint, x, y, size, multiple, color, orientation, pos, Collections.singleton(EyesShapesSVGContants.Incurve_Ball_SVG), 0f);
    }
    public void drawChiselStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, CommonShapeUtils.CornerPosition pos) {
        int[] orientation = {1, 1, 1, -1, 1, -1};
        CommonShapeUtils.drawCommonSVGStyleEyeBall(canvas, paint, x, y, size, multiple, color, orientation, pos, Collections.singleton(EyesShapesSVGContants.Chisel_Ball_SVG), 0f);
    }
}
