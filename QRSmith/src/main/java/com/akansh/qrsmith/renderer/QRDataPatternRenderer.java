package com.akansh.qrsmith.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.google.zxing.qrcode.encoder.ByteMatrix;


class QRDataPatternRenderer {
    public void drawAxisFluidStyle(Canvas canvas, Paint paint, int inputX, int inputY, ByteMatrix input, int inputWidth, int inputHeight, int outputX, int outputY, int multiple, int axis) {
        boolean left = inputX > 0 && input.get(inputX - 1, inputY) == 1;
        boolean right = inputX < inputWidth - 1 && input.get(inputX + 1, inputY) == 1;
        boolean top = inputY > 0 && input.get(inputX, inputY - 1) == 1;
        boolean bottom = inputY < inputHeight - 1 && input.get(inputX, inputY + 1) == 1;

        // Center coordinates
        float cx = outputX + multiple / 2f;
        float cy = outputY + multiple / 2f;
        float radius = multiple / 2.4f;

        // Draw center circle
        canvas.drawCircle(cx, cy, radius, paint);

        // Draw connecting rectangles
        if (right && axis == 0) {
            float nx = outputX + multiple + multiple / 2f;
            canvas.drawRect(cx, cy - radius, nx, cy + radius, paint);
        }else{
            canvas.drawCircle(cx, cy, radius, paint);
        }

        if (left && axis == 0) {
            float nx = outputX - multiple / 2f;
            canvas.drawRect(nx, cy - radius, cx, cy + radius, paint);
        }else{
            canvas.drawCircle(cx, cy, radius, paint);
        }

        if (top && axis == 1) {
            float ny = outputY - multiple / 2f;
            canvas.drawRect(cx - radius, ny, cx + radius, cy, paint);
        }else{
            canvas.drawCircle(cx, cy, radius, paint);
        }

        if (bottom && axis == 1) {
            float ny = outputY + multiple + multiple / 2f;
            canvas.drawRect(cx - radius, cy, cx + radius, ny, paint);
        }else{
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    public void drawFluidStyle(Canvas canvas, Paint paint, int inputX, int inputY, ByteMatrix input, int inputWidth, int inputHeight, int outputX, int outputY, int multiple) {
        boolean left = inputX > 0 && input.get(inputX - 1, inputY) == 1;
        boolean right = inputX < inputWidth - 1 && input.get(inputX + 1, inputY) == 1;
        boolean top = inputY > 0 && input.get(inputX, inputY - 1) == 1;
        boolean bottom = inputY < inputHeight - 1 && input.get(inputX, inputY + 1) == 1;

        // Center coordinates
        float cx = outputX + multiple / 2f;
        float cy = outputY + multiple / 2f;
        float radius = multiple / 2f;
        float sec_radius = 2f;

        // Draw center circle
        canvas.drawCircle(cx, cy, radius, paint);

        // Draw connecting rectangles
        if (right) {
            float nx = outputX + multiple + multiple / sec_radius;
            canvas.drawRect(cx, cy - radius, nx, cy + radius, paint);
        }
        if (left) {
            float nx = outputX - multiple / sec_radius;
            canvas.drawRect(nx, cy - radius, cx, cy + radius, paint);
        }
        if (top) {
            float ny = outputY - multiple / sec_radius;
            canvas.drawRect(cx - radius, ny, cx + radius, cy, paint);
        }
        if (bottom) {
            float ny = outputY + multiple + multiple / sec_radius;
            canvas.drawRect(cx - radius, cy, cx + radius, ny, paint);
        }
    }

    public void drawNormalStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        canvas.drawRoundRect(new RectF(outputX, outputY, outputX + multiple, outputY + multiple), 0, 0, paint);
    }

    public void drawBigDotStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        int circleSize = (int) (multiple * 0.98);
        CommonShapeUtils.drawDottedStylePattern(canvas, paint, outputX, outputY, circleSize, multiple);
    }

    public void drawSmallDotStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        int circleSize = (int) (multiple * 0.40f);
        CommonShapeUtils.drawDottedStylePattern(canvas, paint, outputX, outputY, circleSize, multiple);
    }

    public void drawHexStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        float hexSize = (multiple * 1.2f) / 2f;
        CommonShapeUtils.drawHexagon(canvas, paint, outputX + multiple/2f, outputY + multiple/2f, hexSize);
    }

    public void drawDiamondStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        int centerX = outputX + multiple / 2;
        int centerY = outputY + multiple / 2;
        int size = multiple / 2;

        Path diamondPath = new Path();

        diamondPath.moveTo(centerX, centerY - size);           // top
        diamondPath.lineTo(centerX + size, centerY);           // right
        diamondPath.lineTo(centerX, centerY + size);           // bottom
        diamondPath.lineTo(centerX - size, centerY);           // left

        diamondPath.close();
        canvas.drawPath(diamondPath, paint);
    }

    public void drawStarStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        float cx = outputX + multiple / 2f;
        float cy = outputY + multiple / 2f;

        float outer = multiple / 2f;
        float inner = outer / 2f;

        Path starPath = new Path();
        for (int i = 0; i < 10; i++) {
            double angle = Math.toRadians(36 * i - 90);
            float radius = (i % 2 == 0) ? outer : inner;
            float x = cx + (float) (radius * Math.cos(angle));
            float y = cy + (float) (radius * Math.sin(angle));

            if (i == 0) {
                starPath.moveTo(x, y);
            } else {
                starPath.lineTo(x, y);
            }
        }
        starPath.close();
        canvas.drawPath(starPath, paint);
    }

    public void drawHeartStyle(Canvas canvas, Paint paint, int outputX, int outputY, int multiple) {
        CommonShapeUtils.drawSvgDataPattern(canvas, paint, outputX, outputY, multiple, DataPatternSVGConstants.HEART_SVG);
    }
}
