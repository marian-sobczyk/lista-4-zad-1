package com.marian;

import java.math.BigInteger;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        ArrayList<BigInteger> primes = generator.getPrimes(2, 1024);

        for (BigInteger integer : primes) {
            System.out.println(integer.toString());
        }

    }
}
