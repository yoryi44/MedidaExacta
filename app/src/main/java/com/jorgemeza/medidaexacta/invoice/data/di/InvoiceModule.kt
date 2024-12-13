package com.jorgemeza.medidaexacta.invoice.data.di

import com.jorgemeza.medidaexacta.core.api.Api.BASE_URL
import com.jorgemeza.medidaexacta.core.db.MedidaExactaDataBase
import com.jorgemeza.medidaexacta.invoice.data.local.InvoiceDao
import com.jorgemeza.medidaexacta.invoice.data.remote.IInvoiceApi
import com.jorgemeza.medidaexacta.invoice.data.repository.InvoiceRepositoryImpl
import com.jorgemeza.medidaexacta.invoice.domain.repository.IInvoiceRepository
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GetAllInvoiceUseCase
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
object InvoiceModule {

    @Provides
    @Singleton
    fun provideInvoiceRepository(invoiceDao: InvoiceDao, invoiceApi: IInvoiceApi): IInvoiceRepository {
        return InvoiceRepositoryImpl(invoiceDao, invoiceApi)
    }

    @Provides
    @Singleton
    fun provideInvoiceApi(client: OkHttpClient) : IInvoiceApi {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IInvoiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetAllInvoiceUseCase(invoiceRepository: IInvoiceRepository): GetAllInvoiceUseCase {
        return GetAllInvoiceUseCase(invoiceRepository)
    }

    @Provides
    @Singleton
    fun provideInvoiceDao(db: MedidaExactaDataBase): InvoiceDao = db.invoiceDao


}