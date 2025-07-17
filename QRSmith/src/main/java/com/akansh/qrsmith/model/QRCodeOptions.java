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
    private final int[] foregroundGradientColors;
    private final int[] backgroundGradientColors;
    private final GradientOrientation foregroundGradientOrientation;
    private final GradientOrientation backgroundGradientOrientation;
    private final boolean maxTolerance;
    private final float toleranceMaskOpacity;
    private final float toleranceModuleSize;
    private final boolean clipBackgroundToQR;
    private final boolean bgBlur;
    private final float bgBlurRadius;

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
        this.foregroundGradientColors = builder.foregroundGradientColors;
        this.backgroundGradientColors = builder.backgroundGradientColors;
        this.foregroundGradientOrientation = builder.foregroundGradientOrientation;
        this.backgroundGradientOrientation = builder.backgroundGradientOrientation;
        this.maxTolerance = builder.maxTolerance;
        this.toleranceMaskOpacity = builder.toleranceMaskOpacity;
        this.toleranceModuleSize = builder.toleranceModuleSize;
        this.clipBackgroundToQR = builder.clipBackgroundToQR;
        this.bgBlur = builder.bgBlur;
        this.bgBlurRadius = builder.bgBlurRadius;
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
    public boolean isMaxTolerance() { return maxTolerance; }
    public float getToleranceMaskOpacity() { return toleranceMaskOpacity; }
    public float getToleranceModuleSize() { return toleranceModuleSize; }
    public boolean isClipBackgroundToQR() { return clipBackgroundToQR; }
    public boolean isBgBlur() { return bgBlur; }
    public float getBgBlurRadius() { return bgBlurRadius; }

    public static class Builder {
        // Default values
        private static final int DEFAULT_WIDTH = 500;
        private static final int DEFAULT_HEIGHT = 500;
        private static final int DEFAULT_QUIET_ZONE = 1;
        private static final int DEFAULT_LOGO_PADDING = 1;
        private static final float DEFAULT_TOLERANCE_MASK_OPACITY = 0.5f;
        private static final float DEFAULT_TOLERANCE_MODULE_SIZE = 0.4f;
        private static final float DEFAULT_BG_BLUR_RADIUS = 12f;

        // Allowed ranges
        private static final int MIN_QUIET_ZONE = 0;
        private static final int MAX_QUIET_ZONE = 20;
        private static final int MIN_LOGO_PADDING = 0;
        private static final int MAX_LOGO_PADDING = 100;
        private static final float MIN_TOLERANCE_MASK_OPACITY = 0.1f;
        private static final float MAX_TOLERANCE_MASK_OPACITY = 1f;
        private static final float MIN_TOLERANCE_MODULE_SIZE = 0.1f;
        private static final float MAX_TOLERANCE_MODULE_SIZE = 1f;
        private static final float MIN_BG_BLUR_RADIUS = 1f;
        private static final float MAX_BG_BLUR_RADIUS = 25f;

        private int width = DEFAULT_WIDTH;
        private int height = DEFAULT_HEIGHT;
        private int quietZone = DEFAULT_QUIET_ZONE; // min: 0, max: 20
        private Bitmap logo = null;
        private Bitmap background = null;
        private int backgroundColor = Color.WHITE;
        private int foregroundColor = Color.BLACK;
        private QRErrorCorrectionLevel errorCorrectionLevel = QRErrorCorrectionLevel.H;
        private boolean clearLogoBackground = true;
        private int logoPadding = DEFAULT_LOGO_PADDING; // min: 0, max: 100
        private QRStyles.EyeFrameShape eyeFrameShape = QRStyles.EyeFrameShape.SQUARE;
        private QRStyles.EyeBallShape eyeBallShape = QRStyles.EyeBallShape.SQUARE;
        private QRStyles.PatternStyle patternStyle = QRStyles.PatternStyle.SQUARE;
        private int[] foregroundGradientColors = null;
        private int[] backgroundGradientColors = null;
        private GradientOrientation foregroundGradientOrientation = GradientOrientation.LEFT_RIGHT;
        private GradientOrientation backgroundGradientOrientation = GradientOrientation.LEFT_RIGHT;
        private Integer eyeFrameColor = null;
        private Integer eyeBallColor = null;
        private boolean maxTolerance = false;
        private float toleranceMaskOpacity = DEFAULT_TOLERANCE_MASK_OPACITY; // min: 0.1f, max: 1f
        private float toleranceModuleSize = DEFAULT_TOLERANCE_MODULE_SIZE; // min: 0.1f, max: 1f
        private boolean clipBackgroundToQR = false;
        private boolean bgBlur = false;
        private float bgBlurRadius = DEFAULT_BG_BLUR_RADIUS; // min: 1f, max: 25f

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
            this.foregroundGradientColors = base.foregroundGradientColors;
            this.backgroundGradientColors = base.backgroundGradientColors;
            this.foregroundGradientOrientation = base.foregroundGradientOrientation;
            this.backgroundGradientOrientation = base.backgroundGradientOrientation;
            this.maxTolerance = base.maxTolerance;
            this.toleranceMaskOpacity = base.toleranceMaskOpacity;
            this.toleranceModuleSize = base.toleranceModuleSize;
            this.clipBackgroundToQR = base.clipBackgroundToQR;
            this.bgBlur = base.bgBlur;
            this.bgBlurRadius = base.bgBlurRadius;
        }

        public Builder setWidth(int width) { this.width = width; return this; }
        public Builder setHeight(int height) { this.height = height; return this; }
        public Builder setQuietZone(int quietZone) {
            if (quietZone < MIN_QUIET_ZONE || quietZone > MAX_QUIET_ZONE) {
                this.quietZone = DEFAULT_QUIET_ZONE;
            } else {
                this.quietZone = quietZone;
            }
            return this;
        }
        public Builder setLogo(Bitmap logo) { this.logo = logo; return this; }
        public Builder setBackground(Bitmap background) { this.background = background; return this; }
        public Builder setBackgroundColor(int backgroundColor) { this.backgroundColor = backgroundColor; return this; }
        public Builder setForegroundColor(int foregroundColor) { this.foregroundColor = foregroundColor; return this; }
        public Builder setErrorCorrectionLevel(QRErrorCorrectionLevel level) { this.errorCorrectionLevel = level; return this; }
        public Builder setClearLogoBackground(boolean clearLogoBackground) { this.clearLogoBackground = clearLogoBackground; return this; }
        public Builder setLogoPadding(int logoPadding) {
            if (logoPadding < MIN_LOGO_PADDING || logoPadding > MAX_LOGO_PADDING) {
                this.logoPadding = DEFAULT_LOGO_PADDING;
            } else {
                this.logoPadding = logoPadding;
            }
            return this;
        }
        public Builder setEyeFrameShape(QRStyles.EyeFrameShape eyeFrameShape) { this.eyeFrameShape = eyeFrameShape; return this; }
        public Builder setEyeBallShape(QRStyles.EyeBallShape eyeBallShape) { this.eyeBallShape = eyeBallShape; return this; }
        public Builder setEyeFrameColor(int color) { this.eyeFrameColor = color; return this; }
        public Builder setEyeBallColor(int color) { this.eyeBallColor = color; return this; }
        public Builder setPatternStyle(QRStyles.PatternStyle patternStyle) { this.patternStyle = patternStyle; return this; }
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

        public Builder setMaxTolerance(boolean maxTolerance) { this.maxTolerance = maxTolerance; return this; }
        public Builder setToleranceMaskOpacity(float opacity) {
            if (opacity < MIN_TOLERANCE_MASK_OPACITY || opacity > MAX_TOLERANCE_MASK_OPACITY) {
                this.toleranceMaskOpacity = DEFAULT_TOLERANCE_MASK_OPACITY;
            } else {
                this.toleranceMaskOpacity = opacity;
            }
            return this;
        }
        public Builder setToleranceModuleSize(float size) {
            if (size < MIN_TOLERANCE_MODULE_SIZE || size > MAX_TOLERANCE_MODULE_SIZE) {
                this.toleranceModuleSize = DEFAULT_TOLERANCE_MODULE_SIZE;
            } else {
                this.toleranceModuleSize = size;
            }
            return this;
        }

        public Builder setBgBlur(boolean bgBlur) { this.bgBlur = bgBlur; return this; }
        public Builder setBgBlurRadius(float radius) {
            if (radius < MIN_BG_BLUR_RADIUS || radius > MAX_BG_BLUR_RADIUS) {
                this.bgBlurRadius = DEFAULT_BG_BLUR_RADIUS;
            } else {
                this.bgBlurRadius = radius;
            }
            return this;
        }

        public Builder setClipBackgroundToQR(boolean clipBackgroundToQR) { this.clipBackgroundToQR = clipBackgroundToQR; return this; }

        private void validate() {
            if (quietZone < MIN_QUIET_ZONE || quietZone > MAX_QUIET_ZONE) {
                quietZone = DEFAULT_QUIET_ZONE;
            }
            if (logoPadding < MIN_LOGO_PADDING || logoPadding > MAX_LOGO_PADDING) {
                logoPadding = DEFAULT_LOGO_PADDING;
            }
            if (toleranceMaskOpacity < MIN_TOLERANCE_MASK_OPACITY || toleranceMaskOpacity > MAX_TOLERANCE_MASK_OPACITY) {
                toleranceMaskOpacity = DEFAULT_TOLERANCE_MASK_OPACITY;
            }
            if (toleranceModuleSize < MIN_TOLERANCE_MODULE_SIZE || toleranceModuleSize > MAX_TOLERANCE_MODULE_SIZE) {
                toleranceModuleSize = DEFAULT_TOLERANCE_MODULE_SIZE;
            }
            if (bgBlurRadius < MIN_BG_BLUR_RADIUS || bgBlurRadius > MAX_BG_BLUR_RADIUS) {
                bgBlurRadius = DEFAULT_BG_BLUR_RADIUS;
            }
        }

        public QRCodeOptions build() {
            validate();
            return new QRCodeOptions(this);
        }
    }
}
