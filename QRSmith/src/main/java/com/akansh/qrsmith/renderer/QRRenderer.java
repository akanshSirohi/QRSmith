package com.akansh.qrsmith.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;

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
            if (qrOptions.getBackgroundGradientColors() != null) {
                bgPaint.setShader(buildGradient(
                        qrOptions.getBackgroundGradientColors(),
                        qrOptions.getBackgroundGradientOrientation(),
                        qrOptions.getWidth(),
                        qrOptions.getHeight()));
            } else {
                bgPaint.setColor(qrOptions.getBackgroundColor());
            }
            canvas.drawRect(0, 0, qrOptions.getWidth(), qrOptions.getHeight(), bgPaint);
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        if (qrOptions.getForegroundGradientColors() != null) {
            paint.setShader(buildGradient(
                    qrOptions.getForegroundGradientColors(),
                    qrOptions.getForegroundGradientOrientation(),
                    qrOptions.getWidth(),
                    qrOptions.getHeight()));
        } else {
            paint.setColor(qrOptions.getForegroundColor());
        }

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
                        drawDataPattern(qrOptions.getPatternStyle(), canvas, paint, inputX, inputY, input, inputWidth, inputHeight, outputX, outputY, multiple);
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

        // Prepare solid paints for eyes
        Paint framePaint = new Paint(paint);
        framePaint.setShader(null);
        int frameColor = qrOptions.getEyeFrameColor() != null ?
                qrOptions.getEyeFrameColor() : qrOptions.getForegroundColor();
        framePaint.setColor(frameColor);

        Paint ballPaint = new Paint(paint);
        ballPaint.setShader(null);
        int ballColor = qrOptions.getEyeBallColor() != null ?
                qrOptions.getEyeBallColor() : qrOptions.getForegroundColor();
        ballPaint.setColor(ballColor);

        // Finder frame renderer
        drawEyeFrame(qrOptions.getEyeFrameShape(), canvas, framePaint, EyeAlignmentX, EyeAlignmentY, EyeAlignmentZ, patternSize, multiple, frameColor);

        // Finder ball renderer
        drawEyeBall(qrOptions.getEyeBallShape(), canvas, ballPaint, EyeAlignmentX, EyeAlignmentY, EyeAlignmentZ, patternSize, multiple, ballColor);

        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, true);
            canvas.drawBitmap(scaledLogo, logoX, logoY, null);
        }

        return bitmap;
    }

    private void drawDataPattern(QRStyles.PatternStyle style, Canvas canvas, Paint paint, int inputX, int inputY, ByteMatrix input, int inputWidth, int inputHeight, int outputX, int outputY, int multiple) {
        switch (style) {
            case SQUARE:
                qrDataPatternRenderer.drawNormalStyle(canvas, paint, outputX, outputY, multiple);
                break;
            case FLUID:
                qrDataPatternRenderer.drawFluidStyle(canvas, paint, inputX, inputY, input, inputWidth, inputHeight, outputX, outputY, multiple);
                break;
            case L_DOT:
                qrDataPatternRenderer.drawBigDotStyle(canvas, paint, outputX, outputY, multiple);
                break;
            case S_DOT:
                qrDataPatternRenderer.drawSmallDotStyle(canvas, paint, outputX, outputY, multiple);
                break;
            case HEXAGON:
                qrDataPatternRenderer.drawHexStyle(canvas, paint, outputX, outputY, multiple);
                break;
            case X_AXIS_FLUID:
                qrDataPatternRenderer.drawAxisFluidStyle(canvas, paint, inputX, inputY, input, inputWidth, inputHeight, outputX, outputY, multiple, 0);
                break;
            case Y_AXIS_FLUID:
                qrDataPatternRenderer.drawAxisFluidStyle(canvas, paint, inputX, inputY, input, inputWidth, inputHeight, outputX, outputY, multiple, 1);
                break;
            case DIAMOND:
                qrDataPatternRenderer.drawDiamondStyle(canvas, paint, outputX, outputY, multiple);
                break;
            case STAR:
                qrDataPatternRenderer.drawStarStyle(canvas, paint, outputX, outputY, multiple);
                break;
            case HEART:
                qrDataPatternRenderer.drawHeartStyle(canvas, paint, outputX, outputY, multiple);
                break;
        }
    }

    private void drawEyeFrame(QRStyles.EyeFrameShape shape, Canvas canvas, Paint paint, int[] EyeAlignmentX, int[] EyeAlignmentY, int[] EyeAlignmentZ, int patternSize, int multiple, int color) {
        switch (shape) {
            case SQUARE:
                qrFinderFrameRenderer.drawSquaredStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color);
                qrFinderFrameRenderer.drawSquaredStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color);
                qrFinderFrameRenderer.drawSquaredStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color);
                break;
            case ROUND_SQUARE:
                qrFinderFrameRenderer.drawRoundedSquaredStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color);
                qrFinderFrameRenderer.drawRoundedSquaredStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color);
                qrFinderFrameRenderer.drawRoundedSquaredStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color);
                break;
            case HEXAGON:
                qrFinderFrameRenderer.drawHexStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color);
                qrFinderFrameRenderer.drawHexStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color);
                qrFinderFrameRenderer.drawHexStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color);
                break;
            case CIRCLE:
                qrFinderFrameRenderer.drawCircleStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color);
                qrFinderFrameRenderer.drawCircleStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color);
                qrFinderFrameRenderer.drawCircleStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color);
                break;
            case ONE_SHARP_CORNER:
                qrFinderFrameRenderer.drawOneSharpCornerStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderFrameRenderer.drawOneSharpCornerStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderFrameRenderer.drawOneSharpCornerStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case TECH_EYE:
                qrFinderFrameRenderer.drawTechEyeStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderFrameRenderer.drawTechEyeStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderFrameRenderer.drawTechEyeStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case SOFT_ROUNDED:
                qrFinderFrameRenderer.drawSoftRoundedStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderFrameRenderer.drawSoftRoundedStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderFrameRenderer.drawSoftRoundedStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case PINCHED_SQUIRCLE:
                qrFinderFrameRenderer.drawPinchedSquircleStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderFrameRenderer.drawPinchedSquircleStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderFrameRenderer.drawPinchedSquircleStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case BLOB_CORNER:
                qrFinderFrameRenderer.drawBlobCornerStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderFrameRenderer.drawBlobCornerStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderFrameRenderer.drawBlobCornerStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case CORNER_WARP:
                qrFinderFrameRenderer.drawCornerWarpStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderFrameRenderer.drawCornerWarpStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderFrameRenderer.drawCornerWarpStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
        }
    }

    private void drawEyeBall(QRStyles.EyeBallShape shape, Canvas canvas, Paint paint, int[] EyeAlignmentX, int[] EyeAlignmentY, int[] EyeAlignmentZ, int patternSize, int multiple, int color) {
        switch (shape) {
            case SQUARE:
                qrFinderBallRenderer.drawSquaredStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color);
                qrFinderBallRenderer.drawSquaredStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color);
                qrFinderBallRenderer.drawSquaredStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color);
                break;
            case ROUND_SQUARE:
                qrFinderBallRenderer.drawRoundedSquaredStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color);
                qrFinderBallRenderer.drawRoundedSquaredStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color);
                qrFinderBallRenderer.drawRoundedSquaredStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color);
                break;
            case HEXAGON:
                qrFinderBallRenderer.drawHexStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, color);
                qrFinderBallRenderer.drawHexStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, color);
                qrFinderBallRenderer.drawHexStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, color);
                break;
            case CIRCLE:
                qrFinderBallRenderer.drawCircleStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color);
                qrFinderBallRenderer.drawCircleStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color);
                qrFinderBallRenderer.drawCircleStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color);
                break;
            case ONE_SHARP_CORNER:
                qrFinderBallRenderer.drawOneSharpCornerStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawOneSharpCornerStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawOneSharpCornerStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case TECH_EYE:
                qrFinderBallRenderer.drawTechEyeStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color,  CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawTechEyeStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawTechEyeStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case SOFT_ROUNDED:
                qrFinderBallRenderer.drawSoftRoundedStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawSoftRoundedStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawSoftRoundedStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case PINCHED_SQUIRCLE:
                qrFinderBallRenderer.drawPinchedSquircleStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawPinchedSquircleStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawPinchedSquircleStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case BLOB_CORNER:
                qrFinderBallRenderer.drawBlobCornerStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawBlobCornerStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawBlobCornerStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case CORNER_WARP:
                qrFinderBallRenderer.drawCornerWarpStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawCornerWarpStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawCornerWarpStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case PILL_STACK_H:
                qrFinderBallRenderer.drawPillStackHStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawPillStackHStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawPillStackHStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case PILL_STACK_V:
                qrFinderBallRenderer.drawPillStackVStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawPillStackVStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawPillStackVStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case INCURVE:
                qrFinderBallRenderer.drawIncurveStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawIncurveStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawIncurveStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case CHISEL:
                qrFinderBallRenderer.drawChiselStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawChiselStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawChiselStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
            case HEART:
                qrFinderBallRenderer.drawHeartStyle(canvas, paint, EyeAlignmentX[0], EyeAlignmentX[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_LEFT);
                qrFinderBallRenderer.drawHeartStyle(canvas, paint, EyeAlignmentY[0], EyeAlignmentY[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.TOP_RIGHT);
                qrFinderBallRenderer.drawHeartStyle(canvas, paint, EyeAlignmentZ[0], EyeAlignmentZ[1], patternSize, multiple, color, CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
                break;
        }
    }

    private Shader buildGradient(int[] colors, QRCodeOptions.GradientOrientation orientation, int width, int height) {
        if (orientation == QRCodeOptions.GradientOrientation.RADIAL) {
            float cx = width / 2f;
            float cy = height / 2f;
            float radius = Math.max(width, height) / 2f;
            return new RadialGradient(cx, cy, radius, colors, null, Shader.TileMode.CLAMP);
        }

        float startX = 0, startY = 0, endX = width, endY = height;
        switch (orientation) {
            case TOP_BOTTOM:
                endX = 0;
                break;
            case LEFT_RIGHT:
                endY = 0;
                break;
            case TL_BR:
                break;
            case BL_TR:
                startY = height;
                endY = 0;
                break;
            default:
                break;
        }
        return new LinearGradient(startX, startY, endX, endY, colors, null, Shader.TileMode.CLAMP);
    }
}
