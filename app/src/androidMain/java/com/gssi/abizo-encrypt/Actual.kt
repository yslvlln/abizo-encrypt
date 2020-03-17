package com.gssi.`abizo-encrypt`

import android.os.Build
import android.util.Base64
import sample.BuildConfig
import java.security.spec.KeySpec
import java.util.jar.Manifest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


actual fun encrypt(strToEncrypt: String, secretKey: String): String? {
    var factory: SecretKeyFactory? = null
    try {
        val iv = ByteArray(16)
        val ivspec = IvParameterSpec(iv)
        //PBKDF2WithHmacSHA256 is not supported before API 26.
        //PBKDF2 is the key derivation suggested by google.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        } else {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        }
        //Specifications how the key should be generated
        val spec: KeySpec = PBEKeySpec(secretKey.toCharArray(), BuildConfig.SALT.toByteArray(Charsets.UTF_8), 65536, 256)
        //Generate secret key
        val tmp: SecretKey = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.getEncoded(), "AES")
        //Set transformation
        //PKCS5Padding only means each block is strictly 8 bytes
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec)
        return Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

actual fun decrypt(strToDecrypt: String, secretKey: String): String? {
    try {
        val iv = ByteArray(16)
        val ivspec = IvParameterSpec(iv)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(secretKey.toCharArray(), BuildConfig.SALT.toByteArray(Charsets.UTF_8), 65536, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec)
        return String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
    } catch (e: Exception) {
        println("Error while decrypting: $e")
    }
    return null
}