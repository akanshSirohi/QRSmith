package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Color;

public class QRCodeOptions {
    public int width = 500;
    public int height = 500;
    public int quietZone = 1;
    public Bitmap logo = null;
    public Bitmap background = null;
    public int backgroundColor = Color.WHITE;
    public int foregroundColor = Color.BLACK;
    public QRSmith.QRErrorCorrectionLevel errorCorrectionLevel = QRSmith.QRErrorCorrectionLevel.H; // Default error correction level
    public QRSmith.QRCodeStyle style = QRSmith.QRCodeStyle.SQUARED;
    public float dotSizeFactor = 0.8f;
    public boolean clearLogoBackground = true;
    public int logoPadding = 0;

    /**
     * Radius for rounding the corners of modules when using the
     * {@link QRSmith.QRCodeStyle#SQUARED} style. The value should be in the
     * range {@code 0} (sharp corners) to {@code 10} (fully rounded).
     */
    public int radius = 0;
}
