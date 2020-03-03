package com.lastlysly.service;

import com.lastlysly.utils.WxPayIService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-03 10:09
 **/
@Service
public class OrderService implements WxPayIService {
    /**
     * 微信回调成功，处理业务逻辑，
     * notifyMap为回调成功微信提供的数据包
     * @param notifyMap
     */
    @Override
    public void doWxPayCallBackService(Map notifyMap) {
        System.out.println(2333);
    }
}
