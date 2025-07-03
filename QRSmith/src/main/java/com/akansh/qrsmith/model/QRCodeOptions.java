package com.akansh.qrsmith.model;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Configuration options for generating a QR code.
 */

public class QRCodeOptions {
    private final int width;
    private final int height;
    private final int quietZone;
    private final Bitmap logo;
    private final Bitmap background;
    private final int backgroundColor;
    private final int foregroundColor;
    private final QRErrorCorrectionLevel errorCorrectionLevel;
    private final boolean clearLogoBackground;
    private final int logoPadding;
    private final QRStyles.EyeFrameShape eyeFrameShape;
    private final QRStyles.EyeBallShape eyeBallShape;
    private final Integer eyeFrameColor;
    private final Integer eyeBallColor;
    private final QRStyles.PatternStyle patternStyle;
    private final float eyeFrameStrokeMultiplier;
    private final float eyeFrameSizeScale;
    private final int[] foregroundGradientColors;
    private final int[] backgroundGradientColors;
    private final GradientOrientation foregroundGradientOrientation;
    private final GradientOrientation backgroundGradientOrientation;

    /**
     * Orientation for gradients. The first four values are used for
     * linear gradients while {@code RADIAL} creates a radial gradient.
     */
    public enum GradientOrientation {
        LEFT_RIGHT,
        TOP_BOTTOM,
        TL_BR,
        BL_TR,
        RADIAL
    }

