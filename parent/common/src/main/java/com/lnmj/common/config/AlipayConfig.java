package com.lnmj.common.config;

/**
 * @Auther: panlin
 * @Date: 2019/6/4 12:01
 * @Description:
 */
public class AlipayConfig {
    // 商户appid
    public static String APPID = "2016091200497606";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5d/g315+zn43xW1GrRNEsak8w99cmGMaKzaoJnt8TACXEYpkSOv4NK1rCGrPc9SLe1zYYMXHUo+DKYAqlmuFL1DyQPCV5k+qIUZ3jHArahQ/G4SsWCjALmE8/5rT+htVuutiQ7ysHOtQSvwBuDj/8qgWtcgh7H+LeioYAH/nekVvUxaTG/szaxmHf4xNpybO0OQQQOulVcnv+6EiFAT7gOGgVlPrX12mksSED3MTiHobibrTrgrir+QzfAeAmQBFshK8kZAZCDj1VA2s3t9BV9F35NghZExZiKCgkA8CfA1ggbSucKHLMCADX7/nPNqAI4j9Evuh1HiU2Wo9/m9qJAgMBAAECggEABYVzxz3NDEzgreG5Ea8vxvG3P87xAbpVFsa+nCC1pVwow6rnwaot6/hxcDvZROerTRT9EEVQjTL/uyaWyo1MzbQYxXZ06ySjI8HCQIOSMyUo7hO/850EByRWn6FfnAAplG1NiysDjeiLba9v9NbJwtzQ5HPzmq69U0CucoyBJMYEAvN7kcE0UjR9hPIq/rt4utGBt90Ib4ZEnmOLrKO6OBgIPAH6yE+QZngHO8snCt56OryfXe7DVLMl4ZhYn3m8x9Wgsw9RmPOGRV9FpwHxngUB0QFzOr/RLVIUvGv4JUuKf0BCYK1/syLMoomGezBEmBd+adlZG41B5kf4r8EBMQKBgQDqLud3w4egqsSyniGVfAAtiz1ROq+Eyhb7XO2Mq/5KqnOjqVYFcIyKsV9uQrTXKdGn5D7DkWk3/eQ7E8MaE2buJ/0Z4N/5y28cV1Kjx14IilaaFeT7DuQyI2CJUUKJVaSRmmhibEay7O0RUwW+INXrNX5yXRTVtWXXNAt+SmR3FQKBgQDKv0I5cqJVEqOjyK+F8i/xMXsrPaFlyIraKo1scD/bK+UAxsGLeZ33vuiAyPprKI1I2ujxJdvtqKOBQwOyzvZ/CwEEkykpbSh7R8Fwq1GxwY/gwOdDD8lXTXEkdxME/yTOSt9wO1BQYwqx73C50OuPYeCmi5U3PWAzTT/ZvXUypQKBgCGacWRiDkgjuO3YewE0NeJTJI1PWng3d2zCPOP+mhKkuss1NT66k7AjG4p6z/A/8/GfctIlP2/lYqaHg2tudUqfOlZISltRsbPjY9xUc03Oa/bYRW8xB9WZ9fQ+UMncHC04dtoIp2xtSuc9fol1tLx3hJlsedzglqh2s19/1UKxAoGAJt8WflcpInBpaVxvspmlcXPQLpEpYY9ad8uSw8idpZK73DM/JlOR4s76Wr6trF/g/qfh42Ij0WP0vTKxAdZGUkM01cC1MpkYGtK+FrDx/8ehozC45hWfv5o8aAZgYiPc1aRrRGRK2K60fTrv/OA+WYYlgSjf2fBtgkh8Rm3Zi3UCgYBil8fx5vc0QS5KhwvBEk9NfxYI4Z3Nb5d+kgcMrMlw+wz4vUB1RZ05KSBmFFwc7hp4Z2xl5Z0B1CT9UcsMCjsT/tM/DkXL556TP/SL3qfAp13x7+r/KWvWIeBc9EYo9d0wID6Iq9PKQ/+ofIqXwndegD5bzFYSG6ckWQId3ZNI8w==";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do"/*"https://openapi.alipay.com/gateway.do"*/;
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn6pescLyY6NYlX27Y+XJWbfG2Oj3gIFXjrg0OZd7Mon+FOXdPyUaoiH59X80LBf58dK3UsEWz7A67vcGSq2H8ZziAiw0BajvSf4tMhohjKafTytDqxQBoY+nRgUZIbT+sjbx3L5M/raF0u2oOb7a6aQ1lLHlVIrKMl8R+7UTpstvLGqBM4EhDXibmfsLynZ5LWPEqqDbrZ94eq5yBplYzM5t5K27JYXMabiwao/ndHAWnWIbyCgGEDxwiV084gyunnK7UCO5kOKqsU0pJYisWL75l3Q8LqbOAy9/ZXly90ufF+2axcL979uP3t3/RrG7fC0Nyyp8Ut5vg6BfPbFFCQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
