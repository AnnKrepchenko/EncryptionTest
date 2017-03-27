package encrypt.test.com.encryptiontext.crypto;

import android.util.Base64;

import com.scottyab.aescrypt.AESCrypt;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ann on 3/27/17.
 */

public class CryptoManager {

    private static final String TAG = CryptoManager.class.getSimpleName();

    private static final String UTF8 = "UTF-8";
    private static final int KEY_SIZE = 256;
    private static final String ALGORITHM_TYPE_AES = "AES";
    private static final String ALGORITHM_TYPE_RSA = "RSA";
    private static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    private byte[] IV;

    private SecretKeySpec secretKeySpec;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private static byte[] key = new byte[0];


    public String doEncrypt(String text) {
        createSecretKeySpec();
        return encrypt(text);
    }

    public String doDecrypt(String text) {
        return decrypt(text);
    }

    private SecretKeySpec getRandomSecureKey() {
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance(ALGORITHM_TYPE_AES);
            keyGen.init(KEY_SIZE);
            byte[] randomSecureKey = keyGen.generateKey().getEncoded();
            encryptKey(randomSecureKey);
            return new SecretKeySpec(randomSecureKey, 0, 16, ALGORITHM_TYPE_AES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createIV() {
        IV = new byte[16];
        new Random().nextBytes(IV);
    }

    private void createSecretKeySpec() {
        secretKeySpec = getRandomSecureKey();
    }

    private String encrypt(String data) {
        byte[] result = new byte[0];
        byte[] base64EncryptedMessage;
        try {
            base64EncryptedMessage = data.getBytes(UTF8);
            createIV();
            result = AESCrypt.encrypt(secretKeySpec, IV, base64EncryptedMessage);
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(result, Base64.NO_WRAP);
    }

    private String decrypt(String data) {
        byte[] result = new byte[0];
        byte[] base64EncryptedMessage = Base64.decode(data, Base64.NO_WRAP);
        SecretKeySpec spec = new SecretKeySpec(decryptKey(), 0, 16, ALGORITHM_TYPE_AES);
        try {
            result = AESCrypt.decrypt(spec, IV, base64EncryptedMessage);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return new String(result);
    }

    private void encryptKey(byte[] bytes) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_TYPE_RSA);
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            key = cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }

    private byte[] decryptKey() {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}


