package encrypt.test.com.encryptiontext;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import encrypt.test.com.encryptiontext.crypto.CryptoManager;
import encrypt.test.com.encryptiontext.databinding.ActivityMainBinding;
import encrypt.test.com.encryptiontext.net.NetManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();


    private ActivityMainBinding binding;

    private NetManager netManager;
    private CryptoManager cryptoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        netManager = new NetManager();
        cryptoManager = new CryptoManager();

        binding.btnEncryptText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEncryptText:
                String text = binding.editText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Log.d(TAG, "Empty input");
                    return;
                }
                String encryptedText = cryptoManager.doEncrypt(text);
                if (TextUtils.isEmpty(encryptedText)) {
                    Log.d(TAG, "Empty encryption text");
                    return;
                }
                binding.resultTextView.setText(encryptedText);
                netManager.sendEncryptedString(encryptedText);

               // Log.d(TAG, cryptoManager.doDecrypt(text));
                break;
        }
    }
}
