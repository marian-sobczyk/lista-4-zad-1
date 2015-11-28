package com.marian;

import java.math.BigInteger;

/**
 * Created by marian on 28.11.2015.
 */
public class EquationSolver {

    public static BigInteger modularLinearEquation(BigInteger a, BigInteger b, BigInteger n) {
        BigEuclid euclidSolution = BigEuclid.extendedEuclid(a, n);
        if (euclidSolution.getD().mod(b).equals(BigInteger.ZERO)) {
            BigInteger bDivD = b.divide(euclidSolution.getD());
            BigInteger x0 = euclidSolution.getX().multiply(bDivD);
            x0 = x0.mod(n);

            return x0;
        } else {
            return null;
        }
    }
}
