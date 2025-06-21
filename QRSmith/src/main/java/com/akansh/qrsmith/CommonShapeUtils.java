package com.akansh.qrsmith;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

class CommonShapeUtils {
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
