package com.akansh.qrsmith.renderer;

import android.graphics.Bitmap;

public final class BlurUtil {

    /**
     * Fast, context-free blur that works on API 21-35.
     *
     * @param src        Source bitmap (remains unchanged).
     * @param radiusPx   Blur radius in pixels (1-25).
     * @param downScale  Down-sampling factor 0.25–1.0 (e.g. 0.5 → 50 % size).
     * @return           Blurred bitmap with the SAME width/height as src.
     */
    public static Bitmap blur(Bitmap src, float radiusPx, float downScale) {
        radiusPx  = Math.min(Math.max(radiusPx,  1f), 25f);   // 1–25
        downScale = Math.min(Math.max(downScale, 0.25f), 1f); // 0.25–1

        // 1) Optional down-sample for speed
        Bitmap small = (downScale < 1f)
                ? Bitmap.createScaledBitmap(src,
                Math.round(src.getWidth()  * downScale),
                Math.round(src.getHeight() * downScale),
                true)
                : src;

        Bitmap blurred = stackBlur(small, (int) radiusPx);

        if (blurred.getWidth() != src.getWidth()
                || blurred.getHeight() != src.getHeight()) {
            blurred = Bitmap.createScaledBitmap(
                    blurred, src.getWidth(), src.getHeight(), /*filter*/ true);
        }
        return blurred;
    }

    /* --------------------------------------------------------------------- */
    /* Fast stack-blur implementation (public-domain variant)                */
    /* --------------------------------------------------------------------- */
    private static Bitmap stackBlur(Bitmap src, int radius) {
        if (radius < 1) return src;

        Bitmap bitmap = src.copy(src.getConfig(), true);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1, hm = h - 1, wh = w * h, div = radius + radius + 1;
        int r[] = new int[wh], g[] = new int[wh], b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p1, p2, yp, yi = 0, yw = 0;

        int[] vmin = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1; divsum *= divsum;
        int[] dv = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) dv[i] = (i / divsum);

        int[][] stack = new int[div][3];
        int stackpointer, stackstart;
        int[] sir;
        int rbs;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                int p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = radius + 1 - Math.abs(i);
                rsum += sir[0] * rbs; gsum += sir[1] * rbs; bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0]; ginsum += sir[1]; binsum += sir[2];
                } else {
                    routsum += sir[0]; goutsum += sir[1]; boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum]; g[yi] = dv[gsum]; b[yi] = dv[bsum];

                rsum -= routsum; gsum -= goutsum; bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0]; goutsum -= sir[1]; boutsum -= sir[2];

                if (y == 0) vmin[x] = Math.min(x + radius + 1, wm);
                int p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0]; ginsum += sir[1]; binsum += sir[2];

                rsum += rinsum; gsum += ginsum; bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0]; goutsum += sir[1]; boutsum += sir[2];
                rinsum -= sir[0]; ginsum -= sir[1]; binsum -= sir[2];

                yi++;
            }
            yw += w;
        }

        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi]; sir[1] = g[yi]; sir[2] = b[yi];

                rbs = radius + 1 - Math.abs(i);

                rsum += r[yi] * rbs; gsum += g[yi] * rbs; bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0]; ginsum += sir[1]; binsum += sir[2];
                } else {
                    routsum += sir[0]; goutsum += sir[1]; boutsum += sir[2];
                }

                if (i < hm) yp += w;
            }
            yi = x; stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum; gsum -= goutsum; bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0]; goutsum -= sir[1]; boutsum -= sir[2];

                if (x == 0) vmin[y] = Math.min(y + radius + 1, hm) * w;
                int p = x + vmin[y];

                sir[0] = r[p]; sir[1] = g[p]; sir[2] = b[p];

                rinsum += sir[0]; ginsum += sir[1]; binsum += sir[2];

                rsum += rinsum; gsum += ginsum; bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0]; goutsum += sir[1]; boutsum += sir[2];
                rinsum -= sir[0]; ginsum -= sir[1]; binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return bitmap;
    }
}

