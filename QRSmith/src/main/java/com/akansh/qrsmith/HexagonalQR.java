package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.HashMap;
import java.util.Map;

class HexagonalQR {
    public static Bitmap renderQRImage(String content, QRCodeOptions qrOptions, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCode qrCode = Encoder.encode(content, errorCorrectionLevel, hints);
        qrOptions.dotSizeFactor = (qrOptions.dotSizeFactor < 0.5f ? 0.5f : (Math.min(qrOptions.dotSizeFactor, 1f)));

        Bitmap bitmap = Bitmap.createBitmap(qrOptions.width, qrOptions.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if (qrOptions.background != null) {
            // Draw background image
            Bitmap backgroundBitmap = Bitmap.createScaledBitmap(qrOptions.background, qrOptions.width, qrOptions.height, true);
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        }else{
            // Set up paint for background
            Paint backgroundPaint = new Paint();
            backgroundPaint.setStyle(Paint.Style.FILL);
            backgroundPaint.setColor(qrOptions.backgroundColor);

            // Draw the background
            canvas.drawRect(0, 0, qrOptions.width, qrOptions.height, backgroundPaint);
        }

        // Set up paint for drawing the QR code
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(qrOptions.foregroundColor);

        // Get the QR code matrix
        ByteMatrix input = qrCode.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }

        // Calculate dimensions
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (qrOptions.quiteZone * 2);
        int qrHeight = inputHeight + (qrOptions.quiteZone * 2);
        int outputWidth = Math.max(qrOptions.width, qrWidth);
        int outputHeight = Math.max(qrOptions.height, qrHeight);

        // Calculate scaling factors and padding
        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        int FINDER_PATTERN_SIZE = 7;
        float hexSize = (multiple * qrOptions.dotSizeFactor) / 2f;

        // Iterate through each QR code module
        for (int inputY = 0; inputY < inputHeight; inputY++) {
            int outputY = topPadding + (multiple * inputY);
            for (int inputX = 0; inputX < inputWidth; inputX++) {
                int outputX = leftPadding + (multiple * inputX);
                if (input.get(inputX, inputY) == 1) {
                    if (!(inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE)) {
                        drawHexagon(canvas, paint, outputX + multiple/2f, outputY + multiple/2f, hexSize);
                    }
                }
            }
        }

        // Draw finder patterns
        int patternDiameter = multiple * FINDER_PATTERN_SIZE;
        drawFinderPatternHexStyle(canvas, paint, leftPadding, topPadding, patternDiameter, qrOptions.foregroundColor);
        drawFinderPatternHexStyle(canvas, paint,
                leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple,
                topPadding, patternDiameter, qrOptions.foregroundColor);
        drawFinderPatternHexStyle(canvas, paint, leftPadding,
                topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
                patternDiameter, qrOptions.foregroundColor);

        return bitmap;
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

    private static void drawFinderPatternHexStyle(
            Canvas canvas,
            Paint paint,
            int x,
            int y,
            int size,
            int foregroundColor
    ) {
        float centerX = x + size/2f;
        float centerY = y + size/2f;

        // Draw outer hexagon
        paint.setColor(foregroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(size/8f);
        drawHexagon(canvas, paint, centerX, centerY, size/2f);

        // Draw inner hexagon
        paint.setColor(foregroundColor);
        paint.setStyle(Paint.Style.FILL);
        drawHexagon(canvas, paint, centerX, centerY, size/4.8f);
    }
}