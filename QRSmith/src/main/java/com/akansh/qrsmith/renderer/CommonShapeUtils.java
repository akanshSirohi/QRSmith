package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.core.graphics.PathParser;

class CommonShapeUtils {
    public static final String PinchedSquircle_Frame_SVG = "M 9.10 23.83 C 12.01 16.82 19.09 11.15 26.92 11.40 C 40.30 11.38 53.68 11.34 67.05 11.45 C 72.90 11.28 78.09 15.00 81.51 19.46 C 84.22 23.04 83.95 27.76 84.03 32.00 C 83.91 45.04 84.13 58.08 83.93 71.11 C 84.02 77.23 79.78 82.63 74.51 85.31 C 70.32 87.35 65.51 86.97 60.99 87.03 C 43.61 86.83 26.22 87.29 8.85 86.81 C 9.98 72.93 12.94 58.85 10.29 44.94 C 8.90 38.05 7.20 30.79 9.10 23.83 M 19.51 34.99 C 19.48 40.73 21.54 46.26 21.51 52.00 C 21.47 56.01 21.67 60.05 21.25 64.05 C 20.60 68.15 19.72 72.23 19.46 76.39 C 32.64 76.81 45.85 76.44 59.04 76.56 C 66.32 76.89 74.16 70.77 73.49 63.04 C 73.48 54.01 73.48 44.97 73.48 35.94 C 73.80 29.40 68.40 22.95 61.72 22.75 C 51.87 22.40 41.98 22.34 32.12 22.64 C 25.68 22.73 19.34 28.36 19.51 34.99 Z";
    public static final String PinchedSquircle_Ball_SVG = "M 27.94 31.94 C 32.21 26.51 39.63 25.96 45.99 25.65 C 59.52 25.55 73.10 25.25 86.57 26.79 C 93.53 28.06 99.00 34.83 98.94 41.89 C 99.12 55.59 98.91 69.30 99.03 83.00 C 98.95 86.70 99.25 90.82 96.87 93.94 C 94.47 97.10 91.35 99.92 87.53 101.18 C 84.11 102.14 80.51 101.98 76.99 102.02 C 59.13 101.92 41.26 102.12 23.40 101.92 C 23.96 94.61 25.58 87.40 25.53 80.05 C 25.57 70.52 25.95 60.89 24.11 51.48 C 22.65 44.80 23.00 37.16 27.94 31.94 Z";
    public static final String BlobCorner_Frame_SVG = "M 48.37 12.41 C 51.17 11.59 54.12 11.56 57.01 11.49 C 80.01 11.52 103.00 11.53 126.00 11.49 C 136.18 10.91 146.52 13.57 155.35 18.63 C 164.18 24.45 171.71 32.74 175.30 42.81 C 177.66 49.60 178.90 56.80 178.83 64.00 C 178.69 85.34 178.65 106.67 178.82 128.01 C 178.87 133.35 178.72 139.06 175.83 143.75 C 171.52 151.50 161.28 152.20 156.27 159.29 C 152.80 163.20 151.68 168.49 148.73 172.72 C 145.61 176.96 140.01 178.03 135.05 178.01 C 111.37 178.02 87.69 177.95 64.01 178.04 C 55.71 178.44 47.39 176.49 39.83 173.17 C 28.04 166.65 17.80 155.84 14.71 142.42 C 13.14 136.76 12.42 130.88 12.51 125.00 C 12.67 103.01 12.66 81.01 12.51 59.01 C 12.48 53.69 13.20 47.96 16.74 43.74 C 20.76 38.72 27.50 37.38 32.22 33.23 C 36.53 29.82 38.64 24.61 40.75 19.72 C 42.14 16.37 44.86 13.50 48.37 12.41 M 55.94 43.97 C 51.72 50.09 45.76 54.72 39.41 58.47 C 37.17 59.39 37.52 61.99 37.45 63.96 C 37.65 83.64 37.61 103.33 37.45 123.01 C 37.26 131.18 38.34 140.44 44.79 146.24 C 49.12 151.02 55.63 152.99 61.94 153.00 C 81.62 153.03 101.31 152.96 121.00 153.02 C 124.20 152.99 127.44 153.26 130.61 152.72 C 133.81 147.97 136.64 142.87 140.99 139.02 C 144.64 135.16 149.51 132.88 153.67 129.68 C 154.07 126.80 153.86 123.88 153.86 120.99 C 153.69 104.99 153.76 88.99 153.84 73.00 C 153.85 66.40 154.32 59.68 152.49 53.26 C 151.14 47.30 146.58 42.64 141.58 39.43 C 134.41 35.33 125.88 36.71 118.00 36.52 C 99.05 36.66 80.06 36.22 61.13 36.72 C 58.92 38.74 57.72 41.59 55.94 43.97 Z";
    public static final String BlobCorner_Ball_SVG = "M 39.42 27.18 C 54.32 27.11 69.22 27.13 84.11 27.17 C 91.55 27.24 97.34 34.24 97.46 41.43 C 97.64 55.65 97.38 69.89 97.57 84.12 C 91.40 87.12 86.68 92.36 83.37 98.28 C 68.53 98.18 53.68 98.29 38.84 98.23 C 33.74 98.01 29.14 94.55 26.97 90.02 C 25.59 86.88 25.28 83.39 25.39 79.99 C 25.57 67.10 25.33 54.20 25.51 41.32 C 31.15 37.87 36.58 33.40 39.42 27.18 Z";;
    public static final String CornerWarp_Frame_SVG = "M 29.10 27.78 C 30.40 27.71 31.70 27.70 33.00 27.77 C 77.82 30.74 122.66 33.35 167.49 36.25 C 172.95 36.54 178.75 36.53 183.60 39.44 C 191.00 43.86 194.83 52.66 194.74 61.08 C 195.13 105.18 195.68 149.28 195.92 193.36 C 191.63 193.90 187.30 193.59 183.00 193.64 C 144.00 193.64 105.00 193.62 66.01 193.65 C 52.83 194.09 41.94 181.64 41.17 169.04 C 37.25 121.95 32.88 74.88 29.10 27.78 M 52.88 51.64 C 55.32 84.81 58.60 117.93 61.27 151.10 C 61.51 157.64 64.94 164.06 70.59 167.48 C 74.59 169.92 79.43 170.14 83.99 170.22 C 111.66 170.18 139.34 170.18 167.01 170.22 C 168.66 170.19 170.33 170.09 171.97 169.81 C 172.50 165.90 172.18 161.94 172.18 158.01 C 171.87 130.33 171.68 102.65 171.35 74.98 C 171.63 66.78 165.67 58.16 157.02 57.78 C 126.03 55.75 95.01 54.02 64.02 52.01 C 60.31 51.77 56.59 51.35 52.88 51.64 Z";
    public static final String CornerWarp_Ball_SVG = "M 23.03 30.00 C 43.72 31.34 64.41 32.59 85.09 33.98 C 90.25 34.11 93.83 39.17 93.74 44.04 C 93.98 63.10 94.11 82.18 94.29 101.25 C 75.85 101.27 57.41 101.22 38.98 101.27 C 33.39 101.72 28.72 96.45 28.31 91.15 C 26.53 70.77 24.80 50.38 23.03 30.00 Z";

