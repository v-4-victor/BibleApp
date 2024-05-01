package com.v4vic.bibleapp.di

import android.content.Context
import android.content.res.AssetManager
import com.v4vic.data.BibleSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Singleton
    fun providesDemoAssetManager(
        @ApplicationContext context: Context,
    ): AssetManager = context.assets

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideBibleSource(
        json: Json, assets: AssetManager, scope: CoroutineScope
    ) = BibleSource(json, assets, scope)

    @Provides
    @Singleton
    fun providesCoroutineScope(
    ): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}