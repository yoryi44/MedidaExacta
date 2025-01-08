package com.jorgemeza.medidaexacta.invoice.data.di

import android.content.Context
import com.jorgemeza.medidaexacta.core.api.Api.BASE_URL
import com.jorgemeza.medidaexacta.core.db.MedidaExactaDataBase
import com.jorgemeza.medidaexacta.invoice.data.local.InvoiceDao
import com.jorgemeza.medidaexacta.invoice.data.pdf.InvoicePdfGeneratorImpl
import com.jorgemeza.medidaexacta.invoice.data.remote.IInvoiceApi
import com.jorgemeza.medidaexacta.invoice.data.repository.InvoiceRepositoryImpl
import com.jorgemeza.medidaexacta.invoice.domain.pdf.IInvoicePdfGenerator
import com.jorgemeza.medidaexacta.invoice.domain.repository.IInvoiceRepository
import com.jorgemeza.medidaexacta.invoice.domain.usecase.AddInvoiceUseCase
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GenerateInvoicePdfUseCase
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GetAllInvoiceUseCase
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GetInvoiceByIdUseCase
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GetInvoiceConsecutiveUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesPdfGenerator(@ApplicationContext context: Context): IInvoicePdfGenerator {
        return InvoicePdfGeneratorImpl(context)
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
    fun provideAddInvoiceUseCase(invoiceRepository: IInvoiceRepository): AddInvoiceUseCase {
        return AddInvoiceUseCase(invoiceRepository)
    }

    @Provides
    @Singleton
    fun provideGetInvoiceConsecutiveUseCase(invoiceRepository: IInvoiceRepository): GetInvoiceConsecutiveUseCase {
        return GetInvoiceConsecutiveUseCase(invoiceRepository)
    }

    @Provides
    @Singleton
    fun provideGetInvoiceByIdUseCase(invoiceRepository: IInvoiceRepository): GetInvoiceByIdUseCase {
        return GetInvoiceByIdUseCase(invoiceRepository)
    }

    @Provides
    @Singleton
    fun provideGenerateInvoicePdfUseCase(pdfGenerator: IInvoicePdfGenerator): GenerateInvoicePdfUseCase {
        return GenerateInvoicePdfUseCase(pdfGenerator)
    }

    @Provides
    @Singleton
    fun provideInvoiceDao(db: MedidaExactaDataBase): InvoiceDao = db.invoiceDao


}