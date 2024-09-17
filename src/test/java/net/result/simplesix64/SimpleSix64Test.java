package net.result.simplesix64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

public class SimpleSix64Test {
    @Test
    public void simpleSix64Test() {
        for(String string : List.of("hello WORLD 123", "helloworld", "helloeeeeworld", "sandnode-type:5;", "", "1")){
            encodedecode(string);
        }
    }

    private static void encodedecode(String string) {
        System.out.println("Original: " + string);
        byte[] encoded = SimpleSix64.encode(string);
        decode(string, encoded);
    }

    private static void decode(String string, byte[] bytes) {
        System.out.println("In bytes: " + Arrays.toString(bytes));
        String decoded = SimpleSix64.decode(bytes);
        System.out.println("Decoded: " + decoded);
        Assertions.assertEquals(string.toLowerCase(), decoded);
    }
}