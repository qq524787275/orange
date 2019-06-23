package com.zhuzichu.mvvm.base;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:29
 */
public class BaseRes<T> implements Serializable {
    private String msg;
    private int code;
    private int min_id;
    private T general_classify;
    private T data;
    private T item_info;

    private String total;

    public BaseRes() {
    }

    public BaseRes(String msg, int code, int min_id, T general_classify, T data, T item_info, String total) {
        this.msg = msg;
        this.code = code;
        this.min_id = min_id;
        this.general_classify = general_classify;
        this.data = data;
        this.item_info = item_info;
        this.total = total;
    }

    public T getItem_info() {
        return item_info;
    }

    public void setItem_info(T item_info) {
        this.item_info = item_info;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMin_id() {
        return min_id;
    }

    public void setMin_id(int min_id) {
        this.min_id = min_id;
    }

    public T getGeneral_classify() {
        return general_classify;
    }

    public void setGeneral_classify(T general_classify) {
        this.general_classify = general_classify;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
