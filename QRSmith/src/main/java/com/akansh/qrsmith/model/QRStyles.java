package com.akansh.qrsmith.model;

public class QRStyles {
    public enum EyeFrameShape {
        Square, RoundSquare, Circle, Hexagon,
        OneSharpCorner, TechEye, SoftRounded,
        PinchedSquircle, BlobCorner, CornerWarp
    }

    public enum EyeBallShape {
        Square, RoundSquare, Circle, Hexagon,
        OneSharpCorner, TechEye, SoftRounded,
        PinchedSquircle, BlobCorner, CornerWarp,
        PillStackH, PillStackV, Incurve, Chisel
    }

    public enum PatternStyle {
        Square, Fluid, Dotted, Hexagon
    }
}
