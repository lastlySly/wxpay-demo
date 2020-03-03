package com.lastlysly.utils;

import java.math.BigDecimal;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-07 13:15
 **/
public class MoneyUtil {

    /**
     * 元转分
     * @param yuan
     * @return
     */
    public static Integer Yuan2Fen(Double yuan) {
        return new BigDecimal(yuan).movePointRight(2).intValue();
    }

    /**
     * 分转元
     * @param fen
     * @return
     */
    public static Double Fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2).doubleValue();
    }

}
