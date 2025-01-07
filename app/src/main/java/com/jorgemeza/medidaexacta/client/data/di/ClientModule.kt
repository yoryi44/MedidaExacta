package com.jorgemeza.medidaexacta.client.data.di

import android.content.Context
import androidx.work.WorkManager
import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.client.data.remote.IClientApi
import com.jorgemeza.medidaexacta.client.data.repository.ClientRepositoryImpl
import com.jorgemeza.medidaexacta.client.domain.repository.IClientRepository
import com.jorgemeza.medidaexacta.client.domain.usecase.AddClientUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.DeleteClientUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetAllClientMainUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetAllClientUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetClientByIdUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetClientBySearchUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.SyncClientUseCase
import com.jorgemeza.data.api.Api.BASE_URL
import com.jorgemeza.medidaexacta.db.db.MedidaExactaDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideClientRepository(clientApi: IClientApi, clientDao: ClientDao,workManager: WorkManager): IClientRepository {
        return ClientRepositoryImpl(clientApi, clientDao,workManager)
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
        return Retrofit.Builder().baseUrl(com.jorgemeza.data.api.Api.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IClientApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetAllClientUseCase(clientRepository: IClientRepository) : GetAllClientUseCase {
        return GetAllClientUseCase(clientRepository)
    }

    @Provides
    @Singleton
    fun provideGetClientById(clientRepository: IClientRepository) : GetClientByIdUseCase {
        return GetClientByIdUseCase(clientRepository)
    }

    @Provides
    @Singleton
    fun provideAddClientUseCase(clientRepository: IClientRepository) : AddClientUseCase {
        return AddClientUseCase(clientRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteClientUseCase(clientRepository: IClientRepository) : DeleteClientUseCase {
        return DeleteClientUseCase(clientRepository)
    }

    @Provides
    @Singleton
    fun provideGetClientBySearchUseCase(clientRepository: IClientRepository) : GetClientBySearchUseCase {
        return GetClientBySearchUseCase(clientRepository)
    }

    @Provides
    @Singleton
    fun providesSyncClientUseCase(clientRepository: IClientRepository) : SyncClientUseCase {
        return SyncClientUseCase(clientRepository)
    }

    @Provides
    @Singleton
    fun provideClientDao(db: MedidaExactaDataBase) = db.clientDao

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideGetAllientMainUseCase(clientRepository: IClientRepository) : GetAllClientMainUseCase {
        return GetAllClientMainUseCase(clientRepository)
    }

}