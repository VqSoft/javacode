package javacode;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class MainEntry {
    
    private static final String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2[0-3]))\\:([0-5][0-9])))$";
    public static void main(String[] args) throws CertificateException {
        
        System.out.println("2018-10-02-00:00".matches(regex));
        
        try(Scanner sc = new Scanner(System.in)) {
            String inputStr = null;
            while((inputStr = sc.nextLine()).equals("exit") == false){
               System.out.printf("%s match result = %s %n", inputStr,inputStr.matches(regex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    static class Person{
        public Person(String pName, int pAge){
            this.name = pName;
            this.age = pAge;
        }
        public int age;
        public String name;
    }
    
    static class KeyStoreUtil{
        
        /**
         * 生成keystore: 进入命令行工具，运行命令  keytool -genkeypair -alias key1 -keyalg DSA -keypass 222222 -keystore ks1.keystore -storepass 111111 -storetype JCEKS
         * 察看keystore: 进入命令行工具，运行命令  keytool -list -keystore ks1.keystore -storepass 111111 -storetype JCEKS
         * 导出公钥：  keytool -export -alias key1 -file key1_pub.cer -keystore ks1.keystore -storepass 111111 -storetype JCEKS
         * @param keyStorePwd
         * @param keyStoreFilePath
         * @param keyStoreType
         * @param secretKeyAlias
         * @param secretKeyPwd
         * @return
         */
        public static SimpleEntry<String, String> getPublicAndPrivateKey(String keyStorePwd,String keyStoreFilePath,String keyStoreType,
                String secretKeyAlias,String secretKeyPwd){
            SimpleEntry<String, String> retKey= null;
            char[] keyStorePwdCharArr = keyStorePwd.toCharArray();
            char[] secretKeyPwdCharArr = secretKeyPwd.toCharArray();
            try (FileInputStream fis = new FileInputStream(keyStoreFilePath)) {
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(fis, keyStorePwdCharArr);
                
                //此处读取的是从上面命令行中创建的非对称密钥
                Certificate cert = keyStore.getCertificate(secretKeyAlias);
                
                PublicKey publicKey = cert.getPublicKey();
                
                Key privateKey = keyStore.getKey(secretKeyAlias, secretKeyPwdCharArr);
                
                retKey = new SimpleEntry<String, String>(publicKey.toString(), privateKey.toString());
            }catch (Exception e) {
                System.out.println("get.fis exception.....");
                e.printStackTrace();
            }
            
            return retKey;
        }
        
        public static String getSecretKey(String keyStorePwd,String keyStoreFilePath,String keyStoreType,
                String secretKeyAlias,String secretKeyPwd){
            String retKey = "";
            char[] keyStorePwdCharArr = keyStorePwd.toCharArray();
            try (FileInputStream fis = new FileInputStream(keyStoreFilePath)) {
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(fis, keyStorePwdCharArr);
                ProtectionParameter storePassParam = new KeyStore.PasswordProtection(keyStorePwdCharArr);
                SecretKeyEntry secretKeyEntry = (SecretKeyEntry)keyStore.getEntry(secretKeyAlias, storePassParam);
                
                SecretKey secretKey = secretKeyEntry.getSecretKey();
                
                retKey = secretKey.toString();
            }catch (Exception e) {
                System.out.println("get.fis exception.....");
                e.printStackTrace();
            }
            
            return retKey;
        }
        
        
        /**
         * 
         * @param keyStorePwd
         * @param keyStoreFilePath 
         * @param keyStoreType , 有这几种类型： 
         * JKS: Provider 是 SUN，每个JDK中带有
         * JCEKS: Provider 是 SUNJCE,jdk1.4后可以使用
         * PKCS12: 公钥加密标准，以二进制格式存储，成为PFX文件，在windows中可直接导入到密钥区，PKCS12同时保护密码和Key
         * BKS: Provider是 Bouncy Castle， 可与JKS交互，可防止证书库被不小心修改
         * UBER: keystore能被防止误改，察看及校验。
         * @param secretKeyPwd 
         * @param secretKeyAlgorithm 
         * @param secretKeyAlias 
         * @throws KeyStoreException
         */
        public static void saveSecretKey(String keyStorePwd,String keyStoreFilePath,String keyStoreType,
                String secretKeyAlias,String secretKeyPwd,String secretKeyAlgorithm) throws KeyStoreException{
            char[] keyStorePwdCharArr = keyStorePwd.toCharArray();
            byte[] secretKeyPwdBytes = secretKeyPwd.getBytes();
            try (FileInputStream fis = new FileInputStream(keyStoreFilePath)) {
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(fis, keyStorePwdCharArr);
                
                KeyStore.ProtectionParameter keystorePwdParam = new KeyStore.PasswordProtection(keyStorePwdCharArr);
                SecretKey secretKey = new SecretKeySpec(secretKeyPwdBytes,secretKeyAlgorithm);
                //secret key 就是对称密钥加密， private key 就是非对称密钥加密
                KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
                
                //如果store type 是JKS，会报 Cannot store non-PrivateKeys， 所以要使用JCEKS 来保存SecretKey，另外JCEKS更安全，对私钥保护更高。
                //JKS support KeyStore.PrivateKeyEntry 
                keyStore.setEntry(secretKeyAlias, secretKeyEntry, keystorePwdParam);
                
                try(FileOutputStream fos = new FileOutputStream(keyStoreFilePath)) {
                    keyStore.store(fos, keyStorePwdCharArr);
                    System.out.println("Key Store Success!");
                } catch (Exception e) {
                    System.out.println("fos exception.....");
                    e.printStackTrace();
                }
                
            } catch (Exception e) {
                System.out.println("fis exception.....");
                e.printStackTrace();
            }
        }
        
        public static class KeyStoreInfo{
            
            
        }
        public static boolean generateKeyStoreFile(String filePath) throws CertificateException{
            boolean ret = false;
            
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            
            X509Certificate cert = (X509Certificate)certificateFactory.generateCertificate(null);
            
            return ret;
        }
       
    }//class
    
}//main class
