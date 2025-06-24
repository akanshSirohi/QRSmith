# QRSmith - Android QR Code Library

QRSmith is a powerful and versatile Android library for generating advanced, customizable QR codes. It supports multiple styles, logos, backgrounds, and various error correction levels, making it an essential tool for developers looking to add unique QR code functionalities to their apps.

## Key Features

- **Multiple Styles**: Generate QR codes in **square**, **fluid**, **dotted**, **hexagon**, **star**, **diamond**, or **heart** styles.
- **Heart Pattern**: The `HEART` style draws each module as a small heart shape for a playful look.
- **Logo Integration**: Add logos with optional padding and background clearing.
- **Custom Backgrounds**: Use custom images or colors as QR code backgrounds.
- **Full Customization**: Adjust size, colors, quiet zones, and more.
- **Customizable Finder Patterns**: Choose separate shapes for the finder frame and ball, including options like `SQUARE`, `ROUND_SQUARE`, `CIRCLE`, `HEXAGON`, `ONE_SHARP_CORNER`, `TECH_EYE`, `SOFT_ROUNDED`, `PINCHED_SQUIRCLE`, `BLOB_CORNER`, and `CORNER_WARP`.
- **Error Correction**: Supports error correction levels (L, M, Q, H) for data reliability.
- **Developer-Friendly API**: Easy-to-use interface with robust customization options.

## Sample QR Codes
<table>
    <tr>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/samples/QR-1.jpg?raw=true" width="200" /></td>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/samples/QR-2.jpg?raw=true" width="200" /></td>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/samples/QR-3.jpg?raw=true" width="200" /></td>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/samples/QR-4.jpg?raw=true" width="200" /></td>
    </tr>
    <tr>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/samples/QR-5.png?raw=true" width="200" /></td>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/samples/QR-6.png?raw=true" width="200" /></td>
    </tr>
</table>

## Installation

### Step 1: Add Repositories

Add the following to your root `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the Dependency

Include this in your module-level `build.gradle` file:

```groovy
dependencies {
    implementation 'com.github.akanshSirohi:QRSmith:2.0.0'
}
```

## Getting Started

### Basic Example

```java
import com.akansh.qrsmith.QRSmith;
import com.akansh.qrsmith.model.QRCodeOptions;
import com.akansh.qrsmith.model.QRStyles;
import android.graphics.Bitmap;
import android.graphics.Color;

// Define the QR code content
String content = "https://example.com";

// Create QR code options
QRCodeOptions options = new QRCodeOptions.Builder()
        .setWidth(500)
        .setHeight(500)
        .setForegroundColor(Color.BLACK)
        .setBackgroundColor(Color.WHITE)
        .setPatternStyle(QRStyles.PatternStyle.SQUARE)
        .setEyeFrameShape(QRStyles.EyeFrameShape.SQUARE)
        .setEyeBallShape(QRStyles.EyeBallShape.SQUARE)
        .setQuietZone(1)
        .build();

try {
    // Generate the QR code
    Bitmap qrCode = QRSmith.generateQRCode(content, options);
    // Use the generated QR code Bitmap as needed
} catch (Exception e) {
    e.printStackTrace();
}
```

### Advanced Example with Logo, Background, and Customizations

```java
import com.akansh.qrsmith.QRSmith;
import com.akansh.qrsmith.model.QRCodeOptions;
import com.akansh.qrsmith.model.QRStyles;
import com.akansh.qrsmith.model.QRErrorCorrectionLevel;
import android.graphics.Bitmap;
import android.graphics.Color;

// Define the QR code content
String content = "https://example.com";

// Load your logo and background bitmaps
Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

// Create QR code options
QRCodeOptions options = new QRCodeOptions.Builder()
        .setWidth(600)
        .setHeight(600)
        .setForegroundColor(Color.BLACK)
        .setBackgroundColor(Color.WHITE)
        .setPatternStyle(QRStyles.PatternStyle.HEXAGON)
        .setEyeFrameShape(QRStyles.EyeFrameShape.HEXAGON)
        .setEyeBallShape(QRStyles.EyeBallShape.HEXAGON)
        .setEyeFrameColor(Color.BLUE)
        .setEyeBallColor(Color.RED)
        .setLogo(logo)
        .setBackground(background) // Set custom background
        .setErrorCorrectionLevel(QRErrorCorrectionLevel.Q)
        .setQuietZone(2)
        .build();

