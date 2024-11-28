package com.jorgemeza.medidaexacta.Client.list.data.di

import com.jorgemeza.medidaexacta.Client.list.data.remote.IClientApi
import com.jorgemeza.medidaexacta.Client.list.data.repository.ClientRepositoryImpl
import com.jorgemeza.medidaexacta.Client.list.domain.repository.IClientRepository
import com.jorgemeza.medidaexacta.Client.list.domain.usecase.getAllClientUseCase
import com.jorgemeza.medidaexacta.core.api.Api.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {

    @Provides
    @Singleton
    fun provideClientRepository(clientApi: IClientApi): IClientRepository {
        return ClientRepositoryImpl(clientApi)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Provides
    @Singleton
    fun provideClientApi(client: OkHttpClient) : IClientApi {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IClientApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetAllClientUseCase(clientRepository: IClientRepository) : getAllClientUseCase {
        return getAllClientUseCase(clientRepository)
    }


}