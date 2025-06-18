package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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

                        boolean left = isModuleFilled(input, inputX - 1, inputY, logo, logoX, logoY, logoWidth, logoHeight,
                                qrOptions.clearLogoBackground, FINDER_PATTERN_SIZE, inputWidth, inputHeight, multiple, leftPadding, topPadding);
                        boolean right = isModuleFilled(input, inputX + 1, inputY, logo, logoX, logoY, logoWidth, logoHeight,
                                qrOptions.clearLogoBackground, FINDER_PATTERN_SIZE, inputWidth, inputHeight, multiple, leftPadding, topPadding);
                        boolean top = isModuleFilled(input, inputX, inputY - 1, logo, logoX, logoY, logoWidth, logoHeight,
                                qrOptions.clearLogoBackground, FINDER_PATTERN_SIZE, inputWidth, inputHeight, multiple, leftPadding, topPadding);
                        boolean bottom = isModuleFilled(input, inputX, inputY + 1, logo, logoX, logoY, logoWidth, logoHeight,
                                qrOptions.clearLogoBackground, FINDER_PATTERN_SIZE, inputWidth, inputHeight, multiple, leftPadding, topPadding);

                        drawFluidModule(canvas, paint, outputX, outputY, multiple, cornerRadiusPx,
                                left, top, right, bottom);


    private static boolean isModuleFilled(ByteMatrix matrix, int x, int y, Bitmap logo,
                                          int logoX, int logoY, int logoW, int logoH,
                                          boolean clearLogo, int finderSize,
                                          int width, int height, int multiple, int leftPadding, int topPadding) {
        if (x < 0 || y < 0 || x >= width || y >= height) return false;
        if (matrix.get(x, y) != 1) return false;
        boolean inFinder = (x <= finderSize && y <= finderSize) ||
                (x >= width - finderSize && y <= finderSize) ||
                (x <= finderSize && y >= height - finderSize);
        if (inFinder) return false;

        if (logo != null && clearLogo) {
            int outX = leftPadding + (x * multiple);
            int outY = topPadding + (y * multiple);
            boolean inLogo = outX >= logoX && outX < (logoX + logoW) - multiple &&
                    outY >= logoY && outY < (logoY + logoH) - multiple;
            if (inLogo) return false;
        }
        return true;
    }

    private static void drawFluidModule(Canvas canvas, Paint paint, float x, float y, float size,
                                        float radius, boolean left, boolean top,
                                        boolean right, boolean bottom) {
        float tl = (!top && !left) ? radius : 0f;
        float tr = (!top && !right) ? radius : 0f;
        float br = (!bottom && !right) ? radius : 0f;
        float bl = (!bottom && !left) ? radius : 0f;

        Path path = new Path();
        float[] radii = {tl, tl, tr, tr, br, br, bl, bl};
        RectF r = new RectF(x, y, x + size, y + size);
        path.addRoundRect(r, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }
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
                        canvas.drawRoundRect(new RectF(outputX, outputY, outputX + multiple, outputY + multiple),
                                cornerRadiusPx, cornerRadiusPx, paint);
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
