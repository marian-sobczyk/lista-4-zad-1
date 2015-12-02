package com.marian;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by marian on 28.11.2015.
 */
public class MyRSACipher implements MyRSACipherDelegate {
    private final PrimeRandomNumberGenerator primeGenerator;
    private MyRSAKey privateKey;
    private MyRSAKey publicKey;

    public MyRSACipher(PrimeRandomNumberGenerator generator) {
        this.primeGenerator = generator;
    }

    public void generateKeys(int keyLength, int numberOfFactors) {
        MyRSAKeysGenerator.generateKeys(keyLength, numberOfFactors, primeGenerator, this);
    }

    @Override
    public void setPublicKey(MyRSAKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public void setPrivateKey(MyRSAKey privateKey) {
        this.privateKey = privateKey;
    }

    public void saveKeys(String path) {
        privateKey.saveKey(path);
        publicKey.saveKey(path);
    }

    public void openKeys(String path) {
        try {
            privateKey = new MyRSAKey(path, MyKeyType.PrivateKey);
            publicKey = new MyRSAKey(path, MyKeyType.PublicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encode(String sourcePath, String destinationPath) throws IOException {
        BigInteger M = getMessageInteger(sourcePath);
        M = M.modPow(publicKey.value, publicKey.n);
        saveOutput(destinationPath, M);
    }

    private void saveOutput(String destinationPath, BigInteger m) throws IOException {
        byte[] outputData = m.toByteArray();
        FileOutputStream fos = new FileOutputStream(destinationPath);
        fos.write(outputData);
        fos.close();
    }

    private BigInteger getMessageInteger(String sourcePath) throws IOException {
        Path inputPath = Paths.get(sourcePath);
        byte[] inputData = Files.readAllBytes(inputPath);
        return new BigInteger(inputData);
    }

    public void decode(String sourcePath, String destinationPath) throws IOException {
        BigInteger M = getMessageInteger(sourcePath);
        M = M.modPow(privateKey.value, privateKey.n);
        saveOutput(destinationPath, M);
    }

    public void CRTencode(String sourcePath, String destinationPath) throws IOException, InterruptedException {
        BigInteger M = getMessageInteger(sourcePath);
        CountDownLatch doneSignal = new CountDownLatch(publicKey.numberOfFactors());
        MyRSAKey publicKey = this.publicKey;
        ArrayList<MyRSACRTWorker> workers = createWorkers(M, publicKey, doneSignal);
        startWorkers(workers);
        doneSignal.await();
        M = getMessage(workers, publicKey);
        saveOutput(destinationPath, M);
    }

    private BigInteger getMessage(ArrayList<MyRSACRTWorker> workers, MyRSAKey key) {
        BigInteger M;
        M = BigInteger.ZERO;
        for (MyRSACRTWorker worker : workers) {
            M = M.add(worker.valueToSum);
        }
        M = M.mod(key.n);
        return M;
    }

    public void CRTdecode(String sourcePath, String destinationPath) throws IOException, InterruptedException {
        BigInteger M = getMessageInteger(sourcePath);
        MyRSAKey privateKey = this.privateKey;
        CountDownLatch doneSignal = new CountDownLatch(privateKey.numberOfFactors());
        ArrayList<MyRSACRTWorker> workers = createWorkers(M, privateKey, doneSignal);
        startWorkers(workers);
        doneSignal.await();
        M = getMessage(workers, privateKey);
        saveOutput(destinationPath, M);
    }

    private void startWorkers(ArrayList<MyRSACRTWorker> workers) {
        for (MyRSACRTWorker worker : workers) {
            worker.start();
        }
    }

    private ArrayList<MyRSACRTWorker> createWorkers(BigInteger m, MyRSAKey key, CountDownLatch doneSignal) {
        ArrayList<MyRSACRTWorker> workers = new ArrayList<MyRSACRTWorker>();
        for (int i = 0; i < key.numberOfFactors(); i++) {
            workers.add(new MyRSACRTWorker(m, key.factor(i), key.value, key.n, doneSignal));
        }
        return workers;
    }

    public void openPublicKey(String keyPath) throws IOException {
        publicKey = new MyRSAKey(keyPath, MyKeyType.PublicKey);
    }

    public void openPrivateKey(String keyPath) throws IOException {
        privateKey = new MyRSAKey(keyPath, MyKeyType.PrivateKey);
    }
}
