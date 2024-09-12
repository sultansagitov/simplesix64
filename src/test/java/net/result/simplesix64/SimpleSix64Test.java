package net.result.simplesix64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class SimpleSix64Test {
    @Test
    public void simpleSix64Test() {
        for(String string : List.of("hello WORLD 123", "helloworld", "helloeeeeworld", "tttt:jhhh;tttt:jhhh;tttt:jhhh;")){
            test(string);
        }
    }

    private static void test(String string) {
        System.out.println("Original: " + string);
        byte[] encoded = SimpleSix64.encode(string);
        System.out.println("Encoded: " + Arrays.toString(encoded));
        String decoded = SimpleSix64.decode(encoded);
        System.out.println("Decoded: " + decoded);

        Assertions.assertEquals(string.toLowerCase(), decoded);
    }
}