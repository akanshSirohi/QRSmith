package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRSmith {

    public enum QRCodeStyle {
        SQUARED, ROUNDED, HEXAGONAL
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
            if(options.logoPadding != 0) {
                options.logo = addPaddingToBitmap(options.logo, options.logoPadding);
            }
            if (options.style == QRCodeStyle.SQUARED) {
                qrBitmap = NormalQR.renderQRImage(content, options, errorCorrectionLevel);
            } else if (options.style == QRCodeStyle.ROUNDED) {
                qrBitmap = RoundQR.renderQRImage(content, options, errorCorrectionLevel);
            }else if (options.style == QRCodeStyle.HEXAGONAL) {
                qrBitmap = HexagonalQR.renderQRImage(content, options, errorCorrectionLevel);
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

    /**
     * Adds equal padding (in pixels) around a given Bitmap.
     * @param originalBitmap The original Bitmap.
     * @param paddingPx The padding size in pixels to add on each side.
     * @return A new Bitmap with the specified padding, or null if originalBitmap is null.
     */
    public static Bitmap addPaddingToBitmap(
            Bitmap originalBitmap,
            int paddingPx
    ) {
        if (originalBitmap == null) {
            return null;
        }

        paddingPx = paddingPx * 100;

        // Calculate the size of the new bitmap
        int newWidth = originalBitmap.getWidth() + paddingPx * 2;
        int newHeight = originalBitmap.getHeight() + paddingPx * 2;

        // Fallback to ARGB_8888 if the original config is null
        Bitmap.Config bitmapConfig = originalBitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }

        // Create a new bitmap
        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmapConfig);

        // Important: match the density, in case you use the density for scaling
        paddedBitmap.setDensity(originalBitmap.getDensity());

        // Create a canvas to draw onto the new bitmap
        Canvas canvas = new Canvas(paddedBitmap);

        // Draw the original bitmap onto the new bitmap, offset by 'paddingPx'
        canvas.drawBitmap(originalBitmap, paddingPx, paddingPx, null);

        return paddedBitmap;
    }

}