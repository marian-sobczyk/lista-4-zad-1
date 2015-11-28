package com.marian;

/**
 * Created by marian on 29.11.2015.
 */
public interface MyRSACipherDelegate {
    void setPublicKey(MyRSAKey publicKey);

    void setPrivateKey(MyRSAKey privateKey);
}
