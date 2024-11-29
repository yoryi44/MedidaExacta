package com.jorgemeza.medidaexacta.core.di

import android.content.Context
import androidx.room.Room
import com.jorgemeza.medidaexacta.core.db.DataBase.MEDIDA_EXACTA_DATABASE
import com.jorgemeza.medidaexacta.core.db.MedidaExactaDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMedidaExactaDataBase(@ApplicationContext context: Context) : MedidaExactaDataBase {
        return Room.databaseBuilder(context, MedidaExactaDataBase::class.java,MEDIDA_EXACTA_DATABASE).build()
    }

}