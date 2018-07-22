package com.easyinvest.core

import com.google.gson.*
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private val CONNECT_TIMEOUT = 15L
private val READ_TIMEOUT = 30L
private val WRITE_TIMEOUT = 30L

object RetrofitService {

    val api: EthfinexApi by lazy {
        provideRetrofitBuilder(
            provideOkHttpClient(),
            provideConverter(
                provideGson(
                    provideDateDeserializer(),
                    provideDateSerializer()
                )
            )
        )
            //TODO .baseUrl("https://grandmother.herokuapp.com/")
            .baseUrl("http://192.168.166.101:8000/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(EthfinexApi::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .build()
    }

    private fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
    }

    private fun provideConverter(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    private fun provideDateDeserializer(): JsonDeserializer<Date> {
        val dateFormats = arrayOf("yyyy-MM-dd")

        return JsonDeserializer { json, typeOfT, context ->
            for (format in dateFormats) {
                try {
                    return@JsonDeserializer SimpleDateFormat(
                        format,
                        Locale.getDefault()
                    ).parse(json.asString)
                } catch (ignore: ParseException) {
                    //ignore, try next
                }

            }
            throw JsonParseException(
                "Unparsable date: \"" + json.getAsString() + "\". Supported formats: " + Arrays.toString(
                    dateFormats
                )
            )
        }
    }

    private fun provideDateSerializer(): JsonSerializer<Date> {
        val dateFormat = "yyyy-MM-dd'T'HH:mm:ss"

        return JsonSerializer { src, typeOfSrc, context ->
            JsonPrimitive(
                SimpleDateFormat(
                    dateFormat,
                    Locale.getDefault()
                ).format(src)
            )
        }
    }

    private fun provideGson(
        dateDeserializer: JsonDeserializer<Date>,
        dateSerializer: JsonSerializer<Date>
    ): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java, dateDeserializer)
            .registerTypeAdapter(Date::class.java, dateSerializer)
            .create()
    }
}