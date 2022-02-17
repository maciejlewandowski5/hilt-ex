package com.example.android.hilt.di


import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    private fun getRetrofit(
        moshi: Moshi,
        client: OkHttpClient
    ) =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://my-json-server.typicode.com/typicode/")
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        getRetrofit(moshi, client)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providePostsRetriever(retrofit: Retrofit): PostsRetriever =
        retrofit.create(PostsRetriever::class.java)


    companion object {
        const val CALL_TIMEOUT = 90L
        const val CONNECTION_TIMEOUT = 20L
        const val READ_TIMEOUT = 20L
        const val WRITE_TIMEOUT = 20L
    }
}