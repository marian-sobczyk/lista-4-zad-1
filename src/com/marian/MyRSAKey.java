package com.marian;

import java.io.*;
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

    public MyRSAKey(String path, MyKeyType keyType) throws IOException {
        this.keyType = keyType;
        BufferedReader in = new BufferedReader(new FileReader(path + "/" + getName()));
        String line = in.readLine();
        value = new BigInteger(line);
        line = in.readLine();
        n = new BigInteger(line);
        in.close();
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
