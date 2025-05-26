1. 对外部厂商暴漏接口的安全鉴权设计
   采用RSA非对称加密 + JWT token鉴权 + 拦截器 + 加自定义注解 + AOP 实现防重放的接口安全健全机制
   1）外部厂商通过我提供的公钥加密JWT生成的token，将token放到header中传到我的服务；
   2）token中会有创建时间和过期时间时间戳等字段
   3）我的服务在拦截器中使用AOP切面技术，切配置了注解的方法。校验header中token是否存在，token是否过期，token解密是否成功，token是否使用和token是否有效