package sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.widget.TextView
import com.gssi.`abizo-encrypt`.Encrypt
import com.gssi.`abizo-encrypt`.decrypt
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.KeyGenerator


actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android"
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sample().checkMe()
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.main_text).text = hello()

        try {
            //Get object from mp library
            val encryptor = Encrypt()
            val stringToEncrypt = "gssi"

            //Encrypted message using encryptMsg
            val encryptedMsg: String? = encryptor.encryptMsg(stringToEncrypt, BuildConfig.AES_KEY)
            Log.e("TAG", "Original text: $stringToEncrypt")
            Log.e("TAG", "this is the encrypted message: $encryptedMsg")
            Log.e("TAG", "this is the decrypted message: ${encryptedMsg?.let { encryptor.decryptMsg(it, BuildConfig.AES_KEY) }}")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }
}