    private QRCodeOptions(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.quietZone = builder.quietZone;
        this.logo = builder.logo;
        this.background = builder.background;
        this.backgroundColor = builder.backgroundColor;
        this.foregroundColor = builder.foregroundColor;
        this.errorCorrectionLevel = builder.errorCorrectionLevel;
        this.clearLogoBackground = builder.clearLogoBackground;
        this.logoPadding = builder.logoPadding;
        this.eyeFrameShape = builder.eyeFrameShape;
        this.eyeBallShape = builder.eyeBallShape;
        this.eyeFrameColor = builder.eyeFrameColor;
        this.eyeBallColor = builder.eyeBallColor;
        this.patternStyle = builder.patternStyle;
        this.eyeFrameStrokeMultiplier = builder.eyeFrameStrokeMultiplier;
        this.eyeFrameSizeScale = builder.eyeFrameSizeScale;
        this.foregroundGradientColors = builder.foregroundGradientColors;
        this.backgroundGradientColors = builder.backgroundGradientColors;
        this.foregroundGradientOrientation = builder.foregroundGradientOrientation;
        this.backgroundGradientOrientation = builder.backgroundGradientOrientation;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getQuietZone() { return quietZone; }
    public Bitmap getLogo() { return logo; }
    public Bitmap getBackground() { return background; }
    public int getBackgroundColor() { return backgroundColor; }
    public int getForegroundColor() { return foregroundColor; }
    public QRErrorCorrectionLevel getErrorCorrectionLevel() { return errorCorrectionLevel; }
    public boolean isClearLogoBackground() { return clearLogoBackground; }
    public int getLogoPadding() { return logoPadding; }
    public QRStyles.EyeFrameShape getEyeFrameShape() { return eyeFrameShape; }
    public QRStyles.EyeBallShape getEyeBallShape() { return eyeBallShape; }
    public Integer getEyeFrameColor() { return eyeFrameColor; }
    public Integer getEyeBallColor() { return eyeBallColor; }
    public QRStyles.PatternStyle getPatternStyle() { return patternStyle; }
    public int[] getForegroundGradientColors() { return foregroundGradientColors; }
    public int[] getBackgroundGradientColors() { return backgroundGradientColors; }
    public GradientOrientation getForegroundGradientOrientation() { return foregroundGradientOrientation; }
    public GradientOrientation getBackgroundGradientOrientation() { return backgroundGradientOrientation; }
    public float getEyeFrameStrokeMultiplier() { return eyeFrameStrokeMultiplier; }
    public float getEyeFrameSizeScale() { return eyeFrameSizeScale; }

    public static class Builder {
        private int width = 500;
        private int height = 500;
        private int quietZone = 1;
        private Bitmap logo = null;
        private Bitmap background = null;
        private int backgroundColor = Color.WHITE;
        private int foregroundColor = Color.BLACK;
        private QRErrorCorrectionLevel errorCorrectionLevel = QRErrorCorrectionLevel.H;
        private boolean clearLogoBackground = true;
        private int logoPadding = 0;
        private QRStyles.EyeFrameShape eyeFrameShape = QRStyles.EyeFrameShape.SQUARE;
        private QRStyles.EyeBallShape eyeBallShape = QRStyles.EyeBallShape.SQUARE;
        private QRStyles.PatternStyle patternStyle = QRStyles.PatternStyle.SQUARE;
        private float eyeFrameStrokeMultiplier = 1f;
        private float eyeFrameSizeScale = 1f;
        private int[] foregroundGradientColors = null;
        private int[] backgroundGradientColors = null;
        private GradientOrientation foregroundGradientOrientation = GradientOrientation.LEFT_RIGHT;
        private GradientOrientation backgroundGradientOrientation = GradientOrientation.LEFT_RIGHT;
        private Integer eyeFrameColor = null;
        private Integer eyeBallColor = null;

        public Builder() {}

        public Builder(QRCodeOptions base) {
            this.width = base.width;
            this.height = base.height;
            this.quietZone = base.quietZone;
            this.logo = base.logo;
            this.background = base.background;
            this.backgroundColor = base.backgroundColor;
            this.foregroundColor = base.foregroundColor;
            this.errorCorrectionLevel = base.errorCorrectionLevel;
            this.clearLogoBackground = base.clearLogoBackground;
            this.logoPadding = base.logoPadding;
            this.eyeFrameShape = base.eyeFrameShape;
            this.eyeBallShape = base.eyeBallShape;
            this.eyeFrameColor = base.eyeFrameColor;
            this.eyeBallColor = base.eyeBallColor;
            this.patternStyle = base.patternStyle;
            this.eyeFrameStrokeMultiplier = base.eyeFrameStrokeMultiplier;
            this.eyeFrameSizeScale = base.eyeFrameSizeScale;
            this.foregroundGradientColors = base.foregroundGradientColors;
            this.backgroundGradientColors = base.backgroundGradientColors;
            this.foregroundGradientOrientation = base.foregroundGradientOrientation;
            this.backgroundGradientOrientation = base.backgroundGradientOrientation;
        }

        public Builder setWidth(int width) { this.width = width; return this; }
        public Builder setHeight(int height) { this.height = height; return this; }
        public Builder setQuietZone(int quietZone) { this.quietZone = quietZone; return this; }
        public Builder setLogo(Bitmap logo) { this.logo = logo; return this; }
        public Builder setBackground(Bitmap background) { this.background = background; return this; }
        public Builder setBackgroundColor(int backgroundColor) { this.backgroundColor = backgroundColor; return this; }
        public Builder setForegroundColor(int foregroundColor) { this.foregroundColor = foregroundColor; return this; }
        public Builder setErrorCorrectionLevel(QRErrorCorrectionLevel level) { this.errorCorrectionLevel = level; return this; }
        public Builder setClearLogoBackground(boolean clearLogoBackground) { this.clearLogoBackground = clearLogoBackground; return this; }
        public Builder setLogoPadding(int logoPadding) { this.logoPadding = logoPadding; return this; }
        public Builder setEyeFrameShape(QRStyles.EyeFrameShape eyeFrameShape) { this.eyeFrameShape = eyeFrameShape; return this; }
        public Builder setEyeBallShape(QRStyles.EyeBallShape eyeBallShape) { this.eyeBallShape = eyeBallShape; return this; }
        public Builder setEyeFrameColor(int color) { this.eyeFrameColor = color; return this; }
        public Builder setEyeBallColor(int color) { this.eyeBallColor = color; return this; }
        public Builder setPatternStyle(QRStyles.PatternStyle patternStyle) { this.patternStyle = patternStyle; return this; }
        public Builder setEyeFrameStrokeMultiplier(float multiplier) { this.eyeFrameStrokeMultiplier = multiplier; return this; }
        public Builder setEyeFrameSizeScale(float scale) { this.eyeFrameSizeScale = scale; return this; }
        public Builder setForegroundGradient(int[] colors, GradientOrientation orientation) {
            this.foregroundGradientColors = colors;
            this.foregroundGradientOrientation = orientation;
            return this;
        }

        public Builder setBackgroundGradient(int[] colors, GradientOrientation orientation) {
            this.backgroundGradientColors = colors;
            this.backgroundGradientOrientation = orientation;
            return this;
        }
        public QRCodeOptions build() { return new QRCodeOptions(this); }
    }
}
