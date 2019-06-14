package com.zhuzichu.mvvm.http.converter;

import com.google.gson.Gson;
import com.zhuzichu.mvvm.base.BaseRes;
import com.zhuzichu.mvvm.http.exception.ExceptionHandle;
import com.zhuzichu.mvvm.http.exception.ResponseThrowable;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:26
 */
public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    MyGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        try {
            verify(json);
            return gson.fromJson(json, type);
        } finally {
            value.close();
        }
    }

    private void verify(String json) {
        BaseRes result = gson.fromJson(json, BaseRes.class);
        int code = result.getCode();
        if (code != 1) {
            switch (code) {
                case ExceptionHandle.ERROR.PASSWORD_ERROR:
                    throw new ResponseThrowable(result.getMsg());
                    case ExceptionHandle.ERROR.NO_DATA:
                        throw new ResponseThrowable("没有更多数据");
                default:
                    throw new ResponseThrowable("不清楚什么原因！");
            }
        }
    }
}