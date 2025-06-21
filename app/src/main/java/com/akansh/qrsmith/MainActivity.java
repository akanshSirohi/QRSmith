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
//        Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_img);
        QRCodeOptions options = new QRCodeOptions();
        options.width = 720;
        options.height = 720;
        options.backgroundColor = Color.WHITE;
        options.foregroundColor = Color.BLACK;
        options.errorCorrectionLevel = QRSmith.QRErrorCorrectionLevel.H;
//        options.logo = logo;
//        options.background = bg;
        options.clearLogoBackground = true;
        options.quietZone = 1;
        options.dotSizeFactor = 1f;
        options.patternStyle = QRStyles.PatternStyle.Dotted;
        options.eyeShape = QRStyles.EyeShape.Hexagonal;

        Bitmap bitmap = QRSmith.generateQRCode("Hello, World!", options);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        dr.setCornerRadius(15f);
        qrView.setImageDrawable(dr);
    }
}