package com.lastlysly.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.URI;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-07 13:07
 **/
@Data
public class PayResponse {

    private String prePayParams;

    private URI payUri;

    /** 以下字段仅在微信h5支付返回. */
    private String appId;

    private String timeStamp;

    private String nonceStr;

    @JsonProperty("package")
    private String packAge;

    private String signType;
    /**
     * sign
     */
    private String paySign;

    private String prepay_id;

    /** 以下字段在微信异步通知下返回. */
    private Double orderAmount;

    private String orderId;

    //第三方支付的流水号
    private String outTradeNo;
}
