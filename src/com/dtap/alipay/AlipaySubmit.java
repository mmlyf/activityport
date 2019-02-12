package com.dtap.alipay;

import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.UserInfoAndBenefitQueryResult;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

import sun.util.logging.resources.logging_fr;

public class AlipaySubmit {

    /**
     * 获取AccessToken
     * @param sParaTemp
     * @return
     */
    public static  String buildRequest(Map sParaTemp){
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        System.out.println("当前的auth_code的值是:"+(String)sParaTemp.get("auth_code"));
        request.setGrantType("authorization_code");
        request.setCode((String)sParaTemp.get("auth_code"));
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            System.out.println(oauthTokenResponse.getAccessToken());
            return oauthTokenResponse.getAccessToken();
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @return
     */
    public static AlipayUserInfoShareResponse get(String accessToken){
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        try {
            AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(request, accessToken);
           return  userinfoShareResponse;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return null;
    }

}
