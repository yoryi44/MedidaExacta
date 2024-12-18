package com.jorgemeza.medidaexacta.quotation.data.di

import android.content.Context
import com.jorgemeza.medidaexacta.core.api.Api.BASE_URL
import com.jorgemeza.medidaexacta.core.db.MedidaExactaDataBase
import com.jorgemeza.medidaexacta.quotation.data.local.QuotationDao
import com.jorgemeza.medidaexacta.quotation.data.pdf.QuotationPdfGeneratorImpl
import com.jorgemeza.medidaexacta.quotation.data.remote.IQuotationApi
import com.jorgemeza.medidaexacta.quotation.data.repository.QuotationRepositoryImpl
import com.jorgemeza.medidaexacta.quotation.domain.pdf.IQuotationPdfGenerator
import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import com.jorgemeza.medidaexacta.quotation.domain.usecase.AddQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.DeleteQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GenerateQuotationPdfUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationDetailUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationBySearchUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationConsecutiveUseCase
import com.jorgemeza.medidaexacta.shoppingCar.data.local.DetailDao
import com.jorgemeza.medidaexacta.shoppingCar.data.local.IDetailApi
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
object QuotationModule {

    @Provides
    @Singleton
    fun provideQuotationRepository(
        detailApi: IDetailApi,
        quotationApi: IQuotationApi,
        quotationDao: QuotationDao,
        detailDao: DetailDao
    ): IQuotationRepository {
        return QuotationRepositoryImpl(detailApi, quotationApi, quotationDao, detailDao)
    }

    @Provides
    @Singleton
    fun providePdfGenerator(@ApplicationContext context: Context): IQuotationPdfGenerator {
        return QuotationPdfGeneratorImpl(context)
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
    fun provideGetAllQuotationDetailUseCase(quotationRepository: IQuotationRepository): GetAllQuotationDetailUseCase {
        return GetAllQuotationDetailUseCase(quotationRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteQuotationUseCase(quotationRepository: IQuotationRepository): DeleteQuotationUseCase {
        return DeleteQuotationUseCase(quotationRepository)
    }

    @Provides
    @Singleton
    fun provideGeneratePdfUseCase(pdfGenerator: IQuotationPdfGenerator): GenerateQuotationPdfUseCase {
        return GenerateQuotationPdfUseCase(pdfGenerator)
    }

    @Provides
    @Singleton
    fun provideGetQuotationConsecutiveUseCase(quotationRepository: IQuotationRepository): GetQuotationConsecutiveUseCase {
        return GetQuotationConsecutiveUseCase(quotationRepository)
    }

    @Provides
    @Singleton
    fun providesGetQuotationBySearchUseCase(quotationRepository: IQuotationRepository): GetQuotationBySearchUseCase {
        return GetQuotationBySearchUseCase(quotationRepository)
    }

}