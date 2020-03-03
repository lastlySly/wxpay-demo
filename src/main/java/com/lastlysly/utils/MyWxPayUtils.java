package com.lastlysly.utils;

import com.lastlysly.config.MyWxPayConfig;
import com.lastlysly.config.WxPayService;
import com.lastlysly.sdk.wxpay.WXPay;
import com.lastlysly.sdk.wxpay.WXPayUtil;
import com.lastlysly.view.PayRequest;
import com.lastlysly.view.PayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * @create 2019-11-06 13:46
 **/
@Component
public class MyWxPayUtils {
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private MyWxPayConfig myWxPayConfig;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 下单
     * @param request
     * @return
     * @throws Exception
     */
    public PayResponse beginPay(PayRequest request) throws Exception {
        logger.info("==============进入下单====================");
        Map<String, String> wxRequest = new HashMap<String, String>();
        wxRequest.put("body", request.getOrderName());
        wxRequest.put("out_trade_no", request.getOrderId());
        /**
         * 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
         *
         */
        wxRequest.put("device_info", "");
        /**
         * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，
         */
        wxRequest.put("fee_type", "CNY");
        wxRequest.put("total_fee", request.getOrderAmount().toString());
        wxRequest.put("spbill_create_ip", "8.8.8.8");
        wxRequest.put("notify_url", myWxPayConfig.getNotifyUrl());
        /**
         * //随机字符串，长度要求在32位以内。推荐随机数生成算法
         */
        wxRequest.put("nonce_str", UUID.randomUUID().toString().replaceAll("-",""));
        wxRequest.put("trade_type", "JSAPI");
        wxRequest.put("sign_type", "MD5");
//        wxRequest.put("openid", ""); 非必须
        String sign = WXPayUtil.generateSignature(wxRequest, myWxPayConfig.getMchKey());//后面的参数为秘钥
        wxRequest.put("sign", sign);

        logger.info("======发起微信支付下单，发送请求, request={}", wxRequest);
//        WXPay wxPay = new WXPay(myWxPayConfig,true,false);
        Map<String, String> wxReponse = wxPayService.unifiedOrder(wxRequest);
        logger.info("======请求完成, 返回值 view={}", wxReponse);
        return buildPayResponse(wxReponse);
    }

    /**
     * 返回前端页面的参数
     * @param wxReponse
     * @return
     */
    private PayResponse buildPayResponse(Map<String, String> wxReponse) throws Exception {
        logger.info("===============以下开始对返回值进行解析======");
        String returnCode = wxReponse.get("return_code");
        String returnMsg = wxReponse.get("return_msg");
        logger.info("========返回两个状态信息：returnCode:{}，return_msg:{}",returnCode,returnMsg);
        if (!"SUCCESS".equals(returnCode)) {
            //SUCCESS/FAIL
            //
            //此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
            return null;
        }
        if (!"OK".equals(returnMsg)) {
//            当return_code为FAIL时返回信息为错误原因 ，例如
//                    签名失败
//                    参数格式校验错误
            return null;
        }

        String resultCode = wxReponse.get("result_code");
        logger.info("========返回业务结果：{}",resultCode);
        if (!"SUCCESS".equals(resultCode)) {
            return null;
        }

        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String prepay_id = wxReponse.get("prepay_id");
        logger.info("=======获取预支付ID：{}",prepay_id);
        if (prepay_id == null) {
            return null;
        }
        String nonceStr = wxReponse.get("nonce_str");
        String packAge = "prepay_id=" + prepay_id;
        String signType = "MD5";

        //先构造要签名的map
        Map<String, String> map = new HashMap<>();
        map.put("appId", wxReponse.get("appid"));
        map.put("timeStamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("package", packAge);
        map.put("signType", signType);
        /**
         * // 加密串中包括 appId timeStamp nonceStr package signType 5个参数, 通过sdk WXPayUtil类加密, 注意, 此处使用  MD5加密  方式
         */
        String sign = WXPayUtil.generateSignature(map, myWxPayConfig.getMchKey());

        PayResponse payResponse = new PayResponse();
        payResponse.setAppId(wxReponse.get("appid"));
        payResponse.setTimeStamp(timeStamp);
        payResponse.setNonceStr(nonceStr);
        payResponse.setPackAge(packAge);
        payResponse.setSignType(signType);
        payResponse.setPaySign(sign);
        payResponse.setPrepay_id(prepay_id);
        logger.info("=======下单成功，返回到前端页面的参数：{}",payResponse);
        return payResponse;
    }

    /**
     * 微信回调
     * @param request
     * @return
     */
    public String payCallback(HttpServletRequest request,WxPayIService wxPayIService) {
        logger.info("【微信支付异步通知】进入微信支付异步通知");
        String resXml = "";
        try {
            //
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            logger.info("【微信支付异步通知】微信支付异步通知请求包notifyData: {}", resXml);
            String xmlBack = "";
            Map<String, String> notifyMap = null;
            try {
                logger.info("【微信支付异步通知】开始将xml转为map");
                notifyMap = WXPayUtil.xmlToMap(resXml);
                logger.info("【微信支付异步通知】开始验签，判断支付结果通知中的sign是否有效");
                if (wxPayService.isPayResultNotifySignatureValid(notifyMap)) {
                    logger.info("【微信支付异步通知】验签通过");
                    // 签名正确
                    // 进行处理。
                    // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功

                    String return_code = notifyMap.get("return_code");
                    /**
                     * 订单号
                     */
                    String out_trade_no = notifyMap.get("out_trade_no");
                    logger.info("【微信支付异步通知】获取订单号：{}", out_trade_no);
                    if (out_trade_no == null) {
                        logger.info("【微信支付异步通知】微信支付回调失败，订单号为空: {}", notifyMap);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                        return xmlBack;
                    }

                    logger.info("【微信支付异步通知】微信支付回调成功，订单号: {}，开始处理业务逻辑", notifyMap);

                    // 业务逻辑处理 ****************************
                    // 业务逻辑处理 ****************************
                    // 处理订单的service实现以下接口
                    wxPayIService.doWxPayCallBackService(notifyMap);
                    // 业务逻辑处理 ****************************

                    logger.info("【微信支付异步通知】业务逻辑处理完成，告诉微信服务器收到信息了，不要再调用回调action了");
                    xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
                    return xmlBack;
                } else {
                    logger.error("【微信支付异步通知】微信支付回调通知签名错误");
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }
            } catch (Exception e) {
                logger.error("【微信支付异步通知】微信支付回调通知失败", e);
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            return xmlBack;

        } catch (Exception e) {
            logger.error("【微信支付异步通知】微信支付回调通知失败", e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }

}


