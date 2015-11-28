package com.marian;

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
}
