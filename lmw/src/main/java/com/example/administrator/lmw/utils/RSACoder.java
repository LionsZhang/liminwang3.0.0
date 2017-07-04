package com.example.administrator.lmw.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 非对称加密算法
 *
 * @author Administrator
 */
public class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPL/UxJ7lPhH+3xndF4yr0J21MRFQI38K6hcwO1mOj6lPzIpr8N7++1+qBInHNu3Oiy46PC3tSrzJ" +
            "Ip64ZgPdaVbE/IM/2JToc5s9uCyT2boI4RaRkr7lgjs8/eQ0CIcQQY9Ua70psH9xG9MT8mwKtnnKUbKlf43RrzafWyaUgHQIDAQAB";

    public static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI8v9TEnuU+Ef7fGd0XjKvQnbUxEVAjfwrqFzA7WY6PqU/Mimvw3v77X6oEicc27c6L" +
            "Ljo8Le1KvMkinrhmA91pVsT8gz/YlOhzmz24LJPZugjhFpGSvuWCOzz95DQIhxBBj1RrvSmwf3Eb0xPybAq2ecpRsqV/jdGvNp9bJpSAdAgMBAAECgYBlmwlplR+pb" +
            "oNWvE2c2SN815F2gjIDjgzXYOQ5TyarhTeHG3JRMZot6XdtpSyOacsXpUyG8wXS2aFb7yjazio25/iZnyae3+EO83hR21w+l8wOotZXosbSC1BdWmgXQGLpdtqSU4ZSjf" +
            "qK0zc91xf3tic42HYZiqz/AEOG2tYaXQJBAPFXNSMAxiarai4PQkjdLWE4bzN41gGbp2L8HRVEylx0X3sFHmtzcRgxdzVhpImHJbnwd+X2zQmKknp9GoYOtZMCQQCX4n0F" +
            "UnAbGDbOWv8nyFa+rSOPKAmEzNkXuuM5P+vYnDPvgvZjPKMSphQXQUvvFCWEFNBehLgBFotGz7Dte2GPAkB71d+yIkeKuBI3qCS+9p6+cBK/OpW1JYVySSMzMqUTY7NKkIn" +
            "XYNj7LQaaIYwn9WzqR/V9znHPH4ehyme9ufZ5AkEAge9uyRjHc0Z5zHuogV4wZ5szKosz6Xpd1qE5NobquzqWa7Z8FlWPpKqT0Xk4/uUbWy17PQEJcVTZ/dA8Kk49HQJAPs" +
            "VWO3s7NUkx4BZpKzY6/sAChuskEoycBi08WeR4Akc6z+CB+Xol9V/38pq8O3zaQTRARU60r7TFgv44wWIk5g==";


    public static final String PUBLIC_KEY_CLIENT =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPL/UxJ7lPhH+3xndF4yr0J21MRFQI38K6hcwO1mOj6lPzIpr8N7++1+qBInHNu3Oiy46PC3tSrzJIp64ZgPdaVbE/IM/2JToc5s9uCyT2boI4RaRkr7lgjs8/eQ0CIcQQY9Ua70psH9xG9MT8mwKtnnKUbKlf43RrzafWyaUgHQIDAQAB";
    public static final String PRIVATE_KEY_CLIENT =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANZ8GQrdciNVC0gvXJdEelkfllypCic044WxUkqLQVK+JC65Ha95FQQFJ00H9gnsUDlh4osg7Pyqkd3kt9i0lsYRqFrQEgN1suVDG7XvFheL3B4tRz1Y2RnkpHaIJpSbHpQligIm4UtNIGd4X199hftd0WTFHo3tXo/zUYAgnMPZAgMBAAECgYAdUFan2n4oAA6LrhVsgyppldyaC2xc0WirNAH4ODIUWfJeBGTfP6A0rXOinozs7ERGo28v7q8VFUbcWc8aqgvVBDK42+mkYRW3ffPyJ6if8XyCdHqHOAJE8DS99naOGeOnIzpsmsPNxhFgVy6yqlRBLLYu2h0eL8QUpLTXYZDwkQJBAO64Brl8/4ltU78Ce9fk7d2EgPyOZzc3NplKOmoBZktmDnyEENhMSPgf4GQShj11Mk3bKHi1Vnul8OUGMoapuw0CQQDmAvYudiO2esUOluxN7lK/rY8LBi9ugFXVWcXQrQnxlP7C5stJ8LXLuSYzy5wWDcUef9ZBfnGUz54IcyR9gIj9AkEAy5+RrNrgpfrpen7F4IxYEaIa3FMj28du/SaB2TZEaYAuTyvfqoC3pV6bawEaHIZBWIPea8hScrpDVzgIFTHmaQJAUyzQ/z4jbfJ5Xew/qXK8I6pMZs0my8vGvi8HdI2OYfjiI3K4IpXT98aNRP+lAuiZ1oyd2vMxeYGWgTG6CVQ4tQJAOY9xcYhQo8cfTUEasa+fCJAzpmYTm5/76ctpzpzYw8PvzjJmCJnLm7ljK8cB0emEzmujJCvXN8KiPYrMYA81pQ==";


    /**
     * 公钥加密
     *
     * @param publicKey 公钥
     * @param srcBytes  内容
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(RSAPublicKey publicKey, byte[] srcBytes) throws Exception {
        if (publicKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥字串(base64格式)
     * @param srcBytes        内容
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String publicKeyString, byte[] srcBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        return RSACoder.encryptByPublicKey((RSAPublicKey) publicKey, srcBytes);
    }

    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥(base64格式)
     * @param srcString       明文内容
     * @return 密文(base64格式)
     * @throws Exception
     */
    public static String encryptByPublicKey(String publicKeyString, String srcString) throws Exception {
        byte[] srcBytes = srcString.getBytes("UTF-8");
        byte[] resultBytes = encryptByPublicKey(publicKeyString, srcBytes);
        return Base64.encode(resultBytes);
    }

    /**
     * 私钥加密
     *
     * @param privateKey 私钥
     * @param srcBytes   内容
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(RSAPrivateKey privateKey, byte[] srcBytes) throws Exception {
        if (privateKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 根据私钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param privateKeyString 私钥字串(base64格式)
     * @param srcBytes         内容
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(String privateKeyString, byte[] srcBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        return encryptByPrivateKey((RSAPrivateKey) privateKey, srcBytes);
    }

    /**
     * 私钥加密
     *
     * @param privateKeyString 私钥字串(base64格式)
     * @param srcString        明文内容
     * @param inputCharset
     * @return 密文(base64格式)
     * @throws Exception
     */
    public static String encryptByPrivateKey(String privateKeyString, String srcString, String inputCharset) throws Exception {
        byte[] srcBytes = srcString.getBytes(inputCharset);
        byte[] resultBytes = encryptByPrivateKey(privateKeyString, srcBytes);
        return Base64.encode(resultBytes);
    }


    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param srcBytes   密文
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(RSAPrivateKey privateKey, byte[] srcBytes) throws Exception {
        if (privateKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥字串(base64格式)
     * @param srcBytes         密文
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String privateKeyString, byte[] srcBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        return decryptByPrivateKey((RSAPrivateKey) privateKey, srcBytes);
    }

    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥字串(base64格式)
     * @param srcString        密文(base64格式)
     * @return 明文内容
     * @throws Exception
     */
    public static String decryptByPrivateKey(String privateKeyString, String srcString) throws Exception {
        byte[] decBytes = decryptByPrivateKey(privateKeyString, Base64.decode(srcString));
        return new String(decBytes);
    }
    /**
     * 公钥解密
     *
     * @param publicKeyString 公钥字串(base64格式)
     * @param srcString       密文(base64格式)
     * @return 明文内容
     * @throws Exception
     */
    public static String decryptByPublicKey(String publicKeyString, String srcString) throws Exception {
        byte[] decBytes = decryptByPublicKey(publicKeyString, Base64.decode(srcString));
        return new String(decBytes);
    }
    /**
     * 公钥解密
     *
     * @param publicKey 公钥
     * @param srcBytes  密文
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(RSAPublicKey publicKey, byte[] srcBytes) throws Exception {
        if (publicKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//RSA
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param publicKeyString 公钥字串(base64格式)
     * @param srcBytes        密文
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(String publicKeyString, byte[] srcBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        return decryptByPublicKey((RSAPublicKey) publicKey, srcBytes);
    }



}
