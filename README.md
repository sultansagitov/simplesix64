# SimpleSix64

**SimpleSix64** is an encoding library designed to extend Base64 encoding with a broader set of characters, including case-insensitive letters and a variety of symbols. This library is useful for applications needing a custom encoding scheme that supports a wider range of characters.

## Features

- **Extended Character Set**: Supports a rich set of symbols beyond the standard Base64.
- **Case Insensitivity**: Encoding and decoding processes handle case insensitivity.

## Installation

### Clone the Repository

To get started, clone the repository from GitHub:

```bash
git clone https://github.com/sultansagitov/simplesix64.git
```

### Build and Install Locally

Navigate into the project directory and build the project using Maven:

```bash
cd simplesix64
mvn clean install
```

This will install the library into your local Maven repository.

### Adding to Your Project

Add the following dependency to your `pom.xml` to use SimpleSix64 in your Maven project:

```xml
<dependency>
    <groupId>net.result.simplesix64</groupId>
    <artifactId>simple64</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Encoding

To encode a string, use the `encode` method. This method converts the input string into a custom encoded format using the defined character set.

```java
import net.result.simplesix64.SimpleSix64;

public class EncodingExample {
    public static void main(String[] args) {
        String data = "Hello, World!";
        byte[] encoded = SimpleSix64.encode(data);
        System.out.println("Encoded: " + new String(encoded));
    }
}
```

## License

This project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for details.
