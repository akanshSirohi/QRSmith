package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.HashMap;
import java.util.Map;

public class QRSmith {

    public enum QRCodeStyle {
        SQUARED, DOTS
    }

    public enum QRErrorCorrectionLevel {
        L, M, Q, H
    }

    public static class QRCodeOptions {
        public int width = 500;
        public int height = 500;
        public int quiteZone = 1;
        public Bitmap logo = null;
        public int backgroundColor = Color.WHITE;
        public int foregroundColor = Color.BLACK;
        public QRErrorCorrectionLevel errorCorrectionLevel = QRErrorCorrectionLevel.H; // Default error correction level
        public QRCodeStyle style = QRCodeStyle.SQUARED;
        public float dotSizeFactor = 0.8f;
    }

    public static Bitmap generateQRCode(String content, QRCodeOptions options) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }

        // Prepare hints for encoding
        Map<EncodeHintType, Object> hints = new HashMap<>();
        Bitmap bitmap = null;

        ErrorCorrectionLevel errorCorrectionLevel = getErrorCorrectionLevel(options);

        try {

            if (options.style == QRCodeStyle.SQUARED) {

                hints.put(EncodeHintType.MARGIN, options.quiteZone);
                hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);

                BitMatrix bitMatrix = new MultiFormatWriter().encode(
                        content,
                        BarcodeFormat.QR_CODE,
                        options.width,
                        options.height,
                        hints
                );

                bitmap = renderSquaredQRImage(bitMatrix, options.width, options.height, options.foregroundColor, options.backgroundColor);
            } else if (options.style == QRCodeStyle.DOTS) {
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                QRCode qrCode = Encoder.encode(content, errorCorrectionLevel, hints);
                options.dotSizeFactor = (options.dotSizeFactor < 0.5f ? 0.5f : (Math.min(options.dotSizeFactor, 1f)));
                bitmap = renderRoundedQRImage(qrCode, options.width, options.height, options.quiteZone, options.dotSizeFactor);
            }

            // Add logo if provided
            if (options.logo != null && bitmap != null) {
                bitmap = addLogo(bitmap, options.logo);
            }
        }catch (Exception e) {}
        return bitmap;
    }

    private static ErrorCorrectionLevel getErrorCorrectionLevel(QRCodeOptions options) {
        ErrorCorrectionLevel errorCorrectionLevel;
        switch (options.errorCorrectionLevel) {
            case L:
                errorCorrectionLevel = ErrorCorrectionLevel.L;
                break;
            case M:
                errorCorrectionLevel = ErrorCorrectionLevel.M;
                break;
            case Q:
                errorCorrectionLevel = ErrorCorrectionLevel.Q;
                break;
            default:
                errorCorrectionLevel = ErrorCorrectionLevel.H;
        }
        return errorCorrectionLevel;
    }

    private static Bitmap renderSquaredQRImage(BitMatrix bitMatrix, int width, int height, int foregroundColor, int backgroundColor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(foregroundColor);

        for (int x = 0; x < bitMatrix.getWidth(); x++) {
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                if (bitMatrix.get(x, y)) {
                    bitmap.setPixel(x, y, foregroundColor);
                } else {
                    bitmap.setPixel(x, y, backgroundColor);
                }
            }
        }

        return bitmap;
    }

    private static Bitmap renderRoundedQRImage(QRCode code, int width, int height, int quietZone, float dotSizeFactor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Set up paint for background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.WHITE);

        // Draw the white background
        canvas.drawRect(0, 0, width, height, backgroundPaint);

        // Set up paint for drawing the QR code
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        // Get the QR code matrix
        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }

        // Calculate dimensions
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        // Calculate scaling factors and padding
        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        int FINDER_PATTERN_SIZE = 7;
        int circleSize = (int) (multiple * dotSizeFactor); // Adjust dot size based on dotSizeFactor

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
        drawFinderPatternCircleStyle(canvas, paint, leftPadding, topPadding, circleDiameter);
        drawFinderPatternCircleStyle(
                canvas,
                paint,
                leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple,
                topPadding,
                circleDiameter
        );
        drawFinderPatternCircleStyle(
                canvas,
                paint,
                leftPadding,
                topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
                circleDiameter
        );

        return bitmap;
    }

    private static void drawFinderPatternCircleStyle(
            Canvas canvas,
            Paint paint,
            int x,
            int y,
            int circleDiameter
    ) {
        int WHITE_CIRCLE_DIAMETER = circleDiameter * 5 / 7;
        int WHITE_CIRCLE_OFFSET = circleDiameter / 7;
        int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
        int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;

        // Draw the outer black circle
        paint.setColor(Color.BLACK);
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
        paint.setColor(Color.WHITE);
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
        paint.setColor(Color.BLACK);
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


    private static Bitmap addLogo(Bitmap qrCode, Bitmap logo) {
        Bitmap combinedBitmap = Bitmap.createBitmap(qrCode.getWidth(), qrCode.getHeight(), qrCode.getConfig());
        Canvas canvas = new Canvas(combinedBitmap);
        canvas.drawBitmap(qrCode, 0, 0, null);

        // Calculate logo placement
        int overlayWidth = qrCode.getWidth() / 5;
        int overlayHeight = qrCode.getHeight() / 5;
        int left = (qrCode.getWidth() - overlayWidth) / 2;
        int top = (qrCode.getHeight() - overlayHeight) / 2;

        Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, overlayWidth, overlayHeight, true);
        canvas.drawBitmap(scaledLogo, left, top, null);
        return combinedBitmap;
    }
}