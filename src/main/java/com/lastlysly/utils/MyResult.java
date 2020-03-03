package com.lastlysly.utils;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-11-06 13:44
 **/
public class MyResult {
    public int code;
    public String tip;
    public Object data;

    public MyResult() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MyResult(int code, String tip, Object data) {
        super();
        this.code = code;
        this.tip = tip;
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
