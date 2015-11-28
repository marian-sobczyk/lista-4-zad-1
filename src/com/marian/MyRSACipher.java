package com.marian;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by marian on 28.11.2015.
 */
public class MyRSACipher {
    private final PrimeRandomNumberGenerator generator;

    public MyRSACipher(PrimeRandomNumberGenerator generator) {
        this.generator = generator;
    }

    public void generateKeys(int keyLength) {
        ArrayList<BigInteger> bigPrimeNumbers = generateInequalBigPrimeNumbers(keyLength);
        BigInteger p = bigPrimeNumbers.get(0);
        BigInteger q = bigPrimeNumbers.get(1);
        BigInteger n = p.multiply(q);
        BigInteger phi = phi(p, q);
        BigInteger e = getCoprimeNumber(phi);
    }

    private BigInteger getCoprimeNumber(BigInteger number) {

        return null;
    }

    private BigInteger phi(BigInteger p, BigInteger q) {
        BigInteger p1 = p.subtract(BigInteger.ONE);
        BigInteger q1 = q.subtract(BigInteger.ONE);

        return p1.multiply(q1);
    }

    private ArrayList<BigInteger> generateInequalBigPrimeNumbers(int keyLength) {

        ArrayList<BigInteger> primeNumbers;
        do {
            primeNumbers = generator.getPrimes(2, keyLength);
        } while (primeNumbers.get(0).equals(primeNumbers.get(1)));

        return primeNumbers;
    }
}
