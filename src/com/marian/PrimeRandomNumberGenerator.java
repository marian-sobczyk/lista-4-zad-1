package com.marian;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by marian on 27.11.2015.
 */
public class PrimeRandomNumberGenerator {

    private SecureRandom randomGenerator;

    public ArrayList<BigInteger> getPrimes(int numberOfPrimes, int bitLength) {
        randomGenerator = new SecureRandom();
        CountDownLatch doneSignal = new CountDownLatch(numberOfPrimes);
        ArrayList<PrimeNumberWorker> workers = createWorkers(numberOfPrimes, bitLength, doneSignal);
        startWork(workers);

        try {
            doneSignal.await();
            return getPrimes(workers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<BigInteger> getPrimes(ArrayList<PrimeNumberWorker> workers) {
        ArrayList<BigInteger> primes = new ArrayList<BigInteger>();
        for (PrimeNumberWorker worker : workers) {
            primes.add(worker.prime);
        }
        return primes;
    }

    private void startWork(ArrayList<PrimeNumberWorker> workers) {
        for (PrimeNumberWorker worker : workers) {
            worker.start();
        }
    }

    private ArrayList<PrimeNumberWorker> createWorkers(int numberOfPrimes, int bitLength, CountDownLatch doneSignal) {
        ArrayList<PrimeNumberWorker> workers = new ArrayList<PrimeNumberWorker>();
        for (int i = 0; i < numberOfPrimes; i++) {
            PrimeNumberWorker worker = new PrimeNumberWorker(doneSignal, randomGenerator, bitLength);
            workers.add(worker);
        }
        return workers;
    }
}
