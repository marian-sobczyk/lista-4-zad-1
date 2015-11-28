package com.marian;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * Created by marian on 29.11.2015.
 */
public class MyRSAKey {

    private final BigInteger value;
    private final BigInteger n;
    private final MyKeyType keyType;

    public MyRSAKey(BigInteger value, BigInteger n, MyKeyType keyType) {
        this.value = value;
        this.n = n;
        this.keyType = keyType;
    }

    public void saveKey(String path) {
        try {
            PrintWriter out = new PrintWriter(path + "/" + getName());
            out.println(value.toString());
            out.println(n.toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private String getName() {
        return keyType == MyKeyType.PrivateKey ? "private.key" : "public.key";
    }
}
