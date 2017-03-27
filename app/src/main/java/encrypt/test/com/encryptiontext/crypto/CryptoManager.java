package encrypt.test.com.encryptiontext.crypto;

import android.util.Base64;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ann on 3/27/17.
 */

public class CryptoManager {

    private static final String TAG = CryptoManager.class.getSimpleName();

    private static final int KEY_SIZE = 256;
    private static final String ALGORITHM_TYPE = "AES";

    private SecretKeySpec secretKeySpec;


    public String doEncrypt(String text){
        createSecretKeySpec();
        try {
            String encryptedMsg = AESCrypt.encrypt(Base64.encodeToString(secretKeySpec.getEncoded(), Base64.DEFAULT), text);
            Log.d(TAG,"test encryptedMsg = " + encryptedMsg);
            return encryptedMsg;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String doDecrypt(String text){
        //TODO
        return null;
    }

    public SecretKeySpec getRandomSecureKey() {
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance(ALGORITHM_TYPE);
            keyGen.init(KEY_SIZE);
            byte[] randomSecureKey =  keyGen.generateKey().getEncoded();
            return new SecretKeySpec(randomSecureKey, 0, 16, ALGORITHM_TYPE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createSecretKeySpec() {
        secretKeySpec = getRandomSecureKey();
    }
}


