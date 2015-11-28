package com.marian;

import java.math.BigInteger;

/**
 * Created by marian on 28.11.2015.
 */
public class BigEuclid {

    private final BigInteger d;
    private final BigInteger x;
    private final BigInteger y;

    BigEuclid(BigInteger d, BigInteger x, BigInteger y) {
        this.d = d;
        this.x = x;
        this.y = y;
    }

    public static BigEuclid extendedEuclid(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigEuclid(a, BigInteger.ONE, BigInteger.ZERO);
        } else {
            BigEuclid temp = extendedEuclid(b, a.mod(b));
            BigInteger aDivB = a.divide(b);
            BigInteger aDivBMultY = aDivB.multiply(temp.y);

            return new BigEuclid(temp.d, temp.y, temp.x.subtract(aDivBMultY));
        }
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }
}
