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
    public static Bitmap renderQRImage(String content, QRCodeOptions qrOptions, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
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

        Bitmap qrCodeBitmap = Bitmap.createBitmap(qrOptions.width, qrOptions.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(qrCodeBitmap);

        if (qrOptions.background != null) {
            Bitmap backgroundBitmap = Bitmap.createScaledBitmap(qrOptions.background, qrOptions.width, qrOptions.height, true);
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(qrOptions.foregroundColor);

        // Define logo dimensions
        Bitmap logo = qrOptions.logo; // Assume the logo is passed in QRCodeOptions
        int logoWidth = qrOptions.width / 5; // Adjust as needed (20% of QR code size)
        int logoHeight = qrOptions.height / 5; // Adjust as needed
        int logoX = (qrOptions.width - logoWidth) / 2; // Center horizontally
        int logoY = (qrOptions.height - logoHeight) / 2; // Center vertically

        // Draw QR code with an empty space for the logo
        for (int x = 0; x < bitMatrix.getWidth(); x++) {
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                // Skip drawing inside the logo area
                if (qrOptions.clearLogoBackground && (x >= logoX && x < logoX + logoWidth && y >= logoY && y < logoY + logoHeight)) {
                    if(qrOptions.background == null) {
                        qrCodeBitmap.setPixel(x, y, qrOptions.backgroundColor);
                    }
                    continue;
                }
                if (bitMatrix.get(x, y)) {
                    qrCodeBitmap.setPixel(x, y, qrOptions.foregroundColor);
                } else if (qrOptions.background == null) {
                    qrCodeBitmap.setPixel(x, y, qrOptions.backgroundColor);
                }
            }
        }

        // Draw the logo on top of the QR code
        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, true);
            canvas.drawBitmap(scaledLogo, logoX, logoY, null);
        }

        return qrCodeBitmap;
    }

}
