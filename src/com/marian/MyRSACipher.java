package com.marian;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
