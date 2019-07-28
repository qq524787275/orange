package com.zhuzichu.mvvm.http.interceptor

import com.zhuzichu.mvvm.utils.IOUtils
import okhttp3.*
import okhttp3.Interceptor.Chain
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:09
 */
class HttpLoggingInterceptor(tag: String) : Interceptor {
    @Volatile
    private var printLevel: Level? = Level.NONE
    private var colorLevel: java.util.logging.Level? = null
    private val logger: Logger = Logger.getLogger(tag)

    enum class Level {
        NONE, //不打印log
        HEADERS, //打印请求和响应的所有 Header
        BODY        //所有数据全部打印
    }

    fun setPrintLevel(level: Level) {
        if (printLevel == null) throw NullPointerException("printLevel == null. Use Level.NONE instead.")
        printLevel = level
    }

    fun setColorLevel(level: java.util.logging.Level?) {
        colorLevel = level
    }

    private fun log(message: String) {
        colorLevel?.let {
            logger.log(it, message)
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Chain?): Response? {
        val request = chain!!.request()
        if (printLevel == Level.NONE) {
            return chain.proceed(request)
        }

        //请求日志拦截


        logForRequest(request!!, chain.connection())

        //执行请求，计算请求时间


        val startNs = System.nanoTime()
        val response: Response?
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            log("<-- HTTP FAILED: $e")
            throw e
        }
        val tookMs =
            TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        //响应日志拦截


        return logForResponse(response, tookMs)
    }

    @Throws(IOException::class)
    private fun logForRequest(request: Request, connection: Connection?) {
        val logBody = printLevel == Level.BODY
        val logHeaders =
            printLevel == Level.BODY || printLevel == Level.HEADERS
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        try {
            val requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol
            log(requestStartMessage)
            if (logHeaders) {
                if (hasRequestBody) {
                    if (requestBody!!.contentType() != null) {
                        log("\tContent-Type: " + requestBody.contentType())
                    }
                    if (requestBody.contentLength() != -1L) {
                        log("\tContent-Length: " + requestBody.contentLength())
                    }
                }
                val headers = request.headers()
                var i = 0
                val count = headers!!.size()
                while (i < count) {
                    val name = headers.name(i)
                    if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(
                            name,
                            ignoreCase = true
                        )
                    ) {
                        log("\t" + name + ": " + headers.value(i))
                    }
                    i++
                }
                log(" ")
                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody!!.contentType())) {
                        bodyToString(request)
                    } else {
                        log("\tbody: maybe [binary body], omitted!")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            log("--> END " + request.method())
        }
    }

    private fun logForResponse(response: Response, tookMs: Long): Response? {
        val builder = response.newBuilder()
        val clone = builder!!.build()
        var responseBody = clone!!.body()
        val logBody = printLevel == Level.BODY
        val logHeaders =
            printLevel == Level.BODY || printLevel == Level.HEADERS
        try {
            log("<-- " + clone.code().toString() + ' '.toString() + clone.message() + ' '.toString() + clone.request().url().toString() + " (" + tookMs.toString() + "ms）")
            if (logHeaders) {
                val headers = clone.headers()
                var i = 0
                val count = headers!!.size()
                while (i < count) {
                    log("\t" + headers.name(i) + ": " + headers.value(i))
                    i++
                }
                log(" ")
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (responseBody == null) return response
                    if (isPlaintext(responseBody.contentType())) {
                        val bytes =
                            IOUtils.toByteArray(responseBody.byteStream())
                        val contentType = responseBody.contentType()
                        val body = String(bytes, getCharset(contentType)!!)
                        log("\tbody:$body")
                        responseBody = ResponseBody.create(responseBody.contentType(), bytes)
                        return response.newBuilder().body(responseBody).build()
                    } else {
                        log("\tbody: maybe [binary body], omitted!")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            log("<-- END HTTP")
        }
        return response
    }

    private fun bodyToString(request: Request) {
        try {
            val copy = request.newBuilder().build()
            val body = copy!!.body() ?: return
            val buffer = Buffer()
            body.writeTo(buffer)
            val charset = getCharset(body.contentType())
            charset?.let {
                log("\tbody:" + buffer.readString(it))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        private fun getCharset(contentType: MediaType?): Charset? {
            var charset =
                if (contentType != null) contentType.charset(UTF8) else UTF8
            if (charset == null) charset = UTF8
            return charset
        }

        private fun isPlaintext(mediaType: MediaType?): Boolean {
            if (mediaType == null) return false
            if (mediaType.type() != null && mediaType.type() == "text") {
                return true
            }
            var subtype: String? = mediaType.subtype()
            if (subtype != null) {
                subtype = subtype.toLowerCase()
                if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains(
                        "html"
                    )
                )//
                    return true
            }
            return false
        }
    }
}