package com.marian;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by marian on 29.11.2015.
 */
public class MyRSAKey {

    public final BigInteger value;
    public final BigInteger n;
    private final MyKeyType keyType;
    private final ArrayList<BigInteger> factors;

    public MyRSAKey(BigInteger value, BigInteger n, MyKeyType keyType, ArrayList<BigInteger> factors) {
        this.value = value;
        this.n = n;
        this.keyType = keyType;
        this.factors = factors;
    }

    public MyRSAKey(String path, MyKeyType keyType) throws IOException {
        this.keyType = keyType;
        BufferedReader in = new BufferedReader(new FileReader(path + "/" + getName()));
        String line = in.readLine();
        value = new BigInteger(line);
        line = in.readLine();
        n = new BigInteger(line);
        in.close();
        factors = null;
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

    public int numberOfFactors() {
        return this.factors.size();
    }

    public BigInteger reversedFactor(int i) {
        return this.reversedFactor(i);
    }

    public BigInteger factor(int i) {
        return this.factors.get(i);
    }
}
