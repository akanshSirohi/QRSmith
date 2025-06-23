package com.akansh.qrsmith.model;

public class QRStyles {
    public enum EyeFrameShape {
        SQUARE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawSquaredStyle(c, p, a[0], a[1], size, color);
                }
            }
        },
        ROUND_SQUARE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawRoundedSquaredStyle(c, p, a[0], a[1], size, multiple, color);
                }
            }
        },
        CIRCLE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawCircleStyle(c, p, a[0], a[1], size, color);
                }
            }
        },
        HEXAGON {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawHexStyle(c, p, a[0], a[1], size, color);
                }
            }
        },
        ONE_SHARP_CORNER {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawOneSharpCornerStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawOneSharpCornerStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawOneSharpCornerStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        TECH_EYE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawTechEyeStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawTechEyeStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawTechEyeStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        SOFT_ROUNDED {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawSoftRoundedStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawSoftRoundedStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawSoftRoundedStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        PINCHED_SQUIRCLE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawPinchedSquircleStyle(c, p, x[0], x[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawPinchedSquircleStyle(c, p, y[0], y[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawPinchedSquircleStyle(c, p, z[0], z[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        BLOB_CORNER {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawBlobCornerStyle(c, p, x[0], x[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawBlobCornerStyle(c, p, y[0], y[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawBlobCornerStyle(c, p, z[0], z[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        CORNER_WARP {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawCornerWarpStyle(c, p, x[0], x[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawCornerWarpStyle(c, p, y[0], y[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawCornerWarpStyle(c, p, z[0], z[1], size, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        };

        public abstract void render(com.akansh.qrsmith.renderer.QRFinderFrameRenderer renderer, android.graphics.Canvas canvas, android.graphics.Paint paint,
                                    int[] eyeAlignmentX, int[] eyeAlignmentY, int[] eyeAlignmentZ,
                                    int patternSize, int multiple, int color);
    }

    public enum EyeBallShape {
        SQUARE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawSquaredStyle(c, p, a[0], a[1], size, color);
                }
            }
        },
        ROUND_SQUARE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawRoundedSquaredStyle(c, p, a[0], a[1], size, multiple, color);
                }
            }
        },
        CIRCLE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawCircleStyle(c, p, a[0], a[1], size, multiple, color);
                }
            }
        },
        HEXAGON {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                int[][] arr = {x, y, z};
                for (int[] a : arr) {
                    r.drawHexStyle(c, p, a[0], a[1], size, color);
                }
            }
        },
        ONE_SHARP_CORNER {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawOneSharpCornerStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawOneSharpCornerStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawOneSharpCornerStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        TECH_EYE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawTechEyeStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawTechEyeStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawTechEyeStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        SOFT_ROUNDED {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawSoftRoundedStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawSoftRoundedStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawSoftRoundedStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        PINCHED_SQUIRCLE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawPinchedSquircleStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawPinchedSquircleStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawPinchedSquircleStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        BLOB_CORNER {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawBlobCornerStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawBlobCornerStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawBlobCornerStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        CORNER_WARP {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawCornerWarpStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawCornerWarpStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawCornerWarpStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        PILL_STACK_H {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawPillStackHStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawPillStackHStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawPillStackHStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        PILL_STACK_V {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawPillStackVStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawPillStackVStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawPillStackVStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        INCURVE {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawIncurveStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawIncurveStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawIncurveStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        },
        CHISEL {
            @Override
            public void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer r, android.graphics.Canvas c, android.graphics.Paint p,
                               int[] x, int[] y, int[] z, int size, int multiple, int color) {
                r.drawChiselStyle(c, p, x[0], x[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_LEFT);
                r.drawChiselStyle(c, p, y[0], y[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.TOP_RIGHT);
                r.drawChiselStyle(c, p, z[0], z[1], size, multiple, color, com.akansh.qrsmith.renderer.CommonShapeUtils.CornerPosition.BOTTOM_LEFT);
            }
        };

        public abstract void render(com.akansh.qrsmith.renderer.QRFinderBallRenderer renderer, android.graphics.Canvas canvas, android.graphics.Paint paint,
                                    int[] eyeAlignmentX, int[] eyeAlignmentY, int[] eyeAlignmentZ,
                                    int patternSize, int multiple, int color);
    }

    public enum PatternStyle {
        SQUARE, FLUID, DOTTED, HEXAGON
    }
}
