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

class RoundQR {
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
        } else {
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
        int qrWidth = inputWidth + (qrOptions.quietZone * 2);
        int qrHeight = inputHeight + (qrOptions.quietZone * 2);
        int outputWidth = Math.max(qrOptions.width, qrWidth);
        int outputHeight = Math.max(qrOptions.height, qrHeight);

        // Calculate scaling factors and padding
        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        int FINDER_PATTERN_SIZE = 7;
        int circleSize = (int) (multiple * qrOptions.dotSizeFactor);

        // Calculate logo dimensions and position
        Bitmap logo = qrOptions.logo;

        int logoWidth = qrOptions.width / 5;
        int logoHeight = qrOptions.height / 5;
        int logoX = (qrOptions.width - logoWidth) / 2;
        int logoY = (qrOptions.height - logoHeight) / 2;

        // Only clear logo background if there's no background image and clearLogoBackground is true
        if (logo != null && qrOptions.clearLogoBackground && qrOptions.background == null) {
            Paint clearPaint = new Paint();
            clearPaint.setStyle(Paint.Style.FILL);
            clearPaint.setColor(qrOptions.backgroundColor);
            canvas.drawRect(logoX, logoY, logoX + logoWidth, logoY + logoHeight, clearPaint);
        }

        // Iterate through each QR code module
        for (int inputY = 0; inputY < inputHeight; inputY++) {
            int outputY = topPadding + (multiple * inputY);
            for (int inputX = 0; inputX < inputWidth; inputX++) {
                int outputX = leftPadding + (multiple * inputX);
                if (input.get(inputX, inputY) == 1) {
                    // Skip if in finder pattern area or logo area
                    boolean isInFinderPattern = (inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE);

                    boolean isInLogoArea = logo != null &&
                            outputX >= logoX && outputX < (logoX + logoWidth) - circleSize &&
                            outputY >= logoY && outputY < (logoY + logoHeight) - circleSize;

                    if (!isInFinderPattern && (!isInLogoArea || !qrOptions.clearLogoBackground)) {
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
        drawFinderPatternCircleStyle(canvas, paint, leftPadding, topPadding, circleDiameter, qrOptions.foregroundColor);
        drawFinderPatternCircleStyle(
                canvas,
                paint,
                leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple,
                topPadding,
                circleDiameter,
                qrOptions.foregroundColor
        );
        drawFinderPatternCircleStyle(
                canvas,
                paint,
                leftPadding,
                topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
                circleDiameter,
                qrOptions.foregroundColor
        );

        // Draw logo last
        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, true);
            canvas.drawBitmap(scaledLogo, logoX, logoY, null);
        }

        return bitmap;
    }

    private static void drawFinderPatternCircleStyle(
            Canvas canvas,
            Paint paint,
            int x,
            int y,
            int circleDiameter,
            int foregroundColor
    ) {
        int WHITE_CIRCLE_OFFSET = circleDiameter / 7;
        int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
        int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;

        // Draw the outer circle
        paint.setColor(foregroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(WHITE_CIRCLE_OFFSET);

        canvas.drawOval(
                new RectF(
                        (float) x,
                        (float) y,
                        (float) (x + circleDiameter),
                        (float) (y + circleDiameter)
                ),
                paint
        );

        // Draw the middle dot
        paint.setColor(foregroundColor);
        paint.setStyle(Paint.Style.FILL);
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