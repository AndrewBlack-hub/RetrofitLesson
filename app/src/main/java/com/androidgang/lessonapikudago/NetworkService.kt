package com.androidgang.lessonapikudago

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor() {

    private var mRetrofit: Retrofit

    companion object {
        private var mInstance = NetworkService()
        private const val BASE_URL: String = "https://kudago.com/"

        fun getInstance(): NetworkService {
            if (mInstance == null) {
                mInstance = NetworkService()
            }
            return mInstance
        }
    }

    init {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)//для того чтобы писать в лог

        val client: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)//Вещь, которая связывает твое приложение с интернетом

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //из json в объект
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //для работы rx внутри retrofit
            .client(client.build()) // сборка и запуск
            .build()
    }

    fun getJsonApi(): ApiService {
        return mRetrofit.create(ApiService::class.java) // применяем интерфейс ApiService к Retrofit
    }
}