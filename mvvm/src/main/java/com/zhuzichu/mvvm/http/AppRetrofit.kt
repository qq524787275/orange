package com.zhuzichu.mvvm.http;

import androidx.annotation.NonNull
import com.zhuzichu.mvvm.http.converter.MyGsonConverterFactory
import com.zhuzichu.mvvm.http.interceptor.HttpLoggingInterceptor
import com.zhuzichu.mvvm.utils.getHttpImageCacheDir
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:03
 */
object AppRetrofit {

    private val retrofitMap = HashMap<String, Retrofit>()

    private fun createRetrofit(@NonNull baseUrl: String, isJson: Boolean = true) {
        val timeOut = AppConfig.HTTP_TIME_OUT
        val cache = Cache(
            getHttpImageCacheDir(),
            AppConfig.HTTP_MAX_CACHE_SIZE
        )

        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        loggingInterceptor.setColorLevel(Level.INFO)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeOut.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(BaseInterceptor())
            .addNetworkInterceptor(NetworkBaseInterceptor())
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()

        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)

        if (isJson) {
            builder.addConverterFactory(MyGsonConverterFactory.create())
        } else {
            builder.addConverterFactory(ScalarsConverterFactory.create())
        }

        retrofitMap["$baseUrl-$isJson"] = builder.build()
    }

    fun getRetrofit(baseUrl: String, isJson: Boolean = true): Retrofit {
        val key = "$baseUrl-$isJson"
        if (!retrofitMap.containsKey(key)) {
            createRetrofit(baseUrl, isJson)
        }
        return retrofitMap[key]!!
    }

    /**
     * 拦截器
     */
    private class BaseInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(@NonNull chain: Interceptor.Chain): Response {
            var request = chain.request()
            if (request.method() == "POST") {
                val body = request.body()
                if (body is FormBody) {
                    val bodyBuilder = FormBody.Builder()
                    //将以前的参数添加
                    for (i in 0 until body.size()) {
                        bodyBuilder.add(body.encodedName(i), body.encodedValue(i))
                    }
                    //添加全局参数
//                    bodyBuilder.add("sign", "015bea041fa53399019c79fc3195919c")
//                    bodyBuilder.add(
//                        "policy",
//                        "iLL57l2aXeYAWfnMQfItbVoczhdrlTDL0%252FkIVku7c6FgRbs2xLK9YY6HXpkTL3mZtO5ysJwkoyKa%250AXyE%252Fua3Ht0aGluD1cOzOmbznm13ACQwYPE9nYp0zixww1Sua933ZfZ9bE34vIcHl43k3m5DzZU0d%250A2A5zH64ZQ5MUnty%252F9ts%253D%250A"
//                    )
                    request = request.newBuilder().post(bodyBuilder.build()).build()
                }
            }

            request = request.newBuilder()
                .build()
            return chain.proceed(request)
        }
    }

    /**
     * 网络请求拦截器
     */
    private class NetworkBaseInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(@NonNull chain: Interceptor.Chain): Response {
            var request = chain.request()
            return chain.proceed(request)
        }
    }
}