package com.weishuai.auth.others;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtils {
    private static final Logger log = LoggerFactory.getLogger(RSAUtils.class);
    public static final String ENCRYPT_TYPE = "RSA";

    private RSAUtils() {
    }

    public static String getKeyByPath(String filename) {
        FileReader fileReader = new FileReader(filename);
        return fileReader.readString();
    }

    public static String encrypt(String content, PublicKey publicKey) {
        try {
            RSA rsa = new RSA((PrivateKey)null, publicKey);
            return rsa.encryptBase64(content, KeyType.PublicKey);
        } catch (Exception var3) {
            Exception e = var3;
            log.info("rsa encrypt error :{}", e);
            return null;
        }
    }

    public static String encrypt(String content, String publicKey) {
        try {
            RSA rsa = new RSA((String)null, publicKey);
            return rsa.encryptBase64(content, KeyType.PublicKey);
        } catch (Exception var3) {
            Exception e = var3;
            log.info("rsa encrypt error :{}", e);
            return null;
        }
    }

    public static String decrypt(String content, PrivateKey privateKey) {
        try {
            RSA rsa = new RSA(privateKey, (PublicKey)null);
            return rsa.decryptStr(content, KeyType.PrivateKey);
        } catch (Exception var3) {
            Exception e = var3;
            log.info("rsa decrypt error :{}", e);
            return null;
        }
    }

    public static String decrypt(String content, String privateKey) {
        try {
            RSA rsa = new RSA(privateKey, (String)null);
            return rsa.decryptStr(content, KeyType.PrivateKey);
        } catch (Exception var3) {
            Exception e = var3;
            log.info("rsa decrypt error :{}", e);
            return null;
        }
    }

    /**
     * 生成RSA公私钥对
     * @return
     */
    public static RSAKeyPair generateKeyPair() {
        try {
            KeyPair pair = SecureUtil.generateKeyPair("RSA");
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            byte[] pubEncBytes = publicKey.getEncoded();
            byte[] priEncBytes = privateKey.getEncoded();
            String pubEncBase64 = Base64.encode(pubEncBytes);
            String priEncBase64 = Base64.encode(priEncBytes);
            return new RSAKeyPair(pubEncBase64, priEncBase64);
        } catch (Exception var7) {
            Exception e = var7;
            log.info("rsa generateKeyPair error :{}", e);
            return null;
        }
    }

    public static class RSAKeyPair {
        private String pubKey;
        private String priKey;

        public String getPubKey() {
            return this.pubKey;
        }

        public String getPriKey() {
            return this.priKey;
        }

        public void setPubKey(String pubKey) {
            this.pubKey = pubKey;
        }

        public void setPriKey(String priKey) {
            this.priKey = priKey;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof RSAKeyPair)) {
                return false;
            } else {
                RSAKeyPair other = (RSAKeyPair)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$pubKey = this.getPubKey();
                    Object other$pubKey = other.getPubKey();
                    if (this$pubKey == null) {
                        if (other$pubKey != null) {
                            return false;
                        }
                    } else if (!this$pubKey.equals(other$pubKey)) {
                        return false;
                    }

                    Object this$priKey = this.getPriKey();
                    Object other$priKey = other.getPriKey();
                    if (this$priKey == null) {
                        if (other$priKey != null) {
                            return false;
                        }
                    } else if (!this$priKey.equals(other$priKey)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof RSAKeyPair;
        }

        public int hashCode() {
            int result = 1;
            Object $pubKey = this.getPubKey();
            result = result * 59 + ($pubKey == null ? 43 : $pubKey.hashCode());
            Object $priKey = this.getPriKey();
            result = result * 59 + ($priKey == null ? 43 : $priKey.hashCode());
            return result;
        }

        public String toString() {
            return "RSAUtils.RSAKeyPair(pubKey=" + this.getPubKey() + ", priKey=" + this.getPriKey() + ")";
        }

        public RSAKeyPair(String pubKey, String priKey) {
            this.pubKey = pubKey;
            this.priKey = priKey;
        }
    }
}

