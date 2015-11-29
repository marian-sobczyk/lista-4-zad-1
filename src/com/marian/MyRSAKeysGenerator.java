package com.marian;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by marian on 29.11.2015.
 */
public class MyRSAKeysGenerator {
    private static final BigInteger TWO = new BigInteger("2");

    public static void generateKeys(int keyLength, int numberOfFactors, PrimeRandomNumberGenerator primeGenerator, MyRSACipherDelegate delegate) {
        ArrayList<BigInteger> bigPrimeNumbers = generateInequalBigPrimeNumbers(keyLength, numberOfFactors, primeGenerator);
        MySecureRandom randomGenrator = new MySecureRandom();
        BigInteger n = generateFactor(bigPrimeNumbers);
        BigInteger phi = phi(bigPrimeNumbers);
        BigInteger e = getCoprimeNumber(phi, randomGenrator);
        BigInteger d = EquationSolver.modularLinearEquation(e, BigInteger.ONE, phi);
        delegate.setPublicKey(new MyRSAKey(e, n, MyKeyType.PublicKey, bigPrimeNumbers));
        delegate.setPrivateKey(new MyRSAKey(d, n, MyKeyType.PrivateKey, bigPrimeNumbers));
    }

    private static BigInteger phi(ArrayList<BigInteger> bigPrimeNumbers) {
        BigInteger value = BigInteger.ONE;
        for (BigInteger number : bigPrimeNumbers) {
            BigInteger tempNumber = number.subtract(BigInteger.ONE);
            value = value.multiply(tempNumber);
        }

        return value;
    }

    private static BigInteger generateFactor(ArrayList<BigInteger> bigPrimeNumbers) {
        BigInteger value = BigInteger.ONE;
        for (BigInteger number : bigPrimeNumbers) {
            value = value.multiply(number);
        }

        return value;
    }

    private static ArrayList<BigInteger> generateInequalBigPrimeNumbers(int keyLength, int numberOfFactors, PrimeRandomNumberGenerator primeGenerator) {

        ArrayList<BigInteger> primeNumbers;
        boolean done = false;
        do {
            primeNumbers = primeGenerator.getPrimes(numberOfFactors, keyLength);
            ArrayList<BigInteger> tempPrimeNumbers = (ArrayList<BigInteger>) primeNumbers.clone();
            for (BigInteger number : primeNumbers) {
                tempPrimeNumbers.remove(number);
            }
            if (tempPrimeNumbers.size() == 0) {
                done = true;
            }
        } while (!done);

        return primeNumbers;
    }

    private static BigInteger getCoprimeNumber(BigInteger number, MySecureRandom randomGenrator) {
        BigInteger halfLengthNumber = new BigInteger(number.bitLength() / 2, randomGenrator);

        BigInteger aNumber;
        do {
            aNumber = randomGenrator.uniformRandomWithRandomLength(TWO, halfLengthNumber);
        } while (!(aNumber.gcd(number).equals(BigInteger.ONE) && aNumber.mod(TWO).equals(BigInteger.ONE)));

        return aNumber;
    }

}
