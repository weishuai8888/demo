package com.weishuai.auth.others;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;

/**
 * @Description 外部接口调用咱们系统/iapi路径下的服务，在header中拼装上面测试类生成token、timestamp和timeout
 * 可以作为外部系统调用咱们安全认证的一种方式。这个测试类生成的token有一定的有效期，通过下面这个参数调节。
 * - 供应商带着timestamp到服务端，服务端用来做防重放验证。header的key是 timestamp
 * - 供应商带着timeout到服务端，服务端收到后进行防重放验证。header的key是 timeout，timeout单位是秒，10S <= timeout <= 3600S，建议30S。
 * - 供应商带着加密后的token到服务端，服务端收到后先解密，再校验是否过期，再和自己生成的token比对是否一致。通过后则鉴权通过。header的key是 X-Access-Token
 * @Author ws
 * @Date 2025/4/11 15:40
 */
public class AuthTest {

    public static void main(String[] args) throws JsonProcessingException {
        String secret = "%&*(IO)P)HGJKL_+0fah1548$%^&*(6";

        String token = JWTUtils.createToken(new HashMap<>(), 20, secret);
        String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM0yajvHtAtel0HRyyt1atpKqhK/1vrsm9R6k7rDFczg5hO/7pidvHC/p4Qg8qkhy7HcFo/tB+gsHvE31au0l8z+I48D/OQsVZn9Lr6aH+WCRkgAEVTztGBEMCoQmrG7bO7yfnegIcjfnX8EWJ2ijMif+ZMwHdsKxc493r+knRkpAgMBAAECgYAS1HOHNNZCzNVYhuX8APRjP8LHoa8aq6fJIc54rvpLKMA0Q8KjYqYuOogmydc6yg0lLysq3feByLq2LAeBkDr1vKaEeEu8hfaIc9+18oPiMni2zAoOO1TlNQKLKchVYl5hj+Sb/N2jlURV5Si3Bd04I8TcVIpEedR6u4FI7nBFWQJBAPDuz2lfwN4p+bR38UBHRluhfAn3akxW/vSitKpaDqW76BUwuqrMZLn6AUyMvcqEtQ0bjF3Qw+ciJznBP2FHaRcCQQDaB36GxMabQenogUKSYf+ttrTFyyfOeo7ybQPQ3M89X8+vfnCzMyXgAH5rqPYf3/pU7oneiPthk5+GXt2q5He/AkEAsCY5eJYsYYGY8iv7M36o3xd6o6LDkdrM/rjyk9XR/bQbY1rEL1nOjvZM+tWvcSCDanjosDKQ+CzNNvmlyA64swJAHlBcbzB8zKlldEZEk0W83tJAYB/W8QZpSZuOpEOPLOFdZvEVilTaN0LaUO21CsmBbL2dvaseCHsV+wDFoTUS0QJAXGnOIFcffwab6ypdmnUgKtHPbKXY37pxF4cm+2v/JtBjhWuNrb2ef5eTo7acs9PZm1LZ+QUC3Ml1NM74yOTHkA==";
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNMmo7x7QLXpdB0csrdWraSqoSv9b67JvUepO6wxXM4OYTv+6Ynbxwv6eEIPKpIcux3BaP7QfoLB7xN9WrtJfM/iOPA/zkLFWZ/S6+mh/lgkZIABFU87RgRDAqEJqxu2zu8n53oCHI351/BFidoozIn/mTMB3bCsXOPd6/pJ0ZKQIDAQAB";

        String encryptContent = RSAUtils.encrypt(token, pubKey);
        System.out.println("encryptContent: " + encryptContent);

//        String decryptContent = RSAUtils.decrypt(encryptContent, priKey);
//        System.out.println("decryptContent:" + decryptContent);

    }
}