    public enum CornerPosition {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT
    }

    public static Path makeFinderFramePath(CornerPosition pos, float sizePx, float dstX, float dstY, String svgPath, int[] orientation) {
        final Path TL_TEMPLATE;
        final RectF TL_BBOX = new RectF();

        TL_TEMPLATE = PathParser.createPathFromPathData(svgPath);
        TL_TEMPLATE.setFillType(Path.FillType.EVEN_ODD);
        TL_TEMPLATE.computeBounds(TL_BBOX, true);

        Path p = new Path(TL_TEMPLATE);
        Matrix m = new Matrix();

        m.postTranslate(-TL_BBOX.left, -TL_BBOX.top);

        float cx = TL_BBOX.width()  / 2f;
        float cy = TL_BBOX.height() / 2f;

        if (pos == CornerPosition.TOP_LEFT)  m.postScale(orientation[0],  orientation[1], cx, cy);
        if (pos == CornerPosition.TOP_RIGHT)  m.postScale(orientation[2],  orientation[3], cx, cy);
        if (pos == CornerPosition.BOTTOM_LEFT) m.postScale( orientation[4], orientation[5], cx, cy);

        float uniform = sizePx / Math.max(TL_BBOX.width(), TL_BBOX.height());
        m.postScale(uniform, uniform);

        m.postTranslate(dstX, dstY);

        p.transform(m);
        return p;
    }


    public static void drawMultiRoundCornerStyle(Canvas canvas, Paint paint, int x, int y, int size, int multiple, int color, float[] radii) {
        float gapModules = 1.8f;
        float innerOffset = multiple * gapModules;
        float innerSize = size - (innerOffset * 2f);

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
}
