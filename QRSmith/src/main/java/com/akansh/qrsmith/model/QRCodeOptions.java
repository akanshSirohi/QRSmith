package com.akansh.qrsmith.model;

import android.graphics.Bitmap;
import android.graphics.Color;

public class QRCodeOptions {
    private final int width;
    private final int height;
    private final int quietZone;
    private final Bitmap logo;
    private final Bitmap background;
    private final int backgroundColor;
    private final int foregroundColor;
    private final QRErrorCorrectionLevel errorCorrectionLevel;
    private final float dotSizeFactor;
    private final boolean clearLogoBackground;
    private final int logoPadding;
    private final QRStyles.EyeShape eyeShape;
    private final QRStyles.PatternStyle patternStyle;

    private QRCodeOptions(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.quietZone = builder.quietZone;
        this.logo = builder.logo;
        this.background = builder.background;
        this.backgroundColor = builder.backgroundColor;
        this.foregroundColor = builder.foregroundColor;
        this.errorCorrectionLevel = builder.errorCorrectionLevel;
        this.dotSizeFactor = builder.dotSizeFactor;
        this.clearLogoBackground = builder.clearLogoBackground;
        this.logoPadding = builder.logoPadding;
        this.eyeShape = builder.eyeShape;
        this.patternStyle = builder.patternStyle;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getQuietZone() { return quietZone; }
    public Bitmap getLogo() { return logo; }
    public Bitmap getBackground() { return background; }
    public int getBackgroundColor() { return backgroundColor; }
    public int getForegroundColor() { return foregroundColor; }
    public QRErrorCorrectionLevel getErrorCorrectionLevel() { return errorCorrectionLevel; }
    public float getDotSizeFactor() { return dotSizeFactor; }
    public boolean isClearLogoBackground() { return clearLogoBackground; }
    public int getLogoPadding() { return logoPadding; }
    public QRStyles.EyeShape getEyeShape() { return eyeShape; }
    public QRStyles.PatternStyle getPatternStyle() { return patternStyle; }

    public static class Builder {
        private int width = 500;
        private int height = 500;
        private int quietZone = 1;
        private Bitmap logo = null;
        private Bitmap background = null;
        private int backgroundColor = Color.WHITE;
        private int foregroundColor = Color.BLACK;
        private QRErrorCorrectionLevel errorCorrectionLevel = QRErrorCorrectionLevel.H;
        private float dotSizeFactor = 0.8f;
        private boolean clearLogoBackground = true;
        private int logoPadding = 0;
        private QRStyles.EyeShape eyeShape = QRStyles.EyeShape.Squared;
        private QRStyles.PatternStyle patternStyle = QRStyles.PatternStyle.Squared;

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
            this.dotSizeFactor = base.dotSizeFactor;
            this.clearLogoBackground = base.clearLogoBackground;
            this.logoPadding = base.logoPadding;
            this.eyeShape = base.eyeShape;
            this.patternStyle = base.patternStyle;
        }

        public Builder setWidth(int width) { this.width = width; return this; }
        public Builder setHeight(int height) { this.height = height; return this; }
        public Builder setQuietZone(int quietZone) { this.quietZone = quietZone; return this; }
        public Builder setLogo(Bitmap logo) { this.logo = logo; return this; }
        public Builder setBackground(Bitmap background) { this.background = background; return this; }
        public Builder setBackgroundColor(int backgroundColor) { this.backgroundColor = backgroundColor; return this; }
        public Builder setForegroundColor(int foregroundColor) { this.foregroundColor = foregroundColor; return this; }
        public Builder setErrorCorrectionLevel(QRErrorCorrectionLevel level) { this.errorCorrectionLevel = level; return this; }
        public Builder setDotSizeFactor(float dotSizeFactor) { this.dotSizeFactor = dotSizeFactor; return this; }
        public Builder setClearLogoBackground(boolean clearLogoBackground) { this.clearLogoBackground = clearLogoBackground; return this; }
        public Builder setLogoPadding(int logoPadding) { this.logoPadding = logoPadding; return this; }
        public Builder setEyeShape(QRStyles.EyeShape eyeShape) { this.eyeShape = eyeShape; return this; }
        public Builder setPatternStyle(QRStyles.PatternStyle patternStyle) { this.patternStyle = patternStyle; return this; }
        public QRCodeOptions build() { return new QRCodeOptions(this); }
    }
}
