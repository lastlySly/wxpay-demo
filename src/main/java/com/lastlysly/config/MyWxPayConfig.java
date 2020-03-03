package com.lastlysly.config;

import com.lastlysly.sdk.wxpay.IWXPayDomain;
import com.lastlysly.sdk.wxpay.WXPayConfig;
import com.lastlysly.sdk.wxpay.WXPayConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.*;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-06 14:04
 **/
public class MyWxPayConfig extends WXPayConfig {

    /**
     * 公众号appId
     */
    @Value("${wxpay.appId}")
    private String appId;

    /**
     * 公众号appSecret
     */
    @Value("${wxpay.appSecret}")
    private String appSecret;

    /**
     * 商户号
     */
    @Value("${wxpay.mchId}")
    private String mchId;

    /**
     * 商户密钥
     */
    @Value("${wxpay.mchKey}")
    private String mchKey;

    /**
     * 商户证书路径
     */
    @Value("${wxpay.keyPath}")
    private String keyPath;

    /**
     * 证书内容
     */
    private SSLContext sslContext;

    public String getMchKey() {
        return mchKey;
    }

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    public String getAppID() {
        return this.appId;
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    @Override
    public String getMchID() {
        return this.mchId;
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    public String getKey() {
        return this.mchKey;
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream() throws IOException {

        String certPath = "/path/to/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        byte[] certData = new byte[(int) file.length()];
        certStream.read(certData);
        certStream.close();
        ByteArrayInputStream certBis = new ByteArrayInputStream(certData);
        return certBis;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    @Override
    public IWXPayDomain getWXPayDomain() {

//        return WXPayDomainSimpleImpl.instance();


        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;

    }

    /**
     * 支付完成后的异步通知地址.
     */
    @Value("${wxpay.notifyUrl}")
    private String notifyUrl;

    /**
     * 支付完成后的同步返回地址.
     */
    @Value("${wxpay.returnUrl}")
    private String returnUrl;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }
}
