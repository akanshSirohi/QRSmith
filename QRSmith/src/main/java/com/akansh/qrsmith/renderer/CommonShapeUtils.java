package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.core.graphics.PathParser;

import java.util.Collection;
import java.util.Collections;

class CommonShapeUtils {

    public enum CornerPosition {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT
    }

    public static void drawCommonSVGStyleEyeBall(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color,int[] orientation, CommonShapeUtils.CornerPosition pos, Collection<String> svgCode, float rotation) {
        float gapModules  = 1.8f;
        float innerOffPx  = multiple * gapModules;
        float innerSizePx = size - 2f * innerOffPx;

        Path ball = CommonShapeUtils.makeFinderFramePath(pos, innerSizePx, x + innerOffPx, y + innerOffPx, svgCode, orientation, rotation);

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawPath(ball, paint);
    }

    public static void drawCommonSVGStyleEyeFrame(Canvas canvas, Paint paint, int x, int y, int size, int color, CommonShapeUtils.CornerPosition pos, int[] orientation, String svgCode) {
        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        Path finder = CommonShapeUtils.makeFinderFramePath(pos, size, x, y, svgCode, orientation, 0f);
        canvas.drawPath(finder, paint);
    }

    public static Path makeFinderFramePath(CornerPosition pos, float sizePx, float dstX, float dstY, String svgPath, int[] orientation, float rotationDeg) {
        return makeFinderFramePath(pos, sizePx, dstX, dstY, Collections.singleton(svgPath), orientation, rotationDeg);
    }

    public static Path makeFinderFramePath(CornerPosition pos, float sizePx, float dstX, float dstY, Collection<String> svgPaths, int[] orientation, float rotationDeg) {
        Path merged = new Path();
        RectF unionBox = new RectF();
        boolean first = true;

        for (String d : svgPaths) {
            Path part = PathParser.createPathFromPathData(d);
            part.setFillType(Path.FillType.EVEN_ODD);

            RectF bb = new RectF();
            part.computeBounds(bb, true);
            if (first) {
                unionBox.set(bb);
                first = false;
            } else {
                unionBox.union(bb);
            }

            merged.addPath(part);
        }

        Matrix m = new Matrix();
        m.postTranslate(-unionBox.left, -unionBox.top);

        float cx = unionBox.width()  / 2f;
        float cy = unionBox.height() / 2f;

        m.postRotate(rotationDeg, cx, cy);

        if (pos == CornerPosition.TOP_LEFT)     m.postScale(orientation[0], orientation[1], cx, cy);
        if (pos == CornerPosition.TOP_RIGHT)    m.postScale(orientation[2], orientation[3], cx, cy);
        if (pos == CornerPosition.BOTTOM_LEFT)  m.postScale(orientation[4], orientation[5], cx, cy);

        float uniform = sizePx / Math.max(unionBox.width(), unionBox.height());
        m.postScale(uniform, uniform);
        m.postTranslate(dstX, dstY);
        merged.transform(m);

        return merged;
    }



    public static void drawMultiRoundCornerStyleBall(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, float[] radii) {
        float gapModules = 1.8f;
        float innerOffset = multiple * gapModules;
        float innerSize = size - (innerOffset * 2f);

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
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

    public static void drawMultiRoundCornerStyleFrame(Canvas canvas, Paint paint, int x, int y, int size, float strokeWidth, int color, float[] radii) {
        // stroke and radius follow the library’s own math
        float inset = strokeWidth / 2f;

        if (paint.getShader() == null) {
            paint.setColor(color);
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        RectF rect = new RectF(
                x + inset,
                y + inset,
                x + size - inset,
                y + size - inset
        );

        Path path = new Path();
        path.addRoundRect(rect, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    public static void drawHexagon(Canvas canvas, Paint paint, float centerX, float centerY, float size) {
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

    public static void drawSvgDataPattern(Canvas canvas, Paint paint, int outputX, int outputY, int multiple, String svgPath) {
        if (svgPath == null || svgPath.isEmpty()) {
            return;
        }
        Path path = PathParser.createPathFromPathData(svgPath);
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);

        Matrix m = new Matrix();
        m.postTranslate(-bounds.left, -bounds.top);
        float scale = multiple / Math.max(bounds.width(), bounds.height());
        m.postScale(scale, scale);
        m.postTranslate(outputX, outputY);
        path.transform(m);

        canvas.drawPath(path, paint);
    }


    public static void drawDottedStylePattern(Canvas canvas, Paint paint, int outputX, int outputY, int circleSize) {
        canvas.drawOval(new RectF(outputX, outputY, (outputX + circleSize), (outputY + circleSize)), paint);
    }
}
