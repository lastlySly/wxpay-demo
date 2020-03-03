package com.lastlysly.view;

import lombok.Data;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-07 13:10
 **/
@Data
public class PayRequest {

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 订单金额.
     */
    private Double orderAmount;

    /**
     * 订单名字.
     */
    private String orderName;

    /**
     * 微信openid
     */
    private String openid;
}
