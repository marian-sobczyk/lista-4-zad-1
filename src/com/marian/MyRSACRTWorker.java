package com.marian;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;

/**
 * Created by marian on 29.11.2015.
 */
public class MyRSACRTWorker extends Thread {

    private final BigInteger message;
    private final BigInteger factor;
    private final CountDownLatch doneSignal;
    private final BigInteger key;
    private final BigInteger n;
    public BigInteger valueToSum;

    public MyRSACRTWorker(BigInteger message, BigInteger factor, BigInteger keyValue, BigInteger n, CountDownLatch doneSignal) {
        this.message = message;
        this.factor = factor;
        this.doneSignal = doneSignal;
        this.key = keyValue;
        this.n = n;
    }

    @Override
    public void run() {
        BigInteger reversedFactor = key.mod(factor.subtract(BigInteger.ONE));
        BigInteger partOfMessage = message.modPow(reversedFactor, factor);
        BigInteger ni = n.divide(factor);
        BigEuclid euclid = BigEuclid.extendedEuclid(factor, ni);
        valueToSum = euclid.getY().multiply(ni).multiply(partOfMessage);
        doneSignal.countDown();
    }
}
