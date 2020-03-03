package com.lastlysly.controller;

import com.lastlysly.config.MyWxPayConfig;
import com.lastlysly.sdk.wxpay.WXPay;
import com.lastlysly.sdk.wxpay.WXPayUtil;
import com.lastlysly.service.OrderService;
import com.lastlysly.utils.MyResult;
import com.lastlysly.utils.MyWxPayUtils;
import com.lastlysly.view.PayRequest;
import com.lastlysly.view.PayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-06 14:10
 **/
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private MyWxPayUtils myWxPayUtils;
    @Autowired
    private OrderService orderService;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/hello")
    public MyResult test1(){
        PayRequest request = new PayRequest();
        request.setOrderAmount(200D);
        request.setOrderId("订单号");
        request.setOpenid("openid");
        request.setOrderName("BiliBili大会员充值");
        PayResponse payResponse = null;
        try {
            payResponse = myWxPayUtils.beginPay(request);
        } catch (Exception e) {
            logger.error("下单异常",e);
            return new MyResult(0,"下单异常",payResponse);
        }
        return new MyResult(1,"下单完成",payResponse);
    }

    /**
     * 这是回调接口
     * @param request
     * @return
     */
    @PostMapping("/wxcallback")
    public String test2(HttpServletRequest request){

        /**
         * 在该方法下，orderService业务处理需要实现接口wxPayIService.doWxPayCallBackService
         */
        String result =myWxPayUtils.payCallback(request,orderService);
        return result;
    }

}
