package com.akansh.qrsmith;

import android.graphics.Bitmap;

import org.junit.Test;

import static org.junit.Assert.*;

public class NormalQRRadiusTest {
    @Test
    public void generatesBitmapWithRadius() {
        QRCodeOptions options = new QRCodeOptions();
        options.radius = 5;
        Bitmap bmp = QRSmith.generateQRCode("radius", options);
        assertNotNull(bmp);
    }
}
