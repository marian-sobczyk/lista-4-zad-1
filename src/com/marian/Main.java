package com.marian;

import java.math.BigInteger;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        ArrayList<BigInteger> primes = generator.getPrimes(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        for (BigInteger integer : primes) {
            System.out.println(integer.abs().toString());
        }

    }
}
