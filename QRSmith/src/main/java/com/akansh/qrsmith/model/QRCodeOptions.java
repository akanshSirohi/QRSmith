package com.akansh.qrsmith.model;

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
    public QRErrorCorrectionLevel errorCorrectionLevel = QRErrorCorrectionLevel.H; // Default error correction level
    public float dotSizeFactor = 0.8f;
    public boolean clearLogoBackground = true;
    public int logoPadding = 0;

    public QRStyles.EyeShape eyeShape = QRStyles.EyeShape.Squared;
    public QRStyles.PatternStyle patternStyle = QRStyles.PatternStyle.Squared;
}
