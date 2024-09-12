package net.result.simplesix64;

import java.io.ByteArrayOutputStream;

public class SimpleSix64 {
    // A custom character set including digits, lowercase letters, symbols, and a terminator '~'
    private static final String CHARSET =
                    "0123456789" +                   // Digits
                    "abcdefghijklmnopqrstuvwxyz" +   // Letters
                    "!@$%&*()_+-={}[]:;\"'<>?,./ " + // Symbols
                    "~";                             // Terminator character

    /**
     * Encodes a string into a custom 6-bit format using the defined CHARSET.
     * @param data The input string to encode
     * @return A byte array representing the encoded data
     */
    public static byte[] encode(String data) {
        // Terminator character is the last character in the CHARSET
        String terminator = CHARSET.substring(63);

        // A byte stream to hold the encoded bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Check if the input data contains the terminator character, which is not allowed
        if (data.contains(terminator))
            throw new IllegalArgumentException("Don't use '" + terminator + "' symbol here");

        // Split the input string into chunks of 4 characters
        String[] stringList = splitString(data.toLowerCase(), 4);

        // Flag to indicate whether a terminator should be added at the end
        boolean addTerminator = true;

        // Iterate over each chunk of 4 characters
        for (String string : stringList) {
            // Encode each character of the string chunk to its 6-bit representation
            byte s1 = (byte) CHARSET.indexOf(string.charAt(0));
            byte s2 = (byte) CHARSET.indexOf(string.charAt(1));
            byte s3 = (byte) CHARSET.indexOf(string.charAt(2));
            byte s4 = (byte) CHARSET.indexOf(string.charAt(3));

            // Combine the 6-bit characters into 8-bit bytes
            byte b1 = (byte) (((s1 & 0b00111111) << 2) + ((s2 & 0b00110000) >> 4));
            byte b2 = (byte) (((s2 & 0b00001111) << 4) + ((s3 & 0b00111100) >> 2));
            byte b3 = (byte) (((s3 & 0b00000011) << 6) + ((s4 & 0b00111111)));

            // Write the combined bytes to the output stream
            byteArrayOutputStream.write(b1);

            // If the terminator character is encountered, stop processing further
            if (s1 == 63) {
                addTerminator = false;
                break;
            }

            byteArrayOutputStream.write(b2);
            if (s2 == 63) {
                addTerminator = false;
                break;
            }

            byteArrayOutputStream.write(b3);
            if (s3 == 63 || s4 == 63) {
                addTerminator = false;
                break;
            }
        }

        // If a terminator was not encountered, add a byte indicating the end
        if (addTerminator)
            byteArrayOutputStream.write(255);  // 255 as a terminator

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Decodes a byte array back into the original string using the custom 6-bit format.
     * @param encoded The byte array to decode
     * @return The decoded string
     */
    public static String decode(byte[] encoded) {
        // Check for minimum valid length of the byte array
        if (encoded.length < 2)
            throw new IllegalArgumentException("byte array should be bigger than 2");

        // A string builder to hold the decoded characters
        StringBuilder builder = new StringBuilder();
        int i = 0;

        // Process each set of 3 bytes (which represent 4 characters in 6-bit format)
        while (i < encoded.length) {
            byte b1 = encoded[i];
            byte b2 = encoded[i + 1];
            byte b3 = encoded[i + 2];

            // Decode the bytes back into 6-bit characters
            byte s1 = (byte) (((b1 & 0b11111100) >> 2));
            byte s2 = (byte) (((b1 & 0b00000011) << 4) + ((b2 & 0b11110000) >> 4));
            byte s3 = (byte) (((b2 & 0b00001111) << 2) + ((b3 & 0b11000000) >> 6));
            byte s4 = (byte) (                           ((b3 & 0b00111111)));

            // Append the decoded characters to the string builder
            builder.append(CHARSET.charAt(s1));
            builder.append(CHARSET.charAt(s2));
            builder.append(CHARSET.charAt(s3));
            builder.append(CHARSET.charAt(s4));
            i += 3;  // Move to the next 3 bytes
        }

        // Extract the final string, excluding the terminator character (~)
        String result = builder.toString();
        return result.substring(0, result.indexOf(CHARSET.charAt(63)));
    }

    /**
     * Splits the input string into equal-sized chunks, padding with the terminator if necessary.
     * @param str The input string to split
     * @param chunkSize The size of each chunk (typically 4 characters)
     * @return An array of string chunks
     */
    private static String[] splitString(String str, int chunkSize) {
        int length = str.length();
        // Calculate the padded length to ensure complete chunks
        int paddedLength = ((length + chunkSize - 1) / chunkSize) * chunkSize;
        StringBuilder paddedStr = new StringBuilder(str);

        // Add terminator characters to pad the input string
        while (paddedStr.length() < paddedLength)
            paddedStr.append(CHARSET.substring(63));

        // Split the string into chunks of the specified size
        int numberOfChunks = paddedStr.length() / chunkSize;
        String[] chunks = new String[numberOfChunks];

        for (int i = 0; i < numberOfChunks; i++) {
            int start = i * chunkSize;
            int end = start + chunkSize;
            chunks[i] = paddedStr.substring(start, end);
        }

        return chunks;
    }
}
