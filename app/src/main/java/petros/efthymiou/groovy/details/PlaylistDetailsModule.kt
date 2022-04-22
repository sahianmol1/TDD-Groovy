package petros.efthymiou.groovy.details

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PlaylistDetailsModule {

    @Provides
    @Singleton
    fun playlistDetailsApi(retrofit: Retrofit): PlaylistDetailsApi =
        retrofit.create(PlaylistDetailsApi::class.java)
}