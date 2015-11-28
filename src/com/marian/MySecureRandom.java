package com.marian;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by marian on 28.11.2015.
 */
public class MySecureRandom extends SecureRandom {

    public BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), this);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }

    public BigInteger uniformRandomWithRandomLength(BigInteger bottom, BigInteger top) {
        BigInteger res;
        do {
            res = new BigInteger(this.nextInt(top.bitLength()) + 1, this);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }
}
