package com.marian;

public class Main {

    public static void main(String[] args) {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        MyRSACipher cipher = new MyRSACipher(generator);
        cipher.generateKeys(1024);
        cipher.saveKeys("/Users/marian/Desktop");
    }
    
}
