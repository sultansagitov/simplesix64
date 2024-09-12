# SimpleSix64

**SimpleSix64** is a custom encoding scheme that extends the traditional Base64 encoding to support a case-insensitive alphabet, along with additional symbols. It is designed for efficient encoding and decoding of data, and is perfect for cases where Base64 does not fully meet the need for symbol-rich or case-insensitive environments.

## Features
- Case-insensitive encoding and decoding.
- Support for additional symbols beyond Base64's limited character set.
- Suitable for environments requiring more flexible encoding mechanisms.

## Installation

Since SimpleSix64 is not yet uploaded to Maven Central Repository, you can install it locally by cloning this repository and installing it with Maven.

### Steps:
1. Clone this repository:
    ```bash
    git clone https://github.com/sultansagitov/simplesix64.git
    ```
2. Navigate to the directory:
    ```bash
    cd simplesix64
    ```
3. Install it to your local Maven repository:
    ```bash
    mvn clean install
    ```

### Maven Dependency

After installing the project locally, you can add SimpleSix64 as a dependency in your own project by including the following in your `pom.xml`:

```xml
<dependency>
    <groupId>net.result.simplesix64</groupId>
    <artifactId>simple64</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Encoding and Decoding

SimpleSix64 provides simple API methods to encode and decode strings.

#### Encoding a String
```java
import net.result.simplesix64.SimpleSix64;

public class Main {
    public static void main(String[] args) {
        String input = "Your string to encode";
        String encoded = SimpleSix64.encode(input);
        System.out.println("Encoded: " + Arrays.toString(encoded));
    }
}
```

#### Decoding a String
```java
import net.result.simplesix64.SimpleSix64;

public class Main {
    public static void main(String[] args) {
        String encoded = "Your encoded string";
        String decoded = SimpleSix64.decode(encoded);
        System.out.println("Decoded: " + decoded);
    }
}
```

### Case-Insensitive Encoding Example
SimpleSix64 supports case-insensitive encoding, which means that both upper and lower case letters are treated equally in the encoding process.

```java
String encoded = SimpleSix64.encode("SampleText");
String decoded = SimpleSix64.decode(encoded.toLowerCase()); // Works the same if decoded as upper or mixed case
```

## Building

To build the project, simply run:

```bash
mvn package
```

This will generate a JAR file in the `target` directory.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

If you wish to contribute to this project, feel free to open issues or submit pull requests!

## Contact

For any questions or concerns, feel free to reach out to me:
- GitHub: [sultansagitov](https://github.com/sultansagitov)
