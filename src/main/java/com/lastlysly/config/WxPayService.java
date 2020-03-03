package com.lastlysly.config;

import com.lastlysly.sdk.wxpay.WXPay;
import com.lastlysly.sdk.wxpay.WXPayConfig;
import org.springframework.stereotype.Component;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-07 14:04
 **/
@Component
public class WxPayService extends WXPay {
    public WxPayService(WXPayConfig config) throws Exception {
        super(config);
    }
//    public WxPayService(WXPayConfig config, boolean autoReport) throws Exception {
//        super(config, autoReport);
//    }
//
//    public WxPayService(WXPayConfig config, boolean autoReport, boolean useSandbox) throws Exception {
//        super(config, autoReport, useSandbox);
//    }
//
//    public WxPayService(WXPayConfig config, String notifyUrl) throws Exception {
//        super(config, notifyUrl);
//    }
//
//    public WxPayService(WXPayConfig config, String notifyUrl, boolean autoReport) throws Exception {
//        super(config, notifyUrl, autoReport);
//    }
//
//    public WxPayService(WXPayConfig config, String notifyUrl, boolean autoReport, boolean useSandbox) throws Exception {
//        super(config, notifyUrl, autoReport, useSandbox);
//    }
}
