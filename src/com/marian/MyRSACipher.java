package com.marian;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by marian on 28.11.2015.
 */
public class MyRSACipher {
    private static final BigInteger TWO = new BigInteger("2");
    private final PrimeRandomNumberGenerator primeGenerator;
    private final MySecureRandom randomGenrator;
    private MyRSAKey privateKey;
    private MyRSAKey publicKey;

    public MyRSACipher(PrimeRandomNumberGenerator generator) {
        this.primeGenerator = generator;
        this.randomGenrator = new MySecureRandom();
    }

    public void generateKeys(int keyLength) {
        ArrayList<BigInteger> bigPrimeNumbers = generateInequalBigPrimeNumbers(keyLength);
        BigInteger p = bigPrimeNumbers.get(0);
        BigInteger q = bigPrimeNumbers.get(1);
        BigInteger n = p.multiply(q);
        BigInteger phi = phi(p, q);
        BigInteger e = getCoprimeNumber(phi);
        BigInteger d = EquationSolver.modularLinearEquation(e, BigInteger.ONE, phi);
        publicKey = new MyRSAKey(e, n, MyKeyType.PublicKey);
        privateKey = new MyRSAKey(d, n, MyKeyType.PrivateKey);
    }

    private BigInteger getCoprimeNumber(BigInteger number) {
        BigInteger halfLengthNumber = new BigInteger(number.bitLength()/2, randomGenrator);

        BigInteger aNumber;
        do {
            aNumber = randomGenrator.uniformRandomWithRandomLength(TWO, halfLengthNumber);
        } while (!aNumber.gcd(number).equals(BigInteger.ONE));

        return aNumber;
    }

    private BigInteger phi(BigInteger p, BigInteger q) {
        BigInteger p1 = p.subtract(BigInteger.ONE);
        BigInteger q1 = q.subtract(BigInteger.ONE);

        return p1.multiply(q1);
    }

    private ArrayList<BigInteger> generateInequalBigPrimeNumbers(int keyLength) {

        ArrayList<BigInteger> primeNumbers;
        do {
            primeNumbers = primeGenerator.getPrimes(2, keyLength);
        } while (primeNumbers.get(0).equals(primeNumbers.get(1)));

        return primeNumbers;
    }
}
