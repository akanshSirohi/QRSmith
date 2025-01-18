package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

class NormalQR {
    public static Bitmap renderQRImage(String content,QRCodeOptions qrOptions, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, qrOptions.quiteZone);
        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                qrOptions.width,
                qrOptions.height,
                hints
        );

        // Create a new bitmap to draw the QR code over the background
        Bitmap qrCodeBitmap = Bitmap.createBitmap(qrOptions.width, qrOptions.height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(qrCodeBitmap);

        if(qrOptions.background != null) {
            // Load or create the background bitmap
            Bitmap backgroundBitmap = Bitmap.createScaledBitmap(qrOptions.background, qrOptions.width, qrOptions.height, true);

            // Draw the background image
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        }

        // Paint to draw the QR code
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(qrOptions.foregroundColor);

        // Draw the QR code over the background
        for (int x = 0; x < bitMatrix.getWidth(); x++) {
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                if (bitMatrix.get(x, y)) {
                    qrCodeBitmap.setPixel(x, y, qrOptions.foregroundColor);
                }else if(qrOptions.background == null){
                    qrCodeBitmap.setPixel(x, y, qrOptions.backgroundColor);
                }
            }
        }

        return qrCodeBitmap;
    }
}
