package petros.efthymiou.groovy.playlist

import retrofit2.http.GET

interface PlayListAPI {

    @GET("playlists")
    suspend fun fetchAllPlayLists(): List<PlayListRaw>

}
