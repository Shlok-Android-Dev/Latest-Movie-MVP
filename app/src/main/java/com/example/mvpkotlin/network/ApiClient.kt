package com.example.mvpkotlin.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

open class ApiClient {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private var retrofit: Retrofit? = null
    private val API_KEY = "152383ad3c54b80c1dc5968e66cb70a1"  // Your API Key

    // Create an interceptor for adding the API key to each request
    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url

        // Append the API key as a query parameter to the URL
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        // Modify the request with the new URL
        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        Log.d("ApiClient", "Request sent to: ${request.url}")

        chain.proceed(request)

    }

    // Logging Interceptor for debugging purposes
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Can be set to BODY, HEADERS, BASIC, or NONE
    }

    // OkHttpClient with interceptors
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)  // Add the API key interceptor
        .addInterceptor(loggingInterceptor)  // Add the logging interceptor
        .build()

    // Create and return the Retrofit client
    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)  // Use OkHttpClient with interceptors
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit

    }
}
