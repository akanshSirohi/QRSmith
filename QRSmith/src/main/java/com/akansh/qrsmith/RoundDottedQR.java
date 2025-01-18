package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.HashMap;
import java.util.Map;

class RoundDottedQR {
    public static Bitmap renderQRImage(String content, QRCodeOptions qrOptions, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCode qrCode = Encoder.encode(content, errorCorrectionLevel, hints);
        qrOptions.dotSizeFactor = (qrOptions.dotSizeFactor < 0.5f ? 0.5f : (Math.min(qrOptions.dotSizeFactor, 1f)));

        Bitmap bitmap = Bitmap.createBitmap(qrOptions.width, qrOptions.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Set up paint for background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(qrOptions.backgroundColor);

        // Draw the white background
        canvas.drawRect(0, 0, qrOptions.width, qrOptions.height, backgroundPaint);

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
        int circleSize = (int) (multiple * qrOptions.dotSizeFactor); // Adjust dot size based on dotSizeFactor

        // Iterate through each QR code module
        for (int inputY = 0; inputY < inputHeight; inputY++) {
            int outputY = topPadding + (multiple * inputY);
            for (int inputX = 0; inputX < inputWidth; inputX++) {
                int outputX = leftPadding + (multiple * inputX);
                if (input.get(inputX, inputY) == 1) {
                    if (!(inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE)) {
                        canvas.drawOval(
                                new RectF(
                                        (float) outputX,
                                        (float) outputY,
                                        (float) (outputX + circleSize),
                                        (float) (outputY + circleSize)
                                ),
                                paint
                        );
                    }
                }
            }
        }

        // Draw finder patterns
        int circleDiameter = multiple * FINDER_PATTERN_SIZE;
        drawFinderPatternCircleStyle(canvas, paint, leftPadding, topPadding, circleDiameter, qrOptions.backgroundColor, qrOptions.foregroundColor);
        drawFinderPatternCircleStyle(
                canvas,
                paint,
                leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple,
                topPadding,
                circleDiameter,
                qrOptions.backgroundColor,
                qrOptions.foregroundColor
        );
        drawFinderPatternCircleStyle(
                canvas,
                paint,
                leftPadding,
                topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
                circleDiameter,
                qrOptions.backgroundColor,
                qrOptions.foregroundColor
        );

        return bitmap;
    }

    private static void drawFinderPatternCircleStyle(
            Canvas canvas,
            Paint paint,
            int x,
            int y,
            int circleDiameter,
            int backgroundColor,
            int foregroundColor
    ) {
        int WHITE_CIRCLE_DIAMETER = circleDiameter * 5 / 7;
        int WHITE_CIRCLE_OFFSET = circleDiameter / 7;
        int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
        int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;

        // Draw the outer black circle
        paint.setColor(foregroundColor);
        canvas.drawOval(
                new RectF(
                        (float) x,
                        (float) y,
                        (float) (x + circleDiameter),
                        (float) (y + circleDiameter)
                ),
                paint
        );

        // Draw the white circle
        paint.setColor(backgroundColor);
        canvas.drawOval(
                new RectF(
                        (float) (x + WHITE_CIRCLE_OFFSET),
                        (float) (y + WHITE_CIRCLE_OFFSET),
                        (float) (x + WHITE_CIRCLE_OFFSET + WHITE_CIRCLE_DIAMETER),
                        (float) (y + WHITE_CIRCLE_OFFSET + WHITE_CIRCLE_DIAMETER)
                ),
                paint
        );

        // Draw the middle black dot
        paint.setColor(foregroundColor);
        canvas.drawOval(
                new RectF(
                        (float) (x + MIDDLE_DOT_OFFSET),
                        (float) (y + MIDDLE_DOT_OFFSET),
                        (float) (x + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER),
                        (float) (y + MIDDLE_DOT_OFFSET + MIDDLE_DOT_DIAMETER)
                ),
                paint
        );
    }
}
