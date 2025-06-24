package com.akansh.qrsmith;

import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.RadialGradient;

import com.akansh.qrsmith.model.QRCodeOptions;
import com.akansh.qrsmith.renderer.QRRenderer;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class GradientBuilderTest {
    @Test
    public void radialOrientationProducesRadialGradient() throws Exception {
        QRRenderer renderer = new QRRenderer();
        Method m = QRRenderer.class.getDeclaredMethod("buildGradient", int[].class, QRCodeOptions.GradientOrientation.class, int.class, int.class);
        m.setAccessible(true);
        Shader shader = (Shader) m.invoke(renderer, new int[]{Color.RED, Color.BLUE}, QRCodeOptions.GradientOrientation.RADIAL, 100, 100);
        assertTrue(shader instanceof RadialGradient);
    }
}
