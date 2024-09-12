package net.result.simplesix64;

import java.io.ByteArrayOutputStream;

public class SimpleSix64 {
    private static final String CHARSET =
            "0123456789" +                   // Digits
            "abcdefghijklmnopqrstuvwxyz" +   // Letters
            "!@$%&*()_+-={}[]:;\"'<>?,./ " + // Symbols
            "~";                             // Terminator character

    private static final int BYTE_SIZE = 8;
    private static final int CHAR_BITS = 6;

    public static byte[] encode(String data) {
        String terminator = CHARSET.substring(63);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if (data.contains(terminator))
            throw new IllegalArgumentException("Don't use '" + terminator + "' symbol here");

        String[] stringList = splitString(data.toLowerCase(), 4);
        int index = 0;

        boolean addTerminator = true;

        for (String string : stringList) {
            // string.length() == 4

            // 00xxxxxx
            byte s1 = (byte) CHARSET.indexOf(string.charAt(0));
            byte s2 = (byte) CHARSET.indexOf(string.charAt(1));
            byte s3 = (byte) CHARSET.indexOf(string.charAt(2));
            byte s4 = (byte) CHARSET.indexOf(string.charAt(3));

            byte b1 = (byte) (((s1 & 0b00111111) << 2) + ((s2 & 0b00110000) >> 4));
            byte b2 = (byte) (((s2 & 0b00001111) << 4) + ((s3 & 0b00111100) >> 2));
            byte b3 = (byte) (((s3 & 0b00000011) << 6) + ((s4 & 0b00111111) >> 0));

            byteArrayOutputStream.write(b1);

            if (s1 >= 63) {
                addTerminator = false;
                break;
            }

            byteArrayOutputStream.write(b2);

            if (s2 >= 63) {
                addTerminator = false;
                break;
            }

            byteArrayOutputStream.write(b3);

            if (s3 >= 63 || s4 >= 63) {
                addTerminator = false;
                break;
            }
        }

        if (addTerminator)
            byteArrayOutputStream.write(255);

        return byteArrayOutputStream.toByteArray();
    }

    public static String decode(byte[] encoded) {
        String terminator = CHARSET.substring(63);

        if (encoded.length < 2)
            throw new IllegalArgumentException("byte array should be bigger than 2");

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (; i < encoded.length; i += 3) {
            byte b1 = encoded[i];
            byte b2 = encoded[i + 1];
            byte b3 = encoded[i + 2];

            // 00xxxxxx
            byte s1 = (byte) (((b1 & 0b11111100) >> 2)                           );
            byte s2 = (byte) (((b1 & 0b00000011) << 4) + ((b2 & 0b11110000) >> 4));
            byte s3 = (byte) (((b2 & 0b00001111) << 2) + ((b3 & 0b11000000) >> 6));
            byte s4 = (byte) (                           ((b3 & 0b00111111) << 0));

            builder.append(CHARSET.charAt(s1));
            builder.append(CHARSET.charAt(s2));
            builder.append(CHARSET.charAt(s3));
            builder.append(CHARSET.charAt(s4));
        }


        String result = builder.toString();
        return result.substring(0,result.indexOf(CHARSET.charAt(63)));
    }

    public static String[] splitString(String str, int chunkSize) {
        int length = str.length();
        int paddedLength = ((length + chunkSize - 1) / chunkSize) * chunkSize;
        StringBuilder paddedStr = new StringBuilder(str);
        
        while (paddedStr.length() < paddedLength)
            paddedStr.append(CHARSET.substring(63));

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
