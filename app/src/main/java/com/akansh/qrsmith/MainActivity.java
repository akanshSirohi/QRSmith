package com.akansh.qrsmith;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.akansh.qrsmith.model.QRCodeOptions;
import com.akansh.qrsmith.model.QRErrorCorrectionLevel;
import com.akansh.qrsmith.model.QRStyles;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView qrView = findViewById(R.id.qrView);

//        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_android);
        Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.sample_forest_landscape);

        int[] fgColors = new int[]{Color.RED, Color.BLACK};
        int[] bgColors = new int[]{Color.WHITE, Color.LTGRAY};

        QRCodeOptions options = new QRCodeOptions.Builder()
                .setWidth(720)
                .setHeight(720)
                .setBackgroundColor(Color.WHITE)
                .setForegroundColor(Color.parseColor("#000000"))
                .setErrorCorrectionLevel(QRErrorCorrectionLevel.H)
//                .setForegroundGradient(fgColors, QRCodeOptions.GradientOrientation.RADIAL)
//                .setBackgroundGradient(bgColors, QRCodeOptions.GradientOrientation.TOP_BOTTOM)
//                .setLogo(logo)
                .setBackground(bg)
//                .setClearLogoBackground(true)
                .setQuietZone(1)
                .setLogoPadding(0)
                .setPatternStyle(QRStyles.PatternStyle.XS_DOT)
//                .setEyeBallShape(QRStyles.EyeBallShape.HEART)
//                .setEyeFrameShape(QRStyles.EyeFrameShape.SOFT_ROUNDED)
//                .setEyeFrameColor(Color.parseColor("#000000"))
//                .setEyeBallColor(Color.parseColor("#e60808"))
                .setMaxTolerance(true)
                .setToleranceMaskOpacity(0.4f)
                .setToleranceModuleSize(0.4f)
//                .setClipBackgroundToQR(true)
                .build();

        Bitmap bitmap = QRSmith.generateQRCode("https://akanshsirohi.dev", options);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        dr.setCornerRadius(15f);
        qrView.setImageDrawable(dr);
    }
}