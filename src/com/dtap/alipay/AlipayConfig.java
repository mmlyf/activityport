package com.dtap.alipay;

/**
 * 
 * @author lvgordon
 * 支付宝绑定用户的配置信息
 *
 */
public class AlipayConfig {
	 /**
     * 支付宝网关（固定）
     */
    public static final  String URL="https://openapi.alipay.com/gateway.do";

    /**
     * 授权url
     */
    public static final  String ALIPAY_URL = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";

    /**
     * 应用id
     */
    public static final  String APP_ID="2017091508748022";

    /**
     * 应用私钥
     */
    public static final  String APP_PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDjHo85Kfj5tKT+fVGrL1/K78P5udxnEEmy6/vMh41PE8iV1cdiS/pmY1H8mLMVkRijIoth+t+3rtXQWBNvifmWxv6hYuavaka3KF/kBo8veznBNhVph2IQ9QmsHCfRu/xTQTfexgQz0IH3r6/hLQuZMet0cENqTj4GZFlX8OcLJj3Qy7Na7xNGuHM3PiNeiWmFLDFlcklHr6pxYSeDQXQHB/reaDUOzGx2SSgvepORIBc+7039K+kjdyE7ZhutIA9cWieVwKgH1DtZHj31ZpySXchJZgtODdSi/+M5yUQDl/1yn4gh+qc0MNXH6nPP8LVKLH6ISSdqxixaOKCkxqU7AgMBAAECggEAMkOJHvWTDHBWM5w4ftI1OQxnKtVfWc/Y/6gKSfGV7HJMahyty6tU92PzuRtyRsud0DzM7BpBeL1QxJM5ee3nNt9DayQgOaIjlFVzI1vwRGnZFlGJ25kCsw4H3fc9OMWJykMcwkjAlVcH7HENU6BMeNL7cQGHafd28qMywqul9mlNeCduy10rWYoqxGQTqCgVF5jdF2EraDQl8Tz9aJcOIRHHszi56nSNDFFlp3NddP0CikmPP5sO2ktRVkMgHuc8iDJ1wpws2KC4s2+xMNvONEqZvb7+JRjQjLzSgdxFyfjyqEsd4uUWJ+2mzEtR7x+KbjTkrB4TPLI2PaLnLiXfgQKBgQD0ELxCYu8qhA0nsrfvg8x1TZwjrBkaVx7WQ1qfqdMfFD2xnKrZV91243OkvhVMIvm1lAj4I5aswq5pjjB3BNG9OrqFun4ctxCuxPxDzUPukZHJoMVfZbX1NPiSXiE5YceFdz7GU3aGvMH2JLN0heldgJ8ZPAdS2OA/QxH63fVRMwKBgQDuObC2iD0SlEHhyFJhY1smNVbs0c6dVfT5XLY12ntYVcZRpAJQySz0tgVYpMZ7GqffWaLQQHqTZ85NLp0IK1pnUlsvZJ2hHflS5QjnFbDjF/CZ1YRcwxWMllFRun7RDW7Q6uWE+8olCabP7gPFoq2CwKsjes4ya61ksQqavonr2QKBgCS6F/pNdPwK3wfoRvGN/chxI1vi2+wwy/0mKY75OUHmA9qkXrlSFYt8cryJth687KK8YhUlFdltel17/iMK4Jz/J25UQAIGMkZ6chOp4d0Wnr+ep9TRM25wyC8OZyWBZ0rYROQHT0C7StzFbRPNyu49GrPrapa2+BfM+fmsSOwtAoGBAIp0yTotkKFdvbTGQusLXgyk77KQdm+HZ+wOcHB4XTwIDk2g7x+Y7SIFMLJGgn1GDs+HP1OeRh+qM4M0VlxLJi3q9chjyCG+VAxTxYrZVoeGgHZIXLe1Qq8FJppDtDU4g3G1NFTIKsLAC03KXjLdy860wELMu08euhZNt4Ycu/hxAoGBALLG+9Yed2M6sddfTF+LeYz/tEhf4svKycMTUcU4tQFVaWqFgn9LbzgWajnTCPikMDC6Y0rlgidodAeS4ralYpXv1NK6ANyR6Vem06eJ+T73cDbtBtNBhEMklq7MHUlkp5K+ls3fWrwcX6Ci39WXNHfKW0FI0b8T6P7WEDuhCvwn";

    /**
     * 参数返回格式
     */
    public static final  String FORMAT="json";

    /**
     * 编码集
     */
    public static final  String CHARSET="UTF-8";

    /**
     * 支付宝公钥
     */
    public static final  String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmOS4PRgnXFcpdIUxmyGJnGZQcTUNHDQPnjWjTMT/O/VYNFd1QUP24dV9tJRNJbJ1QE1N5prkloXevUojaIFQFgarQfsrGGDIufu+zstAoHvsf1stQe3XaBREf00/rNzMWUv2H6575fKczASOiAjQdbaBM3UGKDT+ViJLbUztAdGU0V6b0AmUHh3ckS/DJvjOb80sZNE1zNsQ9TQLql0+wbhpbjOXFsfd5FmjGZf9FZrg9IMRXMOrOmlNKhhw3sETFjqNe2cg2wKYTmPofnedp7yRCIlRjeosWOm9Sim6kYZCfyhUA0PNn1NIkFzaL6uqh3foHafqIsxr6SkHmmm/JQIDAQAB";

    /**
     * 商户生成签名字符串所使用的签名算法类型
     */
    public static final  String SIGN_TYPE="RSA2";
}
