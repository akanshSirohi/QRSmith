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
    public boolean fluid = false;

    public QRStyles.CustomEyeShape customEyeShape = QRStyles.CustomEyeShape.Squared;
    public QRStyles.CustomPatternStyle customPatternStyle = QRStyles.CustomPatternStyle.Squared;
}
