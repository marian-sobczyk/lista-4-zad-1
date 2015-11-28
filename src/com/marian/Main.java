package com.marian;

import java.math.BigInteger;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int length = 1024;

        int lengths[] = {256, 512, 1024, 2048, 3072, 7680};

        for (int k = 1; k <= 8; k++ ){
            for (int i = 0; i < lengths.length; i++) {
                System.out.println("k = " + k + ", length = " + lengths[i]);
                long startTime = System.currentTimeMillis();
                test(k, lengths[i]);
                long endTime = System.currentTimeMillis() - startTime;
                System.out.println("Time: " + (double) endTime / 60 + "s");
                System.out.println();
            }
        }

    }

    private static void test(int k, int length) {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        ArrayList<BigInteger> primes = generator.getPrimes(k, length);

        for (BigInteger integer : primes) {
            System.out.println(integer.abs().toString());
        }
    }
}
