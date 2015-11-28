package com.marian;

import java.io.IOException;

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

    public void generateKeys(int keyLength) {
        MyRSAKeysGenerator.generateKeys(keyLength, primeGenerator, this);
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
}
