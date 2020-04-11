package com.lnmj.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * RSA签名工具类
 */
@Slf4j
public class RSAUtils {
    /**
     * 签名方式
     */
    private static final String INSTANCE="RSA";
    /**
     * 加密次数
     */
    private static final int ENCRYPTION=2048;
    /**
     * 读取key文件
     * 获取私钥
     * @param priKeyPath 私钥路径
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey readPriKey(final String priKeyPath)throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(ioReadFile(priKeyPath));
        KeyFactory kf = KeyFactory.getInstance(INSTANCE);
        return kf.generatePrivate(spec);
    }
    
    /**
     * 读取key文件
     * 获取私钥
     * @param resource 私钥路径
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey readPriKey(final Resource resource)throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(ioReadFile(resource));
        KeyFactory kf = KeyFactory.getInstance(INSTANCE);
        return kf.generatePrivate(spec);
    }
    /**
     * 读取key文件
     * 获取私钥
     * @param priKeyPath 私钥路径
     * @return String
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     *
     */
    public static String readPriKeyTS(final String priKeyPath)throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
        return  new String(Base64Utils.encode(readPriKey(priKeyPath).getEncoded()));
    }
    /**
     * 读取key文件
     * 获取私钥
     * @param resource 私钥路径
     * @return String
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     *
     */
    public static String readPriKeyTS(final Resource resource)throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
        return  new String(Base64Utils.encode(readPriKey(resource).getEncoded()));
    }

    /**
     * 读取key文件
     * 获取公钥
     * @param pubKeyPath 公钥路径
     * @return
     */
    public static PublicKey readPubKey(final String pubKeyPath) throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(ioReadFile(pubKeyPath));
        KeyFactory kf = KeyFactory.getInstance(INSTANCE);
        return kf.generatePublic(spec);
    }
    
    /**
     * 读取key文件
     * 获取公钥
     * @param resource 公钥路径
     * @return
     */
    public static PublicKey readPubKey(final Resource resource) throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(ioReadFile(resource));
        KeyFactory kf = KeyFactory.getInstance(INSTANCE);
        return kf.generatePublic(spec);
    }
    /**
     * 读取key文件
     * 获取公钥
     * @param pubKeyPath 公钥路径
     * @return String
     */
    public static String readPubKeyTs(final String pubKeyPath) throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
       return new String(Base64Utils.encode(readPubKey(pubKeyPath).getEncoded()));
    }
    
    /**
     * 读取key文件
     * 获取公钥
     * @param resource 公钥路径
     * @return String
     */
    public static String readPubKeyTs(final Resource resource) throws IOException,NoSuchAlgorithmException, InvalidKeySpecException {
       return new String(Base64Utils.encode(readPubKey(resource).getEncoded()));
    }

    /**
     * 生成公钥私钥
     * @param priKeyFileName 私钥文件名称
     * @param pubKeyFileName 公钥文件名称
     * @param password 密码
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void generateKey(String priKeyFileName,String pubKeyFileName,String password) throws IOException, NoSuchAlgorithmException {
        //初始化一个生成公钥私钥的方式
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(INSTANCE);
        //公钥私钥资源算法密码
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        //初始化密钥加密次数及密码
        keyPairGenerator.initialize(ENCRYPTION, secureRandom);
        //进行生成
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        //获取公钥
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        //生成文件
        ioWriteFile(publicKeyBytes,pubKeyFileName);
        //获取私钥
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        ioWriteFile(privateKeyBytes,priKeyFileName);
    }

    /**
     * 通过密钥文件获取私钥
     * @param keyStoreFile 密钥文件
     * @param keyStoreType 密钥文件类型
     * @param keyStorePwd 密钥文件密码
     * @param alias 密钥文件别名
     * @param keyPwd 密钥生成密码
     * @return
     */
    public static PrivateKey  getPriKey(String keyStoreFile, String keyStoreType, String keyStorePwd, String alias, String keyPwd){
        try {
            KeyStore ks;
            try (FileInputStream in = new FileInputStream(keyStoreFile)) {
                ks = KeyStore.getInstance(keyStoreType);
                ks.load(in, keyStorePwd.toCharArray());
            }
            if (!ks.containsAlias(alias)) {
                log.warn("No such alias in the keystore.");
                return null;
            }
            return (PrivateKey) ks.getKey(alias, keyPwd.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException ex) {
            log.warn("getPrivateKey failure.", ex);
            return null;
        } catch (FileNotFoundException ex) {
            log.warn("getPrivateKey failure.", ex);
            return null;
        } catch (IOException ex) {
            log.warn("getPrivateKey failure.", ex);
            return null;
        }
    }


    /**
     * 通过密钥文件获取私钥
     * @param keyStoreFile 密钥文件
     * @param keyStoreType 密钥文件类型
     * @param keyStorePwd 密钥文件密码
     * @param alias 密钥文件别名
     * @return
     */
    public static PrivateKey  getPriKey(String keyStoreFile, String keyStoreType, String keyStorePwd, String alias){
        return getPriKey(keyStoreFile, keyStoreType,  keyStorePwd, alias,keyStorePwd);
    }


    /**
     * 从密钥文件中读取公钥
     *
     * @param keyStoreFile 密钥文件
     * @param keyStoreType 密钥文件类型，例如：JKS
     * @param keyStorePwd  密钥文件访问密码
     * @param alias      别名
     * @return 公钥
     */
    public static PublicKey getPubKey(String keyStoreFile, String keyStoreType, String keyStorePwd, String alias) {
        try {
            KeyStore ks;
            try (FileInputStream in = new FileInputStream(keyStoreFile)) {
                ks = KeyStore.getInstance(keyStoreType);
                ks.load(in, keyStorePwd.toCharArray());
            }
            if (!ks.containsAlias(alias)) {
                log.warn("No such alias in the keystore.");
                return null;
            }
            Certificate cert = ks.getCertificate(alias);
            return cert.getPublicKey();
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
            log.warn("getPublicKey failure.", ex);
            return null;
        } catch (FileNotFoundException ex) {
            log.warn("getPublicKey failure.", ex);
            return null;
        } catch (IOException ex) {
            log.warn("getPublicKey failure.", ex);
            return null;
        }
    }
    /**
     * 通过密钥文件构建 公钥和私钥文件
     * @param params  keyStoreFile 密钥文件
     *                keyStoreType 密钥文件类型
     *                keyStorePwd 密钥文件密码
     *                alias 密钥文件别名
     *                keyPwd 密钥生成密码
     *                priFileName 生成私钥文件名
     *                pubFileName 生成公钥文件名
     * @return
     */
    public static void generateKey(Map<String,String> params) throws IOException{
        //获取私钥
        String keyPwd = params.get("keyPwd");
        PrivateKey privateKey = getPriKey(
                params.get("keyStoreFile"),
                params.get("keyStoreType"),
                params.get("keyStorePwd"),
                params.get("alias"),keyPwd == null?params.get("keyStorePwd"):keyPwd);
        ioWriteFile(privateKey.getEncoded(),params.get("priFileName"));
        //获取公钥
        PublicKey publicKey = getPubKey( params.get("keyStoreFile"),
                params.get("keyStoreType"),
                params.get("keyStorePwd"),
                params.get("alias"));
        ioWriteFile(publicKey.getEncoded(),params.get("pubFileName"));
    }


    /**
     * 文件写出
     * @param bytes
     * @param fileName
     * @throws IOException
     */
    private static  void ioWriteFile(final byte[] bytes,final String fileName) throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

    /**
     * 文件读取
     * @param keyPath
     * @return
     * @throws IOException
     */
    private static byte[] ioReadFile(final String keyPath) throws IOException{
        //创建文件对象
        File f = new File(keyPath);
        //创建文件流
        FileInputStream fis = new FileInputStream(f);
        //把文件流转为数据流
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int)f.length()];
        //读取文件
        dis.readFully(keyBytes);
        //关闭i流
        dis.close();
        return keyBytes;
    }

    /**
     * 文件读取
     * @param resource
     * @return
     * @throws IOException
     */
    private static byte[] ioReadFile(final Resource resource) throws IOException{
        //创建文件流
    	File f = resource.getFile();
        FileInputStream fis = new FileInputStream(f);
        //把文件流转为数据流
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int)f.length()];
        //读取文件
        dis.readFully(keyBytes);
        //关闭i流
        dis.close();
        return keyBytes;
    }

}
