package com.innovitics.android.architecture.assessmentapplication.di

import android.app.Application
import androidx.room.Room
import com.innovitics.android.architecture.assessmentapplication.data.network.ApiService
import com.innovitics.android.architecture.assessmentapplication.BuildConfig
import com.innovitics.android.architecture.assessmentapplication.database.MatchesListDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val newRequest = it
                    .request()
                    .newBuilder()
                    .addHeader("X-Auth-Token", BuildConfig.API_KEY)
                    .build()
                it.proceed(newRequest)
            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(OkHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMatchesListDatabase(app: Application): MatchesListDB {
        return Room.databaseBuilder(
            app,
            MatchesListDB::class.java,
            "matches_list_saved_DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}