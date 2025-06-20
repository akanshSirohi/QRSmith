package com.akansh.qrsmith;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.zxing.qrcode.encoder.ByteMatrix;

class CustomPattern {
    public static void drawInnerPatternFluidStyle(Canvas canvas, Paint paint, int inputX, int inputY, ByteMatrix input, int inputWidth, int inputHeight, int outputX, int outputY, int multiple) {
        boolean left = inputX > 0 && input.get(inputX - 1, inputY) == 1;
        boolean right = inputX < inputWidth - 1 && input.get(inputX + 1, inputY) == 1;
        boolean top = inputY > 0 && input.get(inputX, inputY - 1) == 1;
        boolean bottom = inputY < inputHeight - 1 && input.get(inputX, inputY + 1) == 1;

        // Center coordinates
        float cx = outputX + multiple / 2f;
        float cy = outputY + multiple / 2f;
        float radius = multiple / 2.1f;

        // Draw center circle
        canvas.drawCircle(cx, cy, radius, paint);

        // Draw connecting rectangles
        if (right) {
            float nx = outputX + multiple + multiple / 2f;
            canvas.drawRect(cx, cy - radius, nx, cy + radius, paint);
        }
        if (left) {
            float nx = outputX - multiple / 2f;
            canvas.drawRect(nx, cy - radius, cx, cy + radius, paint);
        }
        if (top) {
            float ny = outputY - multiple / 2f;
            canvas.drawRect(cx - radius, ny, cx + radius, cy, paint);
        }
        if (bottom) {
            float ny = outputY + multiple + multiple / 2f;
            canvas.drawRect(cx - radius, cy, cx + radius, ny, paint);
        }
    }

    public static void drawInnerPatternNormalStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        canvas.drawRoundRect(new RectF(outputX, outputY, outputX + multiple, outputY + multiple), 0, 0, paint);
    }
}
