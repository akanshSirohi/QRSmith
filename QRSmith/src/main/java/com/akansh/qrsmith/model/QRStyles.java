package com.akansh.qrsmith.model;

public class QRStyles {
    public enum EyeFrameShape {
        SQUARE, ROUND_SQUARE, CIRCLE, HEXAGON,
        ONE_SHARP_CORNER, TECH_EYE, SOFT_ROUNDED,
        PINCHED_SQUIRCLE, BLOB_CORNER, CORNER_WARP
    }

    public enum EyeBallShape {
        SQUARE, ROUND_SQUARE, CIRCLE, HEXAGON,
        ONE_SHARP_CORNER, TECH_EYE, SOFT_ROUNDED,
        PINCHED_SQUIRCLE, BLOB_CORNER, CORNER_WARP,
        PILL_STACK_H, PILL_STACK_V, INCURVE, CHISEL
    }

    public enum PatternStyle {
        SQUARE, FLUID, DOTTED, HEXAGON
    }
}
