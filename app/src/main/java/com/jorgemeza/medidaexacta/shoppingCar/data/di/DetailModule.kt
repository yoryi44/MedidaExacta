package com.jorgemeza.medidaexacta.shoppingCar.data.di

import com.jorgemeza.medidaexacta.core.api.Api.BASE_URL
import com.jorgemeza.medidaexacta.core.db.MedidaExactaDataBase
import com.jorgemeza.medidaexacta.shoppingCar.data.local.DetailDao
import com.jorgemeza.medidaexacta.shoppingCar.data.local.IDetailApi
import com.jorgemeza.medidaexacta.shoppingCar.data.repository.DetailRepositoryImpl
import com.jorgemeza.medidaexacta.shoppingCar.domain.repository.IDetailRepository
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.AddDetailUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.DeleteDetailUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetDetailByIdUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetDetailProductByIdUseCase
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
object DetailModule {

    @Provides
    @Singleton
    fun provideDetailRepository(
        detailApi: IDetailApi,
        detailDao: DetailDao,
    ): IDetailRepository {
        return DetailRepositoryImpl(detailApi,detailDao)
    }

    @Provides
    @Singleton
    fun provideDetailApi(client: OkHttpClient): IDetailApi {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailDao(db: MedidaExactaDataBase) = db.detailDao

    @Provides
    @Singleton
    fun provideGetDetailByIdUseCase(detailRepository: IDetailRepository): GetDetailByIdUseCase {
        return GetDetailByIdUseCase(detailRepository)
    }

    @Provides
    @Singleton
    fun provideGetDetailProductByIdUseCase(detailRepository: IDetailRepository): GetDetailProductByIdUseCase {
        return GetDetailProductByIdUseCase(detailRepository)
    }

    @Provides
    @Singleton
    fun provideAddDetailUseCase(detailRepository: IDetailRepository): AddDetailUseCase {
        return AddDetailUseCase(detailRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteDetailUseCase(detailRepository: IDetailRepository): DeleteDetailUseCase {
        return DeleteDetailUseCase(detailRepository)
    }

}