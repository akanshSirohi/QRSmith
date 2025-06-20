package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.HashMap;
import java.util.Map;

public class CustomQR {

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
                        if(qrOptions.customPatternStyle == QRStyles.CustomPatternStyle.Squared) {
                            CustomPattern.drawInnerPatternNormalStyle(canvas, paint, outputX, outputY, multiple);
                        }else if(qrOptions.customPatternStyle == QRStyles.CustomPatternStyle.Fluid) {
                            CustomPattern.drawInnerPatternFluidStyle(canvas, paint, inputX, inputY, input, inputWidth, inputHeight, outputX, outputY, multiple);
                        }
                    }
                }
            }
        }

        int patternSize = multiple * FINDER_PATTERN_SIZE;

        if(qrOptions.customEyeShape == QRStyles.CustomEyeShape.Squared) {
            CustomEyes.drawFinderPatternSquaredStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.foregroundColor);
            CustomEyes.drawFinderPatternSquaredStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.foregroundColor);
            CustomEyes.drawFinderPatternSquaredStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.foregroundColor);
        }else if(qrOptions.customEyeShape == QRStyles.CustomEyeShape.RoundSquared) {
            CustomEyes.drawFinderPatternRoundedStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.foregroundColor);
            CustomEyes.drawFinderPatternRoundedStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.foregroundColor);
            CustomEyes.drawFinderPatternRoundedStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.foregroundColor);
        }else if(qrOptions.customEyeShape == QRStyles.CustomEyeShape.Hexagonal) {
            CustomEyes.drawFinderPatternHexStyle(canvas, paint, leftPadding, topPadding, patternSize, qrOptions.foregroundColor);
            CustomEyes.drawFinderPatternHexStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.foregroundColor);
            CustomEyes.drawFinderPatternHexStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.foregroundColor);
        }


        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, true);
            canvas.drawBitmap(scaledLogo, logoX, logoY, null);
        }

        return bitmap;
    }



}
