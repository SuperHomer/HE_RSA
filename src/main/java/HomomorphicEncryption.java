import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class HomomorphicEncryption {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        // https://www.educative.io/edpresso/what-is-the-rsa-algorithm
        // https://www.baeldung.com/java-rsa
        // https://asecuritysite.com/encryption/hom_rsa

        // TODO
        // Elgamal addition ?

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        BigInteger a = new BigInteger("5");
        BigInteger b = new BigInteger("7");
        BigInteger c = new BigInteger("2");

        System.out.println("A: " + a);
        System.out.println("B: " + b);

        RSAPublicKey rsaPubKey = (RSAPublicKey)(publicKey);
        RSAPrivateKey rsaPrivKey = (RSAPrivateKey)(privateKey);

        BigInteger e = rsaPubKey.getPublicExponent();
        BigInteger n = rsaPubKey.getModulus(); // or rsaPrivKey.getModulus()

        BigInteger d = rsaPrivKey.getPrivateExponent();

        BigInteger encA = a.modPow(e,n);
        System.out.println("Encrypt(A):" + encA);
        BigInteger encB = b.modPow(e,n);
        System.out.println("Encrypt(B):" + encB);

        //System.out.println("Decrpyt(A): " + encA.modPow(d, n));
        //System.out.println("Decrpyt(B): " + encB.modPow(d, n));

        //BigInteger encSumAB = (encA.add(encB)).mod(n);
        //System.out.println("Encrypt(A) + Encrypt(B):" + encSumAB);

        BigInteger encMultAB = (encA.multiply(encB)).mod(n);
        System.out.println("Encrypt(A) * Encrypt(B):" + encMultAB);

        //BigInteger sumAB = encSumAB.modPow(d, n);
        //System.out.println("Decrypt(Encrypt(A) + Encrypt(B)): " + sumAB);

        BigInteger multAB = encMultAB.modPow(d, n);
        System.out.println("Decrypt(Encrypt(A) * Encrypt(B)): " + multAB);

    }
}
