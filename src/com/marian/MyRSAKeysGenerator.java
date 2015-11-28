package com.marian;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by marian on 29.11.2015.
 */
public class MyRSAKeysGenerator {
    private static final BigInteger TWO = new BigInteger("2");

    public static void generateKeys(int keyLength, PrimeRandomNumberGenerator primeGenerator, MyRSACipherDelegate delegate) {
        ArrayList<BigInteger> bigPrimeNumbers = generateInequalBigPrimeNumbers(keyLength, primeGenerator);
        MySecureRandom randomGenrator = new MySecureRandom();
        BigInteger p = bigPrimeNumbers.get(0);
        BigInteger q = bigPrimeNumbers.get(1);
        BigInteger n = p.multiply(q);
        BigInteger phi = phi(p, q);
        BigInteger e = getCoprimeNumber(phi, randomGenrator);
        BigInteger d = EquationSolver.modularLinearEquation(e, BigInteger.ONE, phi);
        delegate.setPublicKey(new MyRSAKey(e, n, MyKeyType.PublicKey));
        delegate.setPrivateKey(new MyRSAKey(d, n, MyKeyType.PrivateKey));
    }

    private static ArrayList<BigInteger> generateInequalBigPrimeNumbers(int keyLength, PrimeRandomNumberGenerator primeGenerator) {

        ArrayList<BigInteger> primeNumbers;
        do {
            primeNumbers = primeGenerator.getPrimes(2, keyLength);
        } while (primeNumbers.get(0).equals(primeNumbers.get(1)));

        return primeNumbers;
    }

    private static BigInteger getCoprimeNumber(BigInteger number, MySecureRandom randomGenrator) {
        BigInteger halfLengthNumber = new BigInteger(number.bitLength() / 2, randomGenrator);

        BigInteger aNumber;
        do {
            aNumber = randomGenrator.uniformRandomWithRandomLength(TWO, halfLengthNumber);
        } while (!aNumber.gcd(number).equals(BigInteger.ONE));

        return aNumber;
    }

    private static BigInteger phi(BigInteger p, BigInteger q) {
        BigInteger p1 = p.subtract(BigInteger.ONE);
        BigInteger q1 = q.subtract(BigInteger.ONE);

        return p1.multiply(q1);
    }
}
