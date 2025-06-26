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

//        rgb(106, 17, 203), rgb(37, 117, 252)

        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.a_logo);
//        Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.floral_1);

//        int[] fgColors = new int[]{Color.BLACK, Color.parseColor("#004239")};
//        int[] fgColors = new int[]{Color.argb(255, 106, 17, 203), Color.argb(255, 37, 117, 252)};
//        int[] bgColors = new int[]{Color.parseColor("#3F5EFB"), Color.parseColor("#FC466B")};
//        int[] bgColors = new int[]{Color.WHITE, Color.BLACK};
//        int[] fgColors = new int[]{Color.parseColor("#008F11"), Color.parseColor("#00FF41")};

        QRCodeOptions options = new QRCodeOptions.Builder()
                .setWidth(720)
                .setHeight(720)
//                .setBackgroundColor(Color.WHITE)
//                .setForegroundColor(Color.WHITE)
                .setErrorCorrectionLevel(QRErrorCorrectionLevel.H)
//                .setForegroundGradient(fgColors, QRCodeOptions.GradientOrientation.TOP_BOTTOM)
//                .setBackgroundGradient(bgColors, QRCodeOptions.GradientOrientation.RADIAL)
                .setLogo(logo)
//                .setBackground(bg)
//                .setClearLogoBackground(true)
                .setQuietZone(1)
//                .setLogoPadding(2)
                .setPatternStyle(QRStyles.PatternStyle.Y_AXIS_FLUID)
                .setEyeBallShape(QRStyles.EyeBallShape.SOFT_ROUNDED)
                .setEyeFrameShape(QRStyles.EyeFrameShape.SOFT_ROUNDED)
//                .setEyeFrameColor(Color.parseColor("#003B00"))
//                .setEyeBallColor(Color.parseColor("#003B00"))
                .build();

        Bitmap bitmap = QRSmith.generateQRCode("https://akanshsirohi.dev", options);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        dr.setCornerRadius(15f);
        qrView.setImageDrawable(dr);
    }
}