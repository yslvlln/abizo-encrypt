package com.gssi.`abizo-encrypt`
//What is AES encryption?
//AES - Advanced Encryption Standard chosen by US govt
//Substitute for DES - Data Encryption Standard
//AES is a block cipher
//AES is a symmetric which means they uses the same key for encryption and decryption
//AES-128, AES-192, and AES-256. Each of these encrypts and decrypts data in chunks of 128 bits by using cryptographic keys of 128-, 192- or 256-bits
//128-bit keys = 10 rounds
//192-bit keys = 12 rounds
//256-bit keys = 14 rounds
//Each rounds corresponds to multiple processing steps
class Encrypt {

    fun encryptMsg(strToEncrypt: String, secretKey: String): String? {
        return encrypt(strToEncrypt, secretKey)
    }

    fun decryptMsg(strToDecrypt: String, secretKey: String): String? {
        return decrypt(strToDecrypt, secretKey)
    }
}