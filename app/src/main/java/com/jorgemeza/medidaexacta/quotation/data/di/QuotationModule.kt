package com.jorgemeza.medidaexacta.quotation.data.di

import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.core.api.Api.BASE_URL
import com.jorgemeza.medidaexacta.core.db.MedidaExactaDataBase
import com.jorgemeza.medidaexacta.quotation.data.local.QuotationDao
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationDetailEntity
import com.jorgemeza.medidaexacta.quotation.data.remote.IQuotationApi
import com.jorgemeza.medidaexacta.quotation.data.repository.QuotationRepositoryImpl
import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import com.jorgemeza.medidaexacta.quotation.domain.usecase.AddQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationDetailByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationDetailProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuotationModule {

    @Provides
    @Singleton
    fun provideQuotationRepository(
        quotationApi: IQuotationApi,
        clientDao: ClientDao,
        quotationDao: QuotationDao,
    ): IQuotationRepository {
        return QuotationRepositoryImpl(quotationApi, clientDao, quotationDao)
    }

    @Provides
    @Singleton
    fun provideQuotationApi(client: OkHttpClient): IQuotationApi {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IQuotationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuotationDao(db: MedidaExactaDataBase) = db.quotationDao

    @Provides
    @Singleton
    fun provideGetAllQuotationUseCase(quotationRepository: IQuotationRepository): GetAllQuotationUseCase {
        return GetAllQuotationUseCase(quotationRepository)
    }

    @Provides
    @Singleton
    fun provideGetQuotationByIdUseCase(quotationRepository: IQuotationRepository): GetQuotationByIdUseCase {
        return GetQuotationByIdUseCase(quotationRepository)
    }

    @Provides
    @Singleton
    fun provideAddQuotationUseCase(quotationRepository: IQuotationRepository): AddQuotationUseCase {
        return AddQuotationUseCase(quotationRepository)
    }

    @Provides
    @Singleton
    fun provideGetQuotationDetailByIdUseCase(quotationRepository: IQuotationRepository): GetQuotationDetailByIdUseCase {
        return GetQuotationDetailByIdUseCase(quotationRepository)
    }

    @Provides
    @Singleton
    fun provideGetQuotationDetailProductByIdUseCase(quotationRepository: IQuotationRepository): GetQuotationDetailProductByIdUseCase {
        return GetQuotationDetailProductByIdUseCase(quotationRepository)
    }



}