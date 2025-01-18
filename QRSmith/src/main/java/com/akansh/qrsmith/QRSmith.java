package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRSmith {

    public enum QRCodeStyle {
        SQUARED, DOTS, HEXAGONAL
    }

    public enum QRErrorCorrectionLevel {
        L, M, Q, H
    }

    public static Bitmap generateQRCode(String content, QRCodeOptions options) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }

        Bitmap qrBitmap = null;

        ErrorCorrectionLevel errorCorrectionLevel = getErrorCorrectionLevel(options);

        try {

            if (options.style == QRCodeStyle.SQUARED) {
                qrBitmap = NormalQR.renderQRImage(content, options, errorCorrectionLevel);
            } else if (options.style == QRCodeStyle.DOTS) {
                qrBitmap = RoundQR.renderQRImage(content, options, errorCorrectionLevel);
            }else if (options.style == QRCodeStyle.HEXAGONAL) {
                qrBitmap = HexagonalQR.renderQRImage(content, options, errorCorrectionLevel);
            }

            // Add logo if provided
            if (options.logo != null && qrBitmap != null) {
                qrBitmap = addLogo(qrBitmap, options.logo);
            }
        }catch (Exception e) {}
        return qrBitmap;
    }

    private static ErrorCorrectionLevel getErrorCorrectionLevel(QRCodeOptions options) {
        ErrorCorrectionLevel errorCorrectionLevel;
        switch (options.errorCorrectionLevel) {
            case L:
                errorCorrectionLevel = ErrorCorrectionLevel.L;
                break;
            case M:
                errorCorrectionLevel = ErrorCorrectionLevel.M;
                break;
            case Q:
                errorCorrectionLevel = ErrorCorrectionLevel.Q;
                break;
            default:
                errorCorrectionLevel = ErrorCorrectionLevel.H;
        }
        return errorCorrectionLevel;
    }

    private static Bitmap addLogo(Bitmap qrCode, Bitmap logo) {
        Bitmap combinedBitmap = Bitmap.createBitmap(qrCode.getWidth(), qrCode.getHeight(), qrCode.getConfig());
        Canvas canvas = new Canvas(combinedBitmap);
        canvas.drawBitmap(qrCode, 0, 0, null);

        // Calculate logo placement
        int overlayWidth = qrCode.getWidth() / 5;
        int overlayHeight = qrCode.getHeight() / 5;
        int left = (qrCode.getWidth() - overlayWidth) / 2;
        int top = (qrCode.getHeight() - overlayHeight) / 2;

        Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, overlayWidth, overlayHeight, true);
        canvas.drawBitmap(scaledLogo, left, top, null);
        return combinedBitmap;
    }
}