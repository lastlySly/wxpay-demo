package com.lastlysly.utils;

import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-03 09:54
 **/
public interface WxPayIService {
    /**
     * 微信回调成功执行的业务操作，如订单转态修改，订单入库
     * notifyMap中字段 详情请看微信支付api的支付结果通知
     * https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_7&index=8
     */
    void doWxPayCallBackService(Map notifyMap);
}
