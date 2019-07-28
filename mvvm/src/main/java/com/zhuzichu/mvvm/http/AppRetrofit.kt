package com.zhuzichu.mvvm.http;

import androidx.annotation.NonNull
import androidx.collection.SimpleArrayMap
import com.zhuzichu.mvvm.global.cache.CacheGlobal
import com.zhuzichu.mvvm.http.converter.MyGsonConverterFactory
import com.zhuzichu.mvvm.http.interceptor.HttpLoggingInterceptor
import com.zhuzichu.mvvm.utils.encryptPolicy
import okhttp3.*
import org.json.JSONObject
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

    private val retrofitMap = SimpleArrayMap<String, Retrofit>()

    private fun createRetrofit(@NonNull baseUrl: String, isJson: Boolean = true, isEncrypt: Boolean = false) {
        val timeOut = AppConfig.HTTP_TIME_OUT
        val cache = Cache(
            CacheGlobal.getHttpCacheDir(),
            AppConfig.HTTP_MAX_CACHE_SIZE
        )

        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        loggingInterceptor.setColorLevel(Level.INFO)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeOut.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(BaseInterceptor(isEncrypt))
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

        retrofitMap.put("$baseUrl-$isJson-$isEncrypt", builder.build())
    }

    fun getRetrofit(baseUrl: String, isJson: Boolean = true, isEncrypt: Boolean = true): Retrofit {
        val key = "$baseUrl-$isJson-$isEncrypt"
        if (!retrofitMap.containsKey(key)) {
            createRetrofit(baseUrl, isJson, isEncrypt)
        }
        return retrofitMap[key]!!
    }

    /**
     * 拦截器
     */
    private class BaseInterceptor(val encrypt: Boolean) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(@NonNull chain: Interceptor.Chain): Response {
            var request = chain.request()
            if (request.method() == "POST" && encrypt) {
                val body = request.body()
                if (body is FormBody) {
                    val jsonObject = JSONObject()
                    //将以前的参数添加
                    for (i in 0 until body.size()) {
                        jsonObject.put(body.encodedName(i), body.encodedValue(i))
                    }
                    val json = jsonObject.toString()
                    request = request.newBuilder().post(
                        FormBody.create(
                            MediaType.parse("application/json"),
                            encryptPolicy(json)
                        )
                    ).build()
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