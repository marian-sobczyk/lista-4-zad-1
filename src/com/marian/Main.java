package com.marian;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        MyRSACipher cipher = new MyRSACipher(generator);
        cipher.generateKeys(256, 8);
//        cipher.openKeys("/Users/marian/Desktop");
        try {
            cipher.encode("/Users/marian/Desktop/test.txt", "/Users/marian/Desktop/test2.txt");
            cipher.decode("/Users/marian/Desktop/test2.txt", "/Users/marian/Desktop/test3.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
