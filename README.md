# QRSmith - Android QR Code Library

QRSmith is an Android library for generating customizable QR codes with advanced styling and features, including support for logos and different QR code styles. It leverages the ZXing library to provide an easy-to-use interface for developers.

## Features
- Generate QR codes with square or dot styles.
- Customize size, colors, and error correction levels.
- Add logos to the center of the QR codes.
- Support for different error correction levels (L, M, Q, H).
- Adjustable dot size for rounded QR codes.

## Sample QR Codes
<table>
    <tr>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/screenshots/QR-1.jpg?raw=true" width="200" /></td>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/screenshots/QR-2.jpg?raw=true" width="200" /></td>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/screenshots/QR-3.jpg?raw=true" width="200" /></td>
        <td><img src="https://github.com/akanshSirohi/QRSmith/blob/master/screenshots/QR-4.jpg?raw=true" width="200" /></td>
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
Add this to your module-level `build.gradle` file:
```groovy
dependencies {
    implementation 'com.github.akanshSirohi:QRSmith:0.1.3'
}
```

## Usage
Here’s how you can use QRSmith to generate a QR code:

### Basic Example
```java
import com.akansh.qrsmith.QRSmith;
import android.graphics.Bitmap;

// Define the QR code content
String content = "https://example.com";

// Create QR code options
QRSmith.QRCodeOptions options = new QRSmith.QRCodeOptions();
options.width = 500;
options.height = 500;
options.foregroundColor = Color.BLACK;
options.backgroundColor = Color.WHITE;
options.style = QRSmith.QRCodeStyle.SQUARED;

try {
    // Generate the QR code
    Bitmap qrCode = QRSmith.generateQRCode(content, options);
    // Use the generated QR code Bitmap as needed
} catch (WriterException e) {
    e.printStackTrace();
}
```

### Advanced Example with Logo
```java
import com.akansh.qrsmith.QRSmith;
import android.graphics.Bitmap;

// Define the QR code content
String content = "https://example.com";

// Load your logo bitmap
Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

// Create QR code options
QRSmith.QRCodeOptions options = new QRSmith.QRCodeOptions();
options.width = 600;
options.height = 600;
options.foregroundColor = Color.BLACK;
options.backgroundColor = Color.WHITE;
options.style = QRSmith.QRCodeStyle.DOTS;
options.logo = logo;
options.dotSizeFactor = 0.8f;
options.errorCorrectionLevel = QRSmith.QRErrorCorrectionLevel.H;

try {
    // Generate the QR code
    Bitmap qrCode = QRSmith.generateQRCode(content, options);
    // Use the generated QR code Bitmap as needed
} catch (WriterException e) {
    e.printStackTrace();
}
```

## Customization Options
QRSmith allows you to customize various parameters through the `QRCodeOptions` class:

| Option                | Description                                      | Default Value         |
|-----------------------|--------------------------------------------------|-----------------------|
| `width`               | Width of the QR code in pixels                  | 500                   |
| `height`              | Height of the QR code in pixels                 | 500                   |
| `foregroundColor`     | Color of the QR code foreground                 | `Color.BLACK`         |
| `backgroundColor`     | Color of the QR code background                 | `Color.WHITE`         |
| `style`               | Style of the QR code (`SQUARED`, `DOTS`)        | `SQUARED`            |
| `logo`                | Bitmap for the logo to overlay on the QR code   | `null`                |
| `dotSizeFactor`       | Adjusts the size of dots for the `DOTS` style   | `0.8f`                |
| `errorCorrectionLevel`| Error correction level (`L`, `M`, `Q`, `H`)     | `H`                   |

## Contributing
Feel free to open issues or contribute to this library by submitting pull requests.

## License
QRSmith is licensed under the MIT License. See `LICENSE` for details.

---

Start generating stunning QR codes with QRSmith today!

