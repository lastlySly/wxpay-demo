package com.lastlysly.utils;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-06 13:48
 **/
public class WxPayReq {
    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;
    /**
     * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     */
    private String totalAmount;
    /**
     * 商品的标题/交易标题/订单标题/订单关键字等。
     */
    private String subject;
    /**
     * 用户发起支付的ip
     */
    private String spbillCreateIp;
    /**
     * 微信支付类型
     * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付  H5支付-MWEB
     */
    private String tradeType;

    /**
     * 用户唯一标识
     */
    private String openId;
    /**
     * 如果上一层给了，走上一层的默认值
     */
    private String appId;
}
