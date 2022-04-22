package petros.efthymiou.groovy.playlist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val client = OkHttpClient()
////val idlingResource = OkHttp3IdlingResource.create("okhttp", client)
//
//val idlingResource = OkH
@Module
@InstallIn(SingletonComponent::class)
class PlaylistModule {

    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun playListAPI(retrofit: Retrofit): PlayListAPI = retrofit.create(PlayListAPI::class.java)
}