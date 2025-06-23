package com.akansh.qrsmith.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import com.akansh.qrsmith.model.QRCodeOptions;
import com.akansh.qrsmith.model.QRStyles;

import java.util.HashMap;
import java.util.Map;

public class QRRenderer {
    QRDataPatternRenderer qrDataPatternRenderer = new QRDataPatternRenderer();
    QRFinderFrameRenderer qrFinderFrameRenderer = new QRFinderFrameRenderer();
    QRFinderBallRenderer qrFinderBallRenderer = new QRFinderBallRenderer();

    public Bitmap renderQRImage(String content, QRCodeOptions qrOptions, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCode qrCode = Encoder.encode(content, errorCorrectionLevel, hints);

        Bitmap bitmap = Bitmap.createBitmap(qrOptions.getWidth(), qrOptions.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if (qrOptions.getBackground() != null) {
            Bitmap backgroundBitmap = Bitmap.createScaledBitmap(qrOptions.getBackground(), qrOptions.getWidth(), qrOptions.getHeight(), true);
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        } else {
            Paint bgPaint = new Paint();
            bgPaint.setStyle(Paint.Style.FILL);
            bgPaint.setColor(qrOptions.getBackgroundColor());
            canvas.drawRect(0, 0, qrOptions.getWidth(), qrOptions.getHeight(), bgPaint);
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(qrOptions.getForegroundColor());

        ByteMatrix input = qrCode.getMatrix();
        if (input == null) throw new IllegalStateException();

        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + qrOptions.getQuietZone() * 2;
        int qrHeight = inputHeight + qrOptions.getQuietZone() * 2;
        int outputWidth = Math.max(qrOptions.getWidth(), qrWidth);
        int outputHeight = Math.max(qrOptions.getHeight(), qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        int FINDER_PATTERN_SIZE = 7;

        Bitmap logo = qrOptions.getLogo();
        int logoWidth = qrOptions.getWidth() / 5;
        int logoHeight = qrOptions.getHeight() / 5;
        int logoX = (qrOptions.getWidth() - logoWidth) / 2;
        int logoY = (qrOptions.getHeight() - logoHeight) / 2;

        if (logo != null && qrOptions.isClearLogoBackground() && qrOptions.getBackground() == null) {
            Paint clearPaint = new Paint();
            clearPaint.setStyle(Paint.Style.FILL);
            clearPaint.setColor(qrOptions.getBackgroundColor());
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

                    if (!isInFinderPattern && (!isInLogoArea || !qrOptions.isClearLogoBackground())) {
                        if(qrOptions.getPatternStyle() == QRStyles.PatternStyle.SQUARE) {
                            qrDataPatternRenderer.drawNormalStyle(canvas, paint, outputX, outputY, multiple);
                        }else if(qrOptions.getPatternStyle() == QRStyles.PatternStyle.FLUID) {
                            qrDataPatternRenderer.drawFluidStyle(canvas, paint, inputX, inputY, input, inputWidth, inputHeight, outputX, outputY, multiple);
                        }else if(qrOptions.getPatternStyle() == QRStyles.PatternStyle.DOTTED) {
                            qrDataPatternRenderer.drawDottedStyle(canvas, paint, outputX, outputY, multiple);
                        }else if(qrOptions.getPatternStyle() == QRStyles.PatternStyle.HEXAGON) {
                            qrDataPatternRenderer.drawHexStyle(canvas, paint, outputX, outputY, multiple);
                        }
                    }
                }
            }
        }

        int patternSize = multiple * FINDER_PATTERN_SIZE;

        // Eye Alignment Config
        int[] EyeAlignmentX = {
                leftPadding, topPadding
        };
        int[] EyeAlignmentY = {
            leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding
        };
        int[] EyeAlignmentZ = {
            leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple
        };

        // Finder frame renderer
        drawEyeFrame(qrOptions.getEyeFrameShape(), canvas, paint, EyeAlignmentX, EyeAlignmentY, EyeAlignmentZ, patternSize, multiple, qrOptions.getForegroundColor());

        // Finder ball renderer
        drawEyeBall(qrOptions.getEyeBallShape(), canvas, paint, EyeAlignmentX, EyeAlignmentY, EyeAlignmentZ, patternSize, multiple, qrOptions.getForegroundColor());

        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, true);
            canvas.drawBitmap(scaledLogo, logoX, logoY, null);
        }

        return bitmap;
    }

    private void drawEyeFrame(QRStyles.EyeFrameShape shape, Canvas canvas, Paint paint, int[] EyeAlignmentX, int[] EyeAlignmentY, int[] EyeAlignmentZ, int patternSize, int multiple, int color) {
        shape.render(qrFinderFrameRenderer, canvas, paint, EyeAlignmentX, EyeAlignmentY, EyeAlignmentZ, patternSize, multiple, color);
    }

    private void drawEyeBall(QRStyles.EyeBallShape shape, Canvas canvas, Paint paint, int[] EyeAlignmentX, int[] EyeAlignmentY, int[] EyeAlignmentZ, int patternSize, int multiple, int color) {
        shape.render(qrFinderBallRenderer, canvas, paint, EyeAlignmentX, EyeAlignmentY, EyeAlignmentZ, patternSize, multiple, color);
    }
}
