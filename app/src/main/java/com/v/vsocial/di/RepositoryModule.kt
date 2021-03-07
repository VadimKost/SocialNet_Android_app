package com.v.vsocial.di

import android.content.Context
import com.v.vsocial.UserProfileApi
import com.v.vsocial.repository.UserProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//class RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideUserProfileRepository(api:UserProfileApi,@ApplicationContext context:Context): UserProfileRepository=UserProfileRepository(api,context)
//}
