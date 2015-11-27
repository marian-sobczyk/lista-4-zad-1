package com.marian;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;

/**
 * Created by marian on 27.11.2015.
 */
public class PrimeNumberWorker extends Thread {

    private final CountDownLatch doneSignal;
    private final SecureRandom randomGenerator;
    private final int bitLength;
    public BigInteger prime;

    public PrimeNumberWorker(CountDownLatch doneSignal, SecureRandom randomGenerator, int bitLength) {
        this.doneSignal = doneSignal;
        this.randomGenerator = randomGenerator;
        this.bitLength = bitLength;
    }

    @Override
    public void run() {
        boolean done = false;
        BigInteger numberToTest = null;
        while (!done) {
            numberToTest = getRandomBigInteger();

            boolean isOdd = numberToTest.testBit(0);
            boolean isNegative = numberToTest.testBit(numberToTest.bitLength());
            if (!isOdd || isNegative) {
                continue;
            }


            done = true;
        }

        prime = numberToTest;
        doneSignal.countDown();
    }

    private BigInteger getRandomBigInteger() {
        byte[] bytes = new byte[bitLength / 8];
        randomGenerator.nextBytes(bytes);
        return new BigInteger(bytes);
    }
}
