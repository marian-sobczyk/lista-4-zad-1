package com.marian;

import java.math.BigInteger;
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
    private final MySecureRandom randomGenerator;
    private final int bitLength;
    private final int numberOfTests;
    public BigInteger prime;

    public PrimeNumberWorker(CountDownLatch doneSignal, MySecureRandom randomGenerator, int bitLength) {
        int numberOfTests1;
        this.doneSignal = doneSignal;
        this.randomGenerator = randomGenerator;
        this.bitLength = bitLength;
        numberOfTests1 = (bitLength) / 2 + 1;
        numberOfTests1 = numberOfTests1 > 100 ? numberOfTests1 : 100;
        this.numberOfTests = numberOfTests1;
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
            BigInteger a = randomGenerator.uniformRandom(TWO, n.subtract(ONE));
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

}
