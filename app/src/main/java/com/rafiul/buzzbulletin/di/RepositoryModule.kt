package com.rafiul.buzzbulletin.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
//    @Singleton
//    @Provides
//    fun provideUserRepository(api: UserApi): UserRepository = UserRepositoryImpl(api)
}