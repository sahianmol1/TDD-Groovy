package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.data.PlayList
import retrofit2.http.GET

interface PlayListAPI {

    @GET("playlists")
    suspend fun fetchAllPlayLists(): List<PlayList>

}
