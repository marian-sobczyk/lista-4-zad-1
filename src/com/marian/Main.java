package com.marian;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args[0].equals("gen")) {
            generateKeysCommand(args);
        } else if (args[0].equals("enc")) {
            encryptionCommand(args);
        } else if (args[0].equals("dec")) {
            decryptionCommand(args);
        }

    }

    private static void decryptionCommand(String[] args) {
        try {
            String keyPath = args[1];
            String source = args[2];
            String destination = args[3];
            decode(keyPath, source, destination);
        } catch (NumberFormatException e) {
            System.out.println("Błędne dane");
        } catch (IOException e) {
            System.out.println("Nie ma takiego pliku");
        } catch (InterruptedException e) {
            System.out.println("Nieudane szyfrowanie");
        }
    }

    private static void decode(String keyPath, String source, String destination) throws IOException, InterruptedException {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        MyRSACipher cipher = new MyRSACipher(generator);
        cipher.openPrivateKey(keyPath);
        cipher.CRTdecode(source, destination);
    }

    private static void encryptionCommand(String[] args) {
        try {
            String keyPath = args[1];
            String source = args[2];
            String destination = args[3];
            encode(keyPath, source, destination);
        } catch (NumberFormatException e) {
            System.out.println("Błędne dane");
        } catch (IOException e) {
            System.out.println("Nie ma takiego pliku");
        } catch (InterruptedException e) {
            System.out.println("Nieudane szyfrowanie");
        }
    }

    private static void encode(String keyPath, String source, String destination) throws IOException, InterruptedException {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        MyRSACipher cipher = new MyRSACipher(generator);
        cipher.openPublicKey(keyPath);
        cipher.CRTencode(source, destination);
    }

    private static void generateKeysCommand(String[] args) {
        try {
            int k = Integer.parseInt(args[1]);
            int d = Integer.parseInt(args[2]);
            String path = args[3];
            generateKeys(k, d, path);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Błędna liczba danych");
        } catch (NumberFormatException e) {
            System.out.println("Błędne dane");
        }

    }

    private static void generateKeys(int k, int d, String path) {
        PrimeRandomNumberGenerator generator = new PrimeRandomNumberGenerator();
        MyRSACipher cipher = new MyRSACipher(generator);
        cipher.generateKeys(d, k);
        cipher.saveKeys(path);
    }

}
