package com.lastlysly;

import com.lastlysly.config.MyWxPayConfig;
import com.lastlysly.config.WxPayService;
import com.lastlysly.sdk.wxpay.WXPay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WxpayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxpayDemoApplication.class, args);
    }

    @Bean
    public MyWxPayConfig MyWxPayConfig() {
        MyWxPayConfig MyWxPayConfig = new MyWxPayConfig();
        return MyWxPayConfig;
    }

//    @Bean
//    public WXPay wxPayService(MyWxPayConfig myWxPayConfig) {
//        try {
//            WxPayService wxPayService = new WxPayService(myWxPayConfig);
//            return wxPayService;
//        } catch (Exception e) {
//            System.out.println("============初始化自定义wxPayService失败==============");
//        }
//        return null;
//    }
}
