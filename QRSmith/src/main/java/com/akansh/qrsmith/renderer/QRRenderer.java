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

        // Finder frame renderer
        if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.SQUARE) {
            qrFinderFrameRenderer.drawSquaredStyle(canvas, paint, leftPadding, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawSquaredStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawSquaredStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.ROUND_SQUARE) {
            qrFinderFrameRenderer.drawRoundedSquaredStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawRoundedSquaredStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawRoundedSquaredStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.HEXAGON) {
            qrFinderFrameRenderer.drawHexStyle(canvas, paint, leftPadding, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawHexStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawHexStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.CIRCLE) {
            qrFinderFrameRenderer.drawCircleStyle(canvas, paint, leftPadding, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawCircleStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderFrameRenderer.drawCircleStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.ONE_SHARP_CORNER) {
            qrFinderFrameRenderer.drawOneSharpCornerStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderFrameRenderer.drawOneSharpCornerStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderFrameRenderer.drawOneSharpCornerStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.TECH_EYE) {
            qrFinderFrameRenderer.drawTechEyeStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderFrameRenderer.drawTechEyeStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderFrameRenderer.drawTechEyeStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.SOFT_ROUNDED) {
            qrFinderFrameRenderer.drawSoftRoundedStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderFrameRenderer.drawSoftRoundedStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderFrameRenderer.drawSoftRoundedStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.PINCHED_SQUIRCLE) {
            qrFinderFrameRenderer.drawPinchedSquircleStyle(canvas, paint, leftPadding, topPadding, patternSize,  qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderFrameRenderer.drawPinchedSquircleStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor(),CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderFrameRenderer.drawPinchedSquircleStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.BLOB_CORNER) {
            qrFinderFrameRenderer.drawBlobCornerStyle(canvas, paint, leftPadding, topPadding, patternSize,  qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderFrameRenderer.drawBlobCornerStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor(),CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderFrameRenderer.drawBlobCornerStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeFrameShape() == QRStyles.EyeFrameShape.CORNER_WARP) {
            qrFinderFrameRenderer.drawCornerWarpStyle(canvas, paint, leftPadding, topPadding, patternSize,  qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderFrameRenderer.drawCornerWarpStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor(),CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderFrameRenderer.drawCornerWarpStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }

        // Finder ball renderer
        if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.SQUARE) {
            qrFinderBallRenderer.drawSquaredStyle(canvas, paint, leftPadding, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawSquaredStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawSquaredStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.ROUND_SQUARE) {
            qrFinderBallRenderer.drawRoundedSquaredStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawRoundedSquaredStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawRoundedSquaredStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.HEXAGON) {
            qrFinderBallRenderer.drawHexStyle(canvas, paint, leftPadding, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawHexStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawHexStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.CIRCLE) {
            qrFinderBallRenderer.drawCircleStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawCircleStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor());
            qrFinderBallRenderer.drawCircleStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor());
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.ONE_SHARP_CORNER) {
            qrFinderBallRenderer.drawOneSharpCornerStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawOneSharpCornerStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawOneSharpCornerStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.TECH_EYE) {
            qrFinderBallRenderer.drawTechEyeStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawTechEyeStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawTechEyeStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.SOFT_ROUNDED) {
            qrFinderBallRenderer.drawSoftRoundedStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawSoftRoundedStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawSoftRoundedStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.PINCHED_SQUIRCLE) {
            qrFinderBallRenderer.drawPinchedSquircleStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawPinchedSquircleStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawPinchedSquircleStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.BLOB_CORNER) {
            qrFinderBallRenderer.drawBlobCornerStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawBlobCornerStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawBlobCornerStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.CORNER_WARP) {
            qrFinderBallRenderer.drawCornerWarpStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawCornerWarpStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawCornerWarpStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.PILL_STACK_H) {
            qrFinderBallRenderer.drawPillStackHStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawPillStackHStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawPillStackHStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.PILL_STACK_V) {
            qrFinderBallRenderer.drawPillStackVStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawPillStackVStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawPillStackVStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.INCURVE) {
            qrFinderBallRenderer.drawIncurveStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawIncurveStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawIncurveStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }else if(qrOptions.getEyeBallShape() == QRStyles.EyeBallShape.CHISEL) {
            qrFinderBallRenderer.drawChiselStyle(canvas, paint, leftPadding, topPadding, patternSize, multiple, qrOptions.getForegroundColor(),  CommonShapeUtils.CornerPosition.TOP_LEFT);
            qrFinderBallRenderer.drawChiselStyle(canvas, paint, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.TOP_RIGHT);
            qrFinderBallRenderer.drawChiselStyle(canvas, paint, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, patternSize, multiple, qrOptions.getForegroundColor(), CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
        }


        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, true);
            canvas.drawBitmap(scaledLogo, logoX, logoY, null);
        }

        return bitmap;
    }
}
