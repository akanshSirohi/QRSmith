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

class NormalQR {
    public static Bitmap renderQRImage(String content, QRCodeOptions qrOptions, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCode qrCode = Encoder.encode(content, errorCorrectionLevel, hints);

        Bitmap bitmap = Bitmap.createBitmap(qrOptions.width, qrOptions.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if (qrOptions.background != null) {
            Bitmap backgroundBitmap = Bitmap.createScaledBitmap(qrOptions.background, qrOptions.width, qrOptions.height, true);
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        } else {
            Paint bgPaint = new Paint();
            bgPaint.setStyle(Paint.Style.FILL);
            bgPaint.setColor(qrOptions.backgroundColor);
            canvas.drawRect(0, 0, qrOptions.width, qrOptions.height, bgPaint);
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(qrOptions.foregroundColor);

        ByteMatrix input = qrCode.getMatrix();
        if (input == null) throw new IllegalStateException();

        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + qrOptions.quietZone * 2;
        int qrHeight = inputHeight + qrOptions.quietZone * 2;
        int outputWidth = Math.max(qrOptions.width, qrWidth);
        int outputHeight = Math.max(qrOptions.height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        int FINDER_PATTERN_SIZE = 7;

        float radiusFactor = qrOptions.fluid ? 1.0f : 0.0f;
        float cornerRadiusPx = (multiple / 2f) * radiusFactor;

        Bitmap logo = qrOptions.logo;
        int logoWidth = qrOptions.width / 5;
        int logoHeight = qrOptions.height / 5;
        int logoX = (qrOptions.width - logoWidth) / 2;
        int logoY = (qrOptions.height - logoHeight) / 2;

        if (logo != null && qrOptions.clearLogoBackground && qrOptions.background == null) {
            Paint clearPaint = new Paint();
            clearPaint.setStyle(Paint.Style.FILL);
            clearPaint.setColor(qrOptions.backgroundColor);
            canvas.drawRect(logoX, logoY, logoX + logoWidth, logoY + logoHeight, clearPaint);
        }

        for (int inputY = 0; inputY < inputHeight; inputY++) {
            int outputY = topPadding + (multiple * inputY);
            for (int inputX = 0; inputX < inputWidth; inputX++) {
                int outputX = leftPadding + (multiple * inputX);
                if (input.get(inputX, inputY) == 1) {
                    boolean isInFinderPattern = (inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE);

                    boolean isInLogoArea = logo != null &&
                            outputX >= logoX && outputX < (logoX + logoWidth) - multiple &&
                            outputY >= logoY && outputY < (logoY + logoHeight) - multiple;

                    if (!isInFinderPattern && (!isInLogoArea || !qrOptions.clearLogoBackground)) {
                        if(qrOptions.fluid) {
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
                        }else{
                            canvas.drawRoundRect(new RectF(outputX, outputY, outputX + multiple, outputY + multiple), cornerRadiusPx, cornerRadiusPx, paint);
                        }
                    }
                }
            }
        }

        int patternSize = multiple * FINDER_PATTERN_SIZE;
        drawFinderPatternRoundedStyle(canvas, paint, leftPadding, topPadding, patternSize, cornerRadiusPx, qrOptions.foregroundColor);
        drawFinderPatternRoundedStyle(canvas, paint,
                leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple,
                topPadding, patternSize, cornerRadiusPx, qrOptions.foregroundColor);
        drawFinderPatternRoundedStyle(canvas, paint,
                leftPadding,
                topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
                patternSize, cornerRadiusPx, qrOptions.foregroundColor);

        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, true);
            canvas.drawBitmap(scaledLogo, logoX, logoY, null);
        }

        return bitmap;
    }



    private static void drawFinderPatternRoundedStyle(Canvas canvas, Paint paint, int x, int y, int size, float radius, int color) {
        int stroke = size / 7;
        int innerSize = size * 3 / 7;
        int innerOffset = size * 2 / 7;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);
        canvas.drawRoundRect(new RectF(x, y, x + size, y + size), radius, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(x + innerOffset, y + innerOffset,
                x + innerOffset + innerSize, y + innerOffset + innerSize), radius, radius, paint);
    }
}
