# QRSmith - Android QR Code Library

QRSmith is a powerful and versatile Android library for generating advanced, customizable QR codes. It supports multiple styles, logos, backgrounds, and various error correction levels, making it an essential tool for developers looking to add unique QR code functionalities to their apps.

## Key Features

- **Multiple Styles**: Generate QR codes in **square**, **rounded**, or **hexagonal** styles.
- **Logo Integration**: Add logos with optional padding and background clearing.
- **Custom Backgrounds**: Use custom images or colors as QR code backgrounds.
- **Full Customization**: Adjust size, colors, dot size factors, quiet zones, and more.
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
    implementation 'com.github.akanshSirohi:QRSmith:1.1.0'
}
```

## Getting Started

### Basic Example

```java
import com.akansh.qrsmith.QRSmith;
import com.akansh.qrsmith.model.QRCodeOptions;
import android.graphics.Bitmap;

// Define the QR code content
String content = "https://example.com";

// Create QR code options
QRCodeOptions options = new QRCodeOptions();
options.width = 500;
options.height = 500;
options.foregroundColor = Color.BLACK;
options.backgroundColor = Color.WHITE;
options.style = QRSmith.QRCodeStyle.SQUARED;
options.radius = 5; // Rounded corners
options.quietZone = 1; // Set quiet zone size

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
import android.graphics.Bitmap;

// Define the QR code content
String content = "https://example.com";

// Load your logo and background bitmaps
Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

// Create QR code options
QRCodeOptions options = new QRCodeOptions();
options.width = 600;
options.height = 600;
options.foregroundColor = Color.BLACK;
options.backgroundColor = Color.WHITE;
options.style = QRSmith.QRCodeStyle.HEXAGONAL;
options.radius = 0; // Radius only affects SQUARED style
options.logo = logo;
options.background = background; // Set custom background
options.dotSizeFactor = 0.8f;
options.errorCorrectionLevel = QRSmith.QRErrorCorrectionLevel.Q;
options.quietZone = 2; // Set quiet zone size

try {
    // Generate the QR code
    Bitmap qrCode = QRSmith.generateQRCode(content, options);
    // Use the generated QR code Bitmap as needed
} catch (Exception e) {
    e.printStackTrace();
}
```

## Customization Options

QRSmith offers extensive customization through the `QRCodeOptions` class:

| Option                 | Description                                       | Default Value |
| ---------------------- | ------------------------------------------------- | ------------- |
| `width`                | Width of the QR code in pixels                    | 500           |
| `height`               | Height of the QR code in pixels                   | 500           |
| `foregroundColor`      | Color of the QR code foreground                   | `Color.BLACK` |
| `backgroundColor`      | Color of the QR code background                   | `Color.WHITE` |
| `style`                | QR code style (`SQUARED`, `ROUNDED`, `HEXAGONAL`) | `SQUARED`     |
| `logo`                 | Bitmap for the logo to overlay on the QR code     | `null`        |
| `dotSizeFactor`        | Adjusts the size of dots                          | `0.8f`        |
| `radius`              | Corner radius for SQUARED style modules (`0` = sharp, `10` = fully round) | `0` |
| `errorCorrectionLevel` | Error correction level (`L`, `M`, `Q`, `H`)       | `H`           |
| `clearLogoBackground`  | Clears the background under the logo              | `true`        |
| `background`           | Bitmap for the QR code background                 | `null`        |
| `logoPadding`          | Padding around the logo                           | `0`           |
| `quietZone`            | Quiet zone size around the QR code                | `1`           |

## Supported QR Code Styles

1. **Square Style**: The traditional QR code style.
2. **Rounded Style**: Uses circular dots for a softer appearance.
3. **Hexagonal Style**: Creates a hexagonal pattern for a unique, modern design.

## Contributing

We welcome contributions! Feel free to open issues or submit pull requests to improve this library.

## License

QRSmith is licensed under the MIT License. See `LICENSE` for details.

---

Start generating stunning, customizable QR codes with QRSmith today and elevate your app's capabilities!