try {
    // Generate the QR code
    Bitmap qrCode = QRSmith.generateQRCode(content, options);
    // Use the generated QR code Bitmap as needed
} catch (Exception e) {
    e.printStackTrace();
}
```

### Gradient Example
```java
int[] fgColors = new int[]{Color.RED, Color.BLUE};
int[] bgColors = new int[]{Color.WHITE, Color.LTGRAY};
QRCodeOptions options = new QRCodeOptions.Builder()
        .setForegroundGradient(fgColors, QRCodeOptions.GradientOrientation.RADIAL)
        .setBackgroundGradient(bgColors, QRCodeOptions.GradientOrientation.RADIAL)
        .build();
Bitmap qrCode = QRSmith.generateQRCode("https://example.com", options);
```

## Customization Options

QRSmith offers extensive customization through the `QRCodeOptions` class:

| Option                 | Description                                       | Default Value |
| ---------------------- | ------------------------------------------------- | ------------- |
| `width`                | Width of the QR code in pixels                    | 500           |
| `height`               | Height of the QR code in pixels                   | 500           |
| `foregroundColor`      | Color of the QR code foreground                   | `Color.BLACK` |
| `backgroundColor`      | Color of the QR code background                   | `Color.WHITE` |
| `foregroundGradientColors` | Colors for a gradient QR foreground | `null` |
| `backgroundGradientColors` | Colors for the background gradient | `null` |
| `foregroundGradientOrientation` | Gradient orientation (`LEFT_RIGHT`, `TOP_BOTTOM`, `TL_BR`, `BL_TR`) | `LEFT_RIGHT` |
| `backgroundGradientOrientation` | Orientation for the background gradient | `LEFT_RIGHT` |
| `patternStyle` | Pattern style (`SQUARE`, `FLUID`, `S_DOT`, `L_DOT`, `HEXAGON`, `X_AXIS_FLUID`, `Y_AXIS_FLUID`, `DIAMOND`, `STAR`, `HEART`) | `SQUARE`     |
| `logo`                 | Bitmap for the logo to overlay on the QR code     | `null`        |
| `eyeFrameShape`      | Shape of the finder frame (`SQUARE`, `ROUND_SQUARE`, `CIRCLE`, `HEXAGON`, `ONE_SHARP_CORNER`, `TECH_EYE`, `SOFT_ROUNDED`, `PINCHED_SQUIRCLE`, `BLOB_CORNER`, `CORNER_WARP`) | `SQUARE`     |
| `eyeBallShape`       | Shape of the finder ball (`SQUARE`, `ROUND_SQUARE`, `CIRCLE`, `HEXAGON`, `ONE_SHARP_CORNER`, `TECH_EYE`, `SOFT_ROUNDED`, `PINCHED_SQUIRCLE`, `BLOB_CORNER`, `CORNER_WARP`) | `SQUARE`     |
| `eyeFrameColor`      | Solid color for finder frames (overrides gradients) | `null` |
| `eyeBallColor`       | Solid color for finder balls (overrides gradients)  | `null` |
| `errorCorrectionLevel` | Error correction level (`L`, `M`, `Q`, `H`)       | `H`           |
| `clearLogoBackground`  | Clears the background under the logo              | `true`        |
| `background`           | Bitmap for the QR code background                 | `null`        |
| `logoPadding`          | Padding around the logo                           | `0`           |
| `quietZone`            | Quiet zone size around the QR code                | `1`           |

## Contributing

We welcome contributions! Feel free to open issues or submit pull requests to improve this library.

## License

QRSmith is licensed under the MIT License. See `LICENSE` for details.

---

Start generating stunning, customizable QR codes with QRSmith today and elevate your app's capabilities!

