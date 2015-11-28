package com.marian;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;

/**
 * Created by marian on 27.11.2015.
 */
public class PrimeNumberWorker extends Thread {

    private static final BigInteger THREE = new BigInteger("3");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger ZERO = new BigInteger("0");
    private final CountDownLatch doneSignal;
    private final SecureRandom randomGenerator;
    private final int bitLength;
    private final int numberOfTests;
    public BigInteger prime;

    public PrimeNumberWorker(CountDownLatch doneSignal, SecureRandom randomGenerator, int bitLength) {
        this.doneSignal = doneSignal;
        this.randomGenerator = randomGenerator;
        this.bitLength = bitLength;
        this.numberOfTests = (bitLength - 1) / 2 + 1;
    }

    @Override
    public void run() {
        boolean done = false;
        BigInteger numberToTest = null;
        while (!done) {
            numberToTest = new BigInteger(bitLength, randomGenerator);

            boolean isOdd = numberToTest.testBit(0);
            boolean isNegative = numberToTest.testBit(numberToTest.bitLength());
            if (!isOdd || isNegative) {
                continue;
            }

            done = isProbablePrime(numberToTest, numberOfTests);
        }

        prime = numberToTest;
        doneSignal.countDown();
    }

    private boolean isProbablePrime(BigInteger n, int k) {
        if (n.compareTo(THREE) < 0)
            return true;
        int s = 0;
        BigInteger d = n.subtract(ONE);
        while (d.mod(TWO).equals(ZERO)) {
            s++;
            d = d.divide(TWO);
        }
        for (int i = 0; i < k; i++) {
            BigInteger a = uniformRandom(TWO, n.subtract(ONE));
            BigInteger x = a.modPow(d, n);
            if (x.equals(ONE) || x.equals(n.subtract(ONE)))
                continue;
            int r = 1;
            for (; r < s; r++) {
                x = x.modPow(TWO, n);
                if (x.equals(ONE))
                    return false;
                if (x.equals(n.subtract(ONE)))
                    break;
            }
            if (r == s)
                return false;
        }
        return true;
    }

    private BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), randomGenerator);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }
}
