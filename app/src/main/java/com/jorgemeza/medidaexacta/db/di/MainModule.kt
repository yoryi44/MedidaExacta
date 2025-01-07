package com.jorgemeza.medidaexacta.db.di

import android.content.Context
import androidx.room.Room
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
    fun provideMedidaExactaDataBase(@ApplicationContext context: Context) : com.jorgemeza.medidaexacta.db.db.MedidaExactaDataBase {
        return Room.databaseBuilder(context, com.jorgemeza.medidaexacta.db.db.MedidaExactaDataBase::class.java,
            com.jorgemeza.medidaexacta.db.db.DataBase.MEDIDA_EXACTA_DATABASE
        ).build()
    }

}